package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearStatusCommand;
import seedu.address.logic.commands.IgnoreStatusCommand;
import seedu.address.logic.commands.ScamStatusCommand;
import seedu.address.logic.commands.TargetStatusCommand;
import seedu.address.logic.parser.inputpatterns.InputPattern;

/**
 * Contains integration tests (interaction with the Model) and unit tests for various StatusCommandParser.
 */
public class StatusCommandParsersTest {
    @Test
    public void invalid_arguments() {
        ClearStatusCommandParser csParser = new ClearStatusCommandParser();
        TargetStatusCommandParser tParser = new TargetStatusCommandParser();
        ScamStatusCommandParser sParser = new ScamStatusCommandParser();
        IgnoreStatusCommandParser iParser = new IgnoreStatusCommandParser();

        assertParseFailure(csParser, "", InputPattern.MESSAGE_TOO_FEW_FIELDS);
        assertParseFailure(csParser, "1 1 1", InputPattern.MESSAGE_TOO_MANY_FIELDS);
        assertParseFailure(tParser, "", InputPattern.MESSAGE_TOO_FEW_FIELDS);
        assertParseFailure(tParser, "1 1 1", InputPattern.MESSAGE_TOO_MANY_FIELDS);
        assertParseFailure(sParser, "", InputPattern.MESSAGE_TOO_FEW_FIELDS);
        assertParseFailure(sParser, "1 1 1", InputPattern.MESSAGE_TOO_MANY_FIELDS);
        assertParseFailure(iParser, "", InputPattern.MESSAGE_TOO_FEW_FIELDS);
        assertParseFailure(iParser, "1 1 1", InputPattern.MESSAGE_TOO_MANY_FIELDS);
    }

    @Test
    public void success() {
        ClearStatusCommandParser csParser = new ClearStatusCommandParser();
        TargetStatusCommandParser tParser = new TargetStatusCommandParser();
        ScamStatusCommandParser sParser = new ScamStatusCommandParser();
        IgnoreStatusCommandParser iParser = new IgnoreStatusCommandParser();

        assertParseSuccess(csParser, "1", new ClearStatusCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(tParser, "1", new TargetStatusCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(sParser, "1", new ScamStatusCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(iParser, "1", new IgnoreStatusCommand(INDEX_FIRST_PERSON));
    }
}
