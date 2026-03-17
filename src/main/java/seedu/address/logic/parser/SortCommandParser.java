package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Locale;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortCommand.SortMode;
import seedu.address.logic.commands.SortCommand.SortOrder;
import seedu.address.logic.commands.SortCommand.SortSpec;
import seedu.address.logic.commands.SortCommand.SortTargetType;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.inputpatterns.InputPattern;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser extends Parser<SortCommand> {

    @Override
    InputPattern createInputPattern() {
        return null;
    }

    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args == null ? "" : args.trim();

        SortTargetType targetType = SortTargetType.NAME;
        String tagName = null;
        SortOrder order = SortOrder.ASC;
        SortMode mode = SortMode.NUMBER;

        if (!trimmedArgs.isEmpty()) {
            String[] tokens = trimmedArgs.split("\\s+");
            int index = 0;

            if (!tokens[index].startsWith("--")) {
                String selector = tokens[index];
                String normalizedSelector = selector.toLowerCase(Locale.ROOT);

                if ("name".equals(normalizedSelector)) {
                    targetType = SortTargetType.NAME;
                } else if ("phone".equals(normalizedSelector)) {
                    targetType = SortTargetType.PHONE;
                } else if ("email".equals(normalizedSelector)) {
                    targetType = SortTargetType.EMAIL;
                } else {
                    targetType = SortTargetType.TAG;
                    tagName = selector;
                }
                index++;
            }

            boolean hasOrderFlag = false;
            boolean hasModeFlag = false;

            for (; index < tokens.length; index++) {
                String token = tokens[index].toLowerCase(Locale.ROOT);
                switch (token) {
                case "--asc":
                case "--desc":
                    if (hasOrderFlag) {
                        throw usageError();
                    }
                    hasOrderFlag = true;
                    order = "--desc".equals(token) ? SortOrder.DESC : SortOrder.ASC;
                    break;

                case "--number":
                case "--alpha":
                    if (hasModeFlag) {
                        throw usageError();
                    }
                    hasModeFlag = true;
                    mode = "--alpha".equals(token) ? SortMode.ALPHA : SortMode.NUMBER;
                    break;

                default:
                    throw usageError();
                }
            }
        }

        return new SortCommand(new SortSpec(targetType, tagName, order, mode));
    }

    private ParseException usageError() {
        return new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
