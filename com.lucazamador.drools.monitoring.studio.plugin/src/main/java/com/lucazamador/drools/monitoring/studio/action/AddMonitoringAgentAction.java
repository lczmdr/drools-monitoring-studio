package com.lucazamador.drools.monitoring.studio.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitoring.cfg.MonitoringAgentConfiguration;
import com.lucazamador.drools.monitoring.studio.ICommandIds;
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
            System.out.println(configuration.getId());
            System.out.println(configuration.getAddress());
            System.out.println(configuration.getPort());
            System.out.println(configuration.getScanInterval());
            System.out.println(configuration.getRecoveryInterval());
        }
    }

}
