package seedu.address.logic.parser.inputpatterns;

import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_DELETE;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * A Param that takes in a tag-value pair to be added, with id "--delete"
 */
public class TagDeleteParam extends Param {
    public TagDeleteParam(int minOccurrences, int maxOccurrences) {
        super(PARAM_ID_TAG_DELETE, minOccurrences, maxOccurrences);
    }

    @Override
    public String getPreview() {
        return PARAM_ID_TAG_DELETE + "<tag_name>";
    }

    @Override
    boolean valueMatches(String value) throws IllegalValueException {
        return Tag.validateDeleteNameNoDelimiter(value);
    }
}
