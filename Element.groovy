

import flanagan.complex.* 


class Element { 

Element(String name) { 
this.name = name
}

String name



 static Element parseElement(String line, Nodes nodeMap){

   char c = line.charAt(0)

   if(c == 'R')
   	return Resistor.parseLine(line,nodeMap)
	
   if(c == 'C') 
   	return Capacitor.parseLine(line,nodeMap)
   if(c == 'L')
   	return Inductor.parseLine(line,nodeMap)
	
   if(c == 'X')
   	return Crystal.parseLine(line,nodeMap)
   return  null

}

 static double convert(String s ) { 

 def m = (s =~ /([\d\.]+)([fpnumkMG]?)$/ )

 if(m.hasGroup()) { 
     return  Double.valueOf(m[0][1])*eMult(m[0][2])
     
 }
 return 0.0
}


 void stamp(ComplexMatrix y, double f ) { 

}
static  double eMult(String c ) { 
 
     if(c == "f")
     	  return 1e-15
     if(c == "p")
     	  return 1e-12 
     if(c == "n") 
          return 1e-09
     if(c == "u")
          return 1e-06 
     if(c == "m")
     	  return 1e-03
     if(c == "k")
      	  return 1e+03
     if(c == "M")
     	  return 1e+06
      if(c == "G")
      	   return 1e+09
	  
      return 1.0 
 }
}