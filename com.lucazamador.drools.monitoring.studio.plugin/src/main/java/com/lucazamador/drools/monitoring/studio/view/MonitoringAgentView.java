package com.lucazamador.drools.monitoring.studio.view;

import java.util.List;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.service.prefs.BackingStoreException;

import com.lucazamador.drools.monitoring.core.DroolsMonitoring;
import com.lucazamador.drools.monitoring.core.DroolsMonitoringAgent;
import com.lucazamador.drools.monitoring.exception.DroolsMonitoringException;
import com.lucazamador.drools.monitoring.model.kbase.KnowledgeBaseInfo;
import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionInfo;
import com.lucazamador.drools.monitoring.studio.Application;
import com.lucazamador.drools.monitoring.studio.action.AddGraphicAction;
import com.lucazamador.drools.monitoring.studio.action.AddMonitoringAgentAction;
import com.lucazamador.drools.monitoring.studio.action.RemoveGraphicAction;
import com.lucazamador.drools.monitoring.studio.action.RemoveMonitoringAgentAction;
import com.lucazamador.drools.monitoring.studio.cfg.ConfigurationManager;
import com.lucazamador.drools.monitoring.studio.console.ActivityConsoleFactory;
import com.lucazamador.drools.monitoring.studio.console.ActivityConsoleListener;
import com.lucazamador.drools.monitoring.studio.model.Graphic;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeBase;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeSession;
import com.lucazamador.drools.monitoring.studio.model.MonitoringAgent;
import com.lucazamador.drools.monitoring.studio.model.MonitoringAgentFactory;
import com.lucazamador.drools.monitoring.studio.view.provider.MonitorContentProvider;
import com.lucazamador.drools.monitoring.studio.view.provider.MonitorLabelProvider;

public class MonitoringAgentView extends ViewPart {

    public static final String ID = "com.lucazamador.drools.monitoring.studio.view.navigationView";

    private IWorkbenchWindow window;

    protected TreeViewer treeViewer;
    private RemoveMonitoringAgentAction removeAgentAction;

    public void createPartControl(Composite parent) {

        window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

        parent.setLayout(new FillLayout(SWT.VERTICAL));

        treeViewer = new TreeViewer(parent, SWT.SINGLE);
        treeViewer.setContentProvider(new MonitorContentProvider());
        treeViewer.setLabelProvider(new MonitorLabelProvider());

        FillLayout fillLayout = new FillLayout(SWT.FILL);
        treeViewer.getTree().setLayout(fillLayout);

        treeViewer.setUseHashlookup(true);
        treeViewer.setInput(Application.getDroolsMonitor());
        treeViewer.expandAll();
        treeViewer.addDoubleClickListener(new IDoubleClickListener() {
            @Override
            public void doubleClick(DoubleClickEvent event) {
                if (event.getSelection().isEmpty()) {
                    return;
                }
                if (event.getSelection() instanceof IStructuredSelection) {
                    IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                    Object element = selection.getFirstElement();
                    if (element instanceof KnowledgeSession) {
                        KnowledgeSession ksession = (KnowledgeSession) element;
                        String activityConsoleId = ActivityConsoleFactory.getViewId(ksession);
                        ActivityConsoleFactory.openActivityConsole(activityConsoleId);
                    } else if (element instanceof Graphic) {
                        Graphic graphic = (Graphic) element;
                        String viewId = graphic.getParent().getParent().getId() + " - " + graphic.getId();
                        GraphicViewFactory.openView(viewId, graphic.getMetrics());
                    }
                }
            }
        });
        treeViewer.getTree().addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
                Object object = selection.getFirstElement();
                if (object != null) {
                    if (object instanceof MonitoringAgent) {
                        removeAgentAction.setEnabled(true);
                        removeAgentAction.setMonitoringAgent((MonitoringAgent) object);
                    } else {
                        removeAgentAction.setEnabled(false);
                        removeAgentAction.setMonitoringAgent(null);
                    }
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });
        treeViewer.getTree().addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                removeAgentAction.setEnabled(false);
            }

            @Override
            public void focusGained(FocusEvent e) {

            }
        });

        AddMonitoringAgentAction addAgentAction = new AddMonitoringAgentAction(window, "Add Monitor Agent");
        removeAgentAction = new RemoveMonitoringAgentAction(window, "Remove Monitor Agent");
        removeAgentAction.setEnabled(false);
        getViewSite().getActionBars().getToolBarManager().add(addAgentAction);
        getViewSite().getActionBars().getToolBarManager().add(removeAgentAction);

        MenuManager menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown(true);

        menuMgr.addMenuListener(new IMenuListener() {
            public void menuAboutToShow(IMenuManager manager) {
                IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
                Object object = selection.getFirstElement();
                if (object != null) {
                    if (object instanceof KnowledgeSession) {
                        KnowledgeSession ksession = (KnowledgeSession) object;
                        manager.add(new AddGraphicAction(window, ksession));
                    } else if (object instanceof Graphic) {
                        Graphic graphic = (Graphic) object;
                        manager.add(new RemoveGraphicAction(window, graphic));
                    }
                }
            }
        });
        Menu menu = menuMgr.createContextMenu(treeViewer.getControl());
        treeViewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, treeViewer);

        treeViewer.getTree().setFocus();

        initialize();
    }

    private void initialize() {
        ConfigurationManager configurationManager = new ConfigurationManager();
        DroolsMonitoring droolsMonitoring = Application.getDroolsMonitoring();
        try {
            List<MonitoringAgent> agents = configurationManager.read();
            for (MonitoringAgent agent : agents) {
                try {
                    droolsMonitoring.addMonitoringAgent(MonitoringAgentFactory.newMonitoringAgentConfiguration(agent));
                    DroolsMonitoringAgent monitoringAgent = droolsMonitoring.getMonitoringAgent(agent.getId());
                    if (monitoringAgent.isConnected()) {
                        agent.setConnected(true);
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
                            kbase.setId(String.valueOf(kbaseInfo.getKnowledgeBaseId()));
                            agent.addKnowledgeBase(kbase);
                        }
                    }
                } catch (DroolsMonitoringException e) {
                    e.printStackTrace();
                    continue;
                }
                Application.getDroolsMonitor().addMonitoringAgent(agent);
            }
            refresh();
        } catch (BackingStoreException e) {
            MessageDialog.openError(window.getShell(), "Error", "Error reading default configuration");
        }
    }

    @Override
    public void setFocus() {

    }

    public void refresh() {
        treeViewer.refresh();
        treeViewer.expandAll();
    }

}