
import flanagan.complex.*





//TMatrix t = new TMatrix("lc")
//t.load()
//t.loadCkt(1.0,20.0,100)
Netlist n = new Netlist("lc")
n.load()
ComplexMatrix y = n.stamp(10.0)
int N = n.getRank()
Complex[][]c = y.getArrayReference()

for(int i = 0; i < N; i++ ) { 
  for(int j = 0; j < N; j++ ) { 

   print "<"+c[i][j] +"> "

}
println ""
}

//t.t.each { println it}
//ZPoint zin = t.zin(1.0)
//println zin
//double gain = t.power(1.0)
//println gain