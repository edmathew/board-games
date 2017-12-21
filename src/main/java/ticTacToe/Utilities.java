package ticTacToe;

public class Utilities {

	/**
	 * Verifica se o caracter c é uma Letra Maiúscula
	 */
	public static boolean isLetter(char c){
		return c >= 'A' && c <= 'Z';	
	}

	/**
	 * Verifica se o caracter c é um Número
	 */
	public static boolean isNumber(char c){
		return c >= '0' && c <= '9';
	}
}
