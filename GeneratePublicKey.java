package com.serengil.crypto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.serengil.entity.Point;

public class GeneratePublicKey {
	
	public static void main(String[] args) throws Exception {

		//define the curve
		double a = -4, b = 4; //y^2 = x^3 + a*x + b
		
		//public base point G
		Point G = new Point();
		G.setPointX(new BigDecimal(-2));
		G.setPointY(new BigDecimal(-2));
		
		BigDecimal modulo = generatePrimeModulo();
		System.out.println("Public prime modulo: "+modulo.toBigInteger().toString(16));
		
		System.out.println("Public base point G("+G.getPointX()+", "+G.getPointY()
				+") on the curve y^2 = x^3 + ("+a+"*x) + ("+b+")\n");
		
		//--------------
		
		//private key of Alice 
		BigInteger ka = new BigInteger("21");
		
		Date beginDate = new Date();
		
		//Alice computes her public key. ka x G
		Point alicePublic = multiplyScalarPoint(G, ka, a, modulo, false); 
		System.out.println("Alice sends her public key to Bob: kaG("
				+alicePublic.getPointX().toBigInteger()+", "+alicePublic.getPointY().toBigInteger()+")");
		
		Date endDate = new Date();
		
		System.out.println("public key generation lasts "
				+(double)(endDate.getTime() - beginDate.getTime())/1000+" seconds\n");
		
	}
	
	public static Point multiplyScalarPoint(Point G, BigInteger k, double a, BigDecimal modulo, boolean dump) throws Exception{ 
		//this method will compute the Point kG
		
		Point tempPoint = new Point(); //initial value of tempPoint equals to G
		tempPoint.setPointX(G.getPointX());
		tempPoint.setPointY(G.getPointY());
		String kAsBinary = k.toString(2); //to binary string
		
		BigDecimal illustration = new BigDecimal(1);
		
		for(int i=1;i<kAsBinary.length();i++){
			
			int currentBit = Integer.parseInt(kAsBinary.substring(i, i+1));
			
			tempPoint = pointAddition(tempPoint, tempPoint, a, modulo); //doubling
			
			illustration = illustration.add(illustration);		
			
			if(dump)
				System.out.println("double: "+tempPoint.getPointX().toBigInteger()+", "+tempPoint.getPointY().toBigInteger());
			
			if(currentBit == 1){
				tempPoint = pointAddition(tempPoint, G, a, modulo); //tempPoint + P		
				
				illustration = illustration.add(new BigDecimal(1));
				
				if(dump)
					System.out.println("add: "+tempPoint.getPointX().toBigInteger()+", "+tempPoint.getPointY().toBigInteger());
			}
			
		}
		
		return tempPoint;
		
	}
	
	public static Point pointAddition(Point P, Point Q, double a, BigDecimal modulo) throws Exception{
		
		BigDecimal x1 = P.getPointX(), y1 = P.getPointY(), x2 = Q.getPointX(), y2 = Q.getPointY();
		
		BigDecimal beta;
		
		if(x1.compareTo(x2) == 0 && y1.compareTo(y2) == 0){ //check P and Q point
			
			beta = multiplicativeInverse(((BigDecimal.valueOf(3).multiply(x1.multiply(x1))).add(BigDecimal.valueOf(a)))
						, (BigDecimal.valueOf(2).multiply(y1))
						, modulo);
			
		}
		else{
			
			beta = multiplicativeInverse((y2.subtract(y1)), (x2.subtract(x1)), modulo);
	
		}
		
		BigDecimal x3 = (((beta.multiply(beta)).subtract(x1)).subtract(x2)).remainder(modulo);
		BigDecimal y3 = (beta.multiply(x1.subtract(x3))).subtract(y1).remainder(modulo);
		
		while(x3.compareTo(new BigDecimal(0)) == -1)
			x3 = x3.add(modulo);
		
		while(y3.compareTo(new BigDecimal(0)) == -1)
			y3 = y3.add(modulo);
		
		Point R = new Point();
		R.setPointX(x3); 
		R.setPointY(y3);
		
		return R;
		
	}
	
	public static BigDecimal generatePrimeModulo(){
		
		//Secp256k1
		//Recommended 256-bit Elliptic Curve Domain Parameters over Fp (http://www.secg.org/sec2-v2.pdf)
		//2^256 - 2^32 - 2^9 - 2^8 - 2^7 - 2^6 - 2^4 - 2^0
		
		
		BigDecimal base = new BigDecimal(2);
		
		BigDecimal modulus =  base.pow(256)
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
		
		BigDecimal modulus =  base.pow(256)
		.subtract(base.pow(224))
		.add(base.pow(192))
		.add(base.pow(96))
		.subtract(base.pow(0));
		*/
		return modulus;
		
	}
	
	public static BigDecimal multiplicativeInverse(BigDecimal dividend, BigDecimal divider, BigDecimal modulo) throws Exception{
		
		BigDecimal division = dividend.multiply(
				new BigDecimal(applyExtendedEuclidean(divider.toBigInteger(), modulo.toBigInteger(), false))
				);
		
		return division.remainder(modulo);
		
	}
	
	public static BigInteger applyExtendedEuclidean(BigInteger a, BigInteger b, boolean dump) throws Exception{
		
		while(a.compareTo(new BigInteger("0")) == -1)
			a = a.add(b);
		
		BigInteger x1 = new BigInteger("1"); 
		BigInteger x2 = new BigInteger("0");
		BigInteger x3 = new BigInteger("1").multiply(b);
		
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
					throw new Exception("no multiplicative inverse for 1/"+a+" mod "+b);
				
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
		
	}
	
}
