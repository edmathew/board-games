package com.ejpm.boardgames.minesweeper.console;

import com.ejpm.boardgames.common.console.ConsoleInputException;
import com.ejpm.boardgames.minesweeper.Minesweeper;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edgar.mateus
 */
public class MinesweeperCMDTest {

    @Test(expected = ConsoleInputException.class)
    public void invalidOptionThrowsConsoleException() throws ConsoleInputException {
        new MinesweeperCMD().generateGame(new String[]{"0"});
    }

    @Test
    public void option1ReturnsAStandardGame9by9() throws ConsoleInputException {
        final Minesweeper mines = new MinesweeperCMD().generateGame(new String[]{"1"});
        assertThat(mines.getBoard().getWidth(), is(equalTo(9)));
        assertThat(mines.getBoard().getHeight(), is(equalTo(9)));
    }

    @Test
    public void option2ReturnsBoardWithCustomDimentions() throws ConsoleInputException {
        final Minesweeper mines = new MinesweeperCMD().generateGame(new String[]{"2", "10", "10", "5"});
        assertThat(mines.getBoard().getWidth(), is(equalTo(10)));
        assertThat(mines.getBoard().getHeight(), is(equalTo(10)));
    }
}
