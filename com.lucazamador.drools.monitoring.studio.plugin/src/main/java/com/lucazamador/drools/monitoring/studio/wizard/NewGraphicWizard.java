package com.lucazamador.drools.monitoring.studio.wizard;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;

import com.lucazamador.drools.monitoring.studio.MonitoringMetric;

public class NewGraphicWizard extends Wizard {

    private NewGraphicPage1 page1;

    public NewGraphicWizard() {
        setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
        page1 = new NewGraphicPage1();
        addPage(page1);
    }

    @Override
    public boolean performFinish() {
        return page1.isPageComplete();
    }

    public List<MonitoringMetric> getSelectedMetrics() {
        return page1.getSelectedMetrics();
    }

}
