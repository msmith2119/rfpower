


f = 10
TwoPortNetwork net = new TwoPortNetwork("lcr") 
net.load() 
println net.gain(f) 
println "z="+net.zin(f) 
