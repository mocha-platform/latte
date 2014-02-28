package com.ebao.framework.assembly;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class FlowContext implements java.io.Serializable {

  private Map<String, Object> parameters = new HashMap<String, Object>();
  private Map<String, String> conditions = new HashMap<String, String>();

  public Map<String, String> getConditions() {
    return conditions;
  }

  public void putCondition(String fieldName, String fieldValue) {
    conditions.put(fieldName, fieldValue);
  }

  public void putParameter(String parameterName, Object parameterObject) {
    parameters.put(parameterName, parameterObject);
  }

  public Object getParameter(String parameterName) {
    return parameters.get(parameterName);
  }
}