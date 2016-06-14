
import flanagan.complex.*


class  Zimp { 

Zimp(Closure calc){
this.calc = calc
}


Closure calc

String name = "unnamed"



public ZPoint z(double f ){

return calc(f)

}


public Complex zc(double f ) { 
ZPoint z = z(f)
return new Complex(z.zr,z.zi)
}

static Zimp constZ(double zx, double zy) { 

ZPoint z = new ZPoint(0,zx,zy)

return new Zimp({return z}) 

}


}



