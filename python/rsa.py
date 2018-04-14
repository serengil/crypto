import time
import random

starttime = time.time()

"""p = 999900048617
q = 999900049387"""

from Crypto.PublicKey import RSA
RSAkey = RSA.generate(1024)
p = getattr(RSAkey.key, 'p')
q = getattr(RSAkey.key, 'q')

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
print("-------------------------------")

publickey = e 
privatekey = d

#--------------------------------
print("-------------------------")
print("message encryption - decryption")
print("-------------------------")
m = 11

ciphertext = pow(m, e, n)
print("ciphertext: ",ciphertext)
print("encryption is complete in ",time.time() - starttime," seconds\n")

restored = pow(ciphertext, d, n)
print("restored: ", restored)
print("decryption is complete in ",time.time() - starttime," seconds")

#--------------------------------
print("-------------------------")
print("digital signature")
print("-------------------------")

import hashlib

print("----------------\nAlice")

message = b'hello, world!'

hashHex = hashlib.sha256(message).hexdigest()
hash = int(hashHex, 16)

print("message", message)
print("hash: ",hash)

signature = pow(hash, privatekey, n)
print("signature: ",signature)

#alice sends bob message, signature
#--------------------------------

print("----------------\nBob")

decryptedSignature = pow(signature, publickey, n)
print("decryptedSignature: ",decryptedSignature)

bobHashHex = hashlib.sha256(message).hexdigest()
bobHash = int(bobHashHex, 16)
print("Bob calculates this hash value: ",bobHash)

if bobHash == decryptedSignature:
	print("signature is valid")
else:
	print("signature is not valid!!!")

#--------------------------------
print("-------------------------")
print("key exchange")
print("-------------------------")

print("Bob:")

key = 1234567891234567 #16 byte
encryptedkey = pow(key, publickey, n)
print("encryptedkey: ",encryptedkey)

message = "hi alice, howdy?"

from Crypto.Cipher import AES

obj = AES.new(str(key))
ciphertext = obj.encrypt(message)

print("ciphertext: ", ciphertext)

#now, bob sends ciphertext and encrypted key to Alice
#--------------------------------
print("----------------------\nAlice")

restoredkey = pow(encryptedkey, privatekey, n)
print("restored key: ",restoredkey)

obj2 = AES.new(str(restoredkey))
restoredtext = obj2.decrypt(ciphertext)

print("restoredtext: ",restoredtext)
