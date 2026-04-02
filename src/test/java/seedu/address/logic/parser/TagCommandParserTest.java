package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.inputpatterns.InputPattern.MESSAGE_TOO_FEW_FIELDS;
import static seedu.address.logic.parser.inputpatterns.InputPattern.MESSAGE_TOO_MANY_FIELDS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.inputpatterns.IntegerToken;
import seedu.address.model.tag.Tag;

public class TagCommandParserTest {
    private final TagCommandParser parser = new TagCommandParser();
    private final String tooLong = "a".repeat(Tag.MAX_LENGTH + 10);

    @Test
    public void parse_emptyParameters_failure() {
        assertParseFailure(parser, "", MESSAGE_TOO_FEW_FIELDS);
        assertParseFailure(parser, "  \t\t\n\n", MESSAGE_TOO_FEW_FIELDS);
    }

    @Test
    public void parse_tooManyParameters_failure() {
        assertParseFailure(parser, "1 2", MESSAGE_TOO_MANY_FIELDS);
        assertParseFailure(parser, "1 2 3", MESSAGE_TOO_MANY_FIELDS);
        assertParseFailure(parser, "--new name:value", MESSAGE_TOO_MANY_FIELDS);
    }

    @Test
    public void parse_nonIntegerIndex_failure() {
        assertParseFailure(parser, "first",
                "first" + IntegerToken.INVALID_STRING);
        assertParseFailure(parser, "one",
                "one" + IntegerToken.INVALID_STRING);
    }

    @Test
    public void parse_tooLong_failure() {
        String m = " is too long, it should not exceed " + Tag.MAX_LENGTH + " characters.";
        assertParseFailure(parser, "1 --add " + tooLong + ":a", tooLong + m);
        assertParseFailure(parser, "1 --add a:" + tooLong, tooLong + m);
        assertParseFailure(parser, "1 --edit " + tooLong + ":a", tooLong + m);
        assertParseFailure(parser, "1 --edit a:" + tooLong, tooLong + m);
        assertParseFailure(parser, "1 --delete " + tooLong, tooLong + m);
    }

    @Test
    public void parse_noParameters_success() {
        List<Tag> empty = List.of();
        assertParseSuccess(parser, "1", new TagCommand(INDEX_FIRST_PERSON, empty, empty, empty));
    }

    @Test
    public void parse_addSingleTag_success() {
        List<Tag> tagsToAdd = List.of(new Tag("friend:John"));
        List<Tag> empty = List.of();
        assertParseSuccess(parser, "1 --add friend:John",
                new TagCommand(INDEX_FIRST_PERSON, tagsToAdd, empty, empty));
    }

    @Test
    public void parse_addMultipleTags_success() {
        List<Tag> tagsToAdd = List.of(new Tag("friend:John"), new Tag("colleague:Jane"));
        List<Tag> empty = List.of();
        assertParseSuccess(parser, "1 --add friend:John --add colleague:Jane",
                new TagCommand(INDEX_FIRST_PERSON, tagsToAdd, empty, empty));
    }

    @Test
    public void parse_editSingleTag_success() {
        List<Tag> tagsToSet = List.of(new Tag("friend:John"));
        List<Tag> empty = List.of();
        assertParseSuccess(parser, "1 --edit friend:John",
                new TagCommand(INDEX_FIRST_PERSON, empty, tagsToSet, empty));
    }

    @Test
    public void parse_editMultipleTags_success() {
        List<Tag> tagsToSet = List.of(new Tag("friend:John"), new Tag("colleague:Jane"));
        List<Tag> empty = List.of();
        assertParseSuccess(parser, "1 --edit friend:John --edit colleague:Jane",
                new TagCommand(INDEX_FIRST_PERSON, empty, tagsToSet, empty));
    }

    @Test
    public void parse_deleteSingleTag_success() {
        List<Tag> tagsToDelete = List.of(new Tag("friend:dummy"));
        List<Tag> empty = List.of();
        assertParseSuccess(parser, "1 --delete friend",
                new TagCommand(INDEX_FIRST_PERSON, empty, empty, tagsToDelete));
    }

    @Test
    public void parse_deleteMultipleTags_success() {
        List<Tag> tagsToDelete = List.of(new Tag("friend:dummy"), new Tag("colleague:dummy"));
        List<Tag> empty = List.of();
        assertParseSuccess(parser, "1 --delete friend --delete colleague",
                new TagCommand(INDEX_FIRST_PERSON, empty, empty, tagsToDelete));
    }

    @Test
    public void parse_mixedOperations_success() {
        List<Tag> tagsToAdd = List.of(new Tag("friend:John"));
        List<Tag> tagsToEdit = List.of(new Tag("colleague:Jane"));
        List<Tag> tagsToDelete = List.of(new Tag("office:dummy"));
        TagCommand command = new TagCommand(INDEX_FIRST_PERSON, tagsToAdd, tagsToEdit, tagsToDelete);

        assertParseSuccess(parser, "1 --add friend:John --edit colleague:Jane --delete office", command);
        assertParseSuccess(parser, "1 --add friend:John --delete office --edit colleague:Jane", command);
        assertParseSuccess(parser, "1 --edit colleague:Jane --add friend:John --delete office", command);
        assertParseSuccess(parser, "1 --edit colleague:Jane --delete office --add friend:John", command);
        assertParseSuccess(parser, "1 --delete office --add friend:John --edit colleague:Jane", command);
        assertParseSuccess(parser, "1 --delete office --edit colleague:Jane --add friend:John", command);
    }

    @Test
    public void parse_invalidTagFormat_failure() {
        assertParseFailure(parser, "1 --add no_delimiter", Tag.ONE_DELIMITER_CONSTRAINT);
        assertParseFailure(parser, "1 --add invalid:tag:string", Tag.ONE_DELIMITER_CONSTRAINT);
        assertParseFailure(parser, "1 --delete extra:delimiter", Tag.DELETE_TAG_NAME_ONLY);
    }
}
