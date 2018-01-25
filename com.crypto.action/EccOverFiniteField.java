package com.crypto.action;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;

import com.crypto.entity.FFPoint;

public class EccOverFiniteField {
	
	public static BigInteger mod;
	
	public static void main(String[] args) throws Exception {
		
		//inital elliptic curve configuration (public)
		
		mod = generatePrimeModulo();
		//mod = new BigInteger("23"); // F23
		
		//curve equation: y^2 = x^3 + ax + b -> current curve: y^2 = x^3 + x + 1
		BigInteger a = new BigInteger("1");
		BigInteger b = new BigInteger("1");
		
		//base point on the curve
		FFPoint P = new FFPoint();
		P.setPointX(BigInteger.valueOf(3));
		P.setPointY(BigInteger.valueOf(10));
		
		//-----------------------------------------------
		/*
		System.out.println("P: "+displayPoint(P));
		
		//brute force
		
		Point newPoint = pointAddition(P, P, a, b);
		System.out.println("2P: "+displayPoint(newPoint));
		
		for(int i=3;i<=1000;i++) {
			
			try {
				
				newPoint = pointAddition(newPoint, P, a, b);
				
				System.out.println(i+"P: "+displayPoint(newPoint));
				
			}
			catch(Exception ex) {
				
				break;
				
			}
			
		}
		*/

		//-----------------------------------------------
		
		Date generationBegin = new Date(); 
		
		System.out.println("public key generation...");
		
		BigInteger kAlice = new BigInteger("2010000000000017"); //alice's private key
		FFPoint alicePublic = applyDoubleAndAddMethod(P, kAlice, a, b);
		System.out.println("alice public: \t"+displayPoint(alicePublic));
		
		BigInteger kBob = new BigInteger("2010000000000061"); //bob's private key
		FFPoint bobPublic = applyDoubleAndAddMethod(P, kBob, a, b);
		System.out.println("bob public: \t"+displayPoint(bobPublic));
		
		Date generationEnd = new Date();
		
		System.out.println("public key generation lasts "
				+(double)(generationEnd.getTime() - generationBegin.getTime())/1000+" seconds\n");
		
		//------------------------------------------------
		
		Date exchangeBegin = new Date(); 
		
		System.out.println("key exchange...");
		
		FFPoint sefikShared = applyDoubleAndAddMethod(bobPublic, kAlice, a, b);
		System.out.println("alice shared: \t"+displayPoint(sefikShared));
		
		FFPoint emerShared = applyDoubleAndAddMethod(alicePublic, kBob, a, b);
		System.out.println("bob shared: \t"+displayPoint(emerShared));
		
		Date exchangeEnd = new Date(); 
		
		System.out.println("shared key exchange lasts "
				+(double)(exchangeEnd.getTime() - exchangeBegin.getTime())/1000+" seconds\n");
		
	}
	
	public static FFPoint pointAddition(FFPoint P, FFPoint Q, BigInteger a, BigInteger b) throws Exception {
		
		BigInteger x1 = P.getPointX();
		BigInteger y1 = P.getPointY();
		
		BigInteger x2 = Q.getPointX();
		BigInteger y2 = Q.getPointY();
		
		BigInteger beta;
		
		if(x1.compareTo(x2) == 0 && y1.compareTo(y2) == 0) {
			
			//apply doubling
			
			beta = (BigInteger.valueOf(3).multiply(x1.multiply(x1)).add(a))
					.multiply(multiplicativeInverse(BigInteger.valueOf(2).multiply(y1), mod));
		}
		else {
			
			//apply point addition
			
			beta = (y2.subtract(y1))
					.multiply(multiplicativeInverse(x2.subtract(x1), mod));
			
		}
		
		BigInteger x3 = (beta.multiply(beta)).subtract(x1).subtract(x2);
		BigInteger y3 = (beta.multiply(x1.subtract(x3))).subtract(y1);
		
		while(x3.compareTo(BigInteger.valueOf(0)) < 0) {
			
			x3 = x3.add(mod);
			
		}
		
		while(y3.compareTo(BigInteger.valueOf(0)) < 0) {
			
			BigInteger times = y3.abs().divide(mod).add(BigInteger.valueOf(1));
			
			y3 = y3.add(times.multiply(mod));
			
		}
		
		x3 = x3.remainder(mod);
		y3 = y3.remainder(mod);
		
		FFPoint R = new FFPoint();
		R.setPointX(x3);
		R.setPointY(y3);
						
		return R;
		
	}
	
