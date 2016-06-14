

import flanagan.complex.* 

class Capacitor extends Element { 

  double C 
  NodePair nodeA
  NodePair nodeB

 Capacitor(String name,NodePair nodeA, NodePair nodeB, double C) { 
  super(name)
  this.nodeA = nodeA
  this.nodeB = nodeB
  this.C = C
} 
static Capacitor parseLine(String  line, Nodes nodeMap ) { 

String[]  fields = line.split(/\s+/)
String name = fields[0]

NodePair nodeA = nodeMap.getNodePair(fields[1])
NodePair nodeB = nodeMap.getNodePair(fields[2])


double C =  convert(fields[3])

return new Capacitor(name,nodeA,nodeB,C) 

}

  void stamp(ComplexMatrix y, double f) { 

    int i = nodeA.index-1 
    int j = nodeB.index-1
    double yc = 2*Math.PI*f*C*1e+06
    Complex y1 
    Complex y2
    Complex y12
    if(i > -1 ){ 
       y1 = y.getElementCopy(i,i)
       y.setElement(i, i, y1.getReal(), y1.getImag() + yc)
       }
    if(j > -1 ){ 
        y2 = y.getElementCopy(j,j)
       y.setElement(j, j, y2.getReal(), y2.getImag() + yc)
       }
    if(i > -1 && j > -1 ){    
           y12 = y.getElementCopy(i,j)

           y.setElement(i,j,y12.getReal(),-yc + y12.getImag())

           y.setElement(j,i,y12.getReal(),-yc + y12.getImag())
	   
    }

  }

   String toString() { 
    StringBuffer sb = new StringBuffer() 
    sb.append("name="+name+"\n")
    sb.append("nodeA="+nodeA.strVal+" => "+nodeA.index+"\n")
    sb.append("nodeB="+nodeB.strVal+" => "+nodeB.index+"\n")
    sb.append("C="+C+"\n")
    return sb.toString()
}

}


