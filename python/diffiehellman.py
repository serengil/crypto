import random

mod = random.getrandbits(512) #prime
g = 6

print("public information:")
print("mod",mod)
print("base generator: ",g)

print("--------------------")

alicePrivate = random.getrandbits(512)
alicePublic = pow(g, alicePrivate, mod)
print("alice public: ",alicePublic)

bobPrivate = random.getrandbits(512)
bobPublic = pow(g, bobPrivate, mod)
print("bob public: ",bobPublic)

print("--------------------")

aliceShared = pow(bobPublic, alicePrivate, mod)
bobShared = pow(alicePublic, bobPrivate, mod)

print("-alice shared: ",aliceShared)
print("\n-bob shared: ",bobShared)

print("--------------------")

message = "hi alice, howdy?"

from Crypto.Cipher import AES
obj = AES.new(str(aliceShared)[0:32])

ciphertext = obj.encrypt(message)

print("ciphertext: ",ciphertext)

print("--------------------")
#bob will decrypt the message
obj2 = AES.new(str(bobShared)[0:32])
plaintext = obj2.decrypt(ciphertext)

print("plaintext: ",plaintext)

