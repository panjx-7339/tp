package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_EMAIL;

import org.junit.jupiter.api.Test;


class EmailParamTest {
    @Test
    public void emailParam_test() {
        EmailParam param = new EmailParam(2, 5);

        assertEquals(PARAM_ID_EMAIL + " <email>", param.getPreview());
    }
}
