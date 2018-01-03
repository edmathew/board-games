package mines;

public class Coordinate {

	private int line;
	private int column;
	private boolean flag;

	public Coordinate(final int line, final int column) {
		if (line < 0 || column < 0)
			throw new IllegalArgumentException();

		this.line = line;
		this.column = column;
		flag = false;
	}

	public Coordinate(final int line, final int column, final boolean flag) {
		if (line < 0 || column < 0)
			throw new IllegalArgumentException();

		this.line = line;
		this.column = column;
		this.flag = flag;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public boolean getFlag() {
		return flag;
	}

	public boolean isValidFor(char[][] board) {
		if (board == null)
			throw new IllegalArgumentException();

		return line >= 0 && line < board.length && column >= 0
				&& column < board[0].length;
	}

}
