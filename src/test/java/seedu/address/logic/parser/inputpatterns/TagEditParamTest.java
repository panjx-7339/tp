package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_EDIT;

import org.junit.jupiter.api.Test;


class TagEditParamTest {
    @Test
    public void tagEditParam_test() {
        TagEditParam param = new TagEditParam(2, 6);

        assertEquals(PARAM_ID_TAG_EDIT + "<tag_name>:<tag_value>", param.getPreview());
    }
}
