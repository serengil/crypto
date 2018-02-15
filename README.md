# Elliptic Curve Cryptography Masterclass

This repository includes codes while capturing Elliptic Curve Cryptography Masterclass online course

Course link: https://www.udemy.com/elliptic-curve-cryptography-masterclass/?couponCode=ECCMC-BLOG-201801



This project implements the following approaches; double and add method (a.k.a. binary method), Elliptic curve point addition, Elliptic curve point doubling, Extended euclidean algorithm to find multiplicative inverse rapidly.

The code could be applied for key exchanging (aka Elliptic Curve Diffie Hellman), generating and verifying digital signatures (aka Elliptic Curve Digital Signature Algorithm - ECDSA), and also symmetric key encryption (aka Elliptic Curve ElGamal)

BTW, Bitcoin uses ECDSA to sign and verify bitcoin transactions. This code uses bitcoin protocol's configurations directly.


Usage
=====

Run files under the com.crypto.action

com.crypto.action.EccOverFiniteField.java is the current file and it works on finite fields. In other words, key exchanging will be produced on integers. Run this file please.

com.crypto.action.EccOverRealNumbers.java is a legacy file, and it handles key exchange on real numbers. You should run it only if you wonder how ECC formulas work theoretically.

Alternatively, you should run the file under python folder if you feel more comfortable at python

License
=======

Copyright 2016 Sefik Ilkin Serengil (https://sefiks.com/)

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
