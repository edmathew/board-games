package com.ejpm.boardgames.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ejpmateus
 */
public class BoardTest {

    private Board board;

    private static final int BOARD_SIZE = 3;

    @Before
    public void setUp() {
        board = new Board(BOARD_SIZE, BOARD_SIZE);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void boardSizeIsCorrect() {
        assertTrue(board.getWidth() == BOARD_SIZE);
        assertTrue(board.getHeight() == BOARD_SIZE);
    }

    @Test
    public void afterInitTheBoardIsEmpty() {
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                assertTrue(board.getPosition(j, j) == Board.EMPTY_CELL);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidCoordinateThrowsException() {
        board.getPosition(-1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setValueAtACoordinateOutOfBoundsThrowsException() {
        board.setPosition(new Coordinate(100, 100), 'B');
    }

    @Test
    public void coordinatesAreOnTheBoard() {
        assertTrue(board.insideTheBoard(new Coordinate(0, 0)));
        assertTrue(board.insideTheBoard(new Coordinate(1, 0)));
        assertTrue(board.insideTheBoard(new Coordinate(5, 5)));
        assertTrue(board.insideTheBoard(new Coordinate(8, 8)));
        assertTrue(board.insideTheBoard(new Coordinate(0, 8)));
    }

    @Test
    public void coordinatesAreBotOnTheBoard() {
        assertFalse(board.insideTheBoard(new Coordinate(-1, 0)));
        assertFalse(board.insideTheBoard(new Coordinate(1, 9)));
        assertFalse(board.insideTheBoard(new Coordinate(5, 176)));
        assertFalse(board.insideTheBoard(new Coordinate(0, -1)));
        assertFalse(board.insideTheBoard(new Coordinate(-1, -8)));
    }

}
