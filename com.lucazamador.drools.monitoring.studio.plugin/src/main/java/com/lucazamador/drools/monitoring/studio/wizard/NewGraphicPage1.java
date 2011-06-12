package com.lucazamador.drools.monitoring.studio.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.lucazamador.drools.monitoring.studio.MonitoringMetric;
import com.lucazamador.drools.monitoring.studio.view.sorter.MonitoringMetricSorter;

public class NewGraphicPage1 extends WizardPage {

    private Composite container;
    private ListViewer availableMetricsListViewer;
    private ListViewer selectedMetricsListViewer;
    private List<MonitoringMetric> availableMetrics;
    private List<MonitoringMetric> selectedMetrics;

    public NewGraphicPage1() {
        super("New graphic");
        setTitle("Create a new Graphic");
        setImageDescriptor(ImageDescriptor.createFromFile(getClass(), "/icons/chart48.png"));
    }

    @Override
    public void createControl(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        container = new Composite(parent, SWT.NONE);
        container.setLayout(layout);

        Label label = new Label(container, SWT.NONE);
        label.setText("Available Metrics");

        label = new Label(container, SWT.NONE);
        label.setText("");

        label = new Label(container, SWT.NONE);
        label.setText("Selected Metrics");

        availableMetrics = new ArrayList<MonitoringMetric>(Arrays.asList(MonitoringMetric.values()));
        selectedMetrics = new ArrayList<MonitoringMetric>();

        availableMetricsListViewer = new ListViewer(container);
        availableMetricsListViewer.setContentProvider(createContentProvider());
        availableMetricsListViewer.setLabelProvider(createLabelProvider());
        availableMetricsListViewer.setSorter(new MonitoringMetricSorter());
        availableMetricsListViewer.setInput(availableMetrics);
        availableMetricsListViewer.getList().setLayoutData(new GridData(160, 200));

        Composite buttons = new Composite(container, SWT.NONE);
        layout = new GridLayout();
        layout.numColumns = 1;
        buttons.setLayout(layout);
        Button addMetricButton = new Button(buttons, SWT.PUSH);
        addMetricButton.setText("->");
        addMetricButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection) availableMetricsListViewer.getSelection();
                if (selection != null) {
                    Object object = selection.getFirstElement();
                    if (object != null) {
                        MonitoringMetric monitoringMetric = (MonitoringMetric) object;
                        availableMetrics.remove(monitoringMetric);
                        selectedMetrics.add(monitoringMetric);
                        availableMetricsListViewer.refresh();
                        selectedMetricsListViewer.refresh();
                        selectFirstElement(availableMetricsListViewer.getList());
                        setPageComplete(selectedMetrics.size() > 0);
                    }
                }
            }
        });

        Button removeMetricButton = new Button(buttons, SWT.PUSH);
        removeMetricButton.setText("<-");
        removeMetricButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection) selectedMetricsListViewer.getSelection();
                if (selection != null) {
                    Object object = selection.getFirstElement();
                    if (object != null) {
                        MonitoringMetric monitoringMetric = (MonitoringMetric) object;
                        selectedMetrics.remove(monitoringMetric);
                        availableMetrics.add(monitoringMetric);
                        availableMetricsListViewer.refresh();
                        selectedMetricsListViewer.refresh();
                        selectFirstElement(selectedMetricsListViewer.getList());
                        setPageComplete(selectedMetrics.size() > 0);
                    }
                }
            }
        });

        selectedMetricsListViewer = new ListViewer(container);
        selectedMetricsListViewer.setContentProvider(createContentProvider());
        selectedMetricsListViewer.setLabelProvider(createLabelProvider());
        selectedMetricsListViewer.setInput(selectedMetrics);
        selectedMetricsListViewer.setSorter(new MonitoringMetricSorter());
        selectedMetricsListViewer.getList().setLayoutData(new GridData(160, 200));

        label = new Label(container, SWT.NONE);
        label.setText("");

        setControl(container);
        setPageComplete(false);

    }

    protected void selectFirstElement(org.eclipse.swt.widgets.List list) {
        if (list.getItemCount() > 0) {
            list.select(0);
        }
    }

    private IStructuredContentProvider createContentProvider() {
        return new IStructuredContentProvider() {
            public Object[] getElements(Object inputElement) {
                @SuppressWarnings("unchecked")
                List<MonitoringMetric> metrics = (List<MonitoringMetric>) inputElement;
                return metrics.toArray();
            }

            public void dispose() {
            }

            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            }
        };
    }

    private LabelProvider createLabelProvider() {
        return new LabelProvider() {
            public Image getImage(Object element) {
                return null;
            }

            public String getText(Object element) {
                MonitoringMetric metric = (MonitoringMetric) element;
                return metric.getName();
            }
        };
    }

    @Override
    public Control getControl() {
        return container;
    }

    public List<MonitoringMetric> getSelectedMetrics() {
        return selectedMetrics;
    }

}
