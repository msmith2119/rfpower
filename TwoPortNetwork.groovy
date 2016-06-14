
import flanagan.complex.* 


class TwoPortNetwork { 

String name
Netlist net
Zimp zsrc
Zimp zl

TwoPortNetwork(String name) { 

this.name = name
}

void load() { 

net = new Netlist(name)
net.load()
  zsrc = Zimp.constZ(50,0)
  zl =  Zimp.constZ(50,0)
}


ZPoint zin(double f ) { 

Complex[][] t = net.getT(net.solve(f))
Complex A = t[0][0]
Complex B = t[0][1]
Complex C = t[1][0]
Complex D = t[1][1]
Complex z = (A.plus(B.over(zl.zc(f)))).over(C.plus(D.over(zl.zc(f))))
ZPoint zin = new ZPoint(f,z.getReal(),z.getImag())
return zin
}





double gain(double f ) { 


 Complex[][] y = net.solve(f)
 Complex[][] t = net.getT(y)


Complex A = t[0][0]
Complex B = t[0][1]
Complex C = t[1][0]
Complex D = t[1][1]


 def sqr =  { x -> return x*x } 
 ZPoint zx = zin(f)
 Complex a12 = A.times(zl.zc(f)).plus(B)
 Complex a22 = C.times(zl.zc(f)).plus(D) 
 Complex zi = new Complex(zx.zr,zx.zi)
 Complex one = new Complex(1.0,0)
 Complex Finv = one.over(a12)
 Complex Ginv = one.over(a22)
 ZPoint z2 = zl.z(f)
 Complex v1 = zi.over(zsrc.zc(f).plus(zi))
 double gain = sqr(Finv.abs())*z2.mag()*zsrc.z(f).mag()*z2.cos()*v1.abs()*v1.abs()/zsrc.z(f).cos()

// double gain =   sqr(Finv.abs())*zx.mag()*z2.mag()*z2.cos()/zx.cos()
// //double gain =  sqr(Ginv.abs())*z2.mag()*z2.cos()/(zx.mag()*zx.cos())
 return gain
}

  Zimp  inputZ() { 

       
   Closure c = { f ->
     return zin(f)
    }
 Zimp zimp = new Zimp(c)          
 zimp.name="zin("+name+")" 
  return zimp
}

Spectrum process(Spectrum sin) { 
	 Spectrum sout = new Spectrum(name+"("+sin.name+")")
	 double df = sin.df
	 sout.df = sin.df
	 int n = sin.points.size()      
	 for(int i = 0; i < n; i++ )  {
	       double p = sin.points[i].p*gain(sin.points[i].f)
	         FPoint fpo = new FPoint(sin.points[i].f,p,i)
	         sout.points.add(fpo)
	       
	 }

	   sout.update()	
	   return sout
}

double peak(double fl, double fh,  double df ) { 

 
  int N = (fh-fl)/df

  double fmax = fl	
  double gmax = gain(fl)
  for(int i = 0 ;i < N; i++ ) { 
      double f =  fl + df*i
      double g = gain(f)
      if(g > gmax ) { gmax = g; fmax = f}
 }

 return fmax
}

double bandWidth(double peakF, double atten,double df, double dfext) { 

    double p0 =gain(peakF)*atten
    double fext = peakF + dfext 
    double fh = peakF + dfext
    double fl = peakF - dfext 
    double fbl = peakF
    double fbh = peakF
    boolean doit = true
    double f = peakF 
    while(f < fh && doit) { 
    f += df 
    if(gain(f) < p0){
    	fbh = f
	doit = false
    }

    }
    f = peakF     	       
    while(f > fl) { 
    f -= df 
    if(gain(f) < p0 && doit) { 
      fbl = f
      doit = false
    } 

    }

    double bw = fbh - fbl 
    return bw
}

void plot(double fl, double fh, int N, boolean logscale=false) { 

PlotUtils.plotNetwork(this,fl,fh,N,logscale)
}
}