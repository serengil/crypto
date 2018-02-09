package com.crypto.action;

import java.math.BigInteger;

public class AttackingECDSA {
	
	public static void main(String[] args) throws Exception {
		
		/*
		using same random key for different signatures reveals big security issue
		
		Let's focus on a real world example
		
		message: ECC beats RSA
		hash: 320026739459778556085970613903841025917693204146
		
		Signature:
		r = 108450790312736419148091503336190989867379581793003243037811027177541631669413
		s = 42607929338608516553334258379858331648786092653262293992803886156552661298302
		
		---------------------------
		
		message: ECC defeats Diffie Hellman, too
		hash: 440277347181637471621630043227578594221535759787
		
		Signature:
		r = 108450790312736419148091503336190989867379581793003243037811027177541631669413
		s = 93018423342979723123475596426177746328319084281547536693403927266445225542337
		
		Suppose that attacker knows that both of these signatures are signed with same random key value
		
		*/
		
		//--------------------------------------------
		
		//publicly known information
		
		BigInteger order = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16); 
		
		//--------------------------------------------
		
		//hash value of the 1st message
		BigInteger h1 = new BigInteger("320026739459778556085970613903841025917693204146");
		
		//hash value of the 2nd message
		BigInteger h2 = new BigInteger("440277347181637471621630043227578594221535759787");
		
		//--------------------------------------------
		
		//r values are same for both signature
		BigInteger r = new BigInteger("108450790312736419148091503336190989867379581793003243037811027177541631669413");
		
		//s value of 1st message
		BigInteger s1 = new BigInteger("42607929338608516553334258379858331648786092653262293992803886156552661298302");
		
		//s value of 2nd message
		BigInteger s2 = new BigInteger("93018423342979723123475596426177746328319084281547536693403927266445225542337");
		
		//--------------------------------------------
		
		//attack time
		/*
		
		Remember the following formula
		
		r1 = x coordinate of (randomKey x P) mod order
		s1 = randomKeyInv * (h1 + privateKey * r1)
		
		r2 = x coordinate of (randomKey x P) mod order
		s2 = randomKeyInv * (h2 + privateKey * r2)
		
		if random key is same for both signature, then r1 is equal to r2. Let's say them r.
		
		r = x coordinate of (randomKey x P) mod order
		s1 = randomKeyInv * (h1 + privateKey * r)
		s2 = randomKeyInv * (h2 + privateKey * r)
		
		attacker can calculate s1 - s2
		
		s1 - s2 = randomKeyInv * (h1 + privateKey * r) - kInv * (h2 + privateKey * r)
		s1 - s2 = randomKeyInv * (h1 + privateKey * r - h2 - privateKey * r)
		s1 - s2 = randomKeyInv * (h1 - h2)
		
		calculate randomKeyInv from this equation
		
		*/
		
		BigInteger randomKeyInv = s1.subtract(s2).multiply(EccOverFiniteField.multiplicativeInverse(h1.subtract(h2), order)).remainder(order);
		System.out.println("randomKey^-1: "+randomKeyInv);
		
		//finding its inverse is easy
		
		BigInteger randomKey = EccOverFiniteField.multiplicativeInverse(randomKeyInv, order);
		System.out.println("randomKey: "+randomKey);
		
		//--------------------------------------------
		
		//if attacker has randomKey, then he can extract private key from the following equation
		//s1 = randomKeyInv * (h1 + privateKey * r) mod order
		
		BigInteger privateKey = s1.multiply(randomKey).subtract(h1).multiply(EccOverFiniteField.multiplicativeInverse(r, order));
		
		System.out.println("private key: "+privateKey.remainder(order));
		
		System.out.println("private key restored from signatures");
		
		
	}

}
