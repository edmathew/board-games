package ticTacToe;

public class Utilities {

	/**
	 * Verifica se o caracter c  uma Letra Maiscula
	 */
	public static boolean isLetter(char c){
		return c >= 'A' && c <= 'Z';	
	}

	/**
	 * Verifica se o caracter c um Nmero
	 */
	public static boolean isNumber(char c){
		return c >= '0' && c <= '9';
	}
}
