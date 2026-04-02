package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for various StatusCommand.
 */
public class StatusCommandsTest {
    private static final Person ALICE_NONE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("job:banker")
            .withStatus(Status.NONE).build();

    private static final Person ALICE_TARGET = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("job:banker")
            .withStatus(Status.TARGET).build();

    private static final Person ALICE_SCAM = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("job:banker")
            .withStatus(Status.SCAM).build();

    private static final Person ALICE_IGNORE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("job:banker")
            .withStatus(Status.IGNORE).build();

    private static Model newModelWithPerson(Person person) {
        Model model = new ModelManager();
        model.addPerson(person);
        return model;
    }

    @Test
    public void execute_clearStatus_success() {
        Model model = newModelWithPerson(ALICE_TARGET);
        Model expectedModel = newModelWithPerson(ALICE_NONE);
        ClearStatusCommand clearStatusCommand = new ClearStatusCommand(INDEX_FIRST_PERSON);
        assertCommandSuccess(clearStatusCommand, model, ClearStatusCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_targetStatus_success() {
        Model model = newModelWithPerson(ALICE_SCAM);
        Model expectedModel = newModelWithPerson(ALICE_TARGET);
        TargetStatusCommand targetStatusCommand = new TargetStatusCommand(INDEX_FIRST_PERSON);
        assertCommandSuccess(targetStatusCommand, model, TargetStatusCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_scamStatus_success() {
        Model model = newModelWithPerson(ALICE_IGNORE);
        Model expectedModel = newModelWithPerson(ALICE_SCAM);
        ScamStatusCommand scamStatusCommand = new ScamStatusCommand(INDEX_FIRST_PERSON);
        assertCommandSuccess(scamStatusCommand, model, ScamStatusCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_ignoreStatus_success() {
        Model model = newModelWithPerson(ALICE_TARGET);
        Model expectedModel = newModelWithPerson(ALICE_IGNORE);
        IgnoreStatusCommand ignoreStatusCommand = new IgnoreStatusCommand(INDEX_FIRST_PERSON);
        assertCommandSuccess(ignoreStatusCommand, model, IgnoreStatusCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sameStatus_success() {
        Model model = newModelWithPerson(ALICE_TARGET);
        TargetStatusCommand targetStatusCommand = new TargetStatusCommand(INDEX_FIRST_PERSON);
        assertCommandSuccess(targetStatusCommand, model, TargetStatusCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_outOfBoundsIndex_failure() {
        Model model = newModelWithPerson(ALICE_TARGET);
        ClearStatusCommand clearStatusCommand = new ClearStatusCommand(INDEX_SECOND_PERSON);
        assertCommandFailure(clearStatusCommand, model, Messages.MESSAGE_OUT_OF_BOUNDS_PERSON_INDEX
                + "\nThere is/are only " + model.getFilteredPersonList().size() + " person(s) in the list.");
    }

    @Test
    public void execute_validIndex_setsSelectedPerson() throws Exception {
        Model model = newModelWithPerson(ALICE_TARGET);
        ClearStatusCommand clearStatusCommand = new ClearStatusCommand(INDEX_FIRST_PERSON);

        clearStatusCommand.execute(model);

        assertEquals(ALICE_NONE, model.getSelectedPerson().getValue());
    }

    @Test
    public void execute_outOfBoundsIndex_doesNotSetSelectedPerson() {
        Model model = newModelWithPerson(ALICE_TARGET);
        model.setSelectedPerson(ALICE_TARGET);
        ClearStatusCommand clearStatusCommand = new ClearStatusCommand(INDEX_SECOND_PERSON);

        String expectedMessage = Messages.MESSAGE_OUT_OF_BOUNDS_PERSON_INDEX
                + "\nThere is/are only " + model.getFilteredPersonList().size() + " person(s) in the list.";
        assertThrows(CommandException.class, expectedMessage, () -> clearStatusCommand.execute(model));
        assertEquals(ALICE_TARGET, model.getSelectedPerson().getValue()); // unchanged
    }

    @Test
    public void test_equals() {
        ClearStatusCommand command1 = new ClearStatusCommand(INDEX_FIRST_PERSON);
        SetStatusCommand command2 = new SetStatusCommand(Status.NONE, INDEX_FIRST_PERSON);
        assertEquals(command1, command2);

        assertEquals(command1, command1);
        assertNotEquals(command1, null);
        assertNotEquals(command1, new IgnoreStatusCommand(INDEX_FIRST_PERSON));
        assertNotEquals(command1, new ClearStatusCommand(INDEX_SECOND_PERSON));
        assertNotEquals(command1, new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void test_toString() {
        SetStatusCommand command = new SetStatusCommand(Status.IGNORE, INDEX_FIRST_PERSON);
        assertEquals(SetStatusCommand.class.getCanonicalName()
                        + "{targetStatus=" + Status.IGNORE
                        + ", targetIndex=" + INDEX_FIRST_PERSON + "}",
                command.toString());

        ClearStatusCommand csCommand = new ClearStatusCommand(INDEX_FIRST_PERSON);
        assertEquals(ClearStatusCommand.class.getCanonicalName()
                        + "{targetStatus=" + Status.NONE
                        + ", targetIndex=" + INDEX_FIRST_PERSON + "}",
                        csCommand.toString());

        TargetStatusCommand tCommand = new TargetStatusCommand(INDEX_FIRST_PERSON);
        assertEquals(TargetStatusCommand.class.getCanonicalName()
                        + "{targetStatus=" + Status.TARGET
                        + ", targetIndex=" + INDEX_FIRST_PERSON + "}",
                        tCommand.toString());

        ScamStatusCommand sCommand = new ScamStatusCommand(INDEX_FIRST_PERSON);
        assertEquals(ScamStatusCommand.class.getCanonicalName()
                        + "{targetStatus=" + Status.SCAM
                        + ", targetIndex=" + INDEX_FIRST_PERSON + "}",
                        sCommand.toString());

        IgnoreStatusCommand iCommand = new IgnoreStatusCommand(INDEX_FIRST_PERSON);
        assertEquals(IgnoreStatusCommand.class.getCanonicalName()
                        + "{targetStatus=" + Status.IGNORE
                        + ", targetIndex=" + INDEX_FIRST_PERSON + "}",
                        iCommand.toString());
    }
}
