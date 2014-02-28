package com.ebao.framework.cust.test;

import org.springframework.stereotype.Component;

import com.ebao.framework.assembly.FlowContext;
import com.ebao.framework.assembly.OperationStep;

public class CustomizedPolicyDateCheckStep implements OperationStep {

	@Override
	public void perform(FlowContext flowContext) throws Exception {
		System.out.println("Run the customized PolicyDateCheckStep");
	}

}
