

Spectrum s1 = FreqUtils.gaussian("input1",1,14.621,0.02,100)
Spectrum s2 = FreqUtils.gaussian("input2",1,14.721,0.02,100)

Spectrum sall = s1.add(s2)

def dB(x) { return 10*Math.log10(x)}

Spectrum noise = FreqUtils.thermal("white1",s1.df)

Gain gain = new Gain("gain")
gain.load()
//gain.plot()

TMatrix t = new TMatrix("abcd")
t.load()
ZFilter ampin = new ZFilter("ampin")
ampin.load()
Zimp zsrc = Zimp.constZ(50.0,0.0)
Zimp zl = ampin.inputZ()
t.zsrc = zsrc
t.zl = zl
Spectrum sout = t.process(sall)
Spectrum sout2 = gain.process(sout)

//Spectrum sout_p = t.process(s2)
//Spectrum sout2_p = gain.process(sout_p)
 
FreqUtils.plot(sall)
FreqUtils.plot(sout)
FreqUtils.plot(sout2)
//FreqUtils.plot(sout2_p)
//FreqUtils.plotP(t)
//FreqUtils.plotZ(t.inputZ())
println "s1 power="+dB(s1.totalPower())+"dBm"
println "sout power = "+dB(sout2.totalPower())+"dBm"
//println "sout prime power = "+dB(sout2_p.totalPower())+"dBm"



//FreqUtils.plotZ(ampin.inputZ(),false)

