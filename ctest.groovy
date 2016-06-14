
//Spectrum s1 = FreqUtils.gaussian("s1",1,4.0,0.02,100)

Spectrum s2 = FreqUtils.gaussian("s2",1,14.0,0.02,100)

println "inPower = "+s2.totalPower()
s2.plot()

FPoint pk = s2.peak()
println "peak="+pk
Mixer m = new Mixer("mixer",0.25,4.0)
Spectrum s3  = m.process(s2)

s3.plot()

//BWFilter filt = new BWFilter("brick",5,15) 
TMatrix filt = new TMatrix("lc")
filt.loadCkt(1.0,20.0,100)
//filt.load()
FreqUtils.plotP(filt)
FreqUtils.plotZ(filt.inputZ(),true)
//println "df = "+s3.df
Spectrum s4 = filt.process(s3)
s4.plot()
//println "outPower="+s4.totalPower()
//Spectrum s2p = filt.process(s2)
//Spectrum so = s1p.add(s2p)

//FreqUtils.plot(so)
//println "in = "+s1.totalPowerDB()
//println "out = "+so.totalPowerDB()

