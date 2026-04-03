package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_EMAIL;

import org.junit.jupiter.api.Test;

class FilterEmailParamTest {
    @Test
    public void filterEmailParam_getPreview_returnsExpectedFormat() {
        FilterEmailParam param = new FilterEmailParam(0, 100);

        assertEquals(PARAM_ID_EMAIL + " <email_substring>", param.getPreview());
    }

    @Test
    public void filterEmailParam_matchesNonBlankSubstring_returnsTrue() throws Exception {
        FilterEmailParam param = new FilterEmailParam(0, 100);

        assertTrue(param.matches(PARAM_ID_EMAIL + " alice@example.com"));
        assertTrue(param.matches(PARAM_ID_EMAIL + " @example"));
    }

    @Test
    public void filterEmailParam_matchesBlankSubstring_returnsFalse() throws Exception {
        FilterEmailParam param = new FilterEmailParam(0, 100);

        assertFalse(param.matches(PARAM_ID_EMAIL + " "));
        assertFalse(param.matches(PARAM_ID_EMAIL + "    "));
    }
}
