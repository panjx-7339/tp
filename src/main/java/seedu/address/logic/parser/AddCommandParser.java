package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PARAM_ID_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_PHONE;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.inputpatterns.EmailParam;
import seedu.address.logic.parser.inputpatterns.InputPattern;
import seedu.address.logic.parser.inputpatterns.Param;
import seedu.address.logic.parser.inputpatterns.PhoneParam;
import seedu.address.logic.parser.inputpatterns.StringToken;
import seedu.address.logic.parser.inputpatterns.TagParam;
import seedu.address.logic.parser.inputpatterns.Token;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TagList;
import seedu.address.model.tag.Tag;



/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser extends Parser<AddCommand> {

    @Override
    InputPattern createInputPattern() {
        ArrayList<Token> tokens = new ArrayList<Token>(List.of(
                new StringToken("name", "<name>")
        ));

        ArrayList<Param> params = new ArrayList<>(List.of(
                new PhoneParam(0, 1),
                new EmailParam(0, 1),
                new TagParam(0, 100)
        ));

        return new InputPattern("add", tokens, params);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        InputPattern inputPattern = createInputPattern();
        inputPattern.assignSegmentsFromArgs(args.strip());

        Token nameToken = inputPattern.getTokenWithId("name");
        String nameString = nameToken.getAssignedSegment();
        try {
            Name.validateName(nameString);
        } catch (IllegalValueException e) {
            throw new ParseException(e.getMessage());
        }
        Name name = new Name(nameString);

        Phone phone = null;
        Param phoneParam = inputPattern.getParamWithId(PARAM_ID_PHONE);
        ArrayList<String> phoneValues = phoneParam.getValues();
        if (!phoneValues.isEmpty()) {
            phone = new Phone(phoneValues.get(0));
        }

        Email email = null;
        Param emailParam = inputPattern.getParamWithId(PARAM_ID_EMAIL);
        ArrayList<String> emailValues = emailParam.getValues();
        if (!emailValues.isEmpty()) {
            email = new Email(emailValues.get(0));
        }

        try {
            Param tagAddParam = inputPattern.getParamWithId(PARAM_ID_TAG);
            ArrayList<String> tagStrings = tagAddParam.getValues();
            List<Tag> tags = tagStrings.stream().map(Tag::new).toList();

            TagList tagList = new TagList(tags);

            Person person = new Person(name, phone, email, tagList);

            return new AddCommand(person);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
