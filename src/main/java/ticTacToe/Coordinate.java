package ticTacToe;


public class Coordinate {

	private int line;
	private int column;

	/**
	 * Construtor que recebe um coordenada relativa aos indices da Matriz.
	 */
	public Coordinate(int line, int column) {
		this.line = line;
		this.column = column;
	}

	/**
	 * Construtor que recebe uma String com uma coordenada relativa à grelha do
	 * tabuleiro.
	 * 
	 * @pre coordinateText != null && coordinateText.length()>0
	 */
	public Coordinate(String coordinateText) {
		assert validCoordinateText(coordinateText);

		coordinateText = coordinateText.toUpperCase().trim();
		line = (int) (coordinateText.charAt(0) - 'A');

		coordinateText = coordinateText.substring(1).trim();
		column = Integer.parseInt(coordinateText) - 1;

	}

	/**
	 * Inspector da Linha
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Inspector da Coluna
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Verifica se a String coordinateAsString contem uma coordenada Válida
	 * 
	 * @pre coordinateAsString != null && coordinateAsString.length() > 0
	 */
	public static boolean validCoordinateText(String coordinateAsString) {
		assert coordinateAsString != null && coordinateAsString.length() > 0;

		coordinateAsString = coordinateAsString.toUpperCase().trim();

		if (coordinateAsString.length() < 3
				|| !Utilities.isLetter(coordinateAsString.charAt(0))
				|| coordinateAsString.charAt(1) != ' ')
			return false;

		String aux = coordinateAsString.substring(1).trim();

		for (int i = 0; i < aux.length(); i++) {
			if (!Utilities.isNumber(aux.charAt(i)))
				return false;
		}

		int num = Integer.parseInt(aux);

		return num > 0 && num < 100;
	}

	/**
	 * Verifica se esta coordenada é Válida para um tabuleiro passado por
	 * argumento
	 */
	public boolean isValidFor(char[][] board) {
		assert board != null;
		return line >= 0 && line < board.length && column >= 0
				&& column < board[0].length;
	}

	/**
	 * Devolve uma representação Textual da Coordenada no formato (line, column)
	 */
	public String toString() {
		return "(" + (char) ('A' + line) + "," + (column + 1) + ")";
	}

	/**
	 * Verifica se a coordenada é igual a outra passada por argumento.
	 * Entende-se que 2 coordenadas são iguais se os atributos line e column
	 * forem iguais.
	 */
	public boolean equals(Coordinate c) {
		assert c != null;
		return line == c.getLine() && column == c.getColumn();
	}

}
