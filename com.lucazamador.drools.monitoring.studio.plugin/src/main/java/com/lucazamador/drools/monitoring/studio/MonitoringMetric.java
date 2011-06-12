package com.lucazamador.drools.monitoring.studio;

public enum MonitoringMetric {

    AVERATE_FIRING_TIME("Average Firing Time"),
    LAST_REST("Last Reset"),
    TOTAL_ACTIVATIONS_CANCELED("Total Activations Canceled"), 
    TOTAL_ACTIVATIONS_CREATED("Total Activations Created"),
    TOTAL_ACTIVATIONS_FIRED("Total Activations Fired"),
    TOTAL_FACT_COUNT("Total Fact Count"),
    TOTAL_FIRING_TIME("Total Firing Time"),
    TOTAL_PROCESS_INSTANCES_COMPLETED("Total Process Instances Completed"),
    TOTAL_PROCESS_INSTANCES_STARTED("Total Process Instances Started");

    private final String name;

    MonitoringMetric(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
