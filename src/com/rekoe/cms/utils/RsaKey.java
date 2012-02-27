package com.rekoe.cms.utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
public class RsaKey {
	public PublicKey getPublicKey(String modulus, String publicExponent)
			throws Exception {
		BigInteger m = new BigInteger(modulus);
		BigInteger e = new BigInteger(publicExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	public PrivateKey getPrivateKey(String modulus, String privateExponent)
			throws Exception {
		BigInteger m = new BigInteger(modulus);
		BigInteger e = new BigInteger(privateExponent);
		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;

	}

	public static void main(String[] args) throws Exception {
		String modulus = "98252408513243726493342438613655567625754751597088879965244924071538552413109042357097861572682587162862383135037943302700467078024759030159631235294517159438898246778012214576591507521532096539084769967686828910372992188513758529832725714794878620716989915082799982783515374740907676234071231179012789931359";
		String publicExponent = "65537";
		String privateExponet = "47178006980839324793917835737570030022962239315330118309142812695229362129608136685162772263465650478784752656919130228614080572476023344371781365388759934858472165090193606383950076367033111134138257241574561399125046774571040734128592374230473979999736084466566211087654412826152202064285229935744869713721";
		RsaKey key = new RsaKey();
		PublicKey publicKey = key.getPublicKey(modulus, publicExponent);
		PrivateKey privateKey = key.getPrivateKey(modulus, privateExponet);
		// �ӽ�����
		Cipher cipher = Cipher.getInstance("RSA");
		// ����
		byte[] plainText = "���Ƕ��ܺã��ʼ���@sina.com".getBytes();
		// ����
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] enBytes = cipher.doFinal(plainText);
		String jiami = byte2hex(enBytes);
		System.out.println(jiami);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] deBytes = cipher.doFinal(hex2byte(jiami));
		String s = new String(deBytes);
		System.out.println(s);
	}
	 public static String byte2hex(byte[] b) {
	        String hs = "";
	        String stmp = "";
	        for (int i = 0; i < b.length; i++) {
	            stmp = Integer.toHexString(b[i] & 0xFF);
	            if (stmp.length() == 1) {
	                hs += "0" + stmp;
	            } else {
	                hs += stmp;
	            }
	        }
	        //return hs;
	        return hs.toUpperCase();
	    }
	 
	 
	    public static byte[] hex2byte(String hex) throws IllegalArgumentException {
	        if (hex.length() % 2 != 0) {
	            throw new IllegalArgumentException();
	        }
	        char[] arr = hex.toCharArray();
	        byte[] b = new byte[hex.length() / 2];
	        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
	            String swap = "" + arr[i++] + arr[i];
	            int byteint = Integer.parseInt(swap, 16) & 0xFF;
	            b[j] = new Integer(byteint).byteValue();
	        }
	        return b;
	    }
	    
	    /**
		 * 生成密钥
		 * 
		 * @return
		 * @throws NoSuchAlgorithmException
		 */
		public KeyPair generateKey() throws NoSuchAlgorithmException {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			return keyPair;
		}
		public void createKey() throws NoSuchAlgorithmException
		{
			KeyPair keyPair = generateKey();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();// 公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic(); // 私钥
			System.out.println("公钥:"+publicKey);
			System.out.println("私钥:"+privateKey);
		}
}