

@Grapes( 
@Grab(group='org.knowm.xchart', module='xchart', version='3.0.3') 
)

import org.knowm.xchart.*

   // double[] xData = new double[] { 0.0, 1.0, 2.0 };
   // double[] yData = new double[] { 2.0, 1.0, 0.0 };
 
   double[] xData = new double[2]
   double[] yData = new double[2]

   xData[0] = 0.0
   xData[1] = 1.0
   yData[0] = 2.0
   yData[1] = 1.0

    // Create Chart
    XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
 
    // Show it
    new SwingWrapper(chart).displayChart();
