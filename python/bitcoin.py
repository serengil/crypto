import ecc
import codecs
import hashlib

#------------------------------------

dump = False
applyBitcoinAddress = True

#------------------------------------
#curve configuration

mod = pow(2, 256) - pow(2, 32) - pow(2, 9) - pow(2, 8) - pow(2, 7) - pow(2, 6) - pow(2, 4) - pow(2, 0)
order = 115792089237316195423570985008687907852837564279074904382605163141518161494337

#curve configuration
# y^2 = x^3 + a*x + b = y^2 = x^3 + 7
a = 0
b = 7

#base point on the curve
x0 = 55066263022277343669578718895168534326250603453777594175500187360389116729240
y0 = 32670510020758816978083085130507043184471273380659243275938904335757337482424

print("---------------------")
print("initial configuration")
print("---------------------")
print("Curve: y^2 = x^3 + ",a,"*x + ",b)
print("Base point: (",x0,", ",y0,")\n")
print("modulo: ", mod)
print("order of group: ", order)

#------------------------------------
#subsdiary functions

def hexStringToByte(content):
	return codecs.decode(content.encode("utf-8"), 'hex')

def hashHex(algorithm, content):
	my_sha = hashlib.new(algorithm)
	my_sha.update(hexStringToByte(content))
	return my_sha.hexdigest()
	
print("-----------------------")
print("generate bitcoin addres")
print("-----------------------")

privateKey = 11253563012059685825953619222107823549092147699031672238385790369351542642469

#print("private key: ", privateKey)
print("private key (hex): ", hex(privateKey)[2:]," (keep this secret!)\n")

publicKey = ecc.applyDoubleAndAddMethod(x0, y0, privateKey, a, b, mod)
#print("public key: ", publicKey)

publicKeyHex = "04"+hex(publicKey[0])[2:]+hex(publicKey[1])[2:]
print("public key (hex): ", publicKeyHex)

publicKeyMerged = int(publicKeyHex, 16)
#print(publicKeyMerged)

print("---------------------------------------")

output = hashHex('sha256', publicKeyHex)	
print("apply sha-256 to public key: ",output)

output = hashHex('ripemd160', output)	
print("apply ripemd160 to sha-256 applied public key: ", output)

output = "00"+output
print("add network bytes to ripemd160 applied hash - extended ripemd160: ", output,"\n")

print("checksum calculation")

checksum = hashHex('sha256', output)
print("apply sha-256 to extended ripemd160: ", checksum)

checksum = hashHex('sha256', checksum)
print("apply second time sha-256: ", checksum)

checksum = checksum[0:8]
print("extract first 8 characters as checksum: ", checksum)

address = output+checksum
print("append checksum to extended ripemd160", address)

print("---------------------------------------")

import base58
address = base58.b58encode(hexStringToByte(address))
print("this is your bitcoin address:",str(address)[2:len(address)-2])
