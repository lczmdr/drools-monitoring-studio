package com.lucazamador.drools.monitoring.studio.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.lucazamador.drools.monitoring.studio.view.DefaultView;
import com.lucazamador.drools.monitoring.studio.view.NavigationView;

public class StudioPerspective implements IPerspectiveFactory {

    public static final String ID = "com.lucazamador.drools.monitoring.studio.perspective.studioPerspective";

    public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout.setEditorAreaVisible(false);

        layout.addStandaloneView(NavigationView.ID, false, IPageLayout.LEFT, 0.25f, editorArea);

        IFolderLayout folder = layout.createFolder("messages", IPageLayout.TOP, 0.5f, editorArea);
        folder.addView(DefaultView.ID);
        layout.getViewLayout(DefaultView.ID).setCloseable(false);
        layout.getViewLayout(DefaultView.ID).setMoveable(false);

        layout.addPerspectiveShortcut("Drools Monitoring");

    }
}
