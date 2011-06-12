package com.lucazamador.drools.monitoring.studio.view;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.RectangleInsets;

import com.lucazamador.drools.monitoring.model.ksession.KnowledgeSessionMetric;
import com.lucazamador.drools.monitoring.studio.model.MonitoringMetric;

public class GraphicView extends ViewPart {

    public static final String ID = "com.lucazamador.drools.monitoring.studio.view.graphicView";

    private static String DATE_PATTERN = "hh:mm:ss";

    private IWorkbenchWindow window;
    private String ksessionId;
    private String agentId;
    private Map<MonitoringMetric, TimeSeries> timeSeries = new HashMap<MonitoringMetric, TimeSeries>();
    private TimeSeriesCollection dataset = new TimeSeriesCollection();
    private List<MonitoringMetric> metrics;
    private JFreeChart chart;
    private boolean initialized;

    public void createPartControl(Composite parent) {
        window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

        Composite top = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        top.setLayout(layout);

        chart = createTimeSeriesChart();
        ChartComposite chartComposite = new ChartComposite(top, SWT.NONE, chart, true);
        chartComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite refreshComposite = new Composite(top, SWT.NONE);
        layout = new GridLayout();
        layout.numColumns = 2;
        refreshComposite.setLayout(layout);
        Label l = new Label(refreshComposite, SWT.NONE);
        l.setText("Refresh interval (ms):");
        Spinner refreshInterval = new Spinner(refreshComposite, SWT.BORDER);
        refreshInterval.setMaximum(10000);
        refreshInterval.setSelection(1000);
        refreshInterval.setMinimum(500);
        refreshInterval.setIncrement(500);
    }

    private JFreeChart createTimeSeriesChart() {
        JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "", dataset, true, true, false);

        chart.setBackgroundPaint(Color.WHITE);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinePaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.WHITE);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat(DATE_PATTERN));

        return chart;
    }

    public void updateGraphic(final KnowledgeSessionMetric kmetric) {
        window.getShell().getDisplay().asyncExec(new Runnable() {
            @Override
            public void run() {
                if (kmetric.getAgentId().equals(agentId)
                        && kmetric.getKnowledgeSessionId().toString().equals(ksessionId)) {
                    Set<MonitoringMetric> keySet = timeSeries.keySet();
                    for (MonitoringMetric metric : keySet) {
                        TimeSeries timeSerie = timeSeries.get(metric);
                        switch (metric) {
                        case AVERATE_FIRING_TIME:
                            timeSerie.addOrUpdate(new Millisecond(kmetric.getTimestamp()),
                                    new Double(kmetric.getAverageFiringTime()));
                            break;
                        case TOTAL_ACTIVATIONS_CANCELED:
                            timeSerie.addOrUpdate(new Millisecond(kmetric.getTimestamp()),
                                    new Double(kmetric.getTotalActivationsCancelled()));
                            break;
                        case TOTAL_ACTIVATIONS_CREATED:
                            timeSerie.addOrUpdate(new Millisecond(kmetric.getTimestamp()),
                                    new Double(kmetric.getTotalActivationsCreated()));
                            break;
                        case TOTAL_ACTIVATIONS_FIRED:
                            timeSerie.addOrUpdate(new Millisecond(kmetric.getTimestamp()),
                                    new Double(kmetric.getTotalActivationsFired()));
                            break;
                        case TOTAL_FACT_COUNT:
                            timeSerie.addOrUpdate(new Millisecond(kmetric.getTimestamp()),
                                    new Double(kmetric.getTotalFactCount()));
                            break;
                        case TOTAL_FIRING_TIME:
                            timeSerie.addOrUpdate(new Millisecond(kmetric.getTimestamp()),
                                    new Double(kmetric.getTotalFiringTime()));
                            break;
                        case TOTAL_PROCESS_INSTANCES_COMPLETED:
                            timeSerie.addOrUpdate(new Millisecond(kmetric.getTimestamp()),
                                    new Double(kmetric.getTotalProcessInstancesCompleted()));
                            break;
                        case TOTAL_PROCESS_INSTANCES_STARTED:
                            timeSerie.addOrUpdate(new Millisecond(kmetric.getTimestamp()),
                                    new Double(kmetric.getTotalProcessInstancesStarted()));
                            break;
                        }
                    }
                }
            }
        });
    }

    public void setViewTitle(String title) {
        this.setPartName(title);
    }

    @Override
    public void setFocus() {

    }

    public void setMetrics(List<MonitoringMetric> metrics) {
        this.metrics = metrics;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public void setKnowledgeSessionId(String ksessionId) {
        this.ksessionId = ksessionId;
    }

    public void initialize() {
        if (initialized) {
            return;
        }
        for (MonitoringMetric metric : this.metrics) {
            TimeSeries timeSerie = new TimeSeries(metric.getDescription());
            timeSerie.setMaximumItemCount(100);
            timeSeries.put(metric, timeSerie);
            dataset.addSeries(timeSerie);
        }
        chart = createTimeSeriesChart();
        initialized = true;
    }

}