package com.lucazamador.drools.monitoring.studio.view;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
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

import com.lucazamador.drools.monitoring.studio.MonitoringMetric;

public class GraphicView extends ViewPart {

    public static final String ID = "com.lucazamador.drools.monitoring.studio.view.graphicView";

    private static String DATE_PATTERN = "hh:mm:ss";

    private TimeSeriesCollection dataset = new TimeSeriesCollection();
    private TimeSeries pulseTimeSeries = new TimeSeries(".");
    private List<MonitoringMetric> metrics;

    public void createPartControl(Composite parent) {
        Composite top = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        top.setLayout(layout);

        dataset.addSeries(pulseTimeSeries);

        JFreeChart chart = createTimeSeriesChart();
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

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
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

    public void updateTimeSeriesPulseDataset(Date date, double value) {
        pulseTimeSeries.addOrUpdate(new Millisecond(date), new Double(value));
    }

    public void setViewTitle(String title) {
        this.setPartName(title);
    }

    @Override
    public void setFocus() {

    }

    public void setMetrics(List<MonitoringMetric> metrics) {
        this.metrics = metrics;
        System.out.println("metrics size: " + this.metrics.size());
    }

}