package com.lucazamador.drools.monitoring.studio.wizard;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;

import com.lucazamador.drools.monitoring.studio.MonitoringMetric;
import com.lucazamador.drools.monitoring.studio.model.Graphic;

public class NewGraphicWizard extends Wizard {

    private NewGraphicPage1 page1;
    private List<Graphic> graphics;

    public NewGraphicWizard(List<Graphic> graphics) {
        this.graphics = graphics;
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        page1 = new NewGraphicPage1(graphics);
        addPage(page1);
    }

    @Override
    public boolean performFinish() {
        return page1.isPageComplete();
    }

    public String getGraphicId() {
        return page1.getGraphicId();
    }

    public List<MonitoringMetric> getSelectedMetrics() {
        return page1.getSelectedMetrics();
    }

}
