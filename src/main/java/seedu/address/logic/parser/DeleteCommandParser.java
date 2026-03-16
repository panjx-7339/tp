package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.inputpatterns.InputPattern;
import seedu.address.logic.parser.inputpatterns.IntegerToken;
import seedu.address.logic.parser.inputpatterns.Token;



/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser extends Parser<DeleteCommand> {

    @Override
    InputPattern createInputPattern() {
        ArrayList<Token> tokens = new ArrayList<Token>(List.of(
                new IntegerToken("taskno" , 1, 100)
        ));

        return new InputPattern("delete", tokens);
    }
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
