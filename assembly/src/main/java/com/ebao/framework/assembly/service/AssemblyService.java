package com.ebao.framework.assembly.service;

import com.ebao.framework.assembly.FlowContext;

public interface AssemblyService {

	public final static String SPRING_BEAN_NAME = "assembly.service.AssemblyService";
	
	public void processEvent(String eventId, FlowContext flowContext)
			throws Exception;
}
