

class Gain { 



String name 
List g 


public Gain(String name) { 
this.name = name
}



  public void load(){
  String fname = name+".csv"
  g = new ArrayList()
  new File(fname).splitEachLine(",") {f ->
  double fp = Double.valueOf(f[0])/1000000
  double gain = Double.valueOf(f[1])
  g.add(new GPoint(fp,gain))
 
}
}

Spectrum process(Spectrum sin) { 
	 Spectrum sout = new Spectrum(name+"*"+sin.name)
	 sout.df = sin.df
	 int i = 0;
	 sin.points.each { fp ->
	       double p = fp.p*gain(fp.f)
	       FPoint fpo = new FPoint(fp.f,p,i++)
	       sout.points.add(fpo)
           }
	   
	   sout.update()
	   return sout
}




  double gain(double f ) { 

   int i = FreqUtils.findIndex(f,g)
   
   double g1 = g[i].gain 
   double g2 = g[i+1].gain
   double m = (g2-g1)/(g[i+1].f  - g[i].f)
   double gi  = m*(f-g[i].f) +  g1
   return gi

}

   void plot() { 

     double[] x = new double[g.size()]
     double[] y = new double[g.size()]
     
     for(int i = 0; i < g.size(); i++){
     	 x[i] = g[i].f
	 y[i] = g[i].gain
     }
     String title = "gain("+name+")"
     String ylabel = "gain"
     String xlabel = "f(MHz)"
     String seriesName = "gain"
     
     PlotUtils.plotSingle(title,xlabel,ylabel,seriesName,x,y)
     

    }
}