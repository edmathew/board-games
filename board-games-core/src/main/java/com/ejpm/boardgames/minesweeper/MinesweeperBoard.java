package com.ejpm.boardgames.minesweeper;

import com.ejpm.boardgames.common.Board;

/**
 *
 * @author edgar.mateus
 */
public class MinesweeperBoard extends Board {

    private final int bombQty;

    public MinesweeperBoard(int width, int height, int bombQty) {
        super(width, height);
        this.bombQty = bombQty;
    }

    /*
    * Temporary for main class refactor
     */
    char[][] getTabuleiro() {
        return board;
    }

    void setTabuleiro(char[][] tabuleiro) {
        board = tabuleiro;
    }

}
