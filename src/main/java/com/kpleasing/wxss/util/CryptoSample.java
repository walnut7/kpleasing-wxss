package com.kpleasing.wxss.util;

import java.security.MessageDigest;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class CryptoSample {

	private static String digest(String algorithm, String content, String charSet) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(content.getBytes(charSet));
			byte[] digestBytes = messageDigest.digest();
			return DatatypeConverter.printBase64Binary(digestBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String digest(String algorithm, byte[] content) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(content);
			byte[] digestBytes = messageDigest.digest();
			return DatatypeConverter.printBase64Binary(digestBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String encrypt(String cipherAlgorithm, byte[] keyBytes, String keyAlgorithm, String content,
			String charSet) {
		try {
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, keyAlgorithm);
			byte[] iv = UUID.randomUUID().toString().substring(0, 16).getBytes(charSet);
			IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
			byte[] encyptedBytes = cipher.doFinal(content.getBytes(charSet));
			byte[] contentBytes = new byte[encyptedBytes.length + 16];
			System.arraycopy(iv, 0, contentBytes, 0, 16);
			System.arraycopy(encyptedBytes, 0, contentBytes, 16, encyptedBytes.length);
			return DatatypeConverter.printBase64Binary(contentBytes);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String sign(String content, String secret) {
		String charSet = "UTF-8";
		String contentDigest = digest("SHA-1", content, charSet);
		String keyDigest = digest("SHA-256", secret, charSet);
		byte[] keyDigestBytes = DatatypeConverter.parseBase64Binary(keyDigest);
		byte[] keyBytes = new byte[16];
		System.arraycopy(keyDigestBytes, 0, keyBytes, 0, 16);
		String cipherAlgorithm = "AES/CBC/PKCS5Padding";
		String keyAlgorithm = "AES";
		return encrypt(cipherAlgorithm, keyBytes, keyAlgorithm, contentDigest, charSet);
	}
	
	public static String sign(byte[] content, String secret) {
		String charSet = "UTF-8";
		String contentDigest = digest("SHA-1", content);
		String keyDigest = digest("SHA-256", secret, charSet);
		byte[] keyDigestBytes = DatatypeConverter.parseBase64Binary(keyDigest);
		byte[] keyBytes = new byte[16];
		System.arraycopy(keyDigestBytes, 0, keyBytes, 0, 16);
		String cipherAlgorithm = "AES/CBC/PKCS5Padding";
		String keyAlgorithm = "AES";
		return encrypt(cipherAlgorithm, keyBytes, keyAlgorithm, contentDigest, charSet);
	}

	public static void main(String[] args) {
		
		String content = "{\"idChnName\":\"张三\"}";
		String secret = "nS6uP3oO5wK4dY8uX4aN0tT7hH6rU8jU7oW6aE7aI6iV6qO4qD";
		// String secret = "kG6pL8sR3fG0mH6dY8xT5pG8xO2dR4kE7fT8mH8aJ2kU0cD8xN";
		//nS6uP3oO5wK4dY8uX4aN0tT7hH6rU8jU7oW6aE7aI6iV6qO4qD
		System.out.println(sign(content, secret));
	}
}
