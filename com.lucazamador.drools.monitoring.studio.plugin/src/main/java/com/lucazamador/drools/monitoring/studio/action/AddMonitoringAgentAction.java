package com.lucazamador.drools.monitoring.studio.action;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitoring.core.DroolsMonitoring;
import com.lucazamador.drools.monitoring.core.DroolsMonitoringAgent;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;
import com.lucazamador.drools.monitoring.studio.Application;
import com.lucazamador.drools.monitoring.studio.ICommandIds;
import com.lucazamador.drools.monitoring.studio.console.ActivityConsoleFactory;
import com.lucazamador.drools.monitoring.studio.console.ActivityConsoleListener;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeBase;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeSession;
import com.lucazamador.drools.monitoring.studio.model.MonitoringAgent;
import com.lucazamador.drools.monitoring.studio.view.NavigationView;
import com.lucazamador.drools.monitoring.studio.wizard.NewMonitoringAgentWizard;

public class AddMonitoringAgentAction extends Action {

    private final IWorkbenchWindow window;

    public AddMonitoringAgentAction(IWorkbenchWindow window, String label) {
        this.window = window;
        setText(label);
        setToolTipText(label);
        setId(ICommandIds.ADD_MONITORING_AGENT);
        setActionDefinitionId(ICommandIds.ADD_MONITORING_AGENT);
        setImageDescriptor(ImageDescriptor.createFromFile(getClass(), "/icons/add.png"));
    }

    public void run() {
        if (window != null) {
            NewMonitoringAgentWizard wizard = new NewMonitoringAgentWizard();
            WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
            dialog.open();
            if (dialog.getReturnCode() == Window.CANCEL) {
                return;
            }
            MonitoringAgentConfiguration configuration = wizard.getConfiguration();
            DroolsMonitoring droolsMonitoring = Application.getDroolsMonitoring();
            try {
                droolsMonitoring.addMonitoringAgent(configuration);
            } catch (DroolsMonitoringException e) {
                MessageDialog.openError(window.getShell(), "Error", e.getMessage());
                return;
            }
            MonitoringAgent agent = new MonitoringAgent();
            agent.setJvmId(configuration.getId());
            agent.setAddress(configuration.getAddress());
            agent.setPort(configuration.getPort());
            DroolsMonitoringAgent monitoringAgent = droolsMonitoring.getMonitoringAgent(configuration.getId());
            List<KnowledgeSessionInfo> ksessions = monitoringAgent.getDiscoveredKnowledgeSessions();
            for (KnowledgeSessionInfo ksessionInfo : ksessions) {
                KnowledgeSession ksession = new KnowledgeSession();
                ksession.setId(String.valueOf(ksessionInfo.getId()));
                agent.addKnowledgeSession(ksession);
                ActivityConsoleListener listener = new ActivityConsoleListener(
                        ActivityConsoleFactory.getViewId(ksession));
                monitoringAgent.registerListener(listener);
            }
            List<KnowledgeBaseInfo> kbases = monitoringAgent.getDiscoveredKnowledgeBases();
            for (KnowledgeBaseInfo kbaseInfo : kbases) {
                KnowledgeBase kbase = new KnowledgeBase();
                kbase.setId(String.valueOf(kbaseInfo.getId()));
                agent.addKnowledgeBase(kbase);
            }
            Application.getDroolsMonitor().addMonitoringAgent(agent);
            NavigationView navigationView = (NavigationView) window.getActivePage().findView(NavigationView.ID);
            navigationView.refresh();
        }
    }

}
