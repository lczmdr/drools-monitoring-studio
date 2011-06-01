package com.lucazamador.drools.monitoring.studio.view;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.lucazamador.drools.monitoring.studio.Application;
import com.lucazamador.drools.monitoring.studio.console.ActivityConsoleFactory;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeSession;

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
                    }
                }
            }
        });
    }

    @Override
    public void setFocus() {

    }

    public void refresh() {
        treeViewer.setInput(Application.getDroolsMonitor());
        treeViewer.expandAll();
    }

}