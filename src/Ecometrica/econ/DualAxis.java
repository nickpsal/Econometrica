/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ecometrica.econ;

/**
 *
 * @author nickpsal
 */
import Pojos.CountryData;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;

public class DualAxis extends JFrame {
    private List<CountryData> GDPdata = new ArrayList();
    private List<CountryData> OILdata = new ArrayList();
    private final TimeSeriesCollection dataset2 = new TimeSeriesCollection();
    private final TimeSeriesCollection dataset3 = new TimeSeriesCollection();

    public DualAxis(final String title, List<CountryData> GDPdata, List<CountryData> OILdata) {
        super(title);
        this.GDPdata = GDPdata;
        this.OILdata = OILdata;
        //final String chartTitle = "";
        final XYDataset dataset = createDataset1();

        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
            title, 
            "ΗΜΕΡΟΜΗΝΙΕΣ", 
            "GDP (Πρωην LCU)",
            dataset, 
            true, 
            true, 
            false
        );
        final XYPlot plot = chart.getXYPlot();
        final NumberAxis axis2 = new NumberAxis("OIL Consumption");
        axis2.setAutoRangeIncludesZero(false);
        plot.setRangeAxis(1, axis2);
        plot.setDataset(1, createDataset2());
        plot.mapDatasetToRangeAxis(1, 1);
        final XYItemRenderer renderer = plot.getRenderer();
        renderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        if (renderer instanceof StandardXYItemRenderer) {
            final StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
            rr.setShapesFilled(true);
        }
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        NumberFormat formatter = NumberFormat.getIntegerInstance();
        rangeAxis.setNumberFormatOverride(formatter);

        final StandardXYItemRenderer renderer0 = new StandardXYItemRenderer();
        renderer0.setSeriesPaint(0, Color.black);
        renderer0.setPlotLines(true);
        renderer0.setBaseShapesVisible(true);
        renderer0.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
        plot.setRenderer(0, renderer0);
        
        if (!OILdata.isEmpty()) {
            final StandardXYItemRenderer renderer1 = new StandardXYItemRenderer();
            renderer1.setSeriesPaint(0, Color.blue);
            renderer1.setPlotLines(true);
            renderer1.setBaseShapesVisible(true);
            renderer1.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
            plot.setRenderer(1, renderer1);
        }
        
        final DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy"));
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(920, 700));
        setContentPane(chartPanel);
    }
    
    //Τιμές για τα δεδομένα GDP
    private XYDataset createDataset1() {
        final TimeSeries s1 = new TimeSeries("GDP (Current LCU) Data", Year.class);  
        for (int i = 0;i<GDPdata.size();i++) {
            s1.add(new Year(Integer.parseInt(GDPdata.get(i).getDataYear())),Float.parseFloat(GDPdata.get(i).getValue()));
        }    
        dataset2.addSeries(s1);
        return dataset2;
    }
    
    //Τιμές για τα δεδομένα OIL COncumption
    private XYDataset createDataset2() {
       if (!OILdata.isEmpty()) {
            final TimeSeries s2 = new TimeSeries("BP OIL Consumption", Year.class);  
            for (int i = 0;i<OILdata.size();i++) {
                DecimalFormat df = new DecimalFormat("##,##");
                s2.add(new Year(Integer.parseInt(OILdata.get(i).getDataYear())),Float.parseFloat(OILdata.get(i).getValue()));
            }    
            dataset3.addSeries(s2);
            return dataset3;
       }
       return null;
    }
}