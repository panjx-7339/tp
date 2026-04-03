package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all victims profiles";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.showAllPersons();
        // Scroll to and select first person in list
        if (model.getFilteredPersonList().isEmpty()) {
            model.setSelectedPerson(null);
        } else {
            model.setSelectedPerson(model.getFilteredPersonList().get(0));
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
