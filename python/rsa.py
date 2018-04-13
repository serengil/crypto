import time
import random

starttime = time.time()

p = 982449707
q = 961749331

n = p*q
totient = (p-1)*(q-1)

print("mod: ",n)
print("totient function",totient)

e = random.randint(1, totient)

#e and totient must be coprime

def gcd(a, b):
	if a%b == 0:
		return b
	else:
		return gcd(b, a%b)

while gcd(totient, e) != 1:
	e = random.randint(1, totient)

print("public key: ",e)

#-------------------------------
#find multiplicative inverse of e mod totient

#brute force
"""d=0
for i in range(totient):
	if (e*i) % totient == 1:
		d = i
		break
"""

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
	
d = modInverse(e, totient)

print("private key: ",d)

print("key generation is complete in ",time.time() - starttime," seconds\n")

#--------------------------------

m = 11

ciphertext = pow(m, e, n)
print("ciphertext: ",ciphertext)
print("encryption is complete in ",time.time() - starttime," seconds\n")

restored = pow(ciphertext, d, n)
print("restored: ", restored)
print("decryption is complete in ",time.time() - starttime," seconds")

