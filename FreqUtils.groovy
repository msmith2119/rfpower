


@Grapes( 
@Grab(group='org.knowm.xchart', module='xchart', version='3.0.3') 
)

import org.knowm.xchart.*


class FreqUtils { 

static double P_THERMAL =1; //  14.14e-18

static void plot(Spectrum spectrum) { 


 double[] x = new double[spectrum.points.size()]
 double[] y = new double[spectrum.points.size()]

  int i = 0
  spectrum.points.each {point -> x[i] = point.f ; y[i] = point.p; i++ }
    XYChart chart = QuickChart.getChart(spectrum.name, "freq", "power(mW/Hz)", "density", x, y);
 
    // Show it
    new SwingWrapper(chart).displayChart();  	       
       
}

static void plot(double[] x, double[] y ) { 

    XYChart chart = QuickChart.getChart("spectrum", "freq", "power", "sprectum", x, y);
 
    // Show it
    new SwingWrapper(chart).displayChart();  	       
       

}

static void plotZ(ZFilter filt){

int n = filt.z.size()
 double[] x = new double[n]
 double[] y =  new double[n]

 for(int i = 0; i < n; i++ ) { 
        x[i] = filt.points[i].f
	y[i] = filt.points[i].mag()

}

        XYChart chart = QuickChart.getChart("filter", "freq", "ohms", "impedance", x, y);
 
    // Show it
    new SwingWrapper(chart).displayChart();  	       

}
static void plotP(FilterBase filt){

 int n = filt.points.size()
 double[] x = new double[n]
 double[] y =  new double[n]

 for(int i = 0; i < n; i++ ) { 
        x[i] = filt.points[i].f
	double p = filt.power(i) 
	y[i] = p 

}

        XYChart chart = QuickChart.getChart(filt.name, "freq(MHZ)", "gain", "gain", x, y);
 
    // Show it
    new SwingWrapper(chart).displayChart();  	       

}

static void plotP(TMatrix tmat){

 int n = tmat.t.size()
 double[] x = new double[n]
 double[] y =  new double[n]

 for(int i = 0; i < n; i++ ) { 
        x[i] = tmat.t[i].f
	//double p = 10*Math.log10(tmat.power(tmat.t[i].f))
        double p = tmat.power(tmat.t[i].f)
        //double p = tmat.zin(i).mag() 
	y[i] = p 

}

        XYChart chart = QuickChart.getChart(tmat.name, "freq(MHZ)", "gain", "gain", x, y);
 
    // Show it
    new SwingWrapper(chart).displayChart();  	       

}

 static void plotZ(Zimp zimp, double fl, double fh, int N,boolean mag) { 

  int j = (mag ? 1 : 2)
  
  double df = (fh-fl)/N
  double[] x = new double[N]
  double[][] y = new double[j][N]
  String[] names = new String[j]
  if(mag) { names[0] = "mag(z)" } else { names[0]="real(z)" ; names[1]="imag(z)"}

   if(mag) { 

   for(int i = 0; i < N; i++ ) { 
   	   double f = fl + df*i
	   x[i] = f
	   y[0][i] = zimp.calc(f).mag()
}
}
    else { 
   for(int i = 0; i < N; i++ ) { 
   	   double f = fl + df*i
	   x[i] = f
	   y[0][i] = zimp.calc(f).zr
	   y[1][i] = zimp.calc(f).zi

    }}
	 
  
  
	XYChart chart = QuickChart.getChart(zimp.name, "freq(MHz)", "z(ohms)", names, x, y)

		

		
    // Show it
    new SwingWrapper(chart).displayChart();  	       

}


 static Spectrum gaussian(String name,double P, double fo, double B, int n) { 

 
	Spectrum spectrum = new Spectrum(name)
 	double f1 = fo - 3*B
	double f2 = fo + 3*B
	
        double df = (f2-f1)/n
	spectrum.df = df 
	for(int i= 0; i < n; i++ ) { 
		double f = f1 + i*df
		double x = (f-fo)/B
		double p = (P/(Math.sqrt(Math.PI)*B))*Math.exp(-x*x)
		FPoint point = new FPoint(f,p,i)
		spectrum.points.add(point)
		
	}
	
	spectrum.update()
 	return spectrum
}

