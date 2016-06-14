

s="10.0k"

def m = (s =~ /([\d\.]+)([fpnumkMG]?)$/ ) 

println m[0][1]   

