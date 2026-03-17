package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortCommand.SortMode;
import seedu.address.logic.commands.SortCommand.SortOrder;
import seedu.address.logic.commands.SortCommand.SortSpec;
import seedu.address.logic.commands.SortCommand.SortTargetType;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArgs_usesDefaults() {
        SortSpec expected = new SortSpec(SortTargetType.NAME, null, SortOrder.ASC, SortMode.NUMBER);
        assertParseSuccess(parser, "   ", new SortCommand(expected));
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        SortSpec phoneDesc = new SortSpec(SortTargetType.PHONE, null, SortOrder.DESC, SortMode.NUMBER);
        assertParseSuccess(parser, "phone --desc --NUMBER", new SortCommand(phoneDesc));

        SortSpec tagAlpha = new SortSpec(SortTargetType.TAG, "income", SortOrder.ASC, SortMode.ALPHA);
        assertParseSuccess(parser, "income --alpha", new SortCommand(tagAlpha));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "NAME --ASC --desc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "NAME --NUMBER --alpha",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "NAME --weird",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
