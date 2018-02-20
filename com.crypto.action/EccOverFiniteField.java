package com.crypto.action;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

import com.crypto.entity.Point;

public class EccOverFiniteField  {
	
	public static void main(String[] args) throws Exception {
		
		boolean enableBitcoinParams = true;
		
		Random rand = new Random();
		
		//inital elliptic curve configuration (public)
		
		BigInteger mod;
		BigInteger order;
		
		if(enableBitcoinParams){
			
			mod = generatePrimeModulo();
			order = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16); 
			
		}
		else{
			
			mod = new BigInteger("199"); // F199
			order = new BigInteger("211"); //point of the finite field - order of group
			
		}
		
		//curve equation: y^2 = x^3 + ax + b -> current curve: y^2 = x^3 + 7
		BigInteger a = new BigInteger("0");
		BigInteger b = new BigInteger("7");
		
		//base point on the curve
		Point basePoint = new Point();
		
		if(enableBitcoinParams){
			
			basePoint.setPointX(new BigInteger("55066263022277343669578718895168534326250603453777594175500187360389116729240"));
			basePoint.setPointY(new BigInteger("32670510020758816978083085130507043184471273380659243275938904335757337482424"));
			
		}
		else{
			
			basePoint.setPointX(BigInteger.valueOf(2));
			basePoint.setPointY(BigInteger.valueOf(24));
			
		}
		
		//-----------------------------------------------
		
		//brute force
		
		System.out.println("------------------------------");
		System.out.println("brute force addition");
		System.out.println("------------------------------");
		
		System.out.println("P: "+displayPoint(basePoint));
		
		Point newPoint = pointAddition(basePoint, basePoint, a, b, mod);
		System.out.println("2P: "+displayPoint(newPoint));
		
		for(int i=3;i<=20;i++) {
			
			try {
				
				newPoint = pointAddition(newPoint, basePoint, a, b, mod);
				
				System.out.println(i+"P: "+displayPoint(newPoint));
				
			}
			catch(Exception ex) {
				
				System.out.println("order of group: "+(i));
				
				break;
				
			}
			
		}
		
		System.out.println();
		
		//-----------------------------------------------
		
		//key exchange
		
		System.out.println("------------------------------------------");
		System.out.println("Elliptic Curve Diffie Hellman Key Exchange");
		System.out.println("------------------------------------------");
		
		Date generationBegin = new Date(); 
		
		System.out.println("public key generation...");
		
		BigInteger kAlice = new BigInteger("2010000000000017"); //alice's private key
		Point alicePublic = applyDoubleAndAddMethod(basePoint, kAlice, a, b, mod);
		System.out.println("alice public: \t"+displayPoint(alicePublic));
		
		BigInteger kBob = new BigInteger("2010000000000061"); //bob's private key
		Point bobPublic = applyDoubleAndAddMethod(basePoint, kBob, a, b, mod);
		System.out.println("bob public: \t"+displayPoint(bobPublic));
		
		Date generationEnd = new Date();
		
		System.out.println("public key generation lasts "
				+(double)(generationEnd.getTime() - generationBegin.getTime())/1000+" seconds\n");
		
		//------------------------------------------------
		
		Date exchangeBegin = new Date(); 
		
		System.out.println("key exchange...");
		
		Point aliceShared = applyDoubleAndAddMethod(bobPublic, kAlice, a, b, mod);
		System.out.println("alice shared: \t"+displayPoint(aliceShared));
		
		Point bobShared = applyDoubleAndAddMethod(alicePublic, kBob, a, b, mod);
		System.out.println("bob shared: \t"+displayPoint(bobShared));
		
		Date exchangeEnd = new Date(); 
		
		System.out.println("shared key exchange lasts "
				+(double)(exchangeEnd.getTime() - exchangeBegin.getTime())/1000+" seconds\n");
		
		
		//------------------------------------
		
		//ecdsa - elliptic curve digital signature algorithm
		
		System.out.println("---------------------------------------------------");
		System.out.println("Elliptic Curve Digital Signature Algorithm - ECDSA");
		System.out.println("---------------------------------------------------");
		
		String text = "ECC beats RSA";
		
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(text.getBytes());
		byte[] hashByte = md.digest();
		
		BigInteger hash = new BigInteger(hashByte).abs();
		
		System.out.println("message: "+text);
		System.out.println("hash: "+hash);
		
		//------------------------------------
		
		//BigInteger privateKey = new BigInteger("151");
		BigInteger privateKey = new BigInteger("75263518707598184987916378021939673586055614731957507592904438851787542395619");
		
		Point publicKey = applyDoubleAndAddMethod(basePoint, privateKey, a, b, mod);
		
		System.out.println("public key: "+displayPoint(publicKey));
		
		
		//BigInteger randomKey = new BigInteger("115");
		//BigInteger randomKey = new BigInteger("28695618543805844332113829720373285210420739438570883203839696518176414791234");
		BigInteger randomKey = new BigInteger(128, rand);
		
