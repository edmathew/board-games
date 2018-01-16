package com.ejpm.boardgames.minesweeper.console;

import com.ejpm.boardgames.common.Board;
import com.ejpm.boardgames.common.Coordinate;
import com.ejpm.boardgames.minesweeper.MinesweeperBoard;

/**
 *
 * @author edgar.mateus
 */
public class MinesweeperConsoleBoardDisplay {

    private static final String EMPTY_STRING = "";

    public String getStringRepresentation(final MinesweeperBoard board) {
        final StringBuffer representation = new StringBuffer(EMPTY_STRING);

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                if (board.isBomb(new Coordinate(i, j))) {
                    representation.append(Board.EMPTY_CELL);
                } else {
                    representation.append(board.getPosition(i, j));
                }

            }
            representation.append("\n");
        }

        return representation.toString();
    }

}
