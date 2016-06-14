



TwoPortNetwork pre = new TwoPortNetwork("preselect")
pre.load()
//pre.plot(10.0,15.0,100)
Spectrum sin = FreqUtils.gaussian("sin",1,14.25,0.0001,100)



ZFilter ampin = new ZFilter("ampin")
ampin.load()
pre.zl = ampin.inputZ()
FreqUtils.plotZ(pre.zl,5.0,15.0,200,false)
Spectrum sout  = pre.process(sin)
//sout.plot()
println "input power = "+sin.totalPowerDB()
println "p-pre="+sout.totalPowerDB()

Gain gain = new Gain("gain")
gain.load()
Spectrum samp = gain.process(sout)
println "p-mp="+samp.totalPowerDB()
//samp.plot()

Mixer m = new Mixer("mixer",1.0,4.25)

Spectrum smix = m.process(samp)

println "mixer power = "+smix.totalPowerDB()
smix.plot()

TwoPortNetwork cfilt = new TwoPortNetwork("crystal1")
cfilt.load()
cfilt.zsrc= Zimp.constZ(200,0)
cfilt.zl = Zimp.constZ(200,0)

//FreqUtils.plotZ(cfilt.inputZ(), 1.0, 20.0, 200, false)


cfilt.plot(9.90,10.01,500,true)
Spectrum sif = cfilt.process(smix)
sif.plot(9.8,10.2)
println "ifpower="+sif.totalPowerDB()

double fpeak = cfilt.peak(9.95,10.1,0.0001)
println "crystal peak="+fpeak
println "crystal bw = "+cfilt.bandWidth(fpeak,0.5,0.0001,0.1)
FPoint pk = sif.peak(9.00,11.0)
println "sif peak="+pk
println "if bw="+sif.bandWidth(pk,0.5)
//BWFilter bwfilt = new BWFilter("bwfilt",9.95,10.05)

//sin.plot()

//Spectrum s2 = gain.process(sout)
//Spectrum s3 = m.process(s2)
//Spectrum s4 = cfilt.process(s3)
//sout.plot()
//s2.plot()
//s3.plot(9.9,11.0)
//s4.plot(9.9,10.1)

//double bw =  s3.bandWidth(pk,0.1)
//println "bw="+bw
//FreqUtils.plotZ(cfilt.inputZ(),1.0,20.0,200,true)
//double fpeak = cfilt.peak(9.95,10.1,0.0001)

//println "mixer peak="+s3.peak(9.0,11.0)
//println "pin="+sin.totalPowerDB()

//println "p-amp="+s2.totalPowerDB()
//println "p-mixer="+s3.totalPowerDB()
//println "p-if="+s4.totalPowerDB()


