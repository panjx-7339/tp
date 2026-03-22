package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;

/**
 * Sets the associated status of a given person
 */
public class SetStatusCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Status set successfully.";

    private final Index targetIndex;
    private final Status targetStatus;

    /**
     * Initializes a new {@code SetStatusCommand} with target status and index.
     *
     * @param targetStatus Target status to set to.
     * @param targetIndex Target index of person.
     */
    public SetStatusCommand(Status targetStatus, Index targetIndex) {
        this.targetStatus = targetStatus;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_OUT_OF_BOUNDS_PERSON_INDEX);
        }

        Person person = lastShownList.get(targetIndex.getZeroBased());
        Person updatedPerson = new Person(person.getName(),
                person.hasPhone() ? person.getPhone() : null,
                person.hasEmail() ? person.getEmail() : null,
                person.getTags(),
                targetStatus);
        model.setPerson(person, updatedPerson);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetStatusCommand)) {
            return false;
        }

        SetStatusCommand otherCommand = (SetStatusCommand) other;
        return targetStatus.equals(otherCommand.targetStatus) && targetIndex.equals(otherCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetStatus", targetStatus)
                .add("targetIndex", targetIndex)
                .toString();
    }

    /**
     * Returns string representation of specific {@code SetStatusCommand}.
     * Used for subclasses.
     */
    public String toString(SetStatusCommand command) {
        return new ToStringBuilder(command)
                .add("targetStatus", targetStatus)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
