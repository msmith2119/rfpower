

class FilterBase { 


 FilterBase(String name) { 
 this.name = name 
}

String name
List points

double power(int i ) { 
return 0.0
}

 public  int  findIndex(double f) {

        int index = 0

	if(f < points[0].f){
	     return 
	}

	if(f > points[points.size()-1].f ) { 
	     return  points.size()-2
	}   
  
	for(int i  = 0; i < points.size()-1 ; i++ ) { 
		if(f > points[i].f && f <points[i+1].f){
		     index = i
		     break
		}
	}    

	return index 

}

}