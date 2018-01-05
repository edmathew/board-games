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
public class MinesweeperBoardTest {

    private MinesweeperBoard board;
    
    @Before
    public void setUp() {
        board = new MinesweeperBoard(9, 9, 10);
    }
    
    @Test
    public void quantityOfBombsIsEqualToTheBombsSpread(){
        board.spreadBombs();
        assertThat(board.getCurrentBombQty(), is(board.getBombQty()));
    }
 
}
