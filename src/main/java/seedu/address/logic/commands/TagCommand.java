package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_ADD;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_DELETE;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_EDIT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Manages the tags of a specific profile.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String EXAMPLE = COMMAND_WORD + " 1 " + PARAM_ID_TAG_ADD + " school:NUS "
            + PARAM_ID_TAG_EDIT + " salary:10000 " + PARAM_ID_TAG_DELETE + " age";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Manages tags of a profile."
            + "Parameters: INDEX (starting from 1) "
            + "[" + PARAM_ID_TAG_ADD + " <tag-name>:<tag-value>] "
            + "[" + PARAM_ID_TAG_EDIT + " <existing tag-name>:<new tag-value>] "
            + "[" + PARAM_ID_TAG_DELETE + " <existing tag-name>]\n"
            + "Example: " + EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Tags successfully updated";
    public static final String ADD_TAG_ALREADY_EXISTS = "Tag name of tag to be added already exists.";
    public static final String EDIT_TAG_NAME_DOES_NOT_EXIST = "Tag name of tag to be edited does not exist.";
    public static final String DELETE_TAG_NAME_DOES_NOT_EXIST = "Tag name of tag to be deleted does not exist.";

    private final Index index;
    private final List<Tag> addTags;
    private final List<Tag> editTags;
    private final List<Tag> deleteTags;

    /**
     * Creates a new TagCommand object.
     *
     * @param index Index object of the index of the profile whose tags should be edited.
     * @param addTags List of {@code Tag}s to add.
     * @param editTags List of {@code Tag}s to edit.
     * @param deleteTags List of {@code Tag}s to delete.
     */
    public TagCommand(Index index, List<Tag> addTags, List<Tag> editTags, List<Tag> deleteTags) {
        requireNonNull(index);
        requireNonNull(addTags);
        requireNonNull(editTags);
        requireNonNull(deleteTags);

        this.index = index;
        this.addTags = addTags;
        this.editTags = editTags;
        this.deleteTags = deleteTags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_OUT_OF_BOUNDS_PERSON_INDEX
                    + "\nThere is/are only " + lastShownList.size() + " person(s) in the list.");
        }

        Person person = lastShownList.get(index.getZeroBased());
        Person updatedPerson = new Person(person.getName(),
                person.hasPhone() ? person.getPhone() : null,
                person.hasEmail() ? person.getEmail() : null,
                person.getTags(),
                person.getStatus());

        for (Tag tag : addTags) {
            if (updatedPerson.getTags().containsTagName(tag.tagName)) {
                throw new CommandException(ADD_TAG_ALREADY_EXISTS);
            }
            updatedPerson.getTags().addTag(tag);
        }

        for (Tag tag : editTags) {
            if (!updatedPerson.getTags().containsTagName(tag.tagName)) {
                throw new CommandException(EDIT_TAG_NAME_DOES_NOT_EXIST);
            }
            updatedPerson.getTags().editTag(tag);
        }

        for (Tag tag : deleteTags) {
            if (!updatedPerson.getTags().containsTagName(tag.tagName)) {
                throw new CommandException(DELETE_TAG_NAME_DOES_NOT_EXIST);
            }
            updatedPerson.getTags().deleteTag(tag);
        }

        model.setPerson(person, updatedPerson);
        model.setSelectedPerson(updatedPerson);

        if (!model.getMostRecentPredicate().test(updatedPerson)) {
            model.showAllPersons();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand)) {
            return false;
        }

        TagCommand otherTagCommand = (TagCommand) other;
        return index.equals(otherTagCommand.index) && addTags.equals(otherTagCommand.addTags)
                && editTags.equals(otherTagCommand.editTags) && deleteTags.equals(otherTagCommand.deleteTags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("add tags", addTags.toString())
                .add("edit tags", editTags.toString())
                .add("delete tags", deleteTags.toString())
                .toString();
    }
}
