# Elliptic Curve Cryptography Masterclass and Public Key Cryptography From Scratch In Python

This repository covers codes for both Elliptic Curve Cryptography Masterclass and Public Key Cryptography From Scratch In Python online courses.

Courses:

[Elliptic Curve Cryptography Masterclass](https://www.udemy.com/elliptic-curve-cryptography-masterclass/?couponCode=ECCMC-BLOG-201801)

[Public Key Cryptography From Scratch In Python](https://www.udemy.com/public-key-cryptography-from-scratch-in-python/?couponCode=PCC-101-BLOG-1804)

Usage
=====

If you feel more comfortable at Java, please run files under the com.crypto.action

On the other hand, you can run the files under python folder if you enjoy to develop python

# Elliptic Curve Cryptography Masterclass

This project implements the following approaches; double and add method (a.k.a. binary method), Elliptic curve point addition, Elliptic curve point doubling, Extended euclidean algorithm to find multiplicative inverse rapidly.

The code could be applied for key exchanging (aka Elliptic Curve Diffie Hellman), generating and verifying digital signatures (aka Elliptic Curve Digital Signature Algorithm - ECDSA), and also symmetric key encryption (aka Elliptic Curve ElGamal)

BTW, Bitcoin uses ECDSA to sign and verify bitcoin transactions. This code uses bitcoin protocol's configurations directly.

## Java

com.crypto.action.EccOverFiniteField.java is the up-to-date file and it works on finite fields. In other words, key exchanging and other stuff will be handled on integers. Run this file please. 

com.crypto.action.EccOverRealNumbers.java is a legacy file, and it handles key exchange on real numbers. You should run it only if you wonder how ECC formulas work theoretically.

## Python

You should run the file python/EccApp.py folder if you feel more comfortable at python (3.X)

Additionally, bitcoin based codes are pulled in same directories.

# Public Key Cryptography From Scratch

This repository also includes common public key techniques such as Diffie Hellman, RSA, El Gamal and DSA. They are all stored in python folder.

License
=======

This repository is licensed under MIT license - see [LICENSE](https://github.com/serengil/crypto/blob/master/LICENSE) for more details
