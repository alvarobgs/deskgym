package br.com.abg.deskgym.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Criptografa a senha.
 * 
 * @author Alvaro
 */
public class PasswordEncrypter {
    
    /**
     * Recebe uma senha não encriptada e devolve com o hash aplicado.
	 *
	 * @param password
	 *
	 * @return
     */
    public static String encrypt(final String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = md.digest(password.getBytes("UTF-8"));

		StringBuilder sb = new StringBuilder();

		for (byte b : messageDigest){
			sb.append(String.format("%02X", 0xFF & b));
		}
		return sb.toString();
    }
}
