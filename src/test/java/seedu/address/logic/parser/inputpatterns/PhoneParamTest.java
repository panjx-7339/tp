package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_PHONE;

import org.junit.jupiter.api.Test;


class PhoneParamTest {
    @Test
    public void phoneParam_test() {
        PhoneParam param = new PhoneParam(2, 6);

        assertEquals(PARAM_ID_PHONE + " <phone_number>", param.getPreview());
    }
}
