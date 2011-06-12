package com.lucazamador.drools.monitoring.studio.recovery;

import java.util.List;

import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitoring.core.DroolsMonitoringAgent;
import com.lucazamador.drools.monitoring.listener.MonitoringRecoveryListener;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;
import com.lucazamador.drools.monitoring.studio.Application;
import com.lucazamador.drools.monitoring.studio.console.ActivityConsoleFactory;
import com.lucazamador.drools.monitoring.studio.console.ActivityConsoleListener;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeBase;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeSession;
import com.lucazamador.drools.monitoring.studio.model.MonitoringAgent;
import com.lucazamador.drools.monitoring.studio.view.MonitoringAgentView;

public class RecoveryStudioListener implements MonitoringRecoveryListener {

    private final IWorkbenchWindow window;

    public RecoveryStudioListener(IWorkbenchWindow window) {
        this.window = window;
    }

    @Override
    public void reconnected(final String agentId) {
        DroolsMonitoringAgent monitoringAgent = Application.getDroolsMonitoring().getMonitoringAgent(agentId);
        MonitoringAgent agent = Application.getDroolsMonitor().getMonitoringAgent(agentId);
        agent.setConnected(true);
        agent.clear();
        List<KnowledgeSessionInfo> ksessions = monitoringAgent.getDiscoveredKnowledgeSessions();
        for (KnowledgeSessionInfo ksessionInfo : ksessions) {
            KnowledgeSession ksession = new KnowledgeSession();
            ksession.setId(String.valueOf(ksessionInfo.getId()));
            agent.addKnowledgeSession(ksession);
            ActivityConsoleListener listener = new ActivityConsoleListener(ActivityConsoleFactory.getViewId(ksession));
            monitoringAgent.registerListener(listener);
        }
        List<KnowledgeBaseInfo> kbases = monitoringAgent.getDiscoveredKnowledgeBases();
        for (KnowledgeBaseInfo kbaseInfo : kbases) {
            KnowledgeBase kbase = new KnowledgeBase();
            kbase.setId(String.valueOf(kbaseInfo.getId()));
            agent.addKnowledgeBase(kbase);
        }
        refreshMonitoringAgents();
    }

    @Override
    public void disconnected(String agentId) {
        MonitoringAgent agent = Application.getDroolsMonitor().getMonitoringAgent(agentId);
        if (agent != null) {
            agent.setConnected(false);
            agent.clear();
            refreshMonitoringAgents();
        }
    }

    private void refreshMonitoringAgents() {
        window.getShell().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                MonitoringAgentView navigationView = (MonitoringAgentView) window.getActivePage().findView(
                        MonitoringAgentView.ID);
                navigationView.refresh();
            }
        });
    }

}
