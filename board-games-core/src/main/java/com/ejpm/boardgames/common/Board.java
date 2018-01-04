package com.ejpm.boardgames.common;

/**
 * 
 * @author ejpmateus
 */
public class Board {

    public static final char EMPTY_CELL = ' ';

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
        if (x >= width || y >= height || x < 0 || y < 0) {
            throw new IllegalArgumentException("The coordinate must be valid for the board size");
        }

        return board[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
}
