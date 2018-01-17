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

    @Test
    public void decoratorValidation() {
        final String input
                = "----\n"
                + "----\n"
                + "----\n"
                + "----\n";

        final String output
                = "   1  2  3  4  \n"
                + "A  -  -  -  -  \n"
                + "B  -  -  -  -  \n"
                + "C  -  -  -  -  \n"
                + "D  -  -  -  -  \n";

        assertThat(new ConsoleOutputGridDecorator(4).apply(input), is(equalTo(output)));
    }

    @Test
    public void decoratorValidationWhenColumnHas2Digits() {
        final String input
                = "------------\n"
                + "------------\n"
                + "------------\n"
                + "------------\n";

        final String output
                = "   1  2  3  4  5  6  7  8  9  10 11 12 \n"
                + "A  -  -  -  -  -  -  -  -  -  -  -  -  \n"
                + "B  -  -  -  -  -  -  -  -  -  -  -  -  \n"
                + "C  -  -  -  -  -  -  -  -  -  -  -  -  \n"
                + "D  -  -  -  -  -  -  -  -  -  -  -  -  \n";

        assertThat(new ConsoleOutputGridDecorator(12).apply(input), is(equalTo(output)));
    }
}
