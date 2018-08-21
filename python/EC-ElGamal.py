import time
import EccCore

#------------------------------------

def textToInt(text):
	encoded_text = text.encode('utf-8')
	hex_text = encoded_text.hex()
	int_text = int(hex_text, 16)
	return int_text

def intToText(int_text):
	import codecs
	hex_text = hex(int_text)
	hex_text = hex_text[2:] #remove 0x
	return codecs.decode(codecs.decode(hex_text,'hex'),'ascii')

#------------------------------------
#curve configuration

mod = pow(2, 256) - pow(2, 32) - pow(2, 9) - pow(2, 8) - pow(2, 7) - pow(2, 6) - pow(2, 4) - pow(2, 0)
order = 115792089237316195423570985008687907852837564279074904382605163141518161494337

#curve configuration
# y^2 = x^3 + a*x + b = y^2 = x^3 + 7
a = 0
b = 7

#base point on the curve
base_point = [55066263022277343669578718895168534326250603453777594175500187360389116729240, 32670510020758816978083085130507043184471273380659243275938904335757337482424]

print("---------------------")
print("initial configuration")
print("---------------------")
print("Curve: y^2 = x^3 + ",a,"*x + ",b, " mod ", mod," , #F(",mod,") = ", order)
print("Base point: (",base_point[0],", ",base_point[1],")")
#print("modulo: ", mod)
#print("order of group: ", order)
print()
#------------------------------------
#symmetric encryption

encryption_begins = time.time()

print("--------------------------------------------------------------")
print("public key generation")

message = 'hi'
plaintext = textToInt(message)
print("message: ",message,". it is numeric matching is ",plaintext)

plain_coordinates = EccCore.applyDoubleAndAddMethod(base_point[0], base_point[1], plaintext, a, b, mod)

print("message is represented as the following point coordinates")
print("plain coordinates: ",plain_coordinates)

secretKey = 75263518707598184987916378021939673586055614731957507592904438851787542395619

publicKey = EccCore.applyDoubleAndAddMethod(base_point[0], base_point[1], secretKey, a, b, mod)

print("\npublic key: ",publicKey)

print("--------------------------------------------------------------")
print("encryption")

randomKey = 28695618543805844332113829720373285210420739438570883203839696518176414791234
#import random
#randomKey = random.getrandbits(128)

c1 = EccCore.applyDoubleAndAddMethod(base_point[0], base_point[1], randomKey, a, b, mod)

c2 = EccCore.applyDoubleAndAddMethod(publicKey[0], publicKey[1], randomKey, a, b, mod)
c2 = EccCore.pointAddition(c2[0], c2[1], plain_coordinates[0], plain_coordinates[1], a, b, mod)

print("\nciphertext")
print("c1: ", c1)
print("c2: ", c2)

encryption_ends = time.time()

print("encryption lasts ",encryption_ends-encryption_begins," seconds")
print("--------------------------------------------------------------")
#plaintext = c2 - secretKey * c1

decryption_begins = time.time()

#secret key times c1
dx, dy = EccCore.applyDoubleAndAddMethod(c1[0], c1[1], secretKey, a, b, mod)
#-secret key times c1
dy = dy * -1 #curve is symmetric about x-axis. in this way, inverse point found

#c2 + secret key * (-c1)
decrypted = EccCore.pointAddition(c2[0], c2[1], dx, dy, a, b, mod)
print("decrypted coordinates: ",decrypted)
	
#-----------------------------------

decrytion_begin = time.time()
new_point = EccCore.pointAddition(base_point[0], base_point[1], base_point[0], base_point[1], a, b, mod) #2P

#brute force method
for i in range(3, order):
	new_point = EccCore.pointAddition(new_point[0], new_point[1], base_point[0], base_point[1], a, b, mod)
	if new_point[0] == decrypted[0] and new_point[1] == decrypted[1]:
		
		print("decrypted message as numeric: ",i)
		print("decrypted message: ",intToText(i))
		
		break

decrytion_end = time.time()
print("decryption lasts ",decrytion_end-decrytion_begin," seconds")