package com.ebao.framework.assembly.xmlconfig;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class StepConfig implements Serializable {
  private String stepId;

  private Map<String, String> condition;

  public String getStepId() {
    return stepId;
  }
  public void setStepId(String stepId) {
    this.stepId = stepId;
  }
  public Map<String, String> getCondition() {
    return condition;
  }
  public void setCondition(Map<String, String> condition) {
    this.condition = condition;
  }
}
