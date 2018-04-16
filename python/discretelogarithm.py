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

import math

#2^privatekey mod 19 = 15
n = 19; a=2; B=15

privatekey = 0
#brute force O(n)
for i in range(n):
	if pow(2,i,n) == B:
		privatekey = i
		break

print("brute force result: ",privatekey)

#shank algorithm O(sqrt(n))

terminate = False

m = int(math.sqrt(n-1))+1
for i in range(0, m):
	t1 = pow(a, m*i, n)
	#print(t1)
	for j in range(0,m):
		t2 = B * modInverse(pow(a, j),n) % n
		#print("\t",t2)
		if t1 == t2:
			#print("two tables have ",t1," value")
			print("shank algorithm result: ",m*i + j)
			terminate = True
			break
	
	if terminate == True:
		break