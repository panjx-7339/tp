package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for TagCommand.
 */
public class TagCommandTest {
    private static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("job:banker").build();
    private static final Person ALICE_EDITED = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("job:student").build();
    private static final Person ALICE_NO_JOB = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .build();
    private static final Person ALICE_ADDED = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("job:banker", "salary:10000").build();
    private static final Person ALICE_MULTIPLE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("job:professor", "school:NUS").build();
    private static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("rich:yes", "income:$100,000").build();

    private static Model newModelWithPerson(Person person) {
        Model model = new ModelManager();
        model.addPerson(person);
        return model;
    }

    @Test
    public void execute_addTag_success() {
        Model model = newModelWithPerson(ALICE);
        Model expectedModel = newModelWithPerson(ALICE_ADDED);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON,
                List.of(new Tag("salary:10000")), List.of(), List.of());
        assertCommandSuccess(tagCommand, model, TagCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_editTag_success() {
        Model model = newModelWithPerson(ALICE);
        Model expectedModel = newModelWithPerson(ALICE_EDITED);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON,
                List.of(), List.of(new Tag("job:student")), List.of());
        assertCommandSuccess(tagCommand, model, TagCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_deleteTag_success() {
        Model model = newModelWithPerson(ALICE);
        Model expectedModel = newModelWithPerson(ALICE_NO_JOB);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON,
                List.of(), List.of(), List.of(new Tag("job:dummy")));
        assertCommandSuccess(tagCommand, model, TagCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_multiple_success() {
        Model model = newModelWithPerson(ALICE_ADDED);
        Model expectedModel = newModelWithPerson(ALICE_MULTIPLE);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON,
                List.of(new Tag("school:NUS")),
                List.of(new Tag("job:professor")),
                List.of(new Tag("salary:dummy")));
        assertCommandSuccess(tagCommand, model, TagCommand.MESSAGE_SUCCESS, expectedModel);
    }


    @Test
    public void execute_addTag_setsSelectedPerson() throws Exception {
        Model model = newModelWithPerson(ALICE);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON,
                List.of(new Tag("salary:10000")), List.of(), List.of());

        tagCommand.execute(model);

        assertEquals(ALICE_ADDED, model.getSelectedPerson().getValue());
    }

    @Test
    public void execute_invalidIndex_failure() {
        Model model = newModelWithPerson(ALICE);
        Index outOfBoundIndex = Index.fromOneBased(2);
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, List.of(), List.of(), List.of());
        assertCommandFailure(tagCommand, model, Messages.MESSAGE_OUT_OF_BOUNDS_PERSON_INDEX
                + "\nThere is/are only " + model.getFilteredPersonList().size() + " person(s) in the list.");
    }

    @Test
    public void execute_invalidIndex_doesNotSetSelectedPerson() {
        Model model = newModelWithPerson(ALICE);
        model.setSelectedPerson(ALICE); // set a pre-existing selection
        Index outOfBoundIndex = Index.fromOneBased(2);
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, List.of(), List.of(), List.of());