		Point randomPoint = applyDoubleAndAddMethod(basePoint, randomKey, a, b, mod);
		
		System.out.println("random point: "+displayPoint(randomPoint));
		
		//------------------------------------
		
		//signing
		
		System.out.println("\nsigning...");
		
		Date signingBegin = new Date();
		
		BigInteger r = randomPoint.getPointX().remainder(order);
				
		BigInteger s = (hash.add(r.multiply(privateKey)).multiply(multiplicativeInverse(randomKey, order))).remainder(order);
		
		System.out.println("Signature: (r, s) = ("+r+", "+s+")");
		
		Date signingEnd = new Date();
		
		System.out.println("\nmessage signing lasts "
				+(double)(signingEnd.getTime() - signingBegin.getTime())/1000+" seconds\n");
				
		//------------------------------------
		
		//verification
		
		Date verifyBegin = new Date();
		
		System.out.println("verification...");
		
		BigInteger w = multiplicativeInverse(s, order);
		
		Point u1 = applyDoubleAndAddMethod(basePoint, (hash.multiply(w).remainder(order)), a, b, mod);
		
		Point u2 = applyDoubleAndAddMethod(publicKey, (r.multiply(w).remainder(order)), a, b, mod);
		
		Point checkpoint = pointAddition(u1, u2, a, b, mod);
		
		System.out.println("checkpoint: "+displayPoint(checkpoint));
		
		System.out.println(checkpoint.getPointX()+" ?= "+r);
		
		if(checkpoint.getPointX().compareTo(r) == 0){
			
			System.out.println("signature is valid...");
			
		}
		else{
			
			System.out.println("invalid signature detected!!!");
			
		}
		
		Date verifyEnd = new Date();
		
		System.out.println("\nverification lasts "
				+(double)(verifyEnd.getTime() - verifyBegin.getTime())/1000+" seconds\n");
		
		//------------------------------------
		
		//symmetric key encryption / decryption
		
		System.out.println("-----------------------------------");
		System.out.println("Elliptic Curve ElGamal Cryptosystem");
		System.out.println("-----------------------------------");
		
		Point plaintext = new Point();
		plaintext.setPointX(new BigInteger("33614996735103061868086131503312627786077049888376966084542785773152043381677"));
		plaintext.setPointY(new BigInteger("84557594361191031609962062080128931200952163654712344162477769532776951195137"));
		
		System.out.println("plaintext: "+displayPoint(plaintext));
		
		BigInteger secretKey = new BigInteger("75263518707598184987916378021939673586055614731957507592904438851787542395619");
		//alice and bob both must know this secret key
		
		publicKey = applyDoubleAndAddMethod(basePoint, secretKey, a, b, mod);
		
		//encryption 
		
		rand = new Random();
		
		randomKey = new BigInteger(128, rand); //2^128 - 1
		
		Point c1 = applyDoubleAndAddMethod(basePoint, randomKey, a, b, mod);
		
		Point c2 = applyDoubleAndAddMethod(publicKey, randomKey, a, b, mod);
		c2 = pointAddition(c2, plaintext, a, b, mod);
		
		System.out.println("\nciphertext:");
		System.out.println("c1: "+displayPoint(c1));
		System.out.println("c2: "+displayPoint(c2));
		
		//decryption
		
		//message = c2 - secretKey * c1
		
		Point d = applyDoubleAndAddMethod(c1, secretKey, a, b, mod);
		
		Point dInv = new Point();
		dInv.setPointX(d.getPointX());
		dInv.setPointY(d.getPointY().multiply(new BigInteger("-1")));
		
		Point decryption = pointAddition(c2, dInv, a, b, mod);
		System.out.println("\ndecrypted messsage: "+displayPoint(decryption));
		
		//-------------------------------------------------
		
		//baby step giant step approach
		//to find the order of group - points on the curve
		//complexity of this method is sqrt(sqrt(mod))
		
		Point Q = applyDoubleAndAddMethod(basePoint, mod.add(BigInteger.valueOf(1)), a, b, mod);
		System.out.println("Q: "+displayPoint(Q));
		
		BigInteger m = sqrt(sqrt(mod)).add(BigInteger.valueOf(1));
		System.out.println("sqrt("+order+") = "+m);
		
		boolean terminate = false;
		
