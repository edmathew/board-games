package mines;

import java.util.Random;

public class Minesweeper {

	private static final char HIDDEN = '-';
	private static final char FLAG = 'F';
	private static final char FLAG_AND_BOMB = 'B';
	private static final char BOMB = '*';

	private static final int STANDARD_DIMENSION = 9;
	private static final int STANDARD_BOMBS_NR = 10;

	private char[][] board;
	private boolean ended = false;

	public Minesweeper() {
		board = new char[STANDARD_DIMENSION][STANDARD_DIMENSION];
		randomlyDeployBombs(STANDARD_BOMBS_NR);
	}

	public Minesweeper(Dimension d, int bombs_nr) {
		if (d == null)
			throw new IllegalArgumentException();

		board = new char[d.getWidth()][d.getHeight()];
		randomlyDeployBombs(bombs_nr);
	}

	public void randomlyDeployBombs(int bombs_nr) {
		if (bombs_nr < 0)
			throw new IllegalArgumentException();

		fill();

		Random r = new Random();

		for (int i = 0; i < bombs_nr; i++) {
			int x = r.nextInt(board.length);
			int y = r.nextInt(board[0].length);

			if (board[x][y] == HIDDEN)
				board[x][y] = BOMB;
			else
				i--;

		}
	}

	public void play(Coordinate c) {
		if (c == null || !c.isValidFor(board))
			throw new IllegalArgumentException();

		if (!isBomb(c))
			reveal(c);
		else
			ended = true;

	}

	public void putFlag(Coordinate c) {
		if (c == null || !c.isValidFor(board))
			throw new IllegalArgumentException();

		if (isBomb(c))
			board[c.getLine()][c.getColumn()] = FLAG_AND_BOMB;
		else
			board[c.getLine()][c.getColumn()] = FLAG;
	}

	// --------------------------------------------
	// Inspectores
	// --------------------------------------------

	public boolean isBomb(Coordinate c) {
		if (c == null || !c.isValidFor(board))
			throw new IllegalArgumentException();

		return board[c.getLine()][c.getColumn()] == BOMB
				|| board[c.getLine()][c.getColumn()] == FLAG_AND_BOMB;
	}

	public boolean isHidden(Coordinate c) {
		if (c == null || !c.isValidFor(board))
			throw new IllegalArgumentException();

		return board[c.getLine()][c.getColumn()] == HIDDEN;
	}
	
	public boolean isFlag(Coordinate c) {
		if (c == null || !c.isValidFor(board))
			throw new IllegalArgumentException();

		return board[c.getLine()][c.getColumn()] == FLAG
				|| board[c.getLine()][c.getColumn()] == FLAG_AND_BOMB;
	}

	public boolean existsHidden() {
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++)
				if (board[i][j] == HIDDEN)
					return true;

		return false;
	}

	public boolean gameIsOver() {
		return !existsHidden() || ended;
	}

	// --------------------------------------------
	// Métodos Privados
	// --------------------------------------------

	private void fill() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = HIDDEN;
			}
		}
	}

	private int countBombsAround(Coordinate c) {
		if (c == null || !c.isValidFor(board))
			throw new IllegalArgumentException();

		int count = 0;

		for (int i = c.getLine() - 1; i <= c.getLine() + 1; i++) {
			for (int j = c.getColumn() - 1; j <= c.getColumn() + 1; j++) {
				Coordinate aux = new Coordinate(i, j);
				if (aux.isValidFor(board) && isBomb(aux))
					count++;
			}
		}
		return count;
	}

	private void reveal(Coordinate c) {
		if (c == null || !c.isValidFor(board))
			throw new IllegalArgumentException();

		board[c.getLine()][c.getColumn()] = (char) ((int) '0' + countBombsAround(c));

		for (int i = c.getLine() - 1; i <= c.getLine() + 1; i++) {
			for (int j = c.getColumn() - 1; j <= c.getColumn() + 1; j++) {
				Coordinate aux = new Coordinate(i, j);
				if (aux.isValidFor(board) && isHidden(aux))
					reveal(aux);
			}
		}
	}

}
