import cryptocommons as commons

print("key generation")

base = 3
p = 1279 #prime

#p = a * q + 1
def testPrimeness(number):
	for i in range(2, number):
		if number % i == 0:
			return False
			break
	return True

for i in range(10, p):
	if (p-1) % i == 0 and testPrimeness(i):
		q = i
		break

a = int((p-1)/q)
print(p," = ",a," * ",q," + 1")

g = pow(base, a, p) 

x = 15 # private key

y = pow(g, x, p)

print("private key: ",x)
print("public key: ",y)
print("public params: p=",p,", q=",q,", g=",g,"")

#-------------------------------

print("signing")

k = 10 #random key

h = 123

r = pow(g, k, p) % q
s = commons.modInverse(k, q) * (h + x*r) % q

print("signature: (r=",r,", s=",s,")")

#-------------------------------
print("verification")

h = 123

#Bob knows public key -> y
#Also, he knows public params -> p, q, g
#Also, he knows h, (r, s) pair

w = commons.modInverse(s, q)
u1 = h * w % q
u2 = r * w % q
v = ((pow(g, u1, p) * pow(y, u2, p)) % p) % q

print("v:", v)
print("r: ",r)

if v == r:
	print("signature is valid")
else:
	print("invalid signature is detected")
