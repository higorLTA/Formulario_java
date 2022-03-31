package br.senai.sp.cpf138.RestaGuide.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {
	
	public static String hash256(String palavra) {
		//tempero para o hash
		String salt = "h1@or";
		//acrecentando o tempero
		palavra = palavra + salt;
		//fanzendo  o hash e armasenando na String
		String sha256 = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();
		
		return sha256;
	}

}
