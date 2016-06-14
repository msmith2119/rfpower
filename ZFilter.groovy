

class ZFilter extends FilterBase { 


double Zs = 50 


ZFilter(String name ) { 
super(name)
}  



  public void load(){
  String fname = name+".csv"
  points = new ArrayList()
  new File(fname).splitEachLine(",") {f ->
  double fr = Double.valueOf(f[0])/1000000
  double zr = Double.valueOf(f[1])
  double zi = Double.valueOf(f[2])
  points.add(new ZPoint(fr,zr,zi))
 
}
}


double power(int i ) { 
 	return power(points[i])
}

 double power(ZPoint zp){
 	
 	double g =  (0.5*zp.zr*Zs)/(Math.sqrt((Zs+zp.zr)*(Zs+zp.zr) + zp.zi*zp.zi)*zp.mag())
        return g       
	       
   }

Spectrum process(Spectrum sin) { 
	 Spectrum sout = new Spectrum(name+"("+sin.name+")")
	 sout.df = sin.df
	 
	 sin.points.each { fp -> 
	       double p = fp.p*power(fp.f)
	       FPoint fpo = new FPoint(fp.f,p)
	       sout.points.add(fpo)
           }
	   return sout
}
 double power(double f ) {
 
        int i = FreqUtils.findIndex(f,points)
	ZPoint z1 = points[i]
	ZPoint z2 = points[i+1]
	
 	double p1 = power(z1)
	double p2 = power(z2)
	double df = points[i+1].f - points[i].f
	double p = p1 + (p2-p1)*(f-z1.f)/df	
	return  p
  }


  ZPoint z(double f) { 

    
        int i = FreqUtils.findIndex(f,points)
	ZPoint z1 = points[i]
	ZPoint z2 = points[i+1]
	double df = points[i+1].f - points[i].f
	double zr = z1.zr + (z2.zr - z1.zr)*(f-z1.f)/df
	double zi = z1.zi + (z2.zi - z1.zi)*(f-z1.f)/df
	return new ZPoint(f,zr,zi)
    
   }
   Zimp inputZ() { 

       Closure c = { f-> return z(f) } 
       Zimp zin = new Zimp(c)
       zin.name="z("+name+")"
       return zin
   }
   ZPoint peak() { 


	  ZPoint zpeak =points[0]
	  points.each  { zp -> if(power(zp) > power(zpeak))zpeak = zp  }
	  	  
	  return zpeak
}
 double minF() { return points[0].f } 
 double maxF() { return points[points.size()-1].f}
}