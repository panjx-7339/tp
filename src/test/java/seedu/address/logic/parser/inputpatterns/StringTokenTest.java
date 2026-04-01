package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

class StringTokenTest {
    @Test
    public void stringToken_test() throws IllegalValueException {
        StringToken token = new StringToken("field", "<field>");

        assertEquals(token.getPreview(), "<field>");

        assertTrue(token.matches("donk"));

        Exception e = assertThrows(IllegalValueException.class, () -> token.matches(""));
        assertEquals("<field> cannot be empty", e.getMessage());
    }
}
