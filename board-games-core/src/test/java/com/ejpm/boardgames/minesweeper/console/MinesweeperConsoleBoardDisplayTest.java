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
        board = new MinesweeperBoard(4, 4, 1);
        board.setBomb(new Coordinate(2, 0));
        board.setBomb(new Coordinate(3, 1));
        board.toggleFlag(new Coordinate(3, 0));
        board.toggleFlag(new Coordinate(3, 1));

    }

    @Test
    public void anEmptyBoardReturnsEmtpyString() {
        assertThat(new MinesweeperConsoleBoardDisplay().getStringRepresentation(new MinesweeperBoard(0, 0, 0), false), is(equalTo(EMPTY_STRING)));
    }

    @Test
    public void noBombsBoard() {
        final String representation = new MinesweeperConsoleBoardDisplay().getStringRepresentation(new MinesweeperBoard(4, 4, 0), false);
        assertThat(representation, is(equalTo(
                "----\n"
                + "----\n"
                + "----\n"
                + "----\n")));
    }

    @Test
    public void boardWithBombAndFlag() {
        final String representation = new MinesweeperConsoleBoardDisplay().getStringRepresentation(board, false);
        assertThat(representation, is(equalTo(
                "---F\n"
                + "---F\n"
                + "----\n"
                + "----\n")));
    }

    @Test
    public void inRevealBombModeBombsAreShown() {
        final String representation = new MinesweeperConsoleBoardDisplay().getStringRepresentation(board, true);
        assertThat(representation, is(equalTo(
                "--*F\n"
                + "---*\n"
                + "----\n"
                + "----\n")));
    }
}
