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
#elliptic curve based functions

def findModularInverse(a, mod):
			
	while(a < 0):
		a = a + mod
	
	#a = a % mod
	
	x1 = 1; x2 = 0; x3 = mod
	y1 = 0; y2 = 1; y3 = a
	
	q = int(x3 / y3)
	t1 = x1 - q*y1
	t2 = x2 - q*y2
	t3 = x3 - (q*y3)
	
	if dump == True:
		print("q\tx1\tx2\tx3\ty1\ty2\ty3\tt1\tt2\tt3")
		print("----------------------------------------------------------------------------")
		print(q,"\t",x1,"\t",x2,"\t",x3,"\t",y1,"\t",y2,"\t",y3,"\t",t1,"\t",t2,"\t",t3)
	
	while(y3 != 1):
		x1 = y1; x2 = y2; x3 = y3
		
		y1 = t1; y2 = t2; y3 = t3
		
		q = int(x3 / y3)
		t1 = x1 - q*y1
		t2 = x2 - q*y2
		t3 = x3 - (q*y3)
		
		if dump == True:
			print(q,"\t",x1,"\t",x2,"\t",x3,"\t",y1,"\t",y2,"\t",y3,"\t",t1,"\t",t2,"\t",t3)
			print("----------------------------------------------------------------------------")
			print("")
	
	while(y2 < 0):
		y2 = y2 + mod
	
	return y2

def pointAddition(x1, y1, x2, y2, a, b, mod):
	
	if x1 == x2 and y1 == y2:
		#doubling
		beta = (3*x1*x1 + a) * (findModularInverse(2*y1, mod))
	
	else:
		#point addition
		beta = (y2 - y1)*(findModularInverse((x2 - x1), mod))
	
	x3 = beta*beta - x1 - x2
	y3 = beta*(x1 - x3) - y1
	
	x3 = x3 % mod
	y3 = y3 % mod
	
	while(x3 < 0):
		x3 = x3 + mod
	
	while(y3 < 0):
		y3 = y3 + mod
	
	return x3, y3

def applyDoubleAndAddMethod(x0, y0, k, a, b, mod):
	
	x_temp = x0
	y_temp = y0
	
	kAsBinary = bin(k) #0b1111111001
	kAsBinary = kAsBinary[2:len(kAsBinary)] #1111111001
	#print(kAsBinary)
	
	for i in range(1, len(kAsBinary)):
		currentBit = kAsBinary[i: i+1]
		#always apply doubling
		x_temp, y_temp = pointAddition(x_temp, y_temp, x_temp, y_temp, a, b, mod)
		
		if currentBit == '1':
			#add base point
			x_temp, y_temp = pointAddition(x_temp, y_temp, x0, y0, a, b, mod)
	
	return x_temp, y_temp

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

publicKey = applyDoubleAndAddMethod(x0, y0, privateKey, a, b, mod)
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