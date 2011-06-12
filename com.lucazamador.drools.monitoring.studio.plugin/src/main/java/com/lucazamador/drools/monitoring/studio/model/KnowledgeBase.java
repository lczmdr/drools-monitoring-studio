package com.lucazamador.drools.monitoring.studio.model;

import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseMetric;

public class KnowledgeBase {

    private String id;
    private MonitoringAgent parent;
    private KnowledgeBaseMetric lastMetric;

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

    public KnowledgeBaseMetric getLastMetric() {
        return lastMetric;
    }

    public void setLastMetric(KnowledgeBaseMetric lastMetric) {
        this.lastMetric = lastMetric;
    }

}
