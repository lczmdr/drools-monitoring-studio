package com.lucazamador.drools.monitoring.studio.view;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.lucazamador.drools.monitoring.studio.model.Graphic;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeSession;
import com.lucazamador.drools.monitoring.studio.model.MonitoringMetric;

public class GraphicViewFactory {

    public static void openView(Graphic graphic, List<MonitoringMetric> selectedMetrics) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        try {
            KnowledgeSession ksession = graphic.getParent();
            String viewId = ksession.getParent().getId() + " - " + ksession.getId() + " - " + graphic.getId();
            GraphicView graphicView = (GraphicView) window.getActivePage().showView(GraphicView.ID, viewId,
                    IWorkbenchPage.VIEW_ACTIVATE);
            graphicView.setViewTitle(viewId);
            graphicView.setMetrics(selectedMetrics);
            graphicView.setAgentId(ksession.getParent().getId());
            graphicView.setKnowledgeSessionId(ksession.getId());
            graphicView.initialize();
        } catch (PartInitException e) {
            MessageDialog.openError(window.getShell(), "Error", "Error opening graphic view");
        }
    }

}
