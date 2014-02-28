package com.ebao.framework.assembly.xmlconfig;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class EventConfig implements Serializable {
  private String eventId;
  private String description;
  private List<StepConfig> stepConfigs;

  public String getEventId() {
    return eventId;
  }
  public void setEventId(String eventId) {
    this.eventId = eventId;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public List<StepConfig> getStepConfigs() {
    return stepConfigs;
  }
  public void setStepConfigs(List<StepConfig> stepConfigs) {
    this.stepConfigs = stepConfigs;
  }

}
