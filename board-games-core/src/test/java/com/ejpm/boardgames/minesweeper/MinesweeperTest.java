package com.ejpm.boardgames.minesweeper;

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

        private final static String A1_PLAY = "A1";
        
        @Override
        public String getMoveFromKeyboard() {
            return A1_PLAY;
        }
    }

    private Minesweeper mines;
    
    @Before
    public void setUp() {
        mines = new TestableMinesweeper();
    }

    @Test
    public void defaultBoardHas9by9Dimentions() {
        assertThat(mines.tabuleiro.length, is(equalTo(9)));
        assertThat(mines.board.getWidth(), is(equalTo(9)));
        assertThat(mines.board.getHeight(),is(equalTo(9)));
    }

    @Test
    public void atCreationBoardIsFilledWith10Bombs(){
        int bombCount = 0;
        for(int i = 0; i < mines.board.getWidth(); i ++){
            for(int j = 0; j < mines.board.getHeight(); j ++){
                if(mines.board.cellIsBomb(i, j)){
                    bombCount ++;
                }
            }
        }
        
        assertThat(bombCount, is(equalTo(10)));
    }
    
    
    @Test
    public void askForShotReturnsACoordinate(){
        mines.pedeJogada();
    }
    
    
}
