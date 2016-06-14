

import flanagan.complex.*

String name = "test" 


TMatrix tmat = new TMatrix("lc")
tmat.load()
tmat.t.each { println it } 

Netlist n = new Netlist("lc") 
n.load()
int N = n.getRank() 

double f = 1


ComplexMatrix cmat =  n.stamp(f)
Complex[][]c = cmat.getArrayReference() 

for(int i = 0; i < N; i++ ) { 
  for(int j = 0; j < N; j++ ) { 

   print "<"+c[i][j] +"> "

}
println ""
 }
n.display() 

ComplexMatrix  yinv = cmat.inverse()
ComplexMatrix i = new ComplexMatrix(N,1)
Complex one = new   Complex(1,0)
i.setElement(0,0,one)
ComplexMatrix zone = yinv.times(i)
Complex z11 = zone. getElementReference(0,0)
Complex z12 = zone.getElementReference(1,0)

println "z11="+z11
println "z12="+z12

i = new ComplexMatrix(N,1)
i.setElement(1,0,one)
ComplexMatrix ztwo = yinv.times(i)
Complex z22 = ztwo.getElementReference(1,0)

println "z22="+z22 

ComplexMatrix z = new ComplexMatrix(2,2)
z.setElement(0,0,z11)
z.setElement(0,1,z12)
z.setElement(1,0,z12)
z.setElement(1,1,z22)

Complex[][] yy = z.inverse().getArrayReference()

Complex dy = yy[0][0].times(yy[1][1]).minus(yy[0][1].times(yy[0][1]))

ComplexMatrix T = new ComplexMatrix(2,2)
T.setElement(0,0,yy[1][1].over(yy[0][1]).negate())
T.setElement(0,1,one.over(yy[1][0]).negate())
T.setElement(1,0,dy.over(yy[1][0]).negate())
T.setElement(1,1,yy[0][0].over(yy[1][0]).negate())


Complex[][] cz = T.getArrayReference()
//Complex[][] cz = yy
for(int k = 0; k < N; k++ ) { 
  for(int j = 0; j < N; j++ ) { 

   print "<"+cz[k][j] +"> "

}
println ""
 }