		for(BigInteger j = BigInteger.valueOf(1); j.compareTo(m) < 1; j=j.add(BigInteger.valueOf(1))){
			
			Point jP = applyDoubleAndAddMethod(basePoint, j, a, b, mod);
			
			//System.out.println(displayPoint(jP));
			
			for(BigInteger k = m.multiply(BigInteger.valueOf(-1)); k.compareTo(m) < 1; k=k.add(BigInteger.valueOf(1))){
				
				Point checkpoint = applyDoubleAndAddMethod(basePoint, m.multiply(BigInteger.valueOf(2).multiply(k)), a, b, mod);
				checkpoint = pointAddition(checkpoint, Q, a, b, mod);
				
				//System.out.println("\t"+displayPoint(checkpoint));
				
				if(checkpoint.getPointX().compareTo(jP.getPointX()) == 0){
					
					System.out.println(displayPoint(jP)+" == "+displayPoint(checkpoint));
					System.out.println("order of group should be: "+mod.add(BigInteger.valueOf(1)).add(m.multiply(BigInteger.valueOf(2).multiply(k)))+"±"+j);
					
					BigInteger r = mod.add(BigInteger.valueOf(1)).add(m.multiply(BigInteger.valueOf(2).multiply(k)));
					
					//-------------------
					
					try{
						//r1 + j
						Point tmp = applyDoubleAndAddMethod(basePoint,r.add(j), a, b, mod);
						
						try{
							
							tmp = applyDoubleAndAddMethod(basePoint,r.subtract(j), a, b, mod);
													
						}
						catch(Exception exx){
							
							System.out.println("order of group: "+r.subtract(j));
							
							terminate = true;
							
							break;
							
						}
												
					}
					catch(Exception exx){
						
						System.out.println("order of group: "+r.add(j));
						
						terminate = true;
						
						break;
						
					}
					
				}
				
			}
			
			if(terminate == true)
				break;
			
		}
		
	}
	
	public static Point pointAddition(Point P, Point Q, BigInteger a, BigInteger b, BigInteger mod) throws Exception {
		
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
			
			BigInteger times = x3.abs().divide(mod).add(BigInteger.valueOf(1));
			
			//x3 = x3.add(mod);
			x3 = x3.add(times.multiply(mod));
			
		}
		
		while(y3.compareTo(BigInteger.valueOf(0)) < 0) {
			
			BigInteger times = y3.abs().divide(mod).add(BigInteger.valueOf(1));
			
			//y3 = y3.add(mode);
			y3 = y3.add(times.multiply(mod));
			
		}
		
		x3 = x3.remainder(mod);
		y3 = y3.remainder(mod);
		
		Point R = new Point();
		R.setPointX(x3);
		R.setPointY(y3);
						
		return R;
		
	}
	
	public static Point applyDoubleAndAddMethod(Point P, BigInteger k, BigInteger a, BigInteger b, BigInteger mod) throws Exception {
		
		Point tempPoint = new Point();
		tempPoint.setPointX(P.getPointX());
		tempPoint.setPointY(P.getPointY());
		
		String kAsBinary = k.toString(2); //convert to binary
		
		//System.out.println("("+k+")10 = ("+kAsBinary+")2");
		
		for(int i=1;i<kAsBinary.length();i++) {
			
			int currentBit = Integer.parseInt(kAsBinary.substring(i, i+1));
			
			tempPoint = pointAddition(tempPoint, tempPoint, a, b, mod);
			
			if(currentBit == 1) {
				
				tempPoint = pointAddition(tempPoint, P, a, b, mod);
				
			}
			
		}
				
		return tempPoint;
		
	}
	
	public static BigInteger multiplicativeInverse(BigInteger a, BigInteger mod) throws Exception {
		
		//return a.modInverse(mod); //out-of-the-box function
		
		//extended euclidean algorithm to find modular inverse
		
		while(a.compareTo(new BigInteger("0")) == -1){
			
			a = a.add(mod);
			
		}
		
		BigInteger x1 = new BigInteger("1");
		BigInteger x2 = new BigInteger("0");
		BigInteger x3 = mod;
		
		BigInteger y1 = new BigInteger("0");;
		BigInteger y2 = new BigInteger("1");;
		BigInteger y3 = a;
		
		BigInteger q = x3.divide(y3);
		
		BigInteger t1 = x1.subtract(q.multiply(y1));
		BigInteger t2 = x2.subtract(q.multiply(y2));
		BigInteger t3 = x3.subtract(q.multiply(y3));
		
		while(y3.compareTo(new BigInteger("1")) != 0){
			
			x1 = y1;
			x2 = y2;
			x3 = y3;
			
			y1 = t1;
			y2 = t2;
			y3 = t3;
			
			q = x3.divide(y3);
			
			t1 = x1.subtract(q.multiply(y1));
			t2 = x2.subtract(q.multiply(y2));
			t3 = x3.subtract(q.multiply(y3));
			
		}
		
		while(y2.compareTo(new BigInteger("0")) == -1){
			
			y2 = y2.add(mod);
			
		}
		
		return y2;	
		
	}
	
	public static BigInteger generatePrimeModulo(){
		
		//Secp256k1
		//Recommended 256-bit Elliptic Curve Domain Parameters over Fp (http://www.secg.org/sec2-v2.pdf)
		//modulo for bitcoin
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
		
		return modulus;
		
	}
	
	public static String displayPoint(Point P) {
		
		return "("+P.getPointX()+", "+P.getPointY()+")";
				
	}

	
}
