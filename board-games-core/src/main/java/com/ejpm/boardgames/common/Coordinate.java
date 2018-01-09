package com.ejpm.boardgames.common;

/**
 * Represent a position for a given point in 2 dimensions (x as line, y as
 * column).
 *
 * @author edgar.mateus
 */
public class Coordinate {

    private final int line;
    private final int column;

    public Coordinate(final int line, final int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
