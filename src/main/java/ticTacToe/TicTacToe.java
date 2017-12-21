package ticTacToe;

public class TicTacToe {

	private char[][] board = new char[3][3];
	private static final char PL1 = 'O';
	private static final char PL2 = 'X';
	private static final char EMPTY = ' ';
	private char next = PL1;

	public TicTacToe() {
		randomPlayerToStart();
		eraseBoard();
	}	

	public void eraseBoard() {
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				board[i][j] = EMPTY;
	}
	
	public void randomPlayerToStart(){
		int random = new java.util.Random().nextInt(2);
		if (random == 0)
			next = PL1;
		else
			next = PL2;

	}
	
	public char[][] getBoard() {
		return board;
	}

	public String getPlayerToPlay() {
		return next == PL1 ? "Player 2" : "Player 1";
	}

	public void play(Coordinate c) throws IllegalMove {
		if (!isLegal(c))
			throw new IllegalMove("Posição Ocupada");

		board[c.getLine()][c.getColumn()] = next;
		changePlayer();
	}

	public char getValueAt(int i, int j) {
		return board[i][j];
	}

	public String getWinner() {
		return next == PL1 ? "Player 1" : "Player 2";
	}

	public boolean win(char pl) {
		if (checkDiagonals(pl))
			return true;

		if (getValueAt(0, 0) == pl)
			if (getValueAt(0, 1) == pl && getValueAt(0, 2) == pl)
				return true;
			else if (getValueAt(1, 0) == pl && getValueAt(2, 0) == pl)
				return true;

		if (getValueAt(2, 2) == pl)
			if (getValueAt(1, 2) == pl && getValueAt(0, 2) == pl)
				return true;
			else if (getValueAt(2, 1) == pl && getValueAt(2, 0) == pl)
				return true;

		if (getValueAt(1, 1) == pl)
			if (getValueAt(1, 0) == pl && getValueAt(1, 2) == pl)
				return true;
			else if (getValueAt(0, 1) == pl && getValueAt(2, 1) == pl)
				return true;

		return false;
	}

	public boolean endGame() {
		return existsWinner() || !existsMoves();
	}

	public boolean existsWinner() {
		return win(PL1) || win(PL2);
	}

	// ----------------------------------------------------------
	// Métodos Privados
	// ----------------------------------------------------------


	private boolean isLegal(Coordinate c) throws IllegalMove {
		if (c == null)
			throw new IllegalArgumentException();

		if (!c.isValidFor(board))
			throw new IllegalMove("Coordenada Inválida");

		return board[c.getLine()][c.getColumn()] == EMPTY;
	}

	private void changePlayer() {
		if (next == PL1)
			next = PL2;
		else
			next = PL1;
	}

	private boolean existsMoves() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == ' ')
					return true;
			}
		}
		return false;
	}

	private boolean checkDiagonals(char pl) {
		if (board[0][0] == pl && board[1][1] == pl && board[2][2] == pl)
			return true;
		else
			return board[0][2] == pl && board[1][1] == pl && board[2][0] == pl;
	}

}
