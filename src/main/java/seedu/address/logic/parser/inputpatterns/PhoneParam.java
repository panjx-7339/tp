package seedu.address.logic.parser.inputpatterns;

import static seedu.address.logic.parser.CliSyntax.PARAM_ID_PHONE;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Phone;



/**
 * A param that takes in the phone, with id "--phone"
 */
public class PhoneParam extends Param {

    public PhoneParam(int minOccurrences, int maxOccurrences) {
        super(PARAM_ID_PHONE, minOccurrences, maxOccurrences);
    }

    @Override
    public String getPreview() {
        return PARAM_ID_PHONE + " <phone_number>";
    }

    @Override
    boolean valueMatches(String value) throws IllegalValueException {
        return Phone.validatePhone(value);
    }
}
