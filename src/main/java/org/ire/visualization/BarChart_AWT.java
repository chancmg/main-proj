package org.ire.visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.axis.CategoryAxis;
import java.io.*;
import java.util.*;

public class BarChart_AWT extends ApplicationFrame
{
   public BarChart_AWT( String applicationTitle , String chartTitle ) throws IOException
   {
      super( applicationTitle );        
      final JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Feature",            
         "Frequency",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, true);
      ChartPanel chartPanel = new ChartPanel( barChart );        
      CategoryPlot categoryPlot = barChart.getCategoryPlot();
      CategoryAxis axis = categoryPlot.getDomainAxis();
      axis.setCategoryMargin(-1);
      axis.setLowerMargin(0.01);
      axis.setUpperMargin(0.01);
      BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
      br.setMaximumBarWidth(1);
      br.setItemMargin(-5); 
      chartPanel.setPreferredSize(new java.awt.Dimension( 2024 , 2000 ) );        
      setContentPane( chartPanel ); 
   }
   private CategoryDataset createDataset( ) throws IOException
   {
      String line;
      CreateBarGraph cbg1 = new CreateBarGraph();
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  
      //InputStream in = new FileInputStream("WordFrequency.txt");
      //InputStreamReader isr = new InputStreamReader(in, Charset.forName("UTF-8"));
      FileReader fileReader = new FileReader("InputForBar.txt");
      BufferedReader br = new BufferedReader(fileReader);
      //Random rand = new Random();
      while ((line = br.readLine()) != null)
      {
              String[] WordCounts = line.split(",");
      //        System.out.println(WordCounts[0]+WordCounts[1]);
      //        System.out.println(cbg1.featureName);
              dataset.addValue(Double.parseDouble(WordCounts[1]), WordCounts[0], WordCounts[0]);
      }
      /*final String fiat = "FIAT";        
      final String audi = "AUDI";        
      final String ford = "FORD";        
      final String speed = "Speed";        
      final String millage = "Millage";        
      final String userrating = "User Rating";        
      final String safety = "safety";        

      dataset.addValue( 1.0 , fiat , speed );        
      dataset.addValue( 3.0 , fiat , userrating );        
      dataset.addValue( 5.0 , fiat , millage ); 
      dataset.addValue( 5.0 , fiat , safety );           

      dataset.addValue( 5.0 , audi , speed );        
      dataset.addValue( 6.0 , audi , userrating );       
      dataset.addValue( 10.0 , audi , millage );        
      dataset.addValue( 4.0 , audi , safety );

      dataset.addValue( 4.0 , ford , speed );        
      dataset.addValue( 2.0 , ford , userrating );        
      dataset.addValue( 3.0 , ford , millage );        
      dataset.addValue( 6.0 , ford , safety );               
*/
      return dataset; 
   }
   public static void main( String[ ] args) throws IOException
   {
      CreateBarGraph cbg = new CreateBarGraph();
      BarChart_AWT chart = new BarChart_AWT("Feature Visualization", "Cluster");
      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );        
      chart.setVisible( true ); 
   }
}
