package seedu.address.logic.parser.inputpatterns;

import static seedu.address.logic.parser.CliSyntax.PARAM_ID_EMAIL;

import seedu.address.model.person.Email;

/**
 * A Param of the id "-email"
 */
public class EmailParam extends Param {

    public EmailParam(int minOccurences, int maxOccurences) {
        super(PARAM_ID_EMAIL, minOccurences, maxOccurences);
    }

    @Override
    public String getPreview() {
        return PARAM_ID_EMAIL + " <email>";
    }

    @Override
    boolean valueMatches(String value) {
        return Email.isValidEmail(value);
    }
}

