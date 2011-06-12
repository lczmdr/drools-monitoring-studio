package com.lucazamador.drools.monitoring.studio.model;

import java.util.List;

public class KnowledgeSession {

    private String id;
    private MonitoringAgent parent;
    private List<Graphic> graphics;

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

    public List<Graphic> getGraphics() {
        return graphics;
    }

    public void setGraphics(List<Graphic> graphics) {
        this.graphics = graphics;
    }

}
