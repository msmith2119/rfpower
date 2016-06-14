

class Amp {

public Amp(String name ) { 
this.name = name
}

 void load(String filtName,String gainName) { 

 inputFilter = new ZFilter(filtName)
 inputFilter.load()
 gainBlock = new Gain(gainName)
 gainBlock.load()

}

 Spectrum  process (Spectrum sin) { 

   Spectrum s1 = inputFilter.process(sin)
   Spectrum sout = gainBlock.process(s1)
   
   return sout
}
String name 
ZFilter inputFilter 
Gain gainBlock




}