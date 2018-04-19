import cryptocommons as commons
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
		t2 = B * commons.modInverse(pow(a, j),n) % n
		#print("\t",t2)
		if t1 == t2:
			#print("two tables have ",t1," value")
			print("shank algorithm result: ",m*i + j)
			terminate = True
			break
	
	if terminate == True:
		break
