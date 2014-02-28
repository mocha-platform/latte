package com.ebao.framework.assembly.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebao.framework.assembly.Container;
import com.ebao.framework.assembly.FlowContext;

@Service(AssemblyService.SPRING_BEAN_NAME)
public class AssemblyServiceImpl implements AssemblyService {

	@Resource(name=Container.SPRING_BEAN_NAME)
	private Container container;
	
	public void processEvent(String eventId, FlowContext flowContext) throws Exception {
		container.init(eventId, flowContext);
		container.perform();
	}
}
