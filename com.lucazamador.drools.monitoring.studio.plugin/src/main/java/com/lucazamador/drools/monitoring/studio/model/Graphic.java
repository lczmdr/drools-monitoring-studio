package com.lucazamador.drools.monitoring.studio.model;

import java.util.List;

import com.lucazamador.drools.monitoring.studio.MonitoringMetric;

public class Graphic {

    private List<MonitoringMetric> metrics;

    public List<MonitoringMetric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<MonitoringMetric> metrics) {
        this.metrics = metrics;
    }

}
