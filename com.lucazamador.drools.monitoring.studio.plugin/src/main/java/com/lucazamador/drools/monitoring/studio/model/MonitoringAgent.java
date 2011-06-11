package com.lucazamador.drools.monitoring.studio.model;

import java.util.ArrayList;
import java.util.List;

public class MonitoringAgent {

    private DroolsMonitor parent;
    private String id;
    private String address;
    private int port;
    private int scanInterval;
    private int recoveryInterval;
    private List<KnowledgeBase> knowledgeBases = new ArrayList<KnowledgeBase>();
    private List<KnowledgeSession> knowledgeSessions = new ArrayList<KnowledgeSession>();

    public DroolsMonitor getParent() {
        return parent;
    }

    public void setParent(DroolsMonitor parent) {
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(int scanInterval) {
        this.scanInterval = scanInterval;
    }

    public int getRecoveryInterval() {
        return recoveryInterval;
    }

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
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
