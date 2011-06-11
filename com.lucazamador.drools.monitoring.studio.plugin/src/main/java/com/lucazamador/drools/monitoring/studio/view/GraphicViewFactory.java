package com.lucazamador.drools.monitoring.studio.view;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class GraphicViewFactory {

    public static void openView(String viewId) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        try {
            GraphicView graphicView = (GraphicView) window.getActivePage().showView(GraphicView.ID, viewId,
                    IWorkbenchPage.VIEW_ACTIVATE);
            graphicView.setViewTitle(viewId);
        } catch (PartInitException e) {
            MessageDialog.openError(window.getShell(), "Error", "Error opening graphic view");
        }
    }

}
