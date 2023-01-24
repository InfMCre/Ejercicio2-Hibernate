package com.example.ejercicio2.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		// TODO Auto-generated method stub
		return cifrarTexto(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return cifrarTexto(rawPassword.toString()).equals(encodedPassword);
	}

	
	/**
	 * Dado una contraseña sin cifrar, devuelve su contraseña cifrada
	 * @param texto la contraseña sin cifrar
	 * @return la contraseña cifrada según lo que implementemos
	 */
	private String cifrarTexto(String texto) {
		String textoCifrado = "";
		
		// TODO realizar el método criptográfico

		return textoCifrado;
	
	// return null;
	}
	
	
	
	
	
	// Convierte Array de Bytes en hexadecimal
	private static String Hexadecimal(byte[] resumen) {
		String HEX = "";
		for (int i = 0; i < resumen.length; i++) {
			String h = Integer.toHexString(resumen[i] & 0xFF);
			if (h.length() == 1)
				HEX += "0";
			HEX += h;
		}
		return HEX.toUpperCase();
	}
	
	
	

	private String cifrarTexto1(String texto) {
		MessageDigest algoritmo;
		String textoCifrado = "";

		try {
			algoritmo = MessageDigest.getInstance("SHA");
			algoritmo.reset(); // Limpiamos la instancia por si acaso
			byte dataBytes[] = texto.getBytes(); 
			algoritmo.update(dataBytes); // El mensaje que queremos cifrar
			byte resumen[] = algoritmo.digest(); // Generamos el resumen

//				System.out.println("Mensaje original: " + texto);
//				System.out.println("Numero de Bytes: " + algoritmo.getDigestLength());
//				System.out.println("Algoritmo usado: " + algoritmo.getAlgorithm());
//				System.out.println("Resumen del Mensaje: " + new String(resumen));
			System.out.println("Mensaje en Hexadecimal: " + Hexadecimal(resumen));
//				System.out.println("Proveedor: " + algoritmo.getProvider().toString());
			
			textoCifrado = Hexadecimal(resumen);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Algoritmo a usar

		return textoCifrado;
	
	// return null;
	}

}
