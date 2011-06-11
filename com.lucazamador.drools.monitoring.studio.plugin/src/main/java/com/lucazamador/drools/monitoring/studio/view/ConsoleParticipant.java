package com.lucazamador.drools.monitoring.studio.view;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsolePageParticipant;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IPageSite;

import com.lucazamador.drools.monitoring.studio.action.AddMonitoringAgentAction;

public class ConsoleParticipant implements IConsolePageParticipant {

    @SuppressWarnings("rawtypes")
    public Object getAdapter(Class adapter) {
        return null;
    }

    @Override
    public void init(IPageBookViewPage page, IConsole console) {
        IPageSite pageSite = page.getSite();
        IWorkbenchPage workbenchPage = pageSite.getPage();
        IViewPart viewPart = workbenchPage.findView(IConsoleConstants.ID_CONSOLE_VIEW);
        IViewSite viewSite = viewPart.getViewSite();
        IActionBars actionBars = viewSite.getActionBars();
        IToolBarManager toolBarManager = actionBars.getToolBarManager();
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        AddMonitoringAgentAction action = new AddMonitoringAgentAction(window, "test");
        toolBarManager.appendToGroup("outputGroup", action);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void activated() {

    }

    @Override
    public void deactivated() {

    }

}
