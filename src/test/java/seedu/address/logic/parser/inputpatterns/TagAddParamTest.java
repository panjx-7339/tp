package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_ADD;

import org.junit.jupiter.api.Test;


class TagAddParamTest {
    @Test
    public void tagAddParam_test() {
        TagAddParam param = new TagAddParam(2, 6);

        assertEquals(PARAM_ID_TAG_ADD + "<tag_name>:<tag_value>", param.getPreview());
    }
}
