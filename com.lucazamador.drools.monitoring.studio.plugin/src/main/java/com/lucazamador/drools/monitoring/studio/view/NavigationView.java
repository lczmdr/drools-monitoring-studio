package com.lucazamador.drools.monitoring.studio.view;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.lucazamador.drools.monitoring.studio.model.DroolsMonitor;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeBase;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeSession;
import com.lucazamador.drools.monitoring.studio.model.MonitoringAgent;

public class NavigationView extends ViewPart {

    public static final String ID = "com.lucazamador.drools.monitoring.studio.view.navigationView";

    protected TreeViewer treeViewer;

    public void createPartControl(Composite parent) {
        parent.setLayout(new FillLayout(SWT.VERTICAL));

        treeViewer = new TreeViewer(parent, SWT.SINGLE);
        treeViewer.setContentProvider(new MonitorContentProvider());
        treeViewer.setLabelProvider(new MonitorLabelProvider());

        FillLayout fillLayout = new FillLayout(SWT.FILL);
        treeViewer.getTree().setLayout(fillLayout);

        treeViewer.setUseHashlookup(true);
        treeViewer.setInput(getInitialStructure());
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
                    System.out.println(element);
                }
            }
        });
    }

    public DroolsMonitor getInitialStructure() {
        DroolsMonitor monitor = new DroolsMonitor();
        MonitoringAgent agent1 = new MonitoringAgent();
        agent1.setJvmId("jvm1");

        KnowledgeBase kbase1 = new KnowledgeBase();
        kbase1.setId("kbase1");
        KnowledgeBase kbase2 = new KnowledgeBase();
        kbase2.setId("kbase2");
        agent1.addKnowledgeBase(kbase1);
        agent1.addKnowledgeBase(kbase2);

        KnowledgeSession ksession1 = new KnowledgeSession();
        ksession1.setId("ksession1");
        agent1.addKnowledgeSession(ksession1);

        MonitoringAgent agent2 = new MonitoringAgent();
        agent2.setJvmId("jvm2");

        monitor.addMonitoringAgent(agent1);
        monitor.addMonitoringAgent(agent2);

        return monitor;
    }

    @Override
    public void setFocus() {

    }

}