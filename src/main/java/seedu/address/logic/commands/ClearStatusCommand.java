package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Status;

/**
 * Clears the associated status with a given person to {@code Status.NONE}
 */
public class ClearStatusCommand extends SetStatusCommand {
    public static final String COMMAND_WORD = "clearstatus";

    public static final String EXAMPLE = COMMAND_WORD + " 1";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears the status of the person at the given index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + EXAMPLE;

    public ClearStatusCommand(Index targetIndex) {
        super(Status.NONE, targetIndex);
    }

    @Override
    public String toString() {
        return super.toString(this);
    }
}
