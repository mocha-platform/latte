package com.ebao.framework.assembly;

public interface OperationStep {
  public void perform(FlowContext flowContext) throws Exception;
}