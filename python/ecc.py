#The following code is tested on Python 3.5.2 (2018-02-15)

enableBitcoinParams = True

dump = False #enable this variable to trace extended euclidean algorithm table generation

applyBruteForce = True
applyKeyExchange = True
applyDigitalSignature = True
applySymmetricEncryption = True
applyOrderOfGroup = False
applyECDLP = False

#------------------------------------
#curve configuration

if enableBitcoinParams == True:
	mod = pow(2, 256) - pow(2, 32) - pow(2, 9) - pow(2, 8) - pow(2, 7) - pow(2, 6) - pow(2, 4) - pow(2, 0)
	order = 115792089237316195423570985008687907852837564279074904382605163141518161494337
else:
	mod = 199 #F199
	order = 211 #order of group - number of points on the curve

#curve configuration
# y^2 = x^3 + a*x + b = y^2 = x^3 + 7
a = 0
b = 7

#base point on the curve
if enableBitcoinParams == True:
	x0 = 55066263022277343669578718895168534326250603453777594175500187360389116729240
	y0 = 32670510020758816978083085130507043184471273380659243275938904335757337482424
else:
	x0 = 2
	y0 = 24

print("Curve: y^2 = x^3 + ",a,"*x + ",b)
print("Base point: (",x0,", ",y0,")")

#------------------------------------

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
#brute force
if applyBruteForce == True:
	
	print("\n-----------------------------------------")
	print("brute force")
	print("-----------------------------------------")
	
	print("P: (", x0,", ",y0,")")

	new_x, new_y = pointAddition(x0, y0, x0, y0, a, b, mod)

	print("2 P: (",new_x,", ",new_y,")")

	for i in range(3, 20+1):
		try:
			new_x, new_y = pointAddition(new_x, new_y, x0, y0, a, b, mod)
			print(i,"P: (",new_x,", ",new_y,")")
		except:
			print("order of group: ",i)
			break

#------------------------------------
#key exchange

if applyKeyExchange == True:
	
	print("\n------------------------------------------")
	print("Elliptic Curve Diffie Hellman Key Exchange")
	print("------------------------------------------")

	alicePrivate = 2010000000000017
	alicePublicX, alicePublicY = applyDoubleAndAddMethod(x0, y0, alicePrivate, a, b, mod)
	print("alice public key: (",alicePublicX,", ", alicePublicY,")")

	bobPrivate = 2010000000000061
	bobPublicX, bobPublicY = applyDoubleAndAddMethod(x0, y0, bobPrivate, a, b, mod)
	print("bob public key: (",bobPublicX,", ", bobPublicY,")")
	
	print("")

	aliceSharedX, aliceSharedY = applyDoubleAndAddMethod(bobPublicX, bobPublicY, alicePrivate, a, b, mod)
	print("alice shared key: (",aliceSharedX,", ", aliceSharedY,")")

	bobSharedX, bobSharedY = applyDoubleAndAddMethod(alicePublicX, alicePublicY, bobPrivate, a, b, mod)
	print("bob shared key: (",bobSharedX,", ", bobSharedY,")")

#------------------------------------
#digital signature

if applyDigitalSignature == True:
	
	print("\n------------------------------------------")
	print("Elliptic Curve Digital Signature Algorithm")
	print("------------------------------------------")
	
	message = b"ECC beats RSA"

	import hashlib

	hashHex = hashlib.sha1(message).hexdigest()
	hash = int(hashHex, 16)

	print("message: ", message)
	print("hash: ", hash)

	privateKey = 75263518707598184987916378021939673586055614731957507592904438851787542395619

	publicKeyX, publicKeyY = applyDoubleAndAddMethod(x0, y0, privateKey, a, b, mod)

	print("public key: ",publicKeyX, ", ", publicKeyY)

	randomKey = 28695618543805844332113829720373285210420739438570883203839696518176414791234
	"""import random
	randomKey = random.getrandbits(128)"""
	#print("random key: ", randomKey)

	randomPointX, randomPointY = applyDoubleAndAddMethod(x0, y0, randomKey, a, b, mod)
	print("random point: (", randomPointX,", ",randomPointY)

	#signing

	r = randomPointX % order

	s = hash + (r * privateKey)
	s = s * findModularInverse(randomKey, order)
	s = s % order

	print("signature")
	print("r: ", r)
	print("s: ", s)

	#verification
	print("\nverification...")

	w = findModularInverse(s, order)

	u1x, u1y = applyDoubleAndAddMethod(x0, y0, (hash * w) % order, a, b, mod)
	u2x, u2y = applyDoubleAndAddMethod(publicKeyX, publicKeyY, (r * w) % order, a, b, mod)

	checkpointX, checkpointY = pointAddition(u1x, u1y, u2x, u2y, a, b, mod)
	print("checkpoint: (",checkpointX,", ",checkpointY,")")

	print(checkpointX," ?= ", r)

	if(checkpointX == r):
		print("signature is valid...")
	else:
		print("invalid signature detected!!!")

