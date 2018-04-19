def modInverse(number, mod):
	x1 = 1; x2 = 0; x3 = mod
	y1 = 0; y2 = 1; y3 = number
	
	q = int(x3 / y3)
	
	t1 = x1 - q * y1
	t2 = x2 - q * y2
	t3 = x3 - q * y3
	
	while y3 != 1:
		x1 = y1;x2 = y2;x3= y3
		y1 = t1;y2 = t2; y3 = t3
		
		q = int(x3 / y3)
		t1 = x1 - q * y1
		t2 = x2 - q * y2
		t3 = x3 - q * y3
		
	if y2 < 0:
		while y2 < 0:
			y2 = y2 + mod
	
	return y2

def gcd(a, b):
	if a%b == 0:
		return b
	else:
		return gcd(b, a%b)