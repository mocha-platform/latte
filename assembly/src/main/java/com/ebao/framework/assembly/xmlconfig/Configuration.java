package com.ebao.framework.assembly.xmlconfig;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Scope("singleton")
@Component(Configuration.SPRING_BEAN_NAME)
public class Configuration implements Serializable {

	private static final long serialVersionUID = 9164886576037832609L;

	public final static String SPRING_BEAN_NAME = "assembly.service.Configuration";

	protected Map<String, EventConfig> eventConfigMap = new HashMap<String, EventConfig>();
	protected Map<String, Element> eventElementMap = new HashMap<String, Element>();
	@Value("classpath*:/META-INF/gs/package/*")
	protected Resource[] assemblyResources = new Resource[0];

	public void init() throws Exception {
		try {
			eventConfigMap.clear();
			eventElementMap.clear();
	
			SAXReader reader = new SAXReader();
			List<File> assemblyRoutesConfig = this.findAssemblyRoutesConfig();
			for (File file : assemblyRoutesConfig) {
				Document document = reader.read(file);
				Element root = document.getRootElement();
				loadEventElement(root);
			}
			loadEventConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected List<File> findAssemblyRoutesConfig() throws Exception {
		List<File> results = new ArrayList<File>();
		for(Resource resource : assemblyResources) {
			results.add(resource.getFile());
		}
		return results;
	}

	protected void loadEventElement(Element parentElement) {
		for (Object oject : parentElement.elements()) {
			Element element = (Element) oject;
			String name = element.getName();
			if ("Event".equals(name)) {
				String eventId = element.attributeValue("id");
				eventElementMap.put(eventId, element);
			} else {
				loadEventElement(element);
			}
		}
	}

	protected void loadEventConfig() {
		for (Element eventElement : eventElementMap.values()) {
			EventConfig eventConfig = new EventConfig();
			String eventId = eventElement.attributeValue("id");
			eventConfig.setEventId(eventId);
			eventConfig.setDescription(eventElement
					.attributeValue("description"));
			List<StepConfig> stepConfigs = new ArrayList<StepConfig>();
			List<Element> stepElementList = eventElement.elements();
			for (Element stepElement : stepElementList) {
				stepConfigs.add(loadStepConfig(stepElement));
			}
			eventConfig.setStepConfigs(stepConfigs);
			eventConfigMap.put(eventId, eventConfig);
		}
	}

	protected StepConfig loadStepConfig(Element stepElement) {
		StepConfig stepConfig = new StepConfig();
		stepConfig.setStepId(stepElement.attributeValue("id"));
		String condition = stepElement.attributeValue("condition");
		stepConfig.setCondition(convertConditionString2Map(condition));

		return stepConfig;
	}

	protected Map<String, String> convertConditionString2Map(String condition) {
		Map<String, String> conditonMap = new HashMap<String, String>();

		if (!StringUtils.isEmpty(condition)) {
			String s = condition.replaceAll("=", "='");
			s = s.replace(",", "',");
			s = "{" + s + "'}";
			Pattern p = Pattern.compile("([^{,].*?)='(.*?[^},])'");
			Matcher m = p.matcher(s);
			while (m.find())
				conditonMap.put(m.group(1).trim(), m.group(2).trim());
		}
		return conditonMap;

	}

	public EventConfig getEventConfig(String eventId)
			throws MalformedURLException, DocumentException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		EventConfig eventConfig = (EventConfig) eventConfigMap.get(eventId);
		if (eventConfig == null) {
			throw new RuntimeException("The event (id=" + eventId
					+ ") is not found!");
		}
		return eventConfig;
	}

	public Resource[] getAssemblyResources() {
		return assemblyResources;
	}

	public void setAssemblyResources(Resource[] assemblyResources) {
		this.assemblyResources = assemblyResources;
	}

}
