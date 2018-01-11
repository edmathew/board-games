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

    private class TestableMinesweeper extends Minesweeper {

        private final Stack<String> keyboardInputs;
        
        public TestableMinesweeper(final MinesweeperBoard board, final Stack<String> keyboardInputs ) {
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
                if (mines.getBoard().cellIsBomb(new Coordinate(i, j))) {
                    bombCount++;
                }
            }
        }

        assertThat(bombCount, is(equalTo(10)));
    }

    @Test
    public void askForShotReturnsACoordinate() {
        final Coordenada coordinate = mines.pedeJogada();
        assertThat(coordinate.getColuna(), is(equalTo(0)));
        assertThat(coordinate.getLinha(), is(equalTo(0)));
    }
    
    @Test
    public void markFlagInPositionMarkFlagInBoard(){
        int currentFlagCount = mines.getContadorBandeiras();
        mines.markFlag("+ A1", null);
        assertTrue(mines.getBoard().cellIsFlag(new Coordinate(0, 0)));
        assertThat(currentFlagCount + 1, is(equalTo(mines.getContadorBandeiras())));
    }
    
    @Test
    public void whenUnmarkingAFlagThePositionReturnsToHidden(){
        mines.markFlag("+ A1", null);
        mines.markFlag("+ A1", null);
        assertFalse(mines.getBoard().cellIsFlag(new Coordinate(0, 0)));
    }
    
    @Test
    public void markFlagInPositionMarkFlagInBoardAtColumnWith2Digits(){
        mines.markFlag("+ A10", null);
        assertTrue(mines.getBoard().cellIsFlag(new Coordinate(0, 9)));
    }
    
    @Test
    public void FlagInCoordinateOutOfBoounds(){
        mines.markFlag("+ A43", null);
    }
    
}
