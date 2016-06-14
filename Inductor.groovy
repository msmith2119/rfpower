

import flanagan.complex.* 

class Inductor extends Element { 

  double L 
  NodePair nodeA
  NodePair nodeB

 Inductor(String name,NodePair nodeA, NodePair nodeB, double L) { 
  super(name)
  this.nodeA = nodeA
  this.nodeB = nodeB
  this.L = L
} 
static Inductor parseLine(String  line, Nodes nodeMap ) { 

String[]  fields = line.split(/\s+/)
String name = fields[0]

NodePair nodeA = nodeMap.getNodePair(fields[1])
NodePair nodeB = nodeMap.getNodePair(fields[2])

double L =  convert(fields[3])
return new Inductor(name,nodeA,nodeB,L) 

}

void stamp(ComplexMatrix y, double f) { 

    int i = nodeA.index-1 
    int j = nodeB.index-1

    double yl = -1/(2*Math.PI*f*L*1e+06)

    Complex y1 
    Complex y2
    Complex y12
    if(i > -1 ){ 
       y1 = y.getElementCopy(i,i)
       y.setElement(i, i, y1.getReal(), y1.getImag() + yl)
       }
    if(j > -1 ){ 
        y2 = y.getElementCopy(j,j)
       y.setElement(j, j, y2.getReal(), y2.getImag() + yl)
       }
    if(i > -1 && j > -1 ){    
           y12 = y.getElementCopy(i,j)
           y.setElement(i,j,y12.getReal(),-yl + y12.getImag())
           y.setElement(j,i,y12.getReal(),-yl + y12.getImag())
    }
   
 
}

  String toString() { 
    StringBuffer sb = new StringBuffer() 
    sb.append("name="+name+"\n")
    sb.append("nodeA="+nodeA.strVal+" => "+nodeA.index+"\n")
    sb.append("nodeB="+nodeB.strVal+" => "+nodeB.index+"\n")
    sb.append("L="+L+"\n")
    return sb.toString()
}
}


