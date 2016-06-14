

class Spectrum  { 



 Spectrum(String name) { 

 this.name = name 
 points = new ArrayList()
  
}
  Spectrum () {
    
	points = new ArrayList() 

   }

  String name = "unnamed"
  List  points
  double df 
  double  fl
  double fh
  double pd


  void display(){
  
   points.each { point -> println point.f+","+point.p } 
    
}


double pow(double f) { 

    if(points.size() == 0) { 
       return pd
    }

    if(f < 0 ){ 
    	 f = -1.0*f
    	}

 int i = (f  - points[0].f)/df

 int n = points.size()
 
   if(i < 0 ) 
   	i = 0

    if(i >n-2)
    	 i = n-2

     double px = points[i].p + ((points[i+1].p - points[i].p)/df)*(f-points[i].f)

     if(px < 0 ) 
     	   px = 0

     return px

    
	   
     }
    double totalPower () { 
    	   
	   double p = 0
    	   points.each { e -> p+=e.p*df } 
	  // points.each { e -> println e.p} 
	   return p
   }
    
   double totalPowerDB() { 
      return 10*Math.log10(totalPower())
    }
    
    double minFreq() { return fl } 
    double maxFreq() { return fh }

    FPoint peak() { 
    FPoint  pk = points[0]
    points.each { point -> if(point.p> pk.p)pk=point}
    return pk
    }


    FPoint peak(double fl, double fh) {
    FPoint  pk = points[0]

    points.each { point -> if(point.p> pk.p && (point.f >fl && point.f < fh))pk=point}
    return pk
}

    double  bandWidth(FPoint pk, double g ) { 

     double p0 = pk.p*g

     int ih = points.size()-1

     for(int i = pk.i; i < points.size(); i++ ) { 
     	  
     	     if(points[i].p < p0){
	     		ih = i    
			break   
	     }
     }
     int il = 0
     for(int i = pk.i; i >0; i-- ) { 
     	     
	     if(points[i].p < p0) { 
	     	il = i-1
		break
	     }
     }

     double bw = points[ih].f - points[il].f 
     return bw
    }
         
     Spectrum  add(Spectrum other ) { 
     
     double df = Math.min(df,other.df)
     double f0 = Math.min(minFreq(),other.minFreq())
     double f1 = Math.max(maxFreq(),other.maxFreq())
     int n = (f1-f0)/df 

     Spectrum s = new Spectrum(name+"+"+other.name)
     s.df = df

     for(int i = 0 ; i < n; i++ ) { 
     	     double f = f0 + df*i
	     double p = pow(f)+other.pow(f)
     	     s.points.add(new FPoint(f,p))
    }

    s.update()     
     return s     

   }

   void update() { 

     if(points.size() > 0 ){
     fl = points[0].f
     fh = points[points.size()-1].f
}
}

 void plot() { 
 
   int n = points.size() 
   double[] x = new double[n]
   double[] y = new double[n]
   String xlabel = "f(MHz)"
   String ylabel = "mW/Hz"
   String seriesName="pd"
   int i = 0; 
   points.each { p -> x[i]=p.f; y[i++]=p.p } 
   PlotUtils.plotSingle(name,xlabel,ylabel,seriesName,x,y)
     

}

 void plot(double  fl, double fh ) { 
      
      List pp = new ArrayList()
      points.each { point -> 

      if(point.f >= fl && point.f <=fh ) { 
      		 pp.add(point)	 
      }
      }
      int N = pp.size()
      double[] x = new double[N]
      double[] y = new double[N]
      int k = 0
      pp.each { p-> 
      	      x[k] = p.f
	      y[k++] = p.p
      }
      PlotUtils.plotSingle(name,"f(MHz)","mW/Hz","power",x,y)
}

}