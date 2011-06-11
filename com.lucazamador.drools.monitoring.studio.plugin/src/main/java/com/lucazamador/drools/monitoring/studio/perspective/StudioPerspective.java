package com.lucazamador.drools.monitoring.studio.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;

import com.lucazamador.drools.monitoring.studio.view.GraphicView;
import com.lucazamador.drools.monitoring.studio.view.MonitoringAgentView;

public class StudioPerspective implements IPerspectiveFactory {

    public static final String ID = "com.lucazamador.drools.monitoring.studio.perspective.studioPerspective";

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);

        layout.addStandaloneView(MonitoringAgentView.ID, true, IPageLayout.LEFT, 0.25f, editorArea);

        IFolderLayout folder = layout.createFolder("views", IPageLayout.TOP, 0.5f, editorArea);
        folder.addPlaceholder(GraphicView.ID + ":*");
        layout.getViewLayout(MonitoringAgentView.ID).setCloseable(false);

        IFolderLayout consoleFolder = layout.createFolder("console", IPageLayout.BOTTOM, 0.70f, "views");
        consoleFolder.addView(IConsoleConstants.ID_CONSOLE_VIEW);

        layout.addPerspectiveShortcut("Drools Monitoring");

    }
}
