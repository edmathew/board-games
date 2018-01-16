package com.ejpm.boardgames.minesweeper.console;

import com.ejpm.boardgames.common.Coordinate;
import com.ejpm.boardgames.minesweeper.MinesweeperBoard;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 *
 * @author edgar.mateus
 */
public class MinesweeperConsoleBoardDisplayTest {

    private static final String EMPTY_STRING = "";
    private MinesweeperBoard board;

    @Before
    public void setUp() {
        board = new MinesweeperBoard(9, 9, 1);
        board.setBomb(new Coordinate(2, 0));
        board.toggleFlag(new Coordinate(3, 0));
    }

    @Test
    public void anEmptyBoardReturnsEmtpyString() {
        assertThat(new MinesweeperConsoleBoardDisplay().getStringRepresentation(new MinesweeperBoard(0, 0, 0)), is(equalTo(EMPTY_STRING)));
    }

}
