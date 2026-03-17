package seedu.address.logic.parser.inputpatterns;

import static seedu.address.logic.parser.CliSyntax.PARAM_ID_NAME;

import seedu.address.model.person.Name;

/**
 * A Param of the id "--name".
 */
public class NameParam extends Param {

    public NameParam(int minOccurences, int maxOccurences) {
        super(PARAM_ID_NAME, minOccurences, maxOccurences);
    }

    @Override
    public String getPreview() {
        return PARAM_ID_NAME + " <name>";
    }

    @Override
    boolean valueMatches(String value) {
        return Name.isValidName(value);
    }
}

