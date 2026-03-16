package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_PHONE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class InputPatternTest {
    @Test
    public void inputPattern_success() {
        ArrayList<Token> tokens = new ArrayList<Token>(List.of(
                new StringToken("name", "<name>")
        ));

        ArrayList<Param> params = new ArrayList<>(List.of(
                new PhoneParam(0, 1),
                new EmailParam(0, 1)
        ));

        InputPattern addPattern = new InputPattern("add", tokens, params);

        String dummyName = "john Blue";
        String phoneNumber = "81234567";
        String email = "donk@gmail.com";

        String args = dummyName
                + " " + PARAM_ID_EMAIL + " " + email
                + " " + PARAM_ID_PHONE + " " + phoneNumber;

        try {
            addPattern.assignSegmentsFromArgs(args);
            assertEquals(dummyName, addPattern.getTokenWithId("name").getAssignedSegment());

            ArrayList<String> emailValues = addPattern.getParamWithId(PARAM_ID_EMAIL).getValues();
            assertEquals(1, emailValues.size());
            assertEquals(email, emailValues.get(0));

            ArrayList<String> phoneValues = addPattern.getParamWithId(PARAM_ID_PHONE).getValues();
            assertEquals(1, phoneValues.size());
            assertEquals(phoneNumber, phoneValues.get(0));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }
}
