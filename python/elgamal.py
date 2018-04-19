import random
from Crypto.Random import get_random_bytes
from Crypto.PublicKey import ElGamal

import cryptocommons as commons

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
#digital signatures

print("-----------------------")
print("digital signature")
print("-----------------------")

print("signing")

hash = 100

k = random.randint(1,p-1)

while commons.gcd(p-1, k) != 1:
	k = random.randint(1,p-1)
	
#print("random key: ",k)

r = pow(g, k, p)
s = (hash - x*r) * commons.modInverse(k, p-1) % (p-1)

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
