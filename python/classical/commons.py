import string

def preprocessing(m):
    m = m.replace(" ","").replace(",","").replace(".","").replace("'","").replace(":","").replace(";","")
    m = m.lower()
    return m

def lettersOfPlaintext(m):
    letters = []
    for i in range(0, len(m)):
        letters.append(m[i])
    return letters

def letterToNumber(letter):
    return string.ascii_lowercase.index(letter)

def numberToLetter(number):
    return chr(int(number) + 97)

def module(letter_index):
    
    while(letter_index < 0):
        letter_index += 26
       
    while(letter_index > 25):
        letter_index -= 26
    
    return letter_index
    