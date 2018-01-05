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

    @Before
    public void setup() {

    }

    @Test
    public void defaultBoardHas9by9Dimentions() {
        Minesweeper mines = new Minesweeper();
        assertThat(mines.tabuleiro.length, is(equalTo(9)));
        assertThat(mines.board.getWidth(), is(equalTo(9)));
        assertThat(mines.board.getHeight(),is(equalTo(9)));
    }

}
