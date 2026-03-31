package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Status;

/**
 * Sets the associated status with a given person to {@code Status.TARGET}
 */
public class TargetStatusCommand extends SetStatusCommand {
    public static final String COMMAND_WORD = "target";

    public static final String EXAMPLE = COMMAND_WORD + " 1";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the status to be TARGET for the person at the given index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + EXAMPLE;

    public TargetStatusCommand(Index targetIndex) {
        super(Status.TARGET, targetIndex);
    }

    @Override
    public String toString() {
        return super.toString(this);
    }
}
