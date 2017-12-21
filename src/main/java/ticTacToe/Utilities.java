package ticTacToe;

public class Utilities {

	/**
	 * Verifica se o caracter c � uma Letra Mai�scula
	 */
	public static boolean isLetter(char c){
		return c >= 'A' && c <= 'Z';	
	}

	/**
	 * Verifica se o caracter c � um N�mero
	 */
	public static boolean isNumber(char c){
		return c >= '0' && c <= '9';
	}
}
