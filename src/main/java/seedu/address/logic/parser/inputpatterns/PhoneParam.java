package seedu.address.logic.parser.inputpatterns;

import static seedu.address.logic.parser.CliSyntax.PARAM_ID_PHONE;

import seedu.address.model.person.Phone;



/**
 * A param that takes in the phone, with id "-phone"
 */
public class PhoneParam extends Param {

    public PhoneParam(int minOccurences, int maxOccurences) {
        super(PARAM_ID_PHONE, minOccurences, maxOccurences);
    }

    @Override
    public String getPreview() {
        return PARAM_ID_PHONE + " <phone_number>";
    }

    @Override
    boolean valueMatches(String value) {
        return Phone.isValidPhone(value);
    }
}
