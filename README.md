# Crypto

This repository covers both fundamentals of public key cryptography algorithms, tutorials and implementations. I've created this repository during the capture of [Elliptic Curve Cryptography Masterclass](https://www.udemy.com/elliptic-curve-cryptography-masterclass/?couponCode=ECCMC-BLOG-201801) and [Public Key Cryptography From Scratch In Python]() online courses published on Udemy.



## Public Key Cryptography From Scratch In Python

1- **Diffie Hellman Key Exchange Algorithm** [`Code`](https://github.com/serengil/crypto/blob/master/python/diffiehellman.py)

2- **RSA** for Encryption, Digital Signature and Key Exchange [`Code`](https://github.com/serengil/crypto/blob/master/python/rsa.py), [`Tutorial`](http://sefiks.com/2018/05/21/the-math-behind-rsa-algorithm/)

3- **El Gamal** for Encryption and Digital Signature [`Code`](https://github.com/serengil/crypto/blob/master/python/elgamal.py)

4- **Digital Signature Algorithm (DSA)** [`Code`](https://github.com/serengil/crypto/blob/master/python/dsa.py)

5- **Discrete Logarighm Problem** [`Code`](https://github.com/serengil/crypto/blob/master/python/discretelogarithm.py)



## Elliptic Curve Cryptography Masterclass

1- **ECC with Python** [`Code`](https://github.com/serengil/crypto/blob/master/python/EccApp.py)

2- **Edwards Curve Digital Signature Algorithm** [Code](https://github.com/serengil/crypto/blob/master/python/EdDSA.py), [`Tutorial`](https://sefiks.com/2018/12/24/a-gentle-introduction-to-edwards-curve-digital-signature-algorithm-eddsa/)

3- **Finding Bitcoin Address** [`Code`](https://github.com/serengil/crypto/blob/master/python/Bitcoin.py), [`Tutorial`](https://sefiks.com/2018/03/26/a-step-by-step-bitcoin-address-example/)

4- **Elliptic Curve ElGamal** [`Code`](https://github.com/serengil/crypto/blob/master/python/EC-ElGamal.py)

5- **ECC with Java** [`Up-to-date Code`](https://github.com/serengil/crypto/blob/master/com.crypto.action/EccOverFiniteField.java), [`Legacy Code`](https://github.com/serengil/crypto/blob/master/com.crypto.action/EccOverRealNumbers.java)



## Tutorials for Elliptic Curve Cryptography

Elliptic Curve Cryptography is a complex topic. You should read and understand the following tutorials to understand the background of the cryptosystem.

### The Math Behind Elliptic Curves

1- [**Elliptic Curves in Weirstrass Form**](https://sefiks.com/2016/03/13/the-math-behind-elliptic-curve-cryptography/)

This is the most popular elliptic curve form. Bitcoin uses this type of elliptic curve.

2- [**Elliptic Curves in Koblitz Form**](https://sefiks.com/2016/03/13/the-math-behind-elliptic-curves-over-binary-field/)

Koblitz curves are popular in hardware implemantations of elliptic curves.

3- [**Edwards Curves**](https://sefiks.com/2018/12/19/a-gentle-introduction-to-edwards-curves/)

Elliptic curves in Edwards form offer faster calculations when compared to curves in Weirstrass and Koblitz forms.

4- [**Twisted Edwards Curves**](https://sefiks.com/2018/12/26/twisted-edwards-curves/)

This modified type of Edwards Curve will be used in EdDSA.

### Elliptic Curve Arithmetic

5- [Point Addition on Elliptic Curves](http://sefiks.com/2016/03/27/double-and-add-method/)

### Implementations

6- [**Elliptic Curve Diffie Hellman Key Exchange**](https://sefiks.com/2016/04/11/key-exchange-from-carrying-handcuffed-briefcases-to-modern-cryptosystems/)

7- [**Elliptic Curve Digital Signature Algorithm (ECDSA)**](https://sefiks.com/2018/02/16/elegant-signatures-with-elliptic-curve-cryptography/)

8- [**Edwards Curve Digital Signature Algorithm (EdDSA)**](https://sefiks.com/2018/12/24/a-gentle-introduction-to-edwards-curve-digital-signature-algorithm-eddsa/)

9- [**Elliptic Curve ElGamal Symmetric Encryption**](https://sefiks.com/2018/08/21/elliptic-curve-elgamal-encryption/)

This is a de facto implementation of symmetric encryption of ECC. We should encrypt / decrypt points on the curve but handling texts requires to solve ECDLP. That's why, we can just encrypt / decrypt "Hi" message.

### Objectives

10- [**Elliptic Curve Discrete Logarithm Problem**](https://sefiks.com/2018/02/28/attacking-elliptic-curve-discrete-logarithm-problem/)

11- [**Order of Group in Elliptic Curves**](https://sefiks.com/2018/02/27/counting-points-on-elliptic-curves-over-finite-field/)



## Historical Cryptography

1- **Hill Cipher** [`Code`](https://github.com/serengil/crypto/blob/master/python/classical/hill.py), [`Tutorial`](https://sefiks.com/2018/12/04/a-step-by-step-hill-cipher-example/)

Hill cipher is a strong historical method based on matrix multiplication.



# License

This repository is licensed under MIT license - see [`LICENSE`](https://github.com/serengil/crypto/blob/master/LICENSE) for more details
