package com.ebao.framework.assembly.test;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.ebao.framework.assembly.FlowContext;
import com.ebao.framework.assembly.OperationStep;

@Component
public class PolicyCalculateStep implements OperationStep {

	@Override
	public void perform(FlowContext flowContext) throws Exception {
		System.out.println("Run the PolicyCalculateStep");
	}

}
