package com.lucazamador.drools.monitoring.studio.action;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;

import com.lucazamador.drools.monitoring.studio.ICommandIds;
import com.lucazamador.drools.monitoring.studio.MonitoringMetric;
import com.lucazamador.drools.monitoring.studio.model.KnowledgeSession;
import com.lucazamador.drools.monitoring.studio.view.GraphicViewFactory;
import com.lucazamador.drools.monitoring.studio.wizard.NewGraphicWizard;

public class AddGraphicAction extends Action {

    private final IWorkbenchWindow window;
    private final KnowledgeSession ksession;

    public AddGraphicAction(IWorkbenchWindow window, KnowledgeSession ksession) {
        this.window = window;
        this.ksession = ksession;
        setText("Add Graphic");
        setToolTipText("Add Graphic");
        setId(ICommandIds.ADD_GRAPHIC);
        setActionDefinitionId(ICommandIds.ADD_GRAPHIC);
        setImageDescriptor(ImageDescriptor.createFromFile(getClass(), "/icons/chart.png"));
    }

    public void run() {
        if (window != null) {
            NewGraphicWizard wizard = new NewGraphicWizard();
            WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
            dialog.open();
            if (dialog.getReturnCode() == Window.CANCEL) {
                return;
            }
            List<MonitoringMetric> selectedMetrics = wizard.getSelectedMetrics();
            for (MonitoringMetric monitoringMetric : selectedMetrics) {
                System.out.println(monitoringMetric.getName());
            }
            GraphicViewFactory.openView(ksession.getParent().getId() + " - " + ksession.getId(), selectedMetrics);
        }
    }

}
