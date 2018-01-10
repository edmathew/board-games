package com.ejpm.boardgames.minesweeper;

import com.ejpm.boardgames.common.Board;
import com.ejpm.boardgames.common.Coordinate;
import java.util.Random;

/**
 *
 * @author edgar.mateus
 */
public class MinesweeperBoard extends Board {

    private static final char BOMB = '*';
    private static final char FLAG = 'F';

    private final int bombQty;
    private int flagsCount;

    public MinesweeperBoard(final int width, final int height, final int bombQty) {
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

    private boolean cellIsBomb(final int x, final int y) {
        return getPosition(x, y) == BOMB;
    }
    
    public boolean cellIsBomb(final Coordinate coordinate){
        return getPosition(coordinate.getLine(), coordinate.getColumn()) == BOMB;
    }
    
    public boolean cellIsFlag(final Coordinate coordinate){
        return getPosition(coordinate.getLine(), coordinate.getColumn()) == FLAG;
    }

    public void setBomb(final int x, final int y) {
        setPosition(x, y, BOMB);
    }
    
    public int getFlagsCount(){
        return flagsCount;
    }
    
    public boolean isInsideTheBoard(final Coordinate coord) {
        return coord.getLine() >= 0 && coord.getColumn() >= 0 && coord.getLine() < getWidth() && coord.getColumn() < getHeight();
    }
}
