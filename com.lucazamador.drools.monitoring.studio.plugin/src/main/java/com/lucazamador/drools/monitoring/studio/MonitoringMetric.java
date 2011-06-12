package com.lucazamador.drools.monitoring.studio;

public enum MonitoringMetric {

    AVERATE_FIRING_TIME("Average Firing Time"), TOTAL_ACTIVATIONS_CREATED("Total Activations Created"), TOTAL_ACTIVATIONS_CANCELED(
            "Total Activations Canceled"), TOTAL_ACTIVATIONS_FIRED("Total Activations Fired");

    private final String name;

    MonitoringMetric(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
