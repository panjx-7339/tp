package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;

public class SortCommandTest {

    @Test
    public void equals() {
        SortCommand.SortSpec firstSpec = new SortCommand.SortSpec(
                SortCommand.SortTargetType.NAME,
                null,
                SortCommand.SortOrder.ASC,
                SortCommand.SortMode.NUMBER
        );
        SortCommand.SortSpec secondSpec = new SortCommand.SortSpec(
                SortCommand.SortTargetType.PHONE,
                null,
                SortCommand.SortOrder.DESC,
                SortCommand.SortMode.NUMBER
        );

        SortCommand firstCommand = new SortCommand(firstSpec);
        SortCommand secondCommand = new SortCommand(secondSpec);

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(new SortCommand(firstSpec)));
        assertFalse(firstCommand.equals(1));
        assertFalse(firstCommand.equals(null));
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void execute_unfiltered_sortsViewOnly() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> masterBeforeSort = List.copyOf(model.getAddressBook().getPersonList());

        SortCommand.SortSpec spec = new SortCommand.SortSpec(
                SortCommand.SortTargetType.NAME,
                null,
                SortCommand.SortOrder.DESC,
                SortCommand.SortMode.ALPHA
        );
        SortCommand command = new SortCommand(spec);
        command.execute(model);

        // View should be sorted
        assertEquals("George Best", model.getFilteredPersonList().get(0).getName().fullName);
        // Master list should remain unchanged
        assertEquals(masterBeforeSort, model.getAddressBook().getPersonList());
    }

    @Test
    public void execute_filtered_onlySortsCurrentView() throws CommandException {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        List<Person> masterBeforeSort = List.copyOf(model.getAddressBook().getPersonList());

        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList("Meier")));

        SortCommand.SortSpec spec = new SortCommand.SortSpec(
                SortCommand.SortTargetType.NAME,
                null,
                SortCommand.SortOrder.DESC,
                SortCommand.SortMode.ALPHA
        );
        SortCommand command = new SortCommand(spec);
        command.execute(model);

        assertEquals(Arrays.asList(DANIEL, BENSON), model.getFilteredPersonList());
        assertEquals(masterBeforeSort, model.getAddressBook().getPersonList());
    }

    @Test
    public void constructor_nullSpec_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null));
    }
}
