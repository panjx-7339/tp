package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;



class IntegerTokenTest {
    @Test
    public void integerToken_test() {
        IntegerToken token = new IntegerToken("test", 5, 100);

        assertEquals("[5...100]", token.getPreview());

        assertEquals(true, token.matches("5"));

        assertEquals(true, token.matches("100"));

        assertEquals(false, token.matches("101"));

        assertEquals(false, token.matches("-3"));

        assertEquals(false, token.matches("10abcd"));
    }
}