#------------------------------------
#symmetric encryption

if applySymmetricEncryption == True:
	print("\n------------------------------------------")
	print("Elliptic Curve ElGamal Cryptosystem")
	print("------------------------------------------")

	#1000P
	plaintextX = 33614996735103061868086131503312627786077049888376966084542785773152043381677
	plaintextY = 84557594361191031609962062080128931200952163654712344162477769532776951195137

	print("plaintext: (",plaintextX,", ",plaintextY,")")

	secretKey = 75263518707598184987916378021939673586055614731957507592904438851787542395619

	publicKeyX, publicKeyY = applyDoubleAndAddMethod(x0, y0, secretKey, a, b, mod)

	print("public key: (",publicKeyX, ", ", publicKeyY,")")

	#encryption

	randomKey = 28695618543805844332113829720373285210420739438570883203839696518176414791234
	"""import random
	randomKey = random.getrandbits(128)"""

	c1x, c1y = applyDoubleAndAddMethod(x0, y0, randomKey, a, b, mod)

	c2x, c2y = applyDoubleAndAddMethod(publicKeyX, publicKeyY, randomKey, a, b, mod)
	c2x, c2y = pointAddition(c2x, c2y, plaintextX, plaintextY, a, b, mod)

	print("ciphertext")
	print("c1: (", c1x,", ",c1y,")")
	print("c2: (", c2x,", ",c2y,")")
	print("")

	#decryption
	#message = c2 - secretKey * c1

	dx, dy = applyDoubleAndAddMethod(c1x, c1y, secretKey, a, b, mod)
	dy = dy * -1 #curve is symmetric about x-axis. in this way, inverse point found

	#print("d: (",dx,", ",dy,")")

	decrypted = pointAddition(c2x, c2y, dx, dy, a, b, mod)
	print("decrypted: ",decrypted,"")
	
#------------------------------------

if applyOrderOfGroup == True:
	
	from math import sqrt
	
	Q = applyDoubleAndAddMethod(x0, y0, mod + 1, a, b, mod)
	print("(mod + 1)P = ", mod + 1,"P = ",Q)
	
	m = int(sqrt(sqrt(mod))) + 1
	print("1 + (mod^1/4) = 1 + (",mod,")^1/4 = ",m)
	print()
	
	terminate = False
	
	for j in range (1, m+1):
		
		jP = applyDoubleAndAddMethod(x0, y0, j, a, b, mod)
		print(j,"P = ",jP, " -> ", end="")
		
		for k in range (-m, m+1):
			
			checkpoint = applyDoubleAndAddMethod(x0, y0, m*2*k, a, b, mod)
			checkpoint = pointAddition(checkpoint[0], checkpoint[1], Q[0], Q[1], a, b, mod)
			
			print(checkpoint," ", end="")
			
			if checkpoint[0] == jP[0]: #check x-corrdinates of checkpoint and jP
				
				orderOfGroup = mod + 1 + m*2*k
				
				print("\norder of group should be ", orderOfGroup ," ± ", j)
				
				try:
					applyDoubleAndAddMethod(x0, y0, orderOfGroup + j, a, b, mod)
				except:
					orderOfGroup = orderOfGroup + j
					terminate = True
					break
				try:
					applyDoubleAndAddMethod(x0, y0, orderOfGroup - j, a, b, mod)
				except:
					orderOfGroup = orderOfGroup - j
					terminate = True
					break
		
		print()
		if terminate == True:
			break
	
	print("order of group: ", orderOfGroup)

#-----------------------------------

if applyECDLP == True:
	
	from math import sqrt
	
	k = 177
	publicKey = applyDoubleAndAddMethod(x0, y0, k, a, b, mod)
	print("public key: ", publicKey)

	print("Find k such that ",publicKey," = k x (",x0,", ",y0,")")

	terminate = False
	step = 0
	
	#------------------------
	
	m = int(sqrt(order)) + 1
	
	for i in range(1, m):
		iP = applyDoubleAndAddMethod(x0, y0, i, a, b, mod)
		#print("look for", iP," in the following series ")
		
		for j in range(1, m):
			checkpoint = applyDoubleAndAddMethod(x0, y0, j*m, a, b, mod)
			checkpoint = pointAddition(publicKey[0], publicKey[1], checkpoint[0], -checkpoint[1], a, b, mod)
			#print(checkpoint, " ",end ="")
			
			if iP[0] == checkpoint[0] and iP[1] == checkpoint[1]:
				
				print(i+j*m," mod ",order)
				print("ECDLP solved in", i+m,"th step")
				terminate = True
				break
		
		if terminate == True:
			break
