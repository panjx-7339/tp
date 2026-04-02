package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TargetStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.inputpatterns.InputPattern;
import seedu.address.logic.parser.inputpatterns.IntegerToken;
import seedu.address.logic.parser.inputpatterns.Token;

/**
 * Parses input arguments and creates a new TargetStatusCommand object
 */
public class TargetStatusCommandParser extends Parser<TargetStatusCommand> {
    @Override
    InputPattern createInputPattern() {
        ArrayList<Token> tokens = new ArrayList<Token>(List.of(
                new IntegerToken("index" , 1)
        ));

        return new InputPattern("target", tokens);
    }

    @Override
    TargetStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        InputPattern inputPattern = createInputPattern();
        inputPattern.assignSegmentsFromArgs(args.strip());

        Token indexToken = inputPattern.getTokenWithId("index");
        Index index = ParserUtil.parseIndex(indexToken.getAssignedSegment());
        return new TargetStatusCommand(index);
    }
}
