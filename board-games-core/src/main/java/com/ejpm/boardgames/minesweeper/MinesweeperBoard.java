package com.ejpm.boardgames.minesweeper;

import com.ejpm.boardgames.common.Board;
import com.ejpm.boardgames.common.Coordinate;
import java.util.Random;

/**
 *
 * @author edgar.mateus
 */
public class MinesweeperBoard extends Board {

    public static final char FLAG = 'F';
    public static final char BOMB = '*';

    private static final char FLAG_AND_BOMB = 'B';

    private final int bombQty;

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
     *
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

    public boolean isBomb(final Coordinate coordinate) {
        return getPosition(coordinate.getLine(), coordinate.getColumn()) == BOMB;
    }

    public boolean isFlag(final Coordinate coordinate) {
        return getPosition(coordinate.getLine(), coordinate.getColumn()) == FLAG;
    }

    public boolean isFlagAndBomb(final Coordinate coordinate) {
        return getPosition(coordinate.getLine(), coordinate.getColumn()) == FLAG_AND_BOMB;
    }

    private void setFlagAndBomb(final Coordinate coordinate) {
        setPosition(coordinate, FLAG_AND_BOMB);
    }

    @Deprecated
    public void setBomb(final int x, final int y) {
        setBomb(new Coordinate(x, y));
    }

    public int getFlagsCount() {
        int flagsCount = 0;
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (isFlag(new Coordinate(i, j)) || isFlagAndBomb(new Coordinate(i, j))) {
                    flagsCount++;
                }
            }
        }
        return flagsCount;
    }

    public void setBomb(final Coordinate c) {
        setPosition(c, BOMB);
    }

    private void setFlag(final Coordinate c) {
        setPosition(c, FLAG);
    }

    /**
     * Toggle the flag at the given coordinate
     *
     * @param c Coordinate to toogle the flag
     * @pre insideTheBoard(c)
     */
    public void toggleFlag(final Coordinate c) {
        if (isHidden(c)) {
            setFlag(c);
        } else if (isFlag(c)) {
            setHidden(c);
        } else if (isBomb(c)) {
            setFlagAndBomb(c);
        } else if (isFlagAndBomb(c)) {
            setBomb(c);
        }
    }

    public int getBombsAround(final Coordinate c) {
        int bombs = 0;

        for (int x = c.getLine()- 1; x <= c.getLine() + 1; x++) {
            for (int y = c.getColumn() - 1; y <= c.getColumn() + 1; y++) {
                final Coordinate coordAround = new Coordinate(x, y);
                if (insideTheBoard(coordAround)
                        && (isFlagAndBomb(coordAround) || isBomb(coordAround))) {
                    bombs++;
                }
            }
        }

        return bombs;
    }

    public boolean isSolved() {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (isHidden(new Coordinate(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void revealAt(final Coordinate c) {
        final int bombsAround = getBombsAround(c);
        setPosition(c, (char) (bombsAround + '0'));

        if (bombsAround == 0) {
            revealAround(c);
        }
    }

    private void revealAround(final Coordinate c) {
        for (int x = c.getLine()- 1; x <= c.getLine() + 1; x++) {
            for (int y = c.getColumn() - 1; y <= c.getColumn() + 1; y++) {
                final Coordinate coordAround = new Coordinate(x, y);
                if (insideTheBoard(coordAround) && isHidden(coordAround)) {
                    revealAt(coordAround);
                }
            }
        }
    }

}
