package com.lucazamador.drools.monitoring.studio.model;

import java.util.List;


public class Graphic {

    private String id;
    private KnowledgeSession parent;
    private List<MonitoringMetric> metrics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KnowledgeSession getParent() {
        return parent;
    }

    public void setParent(KnowledgeSession parent) {
        this.parent = parent;
    }

    public List<MonitoringMetric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<MonitoringMetric> metrics) {
        this.metrics = metrics;
    }

}
