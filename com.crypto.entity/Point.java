package com.crypto.entity;

import java.math.BigDecimal;

public class Point {
	
	//coordinates of a point on an elliptic curve over real numbers
	
	BigDecimal pointX;
	BigDecimal pointY;
	
	public BigDecimal getPointX() {
		return pointX;
	}
	public void setPointX(BigDecimal pointX) {
		this.pointX = pointX;
	}
	public BigDecimal getPointY() {
		return pointY;
	}
	public void setPointY(BigDecimal pointY) {
		this.pointY = pointY;
	}

}