        assertThrows(CommandException.class, () -> tagCommand.execute(model));
        assertEquals(ALICE, model.getSelectedPerson().getValue()); // unchanged
    }

    @Test
    public void execute_addTagAlreadyExists_failure() {
        Model model = newModelWithPerson(ALICE);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON,
                List.of(new Tag("job:professor")), List.of(), List.of());
        assertCommandFailure(tagCommand, model, TagCommand.ADD_TAG_ALREADY_EXISTS);
    }

    @Test
    public void execute_editTagDoesNotExist_failure() {
        Model model = newModelWithPerson(ALICE);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON,
                List.of(), List.of(new Tag("hello:hi")), List.of());
        assertCommandFailure(tagCommand, model, TagCommand.EDIT_TAG_NAME_DOES_NOT_EXIST);
    }

    @Test
    public void execute_deleteTagDoesNotExist_failure() {
        Model model = newModelWithPerson(ALICE);
        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON,
                List.of(), List.of(), List.of(new Tag("hello:dummy")));
        assertCommandFailure(tagCommand, model, TagCommand.DELETE_TAG_NAME_DOES_NOT_EXIST);
    }

    @Test
    public void execute_addTagAlreadyExists_doesNotSetSelectedPerson() {
        Model model = newModelWithPerson(ALICE);
        model.setSelectedPerson(ALICE);
        model.addPerson(BENSON);
        TagCommand tagCommand = new TagCommand(INDEX_SECOND_PERSON,
                List.of(new Tag("rich:no")), List.of(), List.of());

        assertThrows(CommandException.class, TagCommand.ADD_TAG_ALREADY_EXISTS, () -> tagCommand.execute(model));
        assertEquals(ALICE, model.getSelectedPerson().getValue());
    }

    @Test
    public void execute_editTagDoesNotExist_doesNotSetSelectedPerson() {
        Model model = newModelWithPerson(ALICE);
        model.addPerson(BENSON);
        model.setSelectedPerson(ALICE);
        TagCommand tagCommand = new TagCommand(INDEX_SECOND_PERSON,
                List.of(), List.of(new Tag("job:professor")), List.of()); // job tag doesn't exist on BENSON

        assertThrows(CommandException.class,
                TagCommand.EDIT_TAG_NAME_DOES_NOT_EXIST, () -> tagCommand.execute(model));
        assertEquals(ALICE, model.getSelectedPerson().getValue());
    }

    @Test
    public void execute_deleteTagDoesNotExist_doesNotSetSelectedPerson() {
        Model model = newModelWithPerson(ALICE);
        model.addPerson(BENSON);
        model.setSelectedPerson(ALICE);
        TagCommand tagCommand = new TagCommand(INDEX_SECOND_PERSON,
                List.of(), List.of(), List.of(new Tag("job:dummy"))); // job tag doesn't exist on BENSON

        assertThrows(CommandException.class,
                TagCommand.DELETE_TAG_NAME_DOES_NOT_EXIST, () -> tagCommand.execute(model));
        assertEquals(ALICE, model.getSelectedPerson().getValue());
    }

    @Test
    public void equals() {
        List<Tag> addTags = List.of(new Tag("tag1:value1"));
        List<Tag> editTags = List.of(new Tag("tag2:value2"));
        List<Tag> deleteTags = List.of(new Tag("tag3:dummy"));
        TagCommand tagCommand1 = new TagCommand(INDEX_FIRST_PERSON, addTags, editTags, deleteTags);
        TagCommand tagCommand2 = new TagCommand(INDEX_FIRST_PERSON, addTags, editTags, deleteTags);

        // same object -> returns true
        assertTrue(tagCommand1.equals(tagCommand1));

        // same values -> returns true
        assertTrue(tagCommand1.equals(tagCommand2));

        // different types -> returns false
        assertFalse(tagCommand1.equals(1));

        // null -> returns false
        assertFalse(tagCommand1.equals(null));

        // different index -> returns false
        assertFalse(tagCommand1.equals(new TagCommand(INDEX_SECOND_PERSON, addTags, editTags, deleteTags)));

        // different addTags -> returns false
        assertFalse(tagCommand1.equals(new TagCommand(INDEX_FIRST_PERSON, List.of(), editTags, deleteTags)));

        // different editTags -> returns false
        assertFalse(tagCommand1.equals(new TagCommand(INDEX_FIRST_PERSON, addTags, List.of(), deleteTags)));

        // different deleteTags -> returns false
        assertFalse(tagCommand1.equals(new TagCommand(INDEX_FIRST_PERSON, addTags, editTags, List.of())));
    }

    @Test
    public void test_toString() {
        List<Tag> addTags = List.of(new Tag("job:banker"));
        List<Tag> editTags = List.of(new Tag("school:NTU"));
        List<Tag> deleteTags = List.of(new Tag("salary:dummy"));

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, addTags, editTags, deleteTags);
        assertEquals(TagCommand.class.getCanonicalName()
                        + "{index=" + INDEX_FIRST_PERSON
                        + ", add tags=[job: banker], edit tags=[school: NTU], delete tags=[salary: dummy]}",
                        tagCommand.toString());
    }
}
