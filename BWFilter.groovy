

class BWFilter { 

  String name
  double fl
  double fh 
  double eps = 1e-20

  BWFilter(String name,double fl, double fh) { 
  this.name = name
  this.fl = fl 
  this.fh = fh 
 }

 double gain(double f ) { 
    if(f >=fl && f <=fh){
       return 1.0
   }
  return 0
}
 Spectrum process(Spectrum sin) { 
   	 Spectrum sout = new Spectrum(name+"("+sin.name+")")
         sout.df = sin.df
        int i = 0
   sin.points.each { point -> 

         double   p =  point.p*gain(point.f)
	 if(p > eps) { 
	      sout.points.add(new FPoint(point.f,p,i++))
}
}
    return sout
}
}