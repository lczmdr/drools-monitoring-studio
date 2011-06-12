package com.lucazamador.drools.monitoring.studio.model;

import java.util.List;

import com.lucazamador.drools.monitoring.studio.MonitoringMetric;

public class Graphic {

    private String id;
    private List<MonitoringMetric> metrics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MonitoringMetric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<MonitoringMetric> metrics) {
        this.metrics = metrics;
    }

}
