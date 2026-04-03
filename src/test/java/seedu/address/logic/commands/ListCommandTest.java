package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_unfilteredList_selectsCorrectPerson() throws CommandException {
        Command command = new ListCommand();
        command.execute(model);
        assertEquals(ALICE, model.getSelectedPerson().getValue());
    }

    @Test
    public void execute_filteredList_selectsCorrectPerson() throws CommandException {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Command command = new ListCommand();
        command.execute(model);
        assertEquals(ALICE, model.getSelectedPerson().getValue());
    }

    @Test
    public void execute_emptyList_selectedPersonIsNull() throws CommandException {
        Model model_with_empty_addressbook = new ModelManager(new AddressBook(), new UserPrefs());
        Command command = new ListCommand();
        command.execute(model_with_empty_addressbook);
        assertNull(model_with_empty_addressbook.getSelectedPerson().getValue());
    }
}
