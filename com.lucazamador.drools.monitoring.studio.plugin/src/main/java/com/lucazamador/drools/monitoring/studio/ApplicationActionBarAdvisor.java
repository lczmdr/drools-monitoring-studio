package com.lucazamador.drools.monitoring.studio;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.lucazamador.drools.monitoring.studio.action.AddMonitoringAgentAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private IWorkbenchAction exitAction;
    private Action addMonitoringAgent;

    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
        addMonitoringAgent = new AddMonitoringAgentAction(window, "Add monitoring agent");
        register(addMonitoringAgent);

        exitAction = ActionFactory.QUIT.create(window);
        exitAction.setImageDescriptor(ImageDescriptor.createFromFile(getClass(), "/icons/quit.png"));
        register(exitAction);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    }

    protected void fillCoolBar(ICoolBarManager coolBar) {
        IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
        coolBar.add(new ToolBarContributionItem(toolbar, "Drools Monitoring Studio"));
        toolbar.add(addMonitoringAgent);
        toolbar.add(new Separator());
        toolbar.add(exitAction);
    }

}
