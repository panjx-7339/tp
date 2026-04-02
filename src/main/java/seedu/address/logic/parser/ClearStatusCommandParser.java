package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.inputpatterns.InputPattern;
import seedu.address.logic.parser.inputpatterns.IntegerToken;
import seedu.address.logic.parser.inputpatterns.Token;

/**
 * Parses input arguments and creates a new ClearStatusCommand object
 */
public class ClearStatusCommandParser extends Parser<ClearStatusCommand> {
    @Override
    InputPattern createInputPattern() {
        ArrayList<Token> tokens = new ArrayList<Token>(List.of(
                new IntegerToken("index" , 1)
        ));

        return new InputPattern("clearstatus", tokens);
    }

    @Override
    ClearStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        InputPattern inputPattern = createInputPattern();
        inputPattern.assignSegmentsFromArgs(args.strip());

        Token indexToken = inputPattern.getTokenWithId("index");
        Index index = ParserUtil.parseIndex(indexToken.getAssignedSegment());
        return new ClearStatusCommand(index);
    }
}
