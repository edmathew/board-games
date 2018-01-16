package com.ejpm.boardgames.common;

import com.ejpm.boardgames.common.console.ConsoleOutputGridDecorator;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author edgar.mateus
 */
public class ConsoleOutputGridDecoratorTest {

    private final static String INPUT_TEST
            = "----\n"
            + "----\n"
            + "----\n"
            + "----\n";

    private final static String EXPECTED_OUTPUT
            = "  1 2 3 4 \n"
            + "A - - - - \n"
            + "B - - - - \n"
            + "C - - - - \n"
            + "D - - - - \n";

    @Test
    public void decoratorValidation() {
        assertThat(new ConsoleOutputGridDecorator(4).apply(INPUT_TEST), is(equalTo(EXPECTED_OUTPUT)));
    }

}
