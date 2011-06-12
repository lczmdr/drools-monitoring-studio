package com.lucazamador.drools.monitoring.studio.recovery;

import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitoring.core.DroolsMonitoringAgent;
import com.lucazamador.drools.monitoring.listener.MonitoringRecoveryListener;
import com.lucazamador.drools.monitoring.studio.Application;
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
        agent.build(monitoringAgent);
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
