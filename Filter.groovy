


class Filter { 

List z 
double Zs = 50
int index = 0
String name


Filter(String name){
this.name = name
}
  public void load(){
  String fname = name+".csv"
  z = new ArrayList()
  new File(fname).splitEachLine(",") {f ->
  double fr = Double.valueOf(f[0])/1000000
  double zr = Double.valueOf(f[1])
  double zi = Double.valueOf(f[2])
  z.add(new ZPoint(fr,zr,zi))
 
}
}

 public  int  findIndex(double f) {

        int index = 0

	if(f < z[0].f){
	     return 
	}

	if(f > z[z.size()-1].f ) { 
	     return  z.size()-2
	}   
  
	for(int i  = 0; i < z.size()-1 ; i++ ) { 
		if(f > z[i].f && f < z[i+1].f){
		     index = i
		     break
		}
	}    

	return index 

}


 double power(int i ) { 
 	return power(z[i])
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
 
        int i = findIndex(f)
	ZPoint z1 = z[i]
	ZPoint z2 = z[i+1]
	
 	double p1 = power(z1)
	double p2 = power(z2)
	double df = z[i+1].f - z[i].f
	double p = p1 + (p2-p1)*(f-z1.f)/df	
	return  p
  }

   ZPoint peak() { 


	  ZPoint zpeak =z[0]
	  z.each  { zp -> if(power(zp) > power(zpeak))zpeak = zp  }
	  	  
	  return zpeak
}
}
