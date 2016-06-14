

new File("filter.csv").splitEachLine(",") {fields ->
  f.add(f[0])
  zr.add(f[1])
  zi.add(f[2]) 
  println fields[0] 
}
