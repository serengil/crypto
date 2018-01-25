package com.crypto.entity;

import java.math.BigInteger;

public class FFPoint {
	
	//coordinates of a point on an elliptic curve over finite fields

	BigInteger pointX;
	BigInteger pointY;
	
	public BigInteger getPointX() {
		return pointX;
	}
	public void setPointX(BigInteger pointX) {
		this.pointX = pointX;
	}
	public BigInteger getPointY() {
		return pointY;
	}
	public void setPointY(BigInteger pointY) {
		this.pointY = pointY;
	}
	
	
}
