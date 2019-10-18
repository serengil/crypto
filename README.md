# crypto

This repository covers both fundamentals of public key cryptography algorithms, tutorials and implementations. I've created this repository during the capture of the following online courses published on Udemy.

[**Elliptic Curve Cryptography Masterclass**](https://www.udemy.com/course/elliptic-curve-cryptography-masterclass/?referralCode=C5E74D486DD29F6DAF4A)

[**Public Key Cryptography From Scratch In Python**](https://www.udemy.com/course/public-key-cryptography-from-scratch-in-python/?referralCode=8AF2BB504D810A4C99CB)

[**Cryptography Basics From Scratch In Python**](https://www.udemy.com/course/cryptography-basics-from-scratch-in-python/?referralCode=0B65C57251E2674FAC2C)



## Public Key Cryptography From Scratch In Python

1- **Diffie Hellman Key Exchange Algorithm** [`Code`](https://github.com/serengil/crypto/blob/master/python/diffiehellman.py)

2- **RSA** for Encryption, Digital Signature and Key Exchange [`Code`](https://github.com/serengil/crypto/blob/master/python/rsa.py), [`Tutorial`](http://sefiks.com/2018/05/21/the-math-behind-rsa-algorithm/)

3- **El Gamal** for Encryption and Digital Signature [`Code`](https://github.com/serengil/crypto/blob/master/python/elgamal.py)

4- **Digital Signature Algorithm (DSA)** [`Code`](https://github.com/serengil/crypto/blob/master/python/dsa.py)

5- **Discrete Logarighm Problem** [`Code`](https://github.com/serengil/crypto/blob/master/python/discretelogarithm.py)



## Elliptic Curve Cryptography Masterclass In Python

1- **Elliptic Curve Cryptography with Python** [`Code`](https://github.com/serengil/crypto/blob/master/python/EccApp.py), [`Tutorial`](https://sefiks.com/2016/03/13/the-math-behind-elliptic-curve-cryptography/), [`Video`](https://youtu.be/iydGkrjJkSM)

This code covers key exchange, digital signature, symmetric encryption, order of group (number of points in finite field) and elliptic curve discrete logarithm problem. This is dependent to [EccCore.py](https://github.com/serengil/crypto/blob/master/python/EccCore.py).

2- **Edwards Curve Digital Signature Algorithm** [`Code`](https://github.com/serengil/crypto/blob/master/python/EdDSA.py), [`Tutorial`](https://sefiks.com/2018/12/24/a-gentle-introduction-to-edwards-curve-digital-signature-algorithm-eddsa/)

[Edwards curves](https://sefiks.com/2018/12/19/a-gentle-introduction-to-edwards-curves/) offer faster calculations than regular elliptic curve forms.

3- **Finding Bitcoin Address** [`Code`](https://github.com/serengil/crypto/blob/master/python/Bitcoin.py), [`Tutorial`](https://sefiks.com/2018/03/26/a-step-by-step-bitcoin-address-example/), [`Configuration`](https://github.com/serengil/crypto/blob/master/configuration/bitcoin-configuration.txt)

A bitcoin address consists of a public key.

4- **Elliptic Curve ElGamal** [`Code`](https://github.com/serengil/crypto/blob/master/python/EC-ElGamal.py)

Previously, we have implemented symmetric encryption but in that case we encrypt and decrypt a point on the curve. Now, we will transform a text message to a elliptic curve point and apply encryption. However, this is a de facto implementation because decryption requires to solve ECDLP.

## Elliptic Curve Cryptography Masterclass In Java

1- **Elliptic Curve Cryptography with Java** [`Up-to-date Code`](https://github.com/serengil/crypto/blob/master/com.crypto.action/EccOverFiniteField.java), [`Legacy Code`](https://github.com/serengil/crypto/blob/master/com.crypto.action/EccOverRealNumbers.java)

This java project is dependent to [entity](https://github.com/serengil/crypto/tree/master/com.crypto.entity) objects.

## Cryptography Basics From Scratch In Python



1- **Caesar Cipher** [`Code`](https://github.com/serengil/crypto/blob/master/python/classical/Caesar.ipynb)

2- **Substitution Cipher** [`Code`](https://github.com/serengil/crypto/blob/master/python/classical/Substitution.ipynb)

3- **Affine Cipher** [`Code`](https://github.com/serengil/crypto/blob/master/python/classical/Affine.ipynb)

4- **Homophonic Substitution Cipher** [`Code`](https://github.com/serengil/crypto/blob/master/python/classical/Homophonic.ipynb)

5- **Permutation Cipher** [`Code`](https://github.com/serengil/crypto/blob/master/python/classical/Permutation.ipynb)

6- **Hill Cipher** [`Code`](https://github.com/serengil/crypto/blob/master/python/classical/Hill.ipynb), [`Tutorial`](https://sefiks.com/2018/12/04/a-step-by-step-hill-cipher-example/)

7- **Vigénere Cipher** [`Code`](https://github.com/serengil/crypto/blob/master/python/classical/Vigenere.ipynb)

8- **Kasiski Examination** [`Code`](https://github.com/serengil/crypto/blob/master/python/classical/Kasiski.ipynb)

9- **Enigma Machine** [`Video`](https://youtu.be/XPd8LCxwrsc)

# Support

There are many ways to support a project - starring the GitHub repos is one.

# License

This repository is licensed under MIT license - see [`LICENSE`](https://github.com/serengil/crypto/blob/master/LICENSE) for more details
