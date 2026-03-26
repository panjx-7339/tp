package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_NAME;

import org.junit.jupiter.api.Test;


class NameParamTest {
    @Test
    public void phoneParam_test() {
        NameParam param = new NameParam(2, 6);

        assertEquals(PARAM_ID_NAME + " <name>", param.getPreview());
    }
}
