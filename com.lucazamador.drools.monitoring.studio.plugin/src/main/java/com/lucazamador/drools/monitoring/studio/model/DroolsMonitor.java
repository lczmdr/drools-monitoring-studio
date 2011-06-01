package com.lucazamador.drools.monitoring.studio.model;

import java.util.ArrayList;
import java.util.List;

public class DroolsMonitor {

    private List<MonitoringAgent> monitoringAgents = new ArrayList<MonitoringAgent>();

    public List<MonitoringAgent> getMonitoringAgents() {
        return monitoringAgents;
    }

    public void addMonitoringAgent(MonitoringAgent agent) {
        agent.setParent(this);
        this.monitoringAgents.add(agent);
    }

    public void setMonitoringAgents(List<MonitoringAgent> monitoringAgents) {
        this.monitoringAgents = monitoringAgents;
    }

}
