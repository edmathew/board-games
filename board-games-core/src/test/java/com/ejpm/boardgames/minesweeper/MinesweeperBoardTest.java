package com.ejpm.boardgames.minesweeper;

import com.ejpm.boardgames.common.Coordinate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 *
 * @author edgar.mateus
 */
public class MinesweeperBoardTest {

    private MinesweeperBoard board;

    @Before
    public void setUp() {
        board = new MinesweeperBoard(9, 9, 1);
        board.setBomb(2, 0);
    }

    @Test
    public void quantityOfBombsIsEqualToTheBombsSpread() {
        assertThat(board.getCurrentBombQty(), is(equalTo(board.getBombQty())));
    }

    @Test
    public void c1IsABomb() {
        assertTrue(board.cellIsBomb(new Coordinate(2, 0)));
    }

    @Test
    public void b1IsNotABomb() {
        assertFalse(board.cellIsBomb(new Coordinate(1, 0)));
    }

    @Test
    public void coordinatesAreOnTheBoard() {
        assertTrue(board.isInsideTheBoard(new Coordinate(0, 0)));
        assertTrue(board.isInsideTheBoard(new Coordinate(1, 0)));
        assertTrue(board.isInsideTheBoard(new Coordinate(5, 5)));
        assertTrue(board.isInsideTheBoard(new Coordinate(8, 8)));
        assertTrue(board.isInsideTheBoard(new Coordinate(0, 8)));
    }
    
    @Test
    public void coordinatesAreBotOnTheBoard() {
        assertFalse(board.isInsideTheBoard(new Coordinate(-1, 0)));
        assertFalse(board.isInsideTheBoard(new Coordinate(1, 9)));
        assertFalse(board.isInsideTheBoard(new Coordinate(5, 176)));
        assertFalse(board.isInsideTheBoard(new Coordinate(0, -1)));
        assertFalse(board.isInsideTheBoard(new Coordinate(-1, -8)));
    }

}
