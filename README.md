# crypto

This repository covers both fundamentals of public key cryptography algorithms, tutorials and implementations. I've created this repository during the capture of [**Elliptic Curve Cryptography Masterclass**](https://www.udemy.com/elliptic-curve-cryptography-masterclass/?couponCode=ECCMC-BLOG-201801) and [**Public Key Cryptography From Scratch In Python**](https://www.udemy.com/public-key-cryptography-from-scratch-in-python/?couponCode=PCC-101-BLOG-1804) online courses published on Udemy.



## Public Key Cryptography From Scratch In Python

1- **Diffie Hellman Key Exchange Algorithm** [`Code`](https://github.com/serengil/crypto/blob/master/python/diffiehellman.py)

2- **RSA** for Encryption, Digital Signature and Key Exchange [`Code`](https://github.com/serengil/crypto/blob/master/python/rsa.py), [`Tutorial`](http://sefiks.com/2018/05/21/the-math-behind-rsa-algorithm/)

3- **El Gamal** for Encryption and Digital Signature [`Code`](https://github.com/serengil/crypto/blob/master/python/elgamal.py)

4- **Digital Signature Algorithm (DSA)** [`Code`](https://github.com/serengil/crypto/blob/master/python/dsa.py)

5- **Discrete Logarighm Problem** [`Code`](https://github.com/serengil/crypto/blob/master/python/discretelogarithm.py)



## Elliptic Curve Cryptography Masterclass In Python

1- **Elliptic Curve Cryptography with Python** [`Code`](https://github.com/serengil/crypto/blob/master/python/EccApp.py)

This code covers key exchange, digital signature, symmetric encryption, order of group (number of points in finite field) and elliptic curve discrete logarithm problem. This is dependent to [EccCore.py](https://github.com/serengil/crypto/blob/master/python/EccCore.py).

2- **Edwards Curve Digital Signature Algorithm** [`Code`](https://github.com/serengil/crypto/blob/master/python/EdDSA.py), [`Tutorial`](https://sefiks.com/2018/12/24/a-gentle-introduction-to-edwards-curve-digital-signature-algorithm-eddsa/)

Edwards curves offer faster calculations than regular elliptic curve forms.

3- **Finding Bitcoin Address** [`Code`](https://github.com/serengil/crypto/blob/master/python/Bitcoin.py), [`Tutorial`](https://sefiks.com/2018/03/26/a-step-by-step-bitcoin-address-example/), [`Configuration`](https://github.com/serengil/crypto/blob/master/configuration/bitcoin-configuration.txt)

A bitcoin address consists of a public key.

4- **Elliptic Curve ElGamal** [`Code`](https://github.com/serengil/crypto/blob/master/python/EC-ElGamal.py)

Previously, we have implemented symmetric encryption but in that case we encrypt and decrypt a point on the curve. Now, we will transform a text message to a elliptic curve point and apply encryption. However, this is a de facto implementation because decryption requires to solve ECDLP.

## Elliptic Curve Cryptography Masterclass In Java

1- **Elliptic Curve Cryptography with Java** [`Up-to-date Code`](https://github.com/serengil/crypto/blob/master/com.crypto.action/EccOverFiniteField.java), [`Legacy Code`](https://github.com/serengil/crypto/blob/master/com.crypto.action/EccOverRealNumbers.java)

This java project is dependent to [entity](https://github.com/serengil/crypto/tree/master/com.crypto.entity) objects.

## Tutorials for Elliptic Curve Cryptography

Elliptic Curve Cryptography is a complex topic. You should read and understand the following tutorials to understand the background of the cryptosystem.

### The Math Behind Elliptic Curves

1- **Elliptic Curves in Weirstrass Form** [`Tutorial`](https://sefiks.com/2016/03/13/the-math-behind-elliptic-curve-cryptography/), [`Video`](https://youtu.be/iydGkrjJkSM)

This is the most popular elliptic curve form. Bitcoin uses this type of elliptic curve.

2- **Elliptic Curves in Koblitz Form** [`Tutorial`](https://sefiks.com/2016/03/13/the-math-behind-elliptic-curves-over-binary-field/), [`Video`](https://youtu.be/g8ePU5U5oP8)

Koblitz curves are popular in hardware implemantations of elliptic curves.

3- **Edwards Curves** [`Tutorial`](https://sefiks.com/2018/12/19/a-gentle-introduction-to-edwards-curves/), [`Video`](https://youtu.be/Yn1kD1rNmns)

Elliptic curves in Edwards form offer faster calculations when compared to curves in Weirstrass and Koblitz forms.

4- **Twisted Edwards Curves** [`Tutorial`](https://sefiks.com/2018/12/26/twisted-edwards-curves/)

This modified type of Edwards Curve will be used in EdDSA.

### Elliptic Curve Arithmetic

5- **Point Addition on Elliptic Curves** [`Tutorial`](http://sefiks.com/2016/03/27/double-and-add-method/)

### Implementations

6- **Elliptic Curve Diffie Hellman Key Exchange** [`Tutorial`](https://sefiks.com/2016/04/11/key-exchange-from-carrying-handcuffed-briefcases-to-modern-cryptosystems/)

7- **Elliptic Curve Digital Signature Algorithm (ECDSA)** [`Tutorial`](https://sefiks.com/2018/02/16/elegant-signatures-with-elliptic-curve-cryptography/)

8- **Edwards Curve Digital Signature Algorithm (EdDSA)** [`Tutorial`](https://sefiks.com/2018/12/24/a-gentle-introduction-to-edwards-curve-digital-signature-algorithm-eddsa/)

9- **Elliptic Curve ElGamal Symmetric Encryption** [`Tutorial`](https://sefiks.com/2018/08/21/elliptic-curve-elgamal-encryption/)

### Advanced Topics in ECC

10- **Elliptic Curve Discrete Logarithm Problem (ECDLP)** [`Tutorial`](https://sefiks.com/2018/02/28/attacking-elliptic-curve-discrete-logarithm-problem/)

Finding public key from known private key and base point is easy whereas extracting private key from known public key and base point is almost impossible. Elliptic Curve Discrete Logarithm Problem describes why elliptic curve cryptography is powerful.

11- **Order of Group in Elliptic Curves** [`Tutorial`](https://sefiks.com/2018/02/27/counting-points-on-elliptic-curves-over-finite-field/)

Elliptic curve digital signature algorithm requires the number of points on the elliptic curve in finite field. We will mention baby step giant step to find this faster.

## Historical Cryptography

1- **Hill Cipher** [`Code`](https://github.com/serengil/crypto/blob/master/python/classical/hill.py), [`Tutorial`](https://sefiks.com/2018/12/04/a-step-by-step-hill-cipher-example/)

Hill cipher is a strong historical method based on matrix multiplication.



# License

This repository is licensed under MIT license - see [`LICENSE`](https://github.com/serengil/crypto/blob/master/LICENSE) for more details
