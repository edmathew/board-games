package com.ejpm.boardgames.minesweeper.console;

import com.ejpm.boardgames.common.Coordinate;
import com.ejpm.boardgames.minesweeper.MinesweeperBoard;

/**
 *
 * @author edgar.mateus
 */
public class MinesweeperConsoleBoardDisplay {

    private static final String EMPTY_STRING = "";

    public String getStringRepresentation(final MinesweeperBoard board, final boolean showBombs) {
        final StringBuffer representation = new StringBuffer(EMPTY_STRING);

        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                final Coordinate c = new Coordinate(j, i);

                if (showBombs) {
                    representation.append(getRevealRepresentation(board, c));
                } else {
                    representation.append(getRepresentation(board, c));
                }
            }
            representation.append("\n");
        }

        return representation.toString();
    }

    private char getRepresentation(final MinesweeperBoard board, final Coordinate c) {
        if (board.isBomb(c)) {
            return MinesweeperBoard.EMPTY_CELL;
        }

        if (board.isFlagAndBomb(c)) {
            return MinesweeperBoard.FLAG;
        }

        return board.getPosition(c.getLine(), c.getColumn());
    }

    private char getRevealRepresentation(final MinesweeperBoard board, final Coordinate c) {
        if (board.isFlagAndBomb(c)) {
            return MinesweeperBoard.BOMB;
        }

        return board.getPosition(c.getLine(), c.getColumn());
    }
}
