import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class calculatorTest {

    @Test
    public void testInfix() {
        calc calculator = new calc();
        assertEquals(5, calculator.infixCalculator("2 + 3"));
        assertEquals(7, calculator.infixCalculator("1 + 6"));
        assertEquals(51, calculator.infixCalculator("20 + 31"));
        assertEquals(142, calculator.infixCalculator("123 + 19"));
        assertEquals(-3, calculator.infixCalculator("-5 + 2"));
        assertEquals(-3, calculator.infixCalculator("2 + -5"));
        assertEquals(2, calculator.infixCalculator("5 - 3"));
        assertEquals(13, calculator.infixCalculator("8 - -5"));
        assertEquals(-30, calculator.infixCalculator("-15 * 2"));
        assertEquals(-60, calculator.infixCalculator("3 * -20"));
    }

    @Test
    public void testPostfix() {
        calc calculator = new calc();
        assertEquals(5, calculator.postfixCalculator("2 3 +"));
        assertEquals(7, calculator.postfixCalculator("1 6 +"));
        assertEquals(51, calculator.postfixCalculator("20 31 +"));
        assertEquals(142, calculator.postfixCalculator("123 19 +"));
        assertEquals(-3, calculator.postfixCalculator("-5 2 +"));
        assertEquals(-3, calculator.postfixCalculator("2 -5 +"));
        assertEquals(2, calculator.postfixCalculator("5 3 -"));
        assertEquals(13, calculator.postfixCalculator("8 -5 -"));
        assertEquals(-30, calculator.postfixCalculator("-15 2 *"));
        assertEquals(-60, calculator.postfixCalculator("3 -20 *"));
    }

}
