package seedu.address.logic.parser.inputpatterns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_PHONE;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG;

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
                new EmailParam(0, 1),
                new TagParam(0, 100)
        ));

        InputPattern addPattern = new InputPattern("add", tokens, params);

        // assert is empty at first
        assertEquals(0, addPattern.getParamWithId(PARAM_ID_TAG).getValues().size());
        assertEquals(0, addPattern.getParamWithId(PARAM_ID_EMAIL).getValues().size());
        assertEquals(0, addPattern.getParamWithId(PARAM_ID_PHONE).getValues().size());


        String dummyName = "john Blue";
        String phoneNumber = "81234567";
        String email = "donk@gmail.com";
        String tag1 = "income : $123002";
        String tag2 = "martialstatus:married";

        String args = dummyName
                + " " + PARAM_ID_EMAIL + " " + email
                + " " + PARAM_ID_TAG + " " + tag1
                + " " + PARAM_ID_PHONE + " " + phoneNumber
                + " " + PARAM_ID_TAG + " " + tag2;


        try {
            addPattern.assignSegmentsFromArgs(args);
            assertEquals(dummyName, addPattern.getTokenWithId("name").getAssignedSegment());

            ArrayList<String> emailValues = addPattern.getParamWithId(PARAM_ID_EMAIL).getValues();
            assertEquals(1, emailValues.size());
            assertEquals(email, emailValues.get(0));

            ArrayList<String> phoneValues = addPattern.getParamWithId(PARAM_ID_PHONE).getValues();
            assertEquals(1, phoneValues.size());
            assertEquals(phoneNumber, phoneValues.get(0));

            ArrayList<String> tags = addPattern.getParamWithId(PARAM_ID_TAG).getValues();
            assertEquals(2, tags.size());
            assertEquals(tag1, tags.get(0));
            assertEquals(tag2, tags.get(1));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }
}
