

class Nodes { 

 Map nodeMap
 int index

 Nodes() { 

  index = 3
  nodeMap = new HashMap()
  nodeMap["in"] = new NodePair("in",1)
  nodeMap["out"] = new NodePair("out",2)
  nodeMap["gnd"] = new NodePair("gnd",0)
}

  NodePair getNodePair(String name) { 

   NodePair n = nodeMap[name]
   if(n != null)
   	return n

    nodeMap[name] = new NodePair(name,index++)

    return nodeMap[name]
}
  

}