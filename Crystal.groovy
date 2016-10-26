


import flanagan.complex.*

class Crystal extends Element {


Netlist cnet 
NodePair nodeA
NodePair nodeB
String defName
double Lm
double Cm
double Cp

 Crystal(String name,NodePair nodeA, NodePair nodeB,String defName){
super(name)
this.nodeA = nodeA
this.nodeB = nodeB
this.defName = defName
}

static Crystal parseLine(String line, Nodes nodeMap){

  String[] fields = line.split(/\s+/)
  String name = fields[0]
  NodePair nodeA = nodeMap.getNodePair(fields[1])
 NodePair nodeB = nodeMap.getNodePair(fields[2])
 String defName = fields[3]
 Crystal crystal =   new Crystal(name,nodeA,nodeB,defName)
 crystal.load()
 return crystal
}

 void load() {
 cnet = new Netlist(defName)
 cnet.load()
 
}
 void stamp(ComplexMatrix y, double f ) { 

  Complex[][] yc = cnet.solve(f)

    int i = nodeA.index-1 
    int j = nodeB.index-1

    Complex y1 
    Complex y2
    Complex y12

    if(i > -1 ){ 
       y1 = y.getElementCopy(i,i)
       y.setElement(i, i, y1.plus(yc[0][0]))
}
    if(j > -1 ) {
       y2 = y.getElementCopy(j,j)
       y.setElement(j, j, y2 + yc[1][1])
       }
    if(i > -1 && j > -1 ){    
           y12 = y.getElementCopy(i,j)
           y.setElement(i,j,y12.plus(yc[0][1]))
           y.setElement(j,i,y12.plus(yc[0][1]))
    }
  }
   String toString() { 
    StringBuffer sb = new StringBuffer() 
    sb.append("name="+name+"\n")
    sb.append("nodeA="+nodeA.strVal+" => "+nodeA.index+"\n")
    sb.append("nodeB="+nodeB.strVal+" => "+nodeB.index+"\n")
    sb.append("Lm="+Lm+"\n")
    sb.append("Cm="+Cm+"\n")
    sb.append("Cp="+Cp+"\n")
    return sb.toString()
}
}
