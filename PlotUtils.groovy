

@Grapes( 
@Grab(group='org.knowm.xchart', module='xchart', version='3.0.3') 
)

import org.knowm.xchart.*


class PlotUtils { 



 static void plotNetwork(TwoPortNetwork network,double fl, double fh,  int N, boolean logscale = false )  {
    double df = (fh - fl)/N
     double[] x = new double[N]
     double[] y = new double[N]
     String title = network.name
     String xlabel = "f(MHz)"
     String ylabel = "gain"
     String seriesName = "gain"

     for(int i = 0; i < N; i++ ) { 
     	     double f = fl + df*i

	     x[i] = f
	     y[i] = (logscale ? 10*Math.log10(network.gain(f)+1e-20) : network.gain(f))

     }
        XYChart chart = QuickChart.getChart(title, xlabel, ylabel, seriesName, x, y);
	    new SwingWrapper(chart).displayChart();  	       
}

 static plotSingle(String title,String xlabel, String ylabel, String seriesName,double[] x, double[] y ) { 

        XYChart chart = QuickChart.getChart(title, xlabel, ylabel, seriesName, x, y);
	    new SwingWrapper(chart).displayChart();  	       
}
}