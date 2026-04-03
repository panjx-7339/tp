package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG;

import org.junit.jupiter.api.Test;


class TagParamTest {
    @Test
    public void tagEditParam_test() {
        TagParam param = new TagParam(2, 6);

        assertEquals(PARAM_ID_TAG + "<tag_name>[:<tag_value>]", param.getPreview());
    }
}