   static Spectrum thermal(String name, double df) { 

     Spectrum noise = new Spectrum(name)
      noise.df = df 
      noise.pd = P_THERMAL
      noise.fl = 0 
      noise.fh = 100    
	return noise
}
   static Spectrum convolve(Spectrum  S1, Spectrum S2) { 

   	  def A  = { f -> return Math.sqrt(S1.pow(f)) }
	  def B  = { f -> return Math.sqrt(S2.pow(f)) } 

	  double fal = S1.minFreq()
	  double fah = S1.maxFreq()
	  double fbl = S2.minFreq()
	  double fbh = S2.maxFreq() 
	  
	  double th1_l = fbl - fal 
	  double th1_h = fbh - fah
	  double th2_l = fah + fbl 
	  double th2_h = fal + fbh

	  double f1 = fbl - fah
          double f2 = fbl - fal 
	  double f3 = fal + fbl 
	  double f4 = fah + fbh 
 

	  
	  def limits1 = { f ->

	      double[] a = new double[2]
	      if(f > fbl - fal ) 
	       	   a[0] = fal
	      else 
	      	   a[0] = fbl - f 
		   
              if(f < fbh - fah) 
	      	   a[1] = fah
              else 
	      	   a[1] = fbh - f
           return a
	   }
	   
	   def limits2 = { f -> 
	       double[] a = new double[2] 
	       if(f < fal + fbh)
	       	    a[0] = fal 
	       else 
	            a[0] = f - fbh
	       if(f > fah + fbl)
	       	    a[1] = fah
	       else 
	       	    a[1] = f = fbl
	      return a	    	    
	   }
		     		   		   
	  println "fal = "+fal
	  println "fah = "+fah
	  println "fbl = "+fbl
	  println "fbh = "+fbh
	  List vals = new ArrayList()
	   

          vals[0] = Math.abs(fal-fbl)
	  vals[1] = Math.abs(fal+fbl)
	  vals[2] = Math.abs(fal-fbh)
	  vals[3] = Math.abs(fal+fbh)
	  vals[4] = Math.abs(fah-fbl)
	  vals[5] = Math.abs(fah+fbl)
	  vals[6] = Math.abs(fah-fbh)
	  vals[7] = Math.abs(fah+fbh)
	  
	  vals.sort() 
	  println vals
	  double lowF = vals[0]
	  double highF = vals[vals.size()-1] 


	  
	  Spectrum sout = new Spectrum(S1.name+"(*)"+S2.name)	  	  
	  double df = Math.min(S1.df,S2.df)
	  sout.df = df 	  
	  int n = (highF-lowF)/df
	  for(int i = 0 ; i < n; i++ ) { 
	                    double  f = lowF + df*i
		  if(f >= 0.9*f1 && f <=1.10*f2 || f>=0.9*f3 && f<=1.10*f4){
		  double[]  l1 = limits1(f)
		  double[] l2 = limits2(f)
		  double i1 = integrate(df,l1[0],l1[1],f,A,B)
		  double i2 = integrate(df,l2[0],l2[1],-f,A,B)
		  double p = i1+i2
		  println "f="+f+" p="+p
		  if(p>0){
		  FPoint  fp = new FPoint(f,i1+i2)
		  sout.points.add(fp)
		  }
          }
	  }


	  sout.update()
	  return sout
	  
}


static  int  findIndex(double f, List z) {

        int index = 0

	if(f <= z[0].f){
	     return 0
	}

	if(f >= z[z.size()-1].f ) { 
	     return  z.size()-2
	}   
  
	for(int i  = 0; i < z.size()-1 ; i++ ) { 
		if(f >= z[i].f && f < z[i+1].f){
		     index = i
		     break
		}
	}    

	return index 

}

static 	double integrate(double df,double lower, double upper, double f,def a, def b) { 
	
		if(upper < lower ) 
			 return 0.0

		
		int n  = (upper-lower)/df
		double sum = 0
		for(int i = 0; i < n; i++) { 
			
			double fp = lower + df*i 
			sum += a(fp)*b(f+fp)
		}
		
		return sum
         }


}