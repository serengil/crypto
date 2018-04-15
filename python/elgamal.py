import random
from Crypto.Random import get_random_bytes
from Crypto.PublicKey import ElGamal

print("-----------------------")
print("key generation")
print("-----------------------")

p = 961751257 #prime
"""config = ElGamal.generate(512, get_random_bytes)
p = getattr(config, 'p')"""

g = 6

x = random.randint(1, p-2)

y = pow(g, x, p)

print("public key: (p=",p,", g=",g,", y=",y,")")
print("private key: ", x)

print("-----------------------")
print("encryption decryption")
print("-----------------------")
print("encryption")
#Bob knows g, p, y

m = 100
k = random.randint(1, p-1)

c1 = pow(g, k, p)
c2 = m * pow(y, k, p) % p

print("ciphertext: (c1=",c1,", c2=",c2,")")

#bob sends c1, c2 pair to alice

print("decryption")

restored = c2 * pow(c1, (p-1-x), p) % p
print("restored message: ",restored)


#------------------------------
def gcd(a, b):
	if a%b == 0:
		return b
	else:
		return gcd(b, a%b)

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
#------------------------------
#digital signatures

print("-----------------------")
print("digital signature")
print("-----------------------")

print("signing")

hash = 100

k = random.randint(1,p-1)

while gcd(p-1, k) != 1:
	k = random.randint(1,p-1)
	
#print("random key: ",k)

r = pow(g, k, p)
s = (hash - x*r) * modInverse(k, p-1) % (p-1)

print("signature: (r=",r,", s=",s,")")

print("verification")

hash = 100

checkpoint1 = pow(g, hash, p)
checkpoint2 = (pow(y,r,p) * pow(r,s,p)) % p

print("checkpoint1: ",checkpoint1)
print("checkpoint2: ",checkpoint2)

if checkpoint1 == checkpoint2:
	print("signature is valid")
else:
	print("invalid signature detected")

print("-----------------------")
