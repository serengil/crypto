import random
from Crypto.Random import get_random_bytes
from Crypto.PublicKey import ElGamal

print("----------------------")
print("key generation")

#p = 961751257 #prime
config = ElGamal.generate(512, get_random_bytes)
p = getattr(config, 'p')

g = 6

x = random.randint(1, p-2)

y = pow(g, x, p)

print("public key: (p=",p,", g=",g,", y=",y,")")
print("private key: ", x)

print("----------------------")
print("encryption")
#Bob knows g, p, y

m = 100
k = random.randint(1, p-1)

c1 = pow(g, k, p)
c2 = m * pow(y, k, p) % p

print("ciphertext: (c1=",c1,", c2=",c2,")")

#bob sends c1, c2 pair to alice

print("----------------------")
print("decryption")

restored = c2 * pow(c1, (p-1-x), p) % p
print("restored message: ",restored)