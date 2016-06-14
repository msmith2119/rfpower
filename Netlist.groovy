
import flanagan.complex.* 

class Netlist { 

String name
List elements 
Nodes nodeMap

Netlist (String name) {
this.name = name 
elements = new ArrayList() 
nodeMap = new Nodes()

}


int getRank() { 


 return nodeMap.nodeMap.size()-1

}
void load() {
String fname = name+".cir"
    new File(fname).eachLine {l ->
  if(l.length() > 3) { 
  Element e = Element.parseElement(l,nodeMap)
  elements.add(e)
  }
 

}
}


 Complex[][] solve(double f) {

   ComplexMatrix yy = stamp(f)

   ComplexMatrix  yinv = yy.inverse()
   ComplexMatrix i = new ComplexMatrix(getRank(),1)
   Complex one = new   Complex(1,0)
   i.setElement(0,0,one)
   ComplexMatrix zone = yinv.times(i)
   Complex z11 = zone. getElementReference(0,0)
   Complex z12 = zone.getElementReference(1,0)
   i = new ComplexMatrix(getRank(),1)
   i.setElement(1,0,one)
   ComplexMatrix ztwo = yinv.times(i)
   Complex z22 = ztwo.getElementReference(1,0)
   ComplexMatrix z = new ComplexMatrix(2,2)
   z.setElement(0,0,z11)
   z.setElement(0,1,z12)
   z.setElement(1,0,z12)
   z.setElement(1,1,z22)
   Complex[][] y = z.inverse().getArrayReference()
   return y 
}

  Complex[][] getT(Complex[][] y ) { 
   Complex dy = y[0][0].times(y[1][1]).minus(y[0][1].times(y[0][1]))
   ComplexMatrix T = new ComplexMatrix(2,2)
   T.setElement(0,0,y[1][1].over(y[0][1]).negate())
   T.setElement(0,1,y[1][0].inverse().negate())
   T.setElement(1,0,dy.over(y[1][0]).negate())
   T.setElement(1,1,y[0][0].over(y[1][0]).negate())
   Complex[][] t = T.getArrayReference()
   return t
}

ComplexMatrix  stamp( double f ) { 
 ComplexMatrix y = new ComplexMatrix(getRank(),getRank())
elements.each {  element -> element.stamp(y,f) }
return y
}




void display() { 
    elements.each { println it } 

}
}
