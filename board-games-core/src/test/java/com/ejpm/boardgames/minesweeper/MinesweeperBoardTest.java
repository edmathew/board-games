package com.ejpm.boardgames.minesweeper;

import com.ejpm.boardgames.common.Coordinate;
import com.ejpm.boardgames.minesweeper.console.MinesweeperConsoleBoardDisplay;
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

    private static final Coordinate BOMB_COORDINATE = new Coordinate(2, 0);

    private static final Coordinate B1_COORDINATE = new Coordinate(1, 0);
    private static final Coordinate BOTTOM_RIGHT = new Coordinate(8, 8);

    private MinesweeperBoard board;

    @Before
    public void setUp() {
        board = new MinesweeperBoard(9, 9, 3);
        board.setBomb(BOMB_COORDINATE);

        board.setBomb(new Coordinate(5, 5));
        board.setBomb(new Coordinate(6, 6));
    }

    @Test
    public void quantityOfBombsIsEqualToTheBombsSpread() {
        assertThat(board.getCurrentBombQty(), is(equalTo(board.getBombQty())));
    }

    @Test
    public void c1IsABomb() {
        assertTrue(board.isBomb(BOMB_COORDINATE));
    }

    @Test
    public void b1IsNotABomb() {
        assertFalse(board.isBomb(new Coordinate(1, 0)));
    }

    @Test
    public void flagInBombPositionTogglesABombAndFlag() {
        board.toggleFlag(BOMB_COORDINATE);
        assertThat(board.getPosition(2, 0), is(equalTo('B')));
    }

    @Test
    public void toggleAFlagAndBombReturnsToBomb() {
        board.toggleFlag(BOMB_COORDINATE);
        assertThat(board.getPosition(2, 0), is(equalTo('B')));

        board.toggleFlag(BOMB_COORDINATE);
        assertThat(board.getPosition(2, 0), is(equalTo('*')));
    }

    @Test
    public void thereAreNoBombsAround() {
        assertThat(board.getBombsAround(BOTTOM_RIGHT), is(equalTo(0)));
    }

    @Test
    public void thereIsABombArroundNoBombsAround() {
        System.out.println(new MinesweeperConsoleBoardDisplay().getStringRepresentation(board, true));
        assertThat(board.getBombsAround(B1_COORDINATE), is(equalTo(1)));
    }

    @Test
    public void atStartBoardIsNotSolved() {
        assertFalse(board.isSolved());
    }

    @Test
    public void testReveal() {
        System.out.println(new MinesweeperConsoleBoardDisplay().getStringRepresentation(board, true));
        board.revealAt(BOTTOM_RIGHT);
        assertThat(board.getPosition(1, 0), is(equalTo('1')));
        assertThat(board.getPosition(8, 8), is(equalTo('0')));
        assertThat(board.getPosition(6, 5), is(equalTo('2')));
        System.out.println(new MinesweeperConsoleBoardDisplay().getStringRepresentation(board, true));

        assertTrue(board.isSolved());

        System.out.println(new MinesweeperConsoleBoardDisplay().getStringRepresentation(board, true));

    }
}
