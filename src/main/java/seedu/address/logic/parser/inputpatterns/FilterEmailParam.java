package seedu.address.logic.parser.inputpatterns;

import static seedu.address.logic.parser.CliSyntax.PARAM_ID_EMAIL;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * A filter-only param that accepts any non-empty email substring.
 */
public class FilterEmailParam extends Param {

    public FilterEmailParam(int minOccurrences, int maxOccurrences) {
        super(PARAM_ID_EMAIL, minOccurrences, maxOccurrences);
    }

    @Override
    public String getPreview() {
        return PARAM_ID_EMAIL + " <email_substring>";
    }

    @Override
    boolean valueMatches(String value) throws IllegalValueException {
        return !value.isBlank();
    }
}
