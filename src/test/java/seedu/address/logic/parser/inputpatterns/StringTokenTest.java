package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StringTokenTest {
    @Test
    public void stringToken_test() {
        StringToken token = new StringToken("field", "<field>");

        assertEquals(token.getPreview(), "<field>");

        assertEquals(true, token.matches("donk"));

        assertEquals(false, token.matches(""));
    }
}
