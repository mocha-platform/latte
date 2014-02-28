package com.ebao.framework.assembly;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ebao.framework.assembly.xmlconfig.Configuration;
import com.ebao.framework.assembly.xmlconfig.EventConfig;
import com.ebao.framework.assembly.xmlconfig.StepConfig;

@Scope("singleton")
@Component(Container.SPRING_BEAN_NAME)
public class Container {
	
	public final static String SPRING_BEAN_NAME = "assembly.service.Container";
	
	@Resource(name=Configuration.SPRING_BEAN_NAME)
	private Configuration configuration;
	private EventConfig eventConfig;
	private FlowContext flowContext;

	public void init(String eventId, FlowContext flowContext) throws Exception {
		configuration.init();
		this.eventConfig = configuration.getEventConfig(eventId);
		this.flowContext = flowContext;
	}

	public void perform() throws Exception {
		for (StepConfig stepConfig : eventConfig.getStepConfigs()) {
			if (isTheStepRequired(stepConfig)) {
				// if (Log.isDebugEnabled(this.getClass())) {
				// Log.debug(this.getClass(), "The executing step is ["
				// + stepConfig.getStepId() + "] in event of ["
				// + eventConfig.getEventId() + "]");
				// }
				OperationStep step = (OperationStep) BeanFactory
						.getBean(stepConfig.getStepId());
				step.perform(flowContext);
			}
		}
	}

	protected boolean isTheStepRequired(StepConfig stepConfig) {
		boolean isRequired = true;
		Map<String, String> stepCondition = flowContext.getConditions();
		Map<String, String> stepConfigCondition = stepConfig.getCondition();

		for (String conditionKey : stepConfigCondition.keySet()) {
			String conditionConfigValue = stepConfigCondition.get(conditionKey);
			if (conditionConfigValue != null
					&& !conditionConfigValue.equals(stepCondition
							.get(conditionKey))) {
				isRequired = false;
				break;
			}
		}
		return isRequired;
	}

	public EventConfig getEventConfig() {
		return eventConfig;
	}

	public void setEventConfig(EventConfig eventConfig) {
		this.eventConfig = eventConfig;
	}

	public FlowContext getFlowContext() {
		return flowContext;
	}

	public void setFlowContext(FlowContext flowContext) {
		this.flowContext = flowContext;
	}

}