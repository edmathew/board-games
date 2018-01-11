package com.ejpm.boardgames.common;

/**
 *
 * @author ejpmateus
 */
public class Board {

    public static final char EMPTY_CELL = '-';

    private final int width;
    private final int height;

    protected char[][] board;

    public Board(final int width, final int height) {
        this.width = width;
        this.height = height;

        board = new char[width][height];
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    public char getPosition(final int x, final int y) {
        if (!insideTheBoard(new Coordinate(x, y))) {
            throw new IllegalArgumentException(String.format("Coodinate %s,%s is not inside the board", x, y));
        }

        return board[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void setPosition(final int x, final int y, final char value) {
        board[x][y] = value;
    }

    protected void setPosition(final Coordinate c, final char value) {
        if (!insideTheBoard(c)) {
            throw new IllegalArgumentException(String.format("Coordinate %s is not inside the board", c.toString()));
        }

        setPosition(c.getLine(), c.getColumn(), value);
    }

    public boolean insideTheBoard(final Coordinate coord) {
        return coord.getLine() >= 0 && coord.getColumn() >= 0 && coord.getLine() < getWidth() && coord.getColumn() < getHeight();
    }

}
