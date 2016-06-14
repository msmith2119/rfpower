

class TMatrix  { 

  String name
  List t = new ArrayList()
  Zimp zsrc
  Zimp zl
 double  eps = 1e-20 

 TMatrix(String name) { 

this.name = name 
}


   public void load(){
   
   
  String fname = name+".csv"
  t = new ArrayList()
  new File(fname).splitEachLine(",") {l ->
  double fr = Double.valueOf(l[0])/1000000

   double i1r = -Double.valueOf(l[1])
   double i1pr = -Double.valueOf(l[2])
   double i1i = -Double.valueOf(l[3])
   double i1pi = -Double.valueOf(l[4])
   double i2r = -Double.valueOf(l[5])
   double i2pr = -Double.valueOf(l[6])
   double i2i = -Double.valueOf(l[7])
   double i2pi = -Double.valueOf(l[8])

   ZPoint i1 = new ZPoint(fr,i1r,i1i)
   ZPoint i1p = new ZPoint(fr,i1pr,i1pi)
   ZPoint i2 = new ZPoint(fr,i2r,i2i)
   ZPoint i2p = new ZPoint(fr,i2pr,i2pi)
   
    ZPoint y11 = i1.add(i1p).scale(0.5)

    ZPoint y12 = i1.subtract(i1p).scale(0.5)
    ZPoint y21 = y12
    ZPoint y22 = i2.subtract(i2p).scale(0.5)

   // println "y11="+y11
   // println "y12="+y12
    //println "y22="+y22

    ZPoint  dy = y11.mult(y22).subtract(y12.mult(y21))
    
      
    ZPoint A = y22.div(y21).negate()
    ZPoint B = (new ZPoint(y21.f,1,0)).div(y21).negate()
    ZPoint C = dy.div(y21).negate()
    ZPoint D = y11.div(y21).negate()
    t.add(new TPoint(fr,A,B,C,D))
 

}

  zsrc = Zimp.constZ(50,0)
  zl =  Zimp.constZ(50,0)
}

 
  ZPoint zin(int i ) { 
   ZPoint zin = (t[i].A.add(t[i].B.div(zl.z(t[i].f)))).div(t[i].C.add(t[i].D.div(zl.z(t[i].f))))
   return zin
}


  ZPoint zin(double f ) { 

  int i = FreqUtils.findIndex(f,t)
  


   ZPoint z1 = zin(i)
   ZPoint z2 = zin(i+1)
   double m = (z2.zr-z1.zr)/(z2.f  - z1.f)
   double zr = m*(f-z1.f) + z1.zr
   m = (z2.zi - z1.zi)/(z2.f  - z1.f)
   double zi = m*(f-z1.f) + z1.zi
   ZPoint zin = new ZPoint(f,zr,zi)
   return zin
   
}

  Zimp  inputZ() { 

       
   Closure c = { f ->
     return zin(f)
    }
 Zimp zimp = new Zimp(minF(),maxF(),t.size(),c)          
 zimp.name="zin("+name+")" 
  return zimp
}


 double power(double f) { 
 
 int i = FreqUtils.findIndex(f,t)
 
ZPoint a12 = t[i].A.add(t[i].B.div(zl.z(f)))

ZPoint zi = inputZ().calc(f)

ZPoint v2 = zi.div(zi.add(zsrc.z(f)).mult(a12))

 double z2z1 = zsrc.z(f).div(zl.z(f)).mag()

double alpha = v2.mag() 

 double cr = zl.z(f).cos()/zsrc.z(f).cos()
 double gain = z2z1*alpha*alpha*cr

return  gain
  
}
 double maxF() {  return t[t.size()-1].f } 
 double minF() { return t[0].f }


Spectrum process(Spectrum sin) { 
	 Spectrum sout = new Spectrum(name+"("+sin.name+")")
	 double df = sin.df
	 sout.df = sin.df
	 
  	       
	 int n = sin.points.size()      
//         int n = (sin.fh -sin.fl)/sin.df
	 for(int i = 0; i < n; i++ )  {
	       double f = sin.fl + df*i
	       double p = sin.points[i].p*power(sin.points[i].f)
	      // println "f="+f+" p="+p
	       //if(p > eps ) { 
	         FPoint fpo = new FPoint(sin.points[i].f,p,i)
	         sout.points.add(fpo)
	       	 //   }
	       
	 }


	   sout.update()	


	   return sout
}

 void plot() { 
 int n = t.size()
 double[] x = new double[n]
 double[] y =  new double[n]

 for(int i = 0; i < n; i++ ) { 
        x[i] = t[i].f
        double p = power(t[i].f)
	y[i] = p 

}
 PlotUtils.plotSingle(name,"f(MHz)","mW/Hz","gain",x,y)

}

    double peak() { 
    double pmax = power(t[0].f)
    double fmax = t[0].f
    t.each { tp -> double pw = power(tp.f);if(pw>pmax){fmax = tp.f;pmax = pw}}
    return fmax
    }
}

   

