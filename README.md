# Elliptic Curve Cryptography Masterclass

Elliptic Curve public key generator. It implements the following approaches; Double and add method (a.k.a. binary method), Elliptic curve point addition, Elliptic curve point doubling, Extended euclidean algorithm to find multiplicative inverse rapidly.

The code could be applied for both key exchanging (aka Elliptic Curve Diffie Hellman) and digital signatures (aka Elliptic Curve Digital Signature Algorithm)


Usage
=====

Run files under the com.crypto.action

com.crypto.action.EccOverFiniteField.java works on finite fields. In other words, key exchanging will be produced on integers. Run this file. 

com.crypto.action.EccOverRealNumbers.java is a legacy file, and it handles key exchange on real numbers. You should run it only if you wonder how ECC formulas work.

License
=======

Copyright 2016 Sefik Ilkin Serengil (https://sefiks.com/)

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
