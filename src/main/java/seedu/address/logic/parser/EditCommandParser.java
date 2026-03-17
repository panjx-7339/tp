package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_NAME;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_PHONE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.inputpatterns.EmailParam;
import seedu.address.logic.parser.inputpatterns.InputPattern;
import seedu.address.logic.parser.inputpatterns.IntegerToken;
import seedu.address.logic.parser.inputpatterns.NameParam;
import seedu.address.logic.parser.inputpatterns.Param;
import seedu.address.logic.parser.inputpatterns.PhoneParam;
import seedu.address.logic.parser.inputpatterns.Token;



/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser extends Parser<EditCommand> {

    @Override
    InputPattern createInputPattern() {
        ArrayList<Token> tokens = new ArrayList<Token>(List.of(
            new IntegerToken("index", 1, Integer.MAX_VALUE)
        ));

        ArrayList<Param> params = new ArrayList<>(List.of(
            new NameParam(0, 1),
            new PhoneParam(0, 1),
            new EmailParam(0, 1)
        ));

        return new InputPattern("edit", tokens, params);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        InputPattern inputPattern = createInputPattern();
        inputPattern.assignSegmentsFromArgs(args.strip());

        Token indexToken = inputPattern.getTokenWithId("index");
        Index index = ParserUtil.parseIndex(indexToken.getAssignedSegment());

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        Param nameParam = inputPattern.getParamWithId(PARAM_ID_NAME);
        ArrayList<String> nameValues = nameParam.getValues();
        if (!nameValues.isEmpty()) {
            editPersonDescriptor.setName(ParserUtil.parseName(nameValues.get(0)));
        }

        Param phoneParam = inputPattern.getParamWithId(PARAM_ID_PHONE);
        ArrayList<String> phoneValues = phoneParam.getValues();
        if (!phoneValues.isEmpty()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(phoneValues.get(0)));
        }

        Param emailParam = inputPattern.getParamWithId(PARAM_ID_EMAIL);
        ArrayList<String> emailValues = emailParam.getValues();
        if (!emailValues.isEmpty()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(emailValues.get(0)));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPersonDescriptor);
    }
}
