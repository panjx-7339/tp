package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_NAME;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {
    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PARAM_ID_NAME + " " + VALID_NAME_AMY, " does not match [1...2147483647]");

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", " does not match [1...2147483647]");
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + PARAM_ID_NAME + " " + VALID_NAME_AMY,
                "-5 does not match [1...2147483647]");

        // zero index
        assertParseFailure(parser, "0 " + PARAM_ID_NAME + " " + VALID_NAME_AMY,
                "0 does not match [1...2147483647]");

        // too many token fields before params
        assertParseFailure(parser, "1 some random string", "Too many fields inputted (TODO MORE DESCRIPTIVE)");
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1 " + PARAM_ID_NAME + " James&",
                "James& is not a valid value for the param --name");
        assertParseFailure(parser, "1 " + PARAM_ID_PHONE + " 911a",
                "911a is not a valid value for the param --phone");
        assertParseFailure(parser, "1 " + PARAM_ID_EMAIL + " bob!yahoo",
                "bob!yahoo is not a valid value for the param --email");

        // invalid name followed by a valid email still fails
        assertParseFailure(parser, "1 " + PARAM_ID_NAME + " James& " + PARAM_ID_EMAIL + " " + VALID_EMAIL_AMY,
                "James& is not a valid value for the param --name");
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PARAM_ID_PHONE + " " + VALID_PHONE_BOB
                + " " + PARAM_ID_EMAIL + " " + VALID_EMAIL_AMY
                + " " + PARAM_ID_NAME + " " + VALID_NAME_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PARAM_ID_PHONE + " " + VALID_PHONE_BOB
            + " " + PARAM_ID_EMAIL + " " + VALID_EMAIL_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PARAM_ID_NAME + " " + VALID_NAME_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + " " + PARAM_ID_PHONE + " " + VALID_PHONE_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + " " + PARAM_ID_EMAIL + " " + VALID_EMAIL_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // unknown field - not supported
        userInput = targetIndex.getOneBased() + " --tag friend";
        assertParseFailure(parser, userInput, "Too many fields inputted (TODO MORE DESCRIPTIVE)");
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PARAM_ID_PHONE + " " + VALID_PHONE_BOB
                + " " + PARAM_ID_PHONE + " " + VALID_PHONE_AMY;

        assertParseFailure(parser, userInput, "2 parameters of --phone inputted expected at most 1 only");
    }

    @Test
    public void parse_oldPrefixField_failure() {
        assertParseFailure(parser, "1" + PHONE_DESC_AMY, "Too many fields inputted (TODO MORE DESCRIPTIVE)");
        assertParseFailure(parser, "1" + EMAIL_DESC_AMY, "Too many fields inputted (TODO MORE DESCRIPTIVE)");
        assertParseFailure(parser, "1" + NAME_DESC_AMY, "Too many fields inputted (TODO MORE DESCRIPTIVE)");
    }
}
