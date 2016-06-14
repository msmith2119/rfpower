

TMatrix filt = new TMatrix("lc")
filt.load()
filt.zsrc = Zimp.constZ(50.0,0.0)
filt.zl = Zimp.constZ(50.0,0.0)
FreqUtils.plotZ(filt.inputZ(),true)
FreqUtils.plotP(filt)
