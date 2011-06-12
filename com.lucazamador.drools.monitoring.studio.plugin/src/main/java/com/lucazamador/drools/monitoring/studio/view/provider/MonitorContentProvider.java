package com.lucazamador.drools.monitoring.studio.view.provider;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.lucazamador.drools.monitoring.studio.model.DroolsMonitor;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeSession;
import com.lucazamador.drools.monitoring.studio.model.MonitoringAgent;

public class MonitorContentProvider implements ITreeContentProvider {

    private static Object[] EMPTY_ARRAY = new Object[0];
    protected TreeViewer viewer;

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        this.viewer = (TreeViewer) viewer;
    }

    public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof DroolsMonitor) {
            DroolsMonitor droolsMonitor = (DroolsMonitor) parentElement;
            return droolsMonitor.getMonitoringAgents().toArray();
        } else if (parentElement instanceof MonitoringAgent) {
            MonitoringAgent monitoringAgent = (MonitoringAgent) parentElement;
            return concat(monitoringAgent.getKnowledgeBases().toArray(), monitoringAgent.getKnowledgeSessions()
                    .toArray());
        } else if (parentElement instanceof KnowledgeSession) {
            KnowledgeSession ksession = (KnowledgeSession) parentElement;
            return ksession.getGraphics().toArray();
        }
        return EMPTY_ARRAY;
    }

    protected Object[] concat(Object[] kbase, Object[] ksession) {
        Object[] both = new Object[kbase.length + ksession.length];
        System.arraycopy(kbase, 0, both, 0, kbase.length);
        System.arraycopy(ksession, 0, both, kbase.length, ksession.length);
        return both;
    }

    public Object getParent(Object element) {
        if (element instanceof MonitoringAgent) {
            return ((MonitoringAgent) element).getParent();
        }
        return null;
    }

    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    public void dispose() {
    }

}
