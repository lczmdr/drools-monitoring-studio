package com.lucazamador.drools.monitoring.studio.model;

public class KnowledgeSession {

    private String id;
    private MonitoringAgent parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MonitoringAgent getParent() {
        return parent;
    }

    public void setParent(MonitoringAgent parent) {
        this.parent = parent;
    }

}
