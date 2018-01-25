package com.crypto.action;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import com.crypto.entity.Point;

public class EccOverRealNumbers {
	
	public static void main(String[] args) {
		
		//inital elliptic curve configuration
		
		//equation: y^2 = x^3 + ax + b ->  y^2 = x^3 -4x + 4
		double a = -4, b = 4;
		
		//base point
		Point P = new Point();
		P.setPointX(BigDecimal.valueOf(-2));
		P.setPointY(BigDecimal.valueOf(-2));
		
		//-----------------------------------------------
		
		/*
		//doubling
		Point point2P = pointAddition(P, P, a, b);
		
		//point addition
		Point point3P = pointAddition(point2P, P, a, b);
		Point point4P = pointAddition(point3P, P, a, b);
		*/
		
		//-----------------------------------------------
		/*
		//brute force
		//System.out.print("2P: ");
		Point newPoint = pointAddition(P, P, a, b);
		
		for(int i=3;i<=100;i++) {
			
			//System.out.print(i+"P: ");
			
			newPoint = pointAddition(newPoint, P, a, b);
			
		}
		
		System.out.println("brute force result:");
		//System.out.println("("+newPoint.getPointX()+", "+newPoint.getPointY()+")");
		
		displayPoint(newPoint);
		*/
		//-----------------------------------------------
		/*
		//double and add method
		
		BigInteger k = new BigInteger("100");
		
		System.out.println("double and add method result:");
		
		Point summary = applyDoubleAndAddMethod(P, k, a, b);
		
		displayPoint(summary);
		*/
		//-----------------------------------------------
		
		//key exchange - elliptic curve diffie hellman
		
		BigInteger ka = new BigInteger("100000000021"); //private key of Alice
		
		Point alicePublic = applyDoubleAndAddMethod(P, ka, a, b);
		
		System.out.println("Alice's public key: \t"+displayPoint(alicePublic));
		
		//------------------------------------------------
		
		BigInteger kb = new BigInteger("100000000077"); //private key of Bob
		Point bobPublic = applyDoubleAndAddMethod(P, kb, a, b);
		
		System.out.println("Bob's public key: \t"+displayPoint(bobPublic));
		
		//------------------------------------------------
		
		Point aliceShared = applyDoubleAndAddMethod(bobPublic, ka, a, b);
		
		Point bobShared = applyDoubleAndAddMethod(alicePublic, kb, a, b);
		
		System.out.println("alice produces this shared key: "+displayPoint(aliceShared));	
		
		System.out.println("bob produces this shared key: \t"+displayPoint(bobShared));
		
		
	}
	
	public static Point pointAddition(Point P, Point Q, double a, double b) {
		
		//int scale = 10;
		
		MathContext mc = new MathContext(128);
		
		BigDecimal x1 = P.getPointX();
		BigDecimal y1 = P.getPointY();
		
		BigDecimal x2 = Q.getPointX();
		BigDecimal y2 = Q.getPointY();
		
		//check P == Q. if they are same apply doubling
		
		BigDecimal beta;
		
		if(x1.compareTo(x2) == 0 && y1.compareTo(y2) == 0) {
			
			//apply doubling
			
			beta = (BigDecimal.valueOf(3).multiply(x1.multiply(x1))
					.add(BigDecimal.valueOf(a)))
					.divide((BigDecimal.valueOf(2).multiply(y1)), mc);
			
		}
		else {
			
			//apply point addition
			
			beta = (y2.subtract(y1))
					.divide((x2.subtract(x1)), mc);
			
		}
		
		BigDecimal x3 = (beta.multiply(beta)).subtract(x1).subtract(x2);
		BigDecimal y3 = (beta.multiply(x1.subtract(x3))).subtract(y1);
		
		Point R = new Point();
		R.setPointX(x3);
		R.setPointY(y3);
		
		//System.out.println("("+x3+", "+y3+")");
				
		return R;
		
	}
	
	public static Point applyDoubleAndAddMethod(Point P, BigInteger k, double a, double b) {
		
		Point tempPoint = new Point();
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
		
		//System.out.println("("+tempPoint.getPointX()+", "+tempPoint.getPointY()+")");
		
		return tempPoint;
		
	}
	
	public static String displayPoint(Point P) {
		
		int scale = 100;
		
		return "("+P.getPointX().setScale(scale, BigDecimal.ROUND_HALF_UP)
				+", "+P.getPointY().setScale(scale, BigDecimal.ROUND_HALF_UP)+")";
		
	}

	
}
