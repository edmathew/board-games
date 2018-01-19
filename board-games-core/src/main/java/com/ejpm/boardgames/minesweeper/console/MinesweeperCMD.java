package com.ejpm.boardgames.minesweeper.console;

import com.ejpm.boardgames.common.console.ConsoleInputException;
import com.ejpm.boardgames.minesweeper.Minesweeper;

/**
 * Console game driver
 *
 * @author edgar.mateus
 */
public class MinesweeperCMD {

    /**
     * Returns a Minesweeper game based on the command line args.
     * 
     * @param args The command line inputs
     * @return ready to play game object
     * @throws com.ejpm.boardgames.common.console.ConsoleInputException
     */
    public Minesweeper generateGame(final String[] args) throws ConsoleInputException {
        final int option = Integer.parseInt(args[0]);
        switch (option) {
            case 1:
                return new Minesweeper();
            case 2:
                return new Minesweeper(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
            default:
                throw new ConsoleInputException("Inalid menu option - " + option);
        }
    }
}
