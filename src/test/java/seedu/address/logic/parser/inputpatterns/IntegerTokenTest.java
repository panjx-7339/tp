package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;


class IntegerTokenTest {
    @Test
    public void integerToken_test() throws IllegalValueException {
        IntegerToken token = new IntegerToken("test", 5, 100);

        assertEquals("[5...100]", token.getPreview());

        assertTrue(token.matches("5"));

        assertTrue(token.matches("100"));

        Exception e = assertThrows(IllegalValueException.class, () -> token.matches("101"));
        assertEquals("101 is more than the maximum allowable value of 100.", e.getMessage());

        e = assertThrows(IllegalValueException.class, () ->token.matches("-3"));
        assertEquals("-3 is less than the minimum allowable value of 5.", e.getMessage());

        e = assertThrows(IllegalValueException.class, () -> token.matches("ten"));
        assertEquals("ten" + IntegerToken.INVALID_STRING, e.getMessage());
    }
}
