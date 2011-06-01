package com.lucazamador.drools.monitoring.studio.model;

import java.util.ArrayList;
import java.util.List;

public class MonitoringAgent {

    private DroolsMonitor parent;
    private String jvmId;
    private String address;
    private int port;
    private List<KnowledgeBase> knowledgeBases = new ArrayList<KnowledgeBase>();
    private List<KnowledgeSession> knowledgeSessions = new ArrayList<KnowledgeSession>();

    public DroolsMonitor getParent() {
        return parent;
    }

    public void setParent(DroolsMonitor parent) {
        this.parent = parent;
    }

    public String getJvmId() {
        return jvmId;
    }

    public void setJvmId(String jvmId) {
        this.jvmId = jvmId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<KnowledgeBase> getKnowledgeBases() {
        return knowledgeBases;
    }

    public void addKnowledgeBase(KnowledgeBase knowledgeBase) {
        knowledgeBase.setParent(this);
        this.knowledgeBases.add(knowledgeBase);
    }

    public void setKnowledgeBases(List<KnowledgeBase> knowledgeBases) {
        this.knowledgeBases = knowledgeBases;
    }

    public List<KnowledgeSession> getKnowledgeSessions() {
        return knowledgeSessions;
    }

    public void addKnowledgeSession(KnowledgeSession knowledgeSession) {
        knowledgeSession.setParent(this);
        this.knowledgeSessions.add(knowledgeSession);
    }

    public void setKnowledgeSessions(List<KnowledgeSession> knowledgeSessions) {
        this.knowledgeSessions = knowledgeSessions;
    }

}
