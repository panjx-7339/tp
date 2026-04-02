package seedu.address.logic.parser.inputpatterns;

import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_ADD;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * A Param that takes in a tag-value pair to be added, with id "--add"
 */
public class TagAddParam extends Param {
    public TagAddParam(int minOccurrences, int maxOccurrences) {
        super(PARAM_ID_TAG_ADD, minOccurrences, maxOccurrences);
    }

    @Override
    public String getPreview() {
        return PARAM_ID_TAG_ADD + "<tag_name>:<tag_value>";
    }

    @Override
    boolean valueMatches(String value) throws IllegalValueException {
        return Tag.validateTagString(value);
    }
}
