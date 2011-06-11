package com.lucazamador.drools.monitoring.studio.view;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.lucazamador.drools.monitoring.studio.model.DroolsMonitor;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeBase;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeSession;
import com.lucazamador.drools.monitoring.studio.model.MonitoringAgent;

public class MonitorLabelProvider extends LabelProvider {

    private ImageRegistry imageRegistry = new ImageRegistry();

    public MonitorLabelProvider() {
        imageRegistry
                .put("agent", ImageDescriptor.createFromFile(MonitorLabelProvider.class, "/icons/drools_logo.png"));
        imageRegistry.put("kbase", ImageDescriptor.createFromFile(MonitorLabelProvider.class, "/icons/kbase.png"));
        imageRegistry.put("ksession", ImageDescriptor.createFromFile(getClass(), "/icons/ksession.png"));
    }

    public Image getImage(Object element) {
        if (element instanceof DroolsMonitor) {
            return imageRegistry.get("monitor");
        } else if (element instanceof MonitoringAgent) {
            return imageRegistry.get("agent");
        } else if (element instanceof KnowledgeBase) {
            return imageRegistry.get("kbase");
        } else if (element instanceof KnowledgeSession) {
            return imageRegistry.get("ksession");
        }
        return null;
    }

    public String getText(Object element) {
        if (element instanceof DroolsMonitor) {
            return "Drools Monitoring";
        } else if (element instanceof MonitoringAgent) {
            MonitoringAgent agent = (MonitoringAgent) element;
            return agent.getId() + " (" + agent.getAddress() + ":" + agent.getPort() + ")";
        } else if (element instanceof KnowledgeBase) {
            return ((KnowledgeBase) element).getId();
        } else if (element instanceof KnowledgeSession) {
            return ((KnowledgeSession) element).getId();
        }
        return null;
    }

    public void dispose() {
        imageRegistry.dispose();
    }

}
