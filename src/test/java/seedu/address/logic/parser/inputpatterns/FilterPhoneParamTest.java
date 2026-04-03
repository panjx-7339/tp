package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_PHONE;

import org.junit.jupiter.api.Test;

class FilterPhoneParamTest {
    @Test
    public void filterPhoneParam_getPreview_returnsExpectedFormat() {
        FilterPhoneParam param = new FilterPhoneParam(0, 100);

        assertEquals(PARAM_ID_PHONE + " <phone_substring>", param.getPreview());
    }

    @Test
    public void filterPhoneParam_matchesNonBlankSubstring_returnsTrue() throws Exception {
        FilterPhoneParam param = new FilterPhoneParam(0, 100);

        assertTrue(param.matches(PARAM_ID_PHONE + " 94351253"));
        assertTrue(param.matches(PARAM_ID_PHONE + " 3512"));
    }

    @Test
    public void filterPhoneParam_matchesBlankSubstring_returnsFalse() throws Exception {
        FilterPhoneParam param = new FilterPhoneParam(0, 100);

        assertFalse(param.matches(PARAM_ID_PHONE + " "));
        assertFalse(param.matches(PARAM_ID_PHONE + "    "));
    }
}
