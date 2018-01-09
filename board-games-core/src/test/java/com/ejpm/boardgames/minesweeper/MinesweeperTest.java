package com.ejpm.boardgames.minesweeper;

import com.ejpm.boardgames.common.Coordenada;
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
        
        final MinesweeperBoard board = new MinesweeperBoard(9, 9, 1);
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
        for (int i = 0; i < mines.board.getWidth(); i++) {
            for (int j = 0; j < mines.board.getHeight(); j++) {
                if (mines.board.cellIsBomb(i, j)) {
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

}