	public static FFPoint applyDoubleAndAddMethod(FFPoint P, BigInteger k, BigInteger a, BigInteger b) throws Exception {
		
		FFPoint tempPoint = new FFPoint();
		tempPoint.setPointX(P.getPointX());
		tempPoint.setPointY(P.getPointY());
		
		String kAsBinary = k.toString(2); //convert to binary
		
		//System.out.println("("+k+")10 = ("+kAsBinary+")2");
		
		for(int i=1;i<kAsBinary.length();i++) {
			
			int currentBit = Integer.parseInt(kAsBinary.substring(i, i+1));
			
			tempPoint = pointAddition(tempPoint, tempPoint, a, b);
			
			if(currentBit == 1) {
				
				tempPoint = pointAddition(tempPoint, P, a, b);
				
			}
			
		}
				
		return tempPoint;
		
	}
	
	public static BigInteger multiplicativeInverse(BigInteger a, BigInteger mod) throws Exception {
		
		return a.modInverse(mod);
		
		/*
		//extended euclidean algorithm
		
		boolean dump = false; 
		
		while(a.compareTo(new BigInteger("0")) == -1)
			a = a.add(mod);
		
		BigInteger x1 = new BigInteger("1"); 
		BigInteger x2 = new BigInteger("0");
		BigInteger x3 = new BigInteger("1").multiply(mod);
		
		BigInteger y1 = new BigInteger("0");
		BigInteger y2 = new BigInteger("1");
		BigInteger y3 = new BigInteger("1").multiply(a);  
		
		if(dump)
			System.out.println("Q\tx1\tx2\tx3\ty1\ty2\ty3\tt1\tt2\tt3");
		
		BigInteger i = new BigInteger("1");
		BigInteger q = new BigInteger("0");
		
		BigInteger t1 = new BigInteger("0");
		BigInteger t2 = new BigInteger("0");
		BigInteger t3 = new BigInteger("0");		
		
		while(y3.compareTo(new BigInteger("1")) != 0){
			
			if(i.compareTo(new BigInteger("1")) == 0){
				
				q = x3.divide(y3);
				t1 = x1.subtract(q.multiply(y1));
				t2 = x2.subtract(q.multiply(y2));
				t3 = x3.subtract(q.multiply(y3));
				
			}
			else{
				
				x1 = y1.multiply(new BigInteger("1"));
				x2 = y2.multiply(new BigInteger("1"));
				x3 = y3.multiply(new BigInteger("1"));
				
				y1 = t1.multiply(new BigInteger("1"));
				y2 = t2.multiply(new BigInteger("1"));
				y3 = t3.multiply(new BigInteger("1"));
				
				if(y3.compareTo(new BigInteger("0")) == 0)
					throw new Exception("no multiplicative inverse for 1/"+a+" mod "+mod);				
				
				q = x3.divide(y3);
				
				t1 = x1.subtract(q.multiply(y1));
				t2 = x2.subtract(q.multiply(y2));
				t3 = x3.subtract(q.multiply(y3));
				
			}
			
			i = i.add(new BigInteger("1"));
			
			if(dump)
				System.out.println(q+"\t"+x1+"\t"+x2+"\t"+x3+"\t"+y1+"\t"+y2+"\t"+y3+"\t"+t1+"\t"+t2+"\t"+t3);
			
		}
		
		return y2;
		*/
		
	}
	
	public static BigInteger generatePrimeModulo(){
		
		//Secp256k1
		//Recommended 256-bit Elliptic Curve Domain Parameters over Fp (http://www.secg.org/sec2-v2.pdf)
		//2^256 - 2^32 - 2^9 - 2^8 - 2^7 - 2^6 - 2^4 - 2^0
		
		
		BigInteger base = new BigInteger("2");
		
		BigInteger modulus =  base.pow(256)
		.subtract(base.pow(32))
		.subtract(base.pow(9))
		.subtract(base.pow(8))
		.subtract(base.pow(7))
		.subtract(base.pow(6))
		.subtract(base.pow(4))
		.subtract(base.pow(0));
		
		//----------------------------------
		/*
		//alternative: P-256 prime
		// 2^256 - 2^224 + 2^192 + 2^96 - 1 
		
		BigInteger modulus =  base.pow(256)
		.subtract(base.pow(224))
		.add(base.pow(192))
		.add(base.pow(96))
		.subtract(base.pow(0));
		*/
		return modulus;
		
	}
	
	public static String displayPoint(FFPoint P) {
		
		return "("+P.getPointX()+", "+P.getPointY()+")";
				
	}

	
}
