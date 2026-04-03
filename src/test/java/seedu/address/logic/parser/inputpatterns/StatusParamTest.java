package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_STATUS;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

class StatusParamTest {
    @Test
    public void statusParam_getPreview_returnsExpectedFormat() {
        StatusParam param = new StatusParam(0, 1);

        assertEquals(PARAM_ID_STATUS + " <status>", param.getPreview());
    }

    @Test
    public void statusParam_matchesValidStatus_returnsTrue() throws IllegalValueException {
        StatusParam param = new StatusParam(0, 5);

        assertTrue(param.matches(PARAM_ID_STATUS + " TARGET"));
        assertTrue(param.matches(PARAM_ID_STATUS + " ignore"));
    }

    @Test
    public void statusParam_matchesInvalidStatus_throwsIllegalValueException() {
        StatusParam param = new StatusParam(0, 5);

        assertThrows(IllegalValueException.class,
                "Status must be one of: NONE, TARGET, SCAM, IGNORE", () -> param.matches(PARAM_ID_STATUS + " invalid"));
    }
}
