package com.lucazamador.drools.monitoring.studio.model;

import java.util.ArrayList;
import java.util.List;

import com.lucazamador.drools.monitoring.core.DroolsMonitoringAgent;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;
import com.lucazamador.drools.monitoring.studio.console.ActivityConsoleFactory;
import com.lucazamador.drools.monitoring.studio.console.ActivityConsoleListener;

public class MonitoringAgent {

    private DroolsMonitor parent;
    private String id;
    private String address;
    private int port;
    private int scanInterval;
    private int recoveryInterval;
    private boolean connected;
    private List<KnowledgeBase> knowledgeBases = new ArrayList<KnowledgeBase>();
    private List<KnowledgeSession> knowledgeSessions = new ArrayList<KnowledgeSession>();

    public void build(DroolsMonitoringAgent monitoringAgent) {
        this.clear();
        this.connected = true;
        List<KnowledgeSessionInfo> ksessions = monitoringAgent.getDiscoveredKnowledgeSessions();
        for (KnowledgeSessionInfo ksessionInfo : ksessions) {
            KnowledgeSession ksession = new KnowledgeSession();
            ksession.setId(String.valueOf(ksessionInfo.getKnowledgeSessionId()));
            ksession.setParent(this);
            this.knowledgeSessions.add(ksession);
            ActivityConsoleListener listener = new ActivityConsoleListener(ActivityConsoleFactory.getViewId(ksession));
            monitoringAgent.registerListener(listener);
        }
        List<KnowledgeBaseInfo> kbases = monitoringAgent.getDiscoveredKnowledgeBases();
        for (KnowledgeBaseInfo kbaseInfo : kbases) {
            KnowledgeBase kbase = new KnowledgeBase();
            kbase.setParent(this);
            kbase.setId(String.valueOf(kbaseInfo.getKnowledgeBaseId()));
            this.knowledgeBases.add(kbase);
        }
    }

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

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public List<KnowledgeBase> getKnowledgeBases() {
        return knowledgeBases;
    }

    public void addKnowledgeBase(KnowledgeBase knowledgeBase) {
        this.knowledgeBases.add(knowledgeBase);
    }

    public void setKnowledgeBases(List<KnowledgeBase> knowledgeBases) {
        this.knowledgeBases = knowledgeBases;
    }

    public List<KnowledgeSession> getKnowledgeSessions() {
        return knowledgeSessions;
    }

    public void addKnowledgeSession(KnowledgeSession knowledgeSession) {
        this.knowledgeSessions.add(knowledgeSession);
    }

    public void setKnowledgeSessions(List<KnowledgeSession> knowledgeSessions) {
        this.knowledgeSessions = knowledgeSessions;
    }

    public void clear() {
        this.knowledgeBases.clear();
        this.knowledgeSessions.clear();
    }

}
