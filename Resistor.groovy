
import flanagan.complex.* 

class Resistor extends Element { 

  double R 
  NodePair nodeA
  NodePair nodeB
 

 Resistor(String name,NodePair nodeA, NodePair nodeB, double R) { 
  super(name)
  this.nodeA = nodeA
  this.nodeB = nodeB
  this.R = R
 

}


static Resistor parseLine(String  line, Nodes nodeMap ) { 

String[]  fields = line.split(/\s+/)
String name = fields[0]


NodePair nodeA = nodeMap.getNodePair(fields[1])
NodePair nodeB = nodeMap.getNodePair(fields[2])

double R =  convert(fields[3])
return new Resistor(name,nodeA,nodeB,R) 

}


 void stamp(ComplexMatrix y, double f) { 

    int i = nodeA.index-1 
    int j = nodeB.index-1
    Complex y1 
    Complex y2
    Complex y12

    if(i > -1 ){
       y1 = y.getElementCopy(i,i) 
       y.setElement(i, i, y1.getReal()+(1/R), y1.getImag())
}

    if(j > -1 ){ 
       y2 = y.getElementCopy(j,j)
       y.setElement(j, j, y2.getReal()+(1/R), y2.getImag())
}
    if(i > -1 && j > -1 ){    
    	 y12 = y.getElementCopy(i,j)
           y.setElement(i,j,y12.getReal()-(1/R),y12.getImag())
           y.setElement(j,i,y12.getReal()-(1/R),y12.getImag())
    }

}


   String toString() { 
    StringBuffer sb = new StringBuffer() 
    sb.append("name="+name+"\n")
    sb.append("nodeA="+nodeA.strVal+" => "+nodeA.index+"\n")
    sb.append("nodeB="+nodeB.strVal+" => "+nodeB.index+"\n")
    sb.append("R="+R+"\n")
    return sb.toString()
}
}


 