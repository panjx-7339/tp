package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_DELETE;

import org.junit.jupiter.api.Test;


class TagDeleteParamTest {
    @Test
    public void tagAddParam_test() {
        TagDeleteParam param = new TagDeleteParam(2, 6);

        assertEquals(PARAM_ID_TAG_DELETE + "<tag_name>", param.getPreview());
    }
}
