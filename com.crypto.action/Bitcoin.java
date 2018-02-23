package com.crypto.action;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Security;

import org.bitcoinj.core.Base58;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.crypto.entity.Point;

public class Bitcoin {
	
	public static void main(String[] args) throws Exception {
		
		BigInteger base = new BigInteger("2");
		
		BigInteger mod = base.pow(256)
				.subtract(base.pow(32))
				.subtract(base.pow(9))
				.subtract(base.pow(8))
				.subtract(base.pow(7))
				.subtract(base.pow(6))
				.subtract(base.pow(4))
				.subtract(base.pow(0));
		
		BigInteger order = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16); 
		
		//curve equation: y^2 = x^3 + ax + b -> current curve: y^2 = x^3 + 7
		BigInteger a = new BigInteger("0");
		BigInteger b = new BigInteger("7");
		
		System.out.println("Curve: y^2 = x^3 + "+a+"*x + "+b);
		
		Point basePoint = new Point();
		basePoint.setPointX(new BigInteger("55066263022277343669578718895168534326250603453777594175500187360389116729240"));
		basePoint.setPointY(new BigInteger("32670510020758816978083085130507043184471273380659243275938904335757337482424"));
		
		System.out.println("base point: ("+basePoint.getPointX()+", "+basePoint.getPointY()+")\n");
		
		System.out.println("modulo: "+mod);
		System.out.println("order of group: "+order+"\n");
		
		System.out.println("--------------------------------");
		System.out.println("Bitcoin Address Generator");
		System.out.println("--------------------------------\n");
		
		//BigInteger privateKey = new BigInteger("11253563012059685825953619222107823549092147699031672238385790369351542642469");
		
		//BigInteger privateKey = new BigInteger("18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725", 16);
		//BigInteger privateKey = new BigInteger("945FB84E92575908A000D9D4A0B136EB0B69F8B5BCDAF157A3A91B7C83A02B1B", 16);
		BigInteger privateKey = new BigInteger("118CC1421845A67643244180F299147659E55C439B34E9E2380946579F64EF16", 16);
		
		System.out.println("private key (hex): "+privateKey.toString(16)+" ("+privateKey.toString(16).length()*4+" bits)\n");
		
		Point publicKey = EccOverFiniteField.applyDoubleAndAddMethod(basePoint, privateKey, a, b, mod);
		
		System.out.println("public key: ("+publicKey.getPointX()+", "+publicKey.getPointY()+")");
		
		String publicKeyString = "04"+publicKey.getPointX().toString(16)+publicKey.getPointY().toString(16);
		
		BigInteger publicKeyMerged = new BigInteger(publicKeyString, 16);
		
		System.out.println("public key (merged): "+publicKeyString+" ("+publicKeyString.length()*4+" bits)\n");
		
		System.out.println("--------------------------------\n");
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(publicKeyMerged.toByteArray());
		byte[] sha256HashByte = md.digest();
		
		BigInteger hash = new BigInteger(sha256HashByte).abs();
		
		System.out.println("sha-256 applied hash: "+hash.toString(16)+" ("+hash.toString(16).length()*4+" bits)");
		
		//-------------------------------------------------------
		
		Security.addProvider(new BouncyCastleProvider());
		
		md = MessageDigest.getInstance("RIPEMD160");
		md.update(sha256HashByte);
		byte[] ripemd256HashByte = md.digest();
		
		BigInteger ripemdHash = new BigInteger(1, ripemd256HashByte);
		String ripemdHashHex = ripemdHash.toString(16);
		
		while(ripemdHashHex.length()<40){
			
			ripemdHashHex = "0"+ripemdHashHex;
			
		}
		
		System.out.println("ripemd160 applied hash: "+ripemdHashHex+" ("+ripemdHashHex.length()*4+" bits)");
		
		//-------------------------------------------------------
		
		ripemdHashHex = "00"+ripemdHashHex;
		
		System.out.println("adding network bytes to ripemd160 hash - extended ripemd160: "+ripemdHashHex+"\n");
		
		//-------------------------------------------------------
		
		md = MessageDigest.getInstance("SHA-256");
		md.update(hexStringToByte(ripemdHashHex, 21));
		byte[] checking = md.digest();
		
		System.out.println("sha-256 to extended ripemd160: "+new BigInteger(checking).toString(16)+" ("+new BigInteger(checking).toString(16).length()*4+" bits)");
		
		//-------------------------------------------------------
		
		md = MessageDigest.getInstance("SHA-256");
		md.update(checking);
		checking = md.digest();
		
		BigInteger checkingInt = new BigInteger(1, checking); // use this 1 to tell it is positive (https://stackoverflow.com/questions/6357234/sha-hash-function-gives-a-negative-output)
		
		System.out.println("second time sha-256 applied to extended ripe160: "+checkingInt.toString(16)+" ("+checkingInt.toString(16).length()*4+" bits)");
		
		String checksum = checkingInt.toString(16).substring(0,8);
		
		System.out.println("checksum: "+checksum+"\n");
		
		//-------------------------------------------
		
		//add checksum to network bytes added RIPEMD160 hash
		
		String address = ripemdHashHex+checksum;
		
		System.out.println("adding checksum to extended ripemd160 "+address+" ("+address.length()*4+" bits)");
		
		//-------------------------------------------
		
		String base58Address = Base58.encode(new BigInteger(address, 16).toByteArray());
		
		while(base58Address.length() < 34){
			
			base58Address = "1"+base58Address;
			
		}
		
		System.out.println("base 58 bitcoin address: "+base58Address+" ("+base58Address.length()*4+" bits)");
		
	}
	
	public static byte[] hexStringToByte(String hexString, int desiredLength){
		
		byte[] byteTransformation = new BigInteger(hexString, 16).toByteArray();
		
		if(byteTransformation.length < desiredLength){
			
			byte[] paddingApplied = new byte[desiredLength];
			
			//initialize
			for(int i=0;i<desiredLength;i++){
				
				paddingApplied[i] = 0;
				
			}
			
			//transfer
			
			for(int i=0;i<byteTransformation.length;i++){
				
				paddingApplied[i + (desiredLength - byteTransformation.length)] = byteTransformation[i];
				
			}
			
			return paddingApplied;
			
		}
		
		return byteTransformation;
		
	}

}
