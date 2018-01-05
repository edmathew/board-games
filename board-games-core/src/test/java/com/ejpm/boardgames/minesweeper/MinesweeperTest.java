package com.ejpm.boardgames.minesweeper;

import com.ejpm.boardgames.common.Board;
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

    private Minesweeper mines;
    
    @Before
    public void setUp() {
        mines = new Minesweeper();
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
}
