
import flanagan.complex.* 

//Nodes nodes = new Nodes()
//String line="X1 in out (0.025,0.01p,0.035p)"
//Crystal crystal  = Crystal.parseLine(line,nodes)
//println crystal

//Netlist n = new Netlist("crystal1")
//n.load()

TwoPortNetwork network = new TwoPortNetwork("crystal1")
network.load()
//Complex[][] y = n.solve(12)
//println y
network.plot(9.98,10.02,200)
