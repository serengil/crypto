import string
import numpy as np
import math
from sympy import Matrix #inverse key

#-------------------------
def letterToNumber(letter):
	return string.ascii_lowercase.index(letter)

def numberToLetter(number):
	return chr(int(number) + 97)
#-------------------------

module = 26 #english alphabet

#raw_message = "act"
raw_message = "attack is to night"
print("raw message: ",raw_message)

message = []

key = np.array([
	[3, 10, 20], 
	[20, 9, 17], 
	[9, 4, 17]
]) #3x3

key_rows = key.shape[0]
key_columns = key.shape[1]

if key_rows != key_columns:
	raise Exception('key must be square matrix!')

#-------------------------
for i in range(0, len(raw_message)):
	current_letter = raw_message[i:i+1].lower()
	if current_letter != ' ':
		letter_index = letterToNumber(current_letter)
		message.append(letter_index)
		
#-------------------------
#message must be multiplier of key line count.
#if not append beginning of the message to the end

if len(message) % key_rows != 0:
	for i in range(0, len(message)):
		message.append(message[i])
		if len(message) % key_rows == 0:
			break

#------------------------
#transform message to numpy array. we'll use numpy's matrix multiplication
message = np.array(message)
message_length = message.shape[0]
print("message: ",message)

#transform message array to matrix
message.resize(int(message_length/key_rows), key_rows)
#------------------------

encryption = np.matmul(message, key)
encryption = np.remainder(encryption, module)
print("encrypted text: \n",encryption)

#------------------------

#decryption
#------------------------
print("finding inverse key")
inverse_key = Matrix(key).inv_mod(module)
inverse_key = np.array(inverse_key) #sympy to numpy
inverse_key = inverse_key.astype(float)
print("inverse key: ",inverse_key)

#------------------------
print("validating inverse key. key times inverse key must be idendity matrix")
check = np.matmul(key, inverse_key)
check = np.remainder(check, module)
print("key times inverse key: ",check)
print("it is ",np.allclose(check, np.eye(3)))

#------------------------
print("decryption:")
decryption = np.matmul(encryption, inverse_key)
decryption = np.remainder(decryption, module).flatten()
print("decryption: ",decryption)

decrypted_message = ""
for i in range(0, len(decryption)):
	letter_num = int(decryption[i])
	letter = numberToLetter(decryption[i])
	decrypted_message = decrypted_message + letter
	
print("decrypted message: ",decrypted_message)