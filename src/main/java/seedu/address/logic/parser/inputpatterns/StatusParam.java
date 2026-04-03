package seedu.address.logic.parser.inputpatterns;

import static seedu.address.logic.parser.CliSyntax.PARAM_ID_STATUS;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Status;

/**
 * A Param of the id "--status".
 */
public class StatusParam extends Param {

    public StatusParam(int minOccurrences, int maxOccurrences) {
        super(PARAM_ID_STATUS, minOccurrences, maxOccurrences);
    }

    @Override
    public String getPreview() {
        return PARAM_ID_STATUS + " <status>";
    }

    @Override
    boolean valueMatches(String value) throws IllegalValueException {
        if (!Status.isValidStatus(value)) {
            throw new IllegalValueException("Status must be one of: NONE, TARGET, SCAM, IGNORE");
        }
        return true;
    }
}
