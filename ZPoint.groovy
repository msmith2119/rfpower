

class ZPoint { 

double f
double zr
double zi

ZPoint(f,zr,zi) { this.f = f; this.zr=zr; this.zi=zi }



ZPoint scale(double a) {



return  new ZPoint(f,zr*a,zi*a)
 
}
ZPoint add(ZPoint other) { 

 return new ZPoint(f,zr + other.zr,zi + other.zi)

}

ZPoint subtract(ZPoint other) { 

 return new ZPoint(f,zr - other.zr,zi - other.zi)

}

ZPoint mult(ZPoint other) { 
 return new ZPoint(f,zr*other.zr-zi*other.zi,zr*other.zi+zi*other.zr)
}

ZPoint div(ZPoint other) { 
       double z2 = other.mag()*other.mag()
       double realz = (zr*other.zr + zi*other.zi)/z2
       double imagz = (zi*other.zr  - zr*other.zi)/z2
       return new  ZPoint(f,realz, imagz)
}
ZPoint negate(ZPoint other) { 
       return new ZPoint(f,-1*zr,-1*zi)
}



double mag() { return Math.sqrt(zr*zr + zi*zi)}
double phase() { 

return Math.atan(zi/zr)
 } 
double cos() { 

 return zr/mag()
}

String toString() { return f+","+zr+","+zi }
}