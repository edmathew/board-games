package com.ejpm.boardgames.minesweeper;

import com.ejpm.boardgames.common.Coordenada;
import com.ejpm.boardgames.common.Coordinate;
import java.util.Stack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 *
 * @author edgar.mateus
 */
public class MinesweeperTest {

    private final Coordinate COORDINATE_A1 = new Coordinate(0, 0);
    private final Coordinate COORDINATE_A10 = new Coordinate(0, 9);
    private final Coordinate COORDINATE_A100 = new Coordinate(0, 99);

    private final Coordinate COORDINATE_C1_BOMB = new Coordinate(2, 0);

    private class TestableMinesweeper extends Minesweeper {

        private final Stack<String> keyboardInputs;

        public TestableMinesweeper(final MinesweeperBoard board, final Stack<String> keyboardInputs) {
            super(board);
            this.keyboardInputs = keyboardInputs;
        }

        @Override
        public String getMoveFromKeyboard() {
            return keyboardInputs.pop();
        }
    }

    private Minesweeper mines;

    @Before
    public void setUp() {
        final Stack<String> movePlays = new Stack<>();
        movePlays.push("A1");
        movePlays.push("+ B1");

        final MinesweeperBoard board = new MinesweeperBoard(9, 10, 1);
        board.setBomb(2, 0);
        mines = new TestableMinesweeper(board, movePlays);

    }

    @Test
    public void inDefaultGameBoardHas9by9Dimentions() {
        mines = new Minesweeper();
        assertThat(mines.tabuleiro.length, is(equalTo(9)));
        assertThat(mines.board.getWidth(), is(equalTo(9)));
        assertThat(mines.board.getHeight(), is(equalTo(9)));
    }

    @Test
    public void inDefaultGameBoardIsFilledWith10Bombs() {
        mines = new Minesweeper();
        int bombCount = 0;
        for (int i = 0; i < mines.getBoard().getWidth(); i++) {
            for (int j = 0; j < mines.getBoard().getHeight(); j++) {
                if (mines.getBoard().isBomb(new Coordinate(i, j))) {
                    bombCount++;
                }
            }
        }

        assertThat(bombCount, is(equalTo(10)));
    }

    @Test
    public void askForShotReturnsACoordinate() {
        final Coordenada coordinate = mines.pedeJogada("A1");
        assertThat(coordinate.getColuna(), is(equalTo(0)));
        assertThat(coordinate.getLinha(), is(equalTo(0)));
    }

    @Test
    public void coordinatePrefixedByPlusSignMarksAFlagInBoard() {
        mines.pedeJogada("+ A1");
        assertTrue(mines.getBoard().isFlag(COORDINATE_A1));
    }

    @Test
    public void markFlagInPositionMarkFlagInBoard() {
        mines.markFlag(COORDINATE_A1);
        assertThat(mines.getBoard().getFlagsCount(), is(equalTo(1)));

    }

    @Test
    public void whenUnmarkingAFlagThePositionReturnsToHidden() {
        mines.markFlag(COORDINATE_A1);
        mines.markFlag(COORDINATE_A1);
        assertFalse(mines.getBoard().isFlag(COORDINATE_A1));
    }

    @Test
    public void markFlagInPositionMarkFlagInBoardAtColumnWith2Digits() {
        mines.markFlag(COORDINATE_A10);
        assertTrue(mines.getBoard().isFlag(COORDINATE_A10));
    }

    @Test
    public void flagInCoordinateOutOfBoounds() {
        int currentFlagCount = mines.getBoard().getFlagsCount();
        mines.markFlag(COORDINATE_A100);
        assertThat(currentFlagCount, is(equalTo(mines.getBoard().getFlagsCount())));
    }

    @Test
    public void flagInBombPositionTogglesABombAndFlag() {
        mines.markFlag(COORDINATE_C1_BOMB);
        assertThat(mines.getBoard().getPosition(COORDINATE_C1_BOMB.getLine(), COORDINATE_C1_BOMB.getColumn()), is(equalTo('B')));
    }

}
