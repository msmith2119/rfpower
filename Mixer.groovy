

class Mixer  { 

static N = 2

String name
double fo 
double gain

Mixer(String name,double gain,double fo ) { 

 this.name = name
this.gain = gain
this.fo = fo
}


 Spectrum process(Spectrum sin ) { 

 println "sin.fh="+sin.fh
 Closure freq = { n -> return (2*n+1)*fo } 
 Closure amp = { n-> return Math.abs(1.0/(2*n+1))}
  List vals = new ArrayList() 
  (-N..N-1).each { vals.add(Math.abs(sin.fl-freq(it))) } 
  (-N..-1).each { vals.add(Math.abs(sin.fh-freq(it))) } 
  (-N..-1).each { vals.add(Math.abs(sin.fh+freq(it))) } 

  vals.sort()
  println vals   
  double lowF = vals[0]
  println "lowF="+lowF
  double highF = sin.fh + freq(N)
  println "highF="+highF
  double df = sin.df
  
  int n = (highF-lowF)/df
  Spectrum sout = new Spectrum(name) 
  sout.df = df 
  Closure  pgain = { fx->
    double s = 0;  
    for(int k = -N; k<N; k++ ) {
      s+= gain*sin.pow(fx-freq(k))*amp(k)
      }
      return s
}

	int k = 0
         for(int i  =  0 ; i < vals.size(); i+=2 ) { 

	 	 int M = (vals[i+1]-vals[i])/df
	 	 for(int j = 0; j < M; j++){
		 	 
			double f = vals[i] + df*j
			double p = pgain(f)
		 	if(p > 0 ) { 
		     	     FPoint fp =  new FPoint(f,p,k++)
		     	     sout.points.add(fp)
		 	}
			
		 }
       }
 	
	
	sout.update()
        return sout
}


}