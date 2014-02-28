package com.ebao.framework.assembly.test;

import org.springframework.stereotype.Component;

import com.ebao.framework.assembly.FlowContext;
import com.ebao.framework.assembly.OperationStep;

@Component("policyDateCheckStep")
public class PolicyDateCheckStep implements OperationStep {

	@Override
	public void perform(FlowContext flowContext) throws Exception {
		System.out.println("Run the PolicyDateCheckStep");
	}

}
