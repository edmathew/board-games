package com.ejpm.boardgames.minesweeper;

import com.ejpm.boardgames.common.Board;
import java.util.Random;

/**
 *
 * @author edgar.mateus
 */
public class MinesweeperBoard extends Board {

    private static final char BOMB = '*';

    private final int bombQty;
    private int flagsCount = 0;

    public MinesweeperBoard(int width, int height, int bombQty) {
        super(width, height);
        this.bombQty = bombQty;
    }

    /**
     * Spreads the bombs in the board
     */
    public void spreadBombs() {
        final Random r = new Random();

        for (int i = 0; i < bombQty; i++) {
            int x, 
                y;
            do {
                x = r.nextInt(getWidth());
                y = r.nextInt(getHeight());
            } while (cellIsBomb(x, y));

            setBomb(x, y);
        }

    }
    
    
    /**
     * Get the quantity of bombs in the board
     * @return 
     */
    public int getCurrentBombQty() {
        int bombCount = 0;
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (cellIsBomb(i, j)) {
                    bombCount++;
                }
            }
        }

        return bombCount;
    }

    /*
    * Temporary for main class refactor
     */
    @Deprecated
    public char[][] getTabuleiro() {
        return board;
    }

    public int getBombQty() {
        return bombQty;
    }

    public boolean cellIsBomb(final int x, final int y) {
        return getPosition(x, y) == BOMB;
    }

    private void setBomb(final int x, final int y) {
        setPosition(x, y, BOMB);
    }
    
    public void increaseFlags(){
        flagsCount ++;
    }

    public void decreaseFlags(){
        flagsCount --;
    }
    
    public int getFlagsCount(){
        return flagsCount;
    }
}
