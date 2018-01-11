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

    private static final Coordinate BOMB_COORDINATE = new Coordinate(2, 0);

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
}
