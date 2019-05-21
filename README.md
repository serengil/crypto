# Crypto

# Public Key Cryptography From Scratch

This repository covers codes for both [Elliptic Curve Cryptography Masterclass](https://www.udemy.com/elliptic-curve-cryptography-masterclass/?couponCode=ECCMC-BLOG-201801) and [Public Key Cryptography From Scratch In Python](https://www.udemy.com/public-key-cryptography-from-scratch-in-python/?couponCode=PCC-101-BLOG-1804) online courses published on Udemy.

1- [Diffie Hellman Key Exchange Algorithm](https://github.com/serengil/crypto/blob/master/python/diffiehellman.py)

2- [RSA Encryption, Digital Signature, Key Exchange](https://github.com/serengil/crypto/blob/master/python/rsa.py)

3- [El Gamal Encryption and Digital Signature](https://github.com/serengil/crypto/blob/master/python/elgamal.py)

4- [Digital Signature Algorithm DSA](https://github.com/serengil/crypto/blob/master/python/dsa.py)

5- [Discrete Logarighm Problem](https://github.com/serengil/crypto/blob/master/python/discretelogarithm.py)

# Elliptic Curve Cryptography Masterclass

1- ECC with Python [Code](Public Key Cryptography From Scratch)

This covers Key Exchange, Digital Signature, Symmetric Encryption for points on the curve. Also, we will mention order of group (number of points on the curve) and Elliptic Curve Discrete Logarithm Problem (ECDLP)

2- Edwards Curve Digital Signature Algorithm [Code](https://github.com/serengil/crypto/blob/master/python/EdDSA.py), [Tutorial](https://sefiks.com/2018/12/24/a-gentle-introduction-to-edwards-curve-digital-signature-algorithm-eddsa/)

Elliptic curves in [Edwards form](https://sefiks.com/2018/12/19/a-gentle-introduction-to-edwards-curves/) offer faster implementations.

3- Finding Bitcoin Address [Code](https://github.com/serengil/crypto/blob/master/python/Bitcoin.py), [Tutorial](https://sefiks.com/2018/03/26/a-step-by-step-bitcoin-address-example/)

4- [Elliptic Curve ElGamal](https://github.com/serengil/crypto/blob/master/python/EC-ElGamal.py)

This is a de facto implementation of symmetric encryption of ECC. We should encrypt / decrypt points on the curve but handling texts requires to solve ECDLP. That's why, we can just encrypt / decrypt "Hi" message.

5- [ECC with Java](https://github.com/serengil/crypto/tree/master/com.crypto.action)

com.crypto.action.EccOverFiniteField.java is the up-to-date file and it works on finite fields. In other words, key exchanging and other stuff will be handled on integers. Run this file please. 

com.crypto.action.EccOverRealNumbers.java is a legacy file, and it handles key exchange on real numbers. You should run it only if you wonder how ECC formulas work theoretically.

# Historical Cryptography

1- Hill Cipher [Code](https://github.com/serengil/crypto/blob/master/python/classical/hill.py), [Tutorial](https://sefiks.com/2018/12/04/a-step-by-step-hill-cipher-example/)

Hill cipher is a strong historical method based on matrix multiplication.

License
=======

This repository is licensed under MIT license - see [LICENSE](https://github.com/serengil/crypto/blob/master/LICENSE) for more details
