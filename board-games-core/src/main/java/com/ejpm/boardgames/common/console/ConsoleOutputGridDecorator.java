package com.ejpm.boardgames.common.console;

/**
 * Decorates the grid for console output.
 *
 * @author edgar.mateus
 */
public class ConsoleOutputGridDecorator {

    private static final String LINE_FEED = "\n";

    private static final String DOUBLE_CELL_SPACING = "  ";
    private static final String SINGLE_CELL_SPACING = " ";

    private final int width;

    /**
     *
     * @param width The column count
     */
    public ConsoleOutputGridDecorator(final int width) {
        this.width = width;
    }

    public String apply(final String grid) {
        final StringBuilder builder = new StringBuilder(DOUBLE_CELL_SPACING).append(SINGLE_CELL_SPACING);

        for (int i = 1; i <= width; i++) {
            builder.append(i).append(i > 9 ? SINGLE_CELL_SPACING : DOUBLE_CELL_SPACING);
        }

        builder.append(LINE_FEED);

        char letterCoord = 'A';

        for (String line : grid.split(LINE_FEED)) {
            builder.append(letterCoord++).append(DOUBLE_CELL_SPACING);
            for (int i = 0; i < line.length(); i++) {
                builder.append(line.charAt(i)).append(DOUBLE_CELL_SPACING);
            }
            builder.append(LINE_FEED);
        }

        return builder.toString();
    }
}
