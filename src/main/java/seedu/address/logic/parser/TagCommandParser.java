package seedu.address.logic.parser;



import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_ADD;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_DELETE;
import static seedu.address.logic.parser.CliSyntax.PARAM_ID_TAG_EDIT;
import static seedu.address.model.tag.Tag.DELIMITER_MESSAGE_CONSTRAINTS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.inputpatterns.InputPattern;
import seedu.address.logic.parser.inputpatterns.IntegerToken;
import seedu.address.logic.parser.inputpatterns.Param;
import seedu.address.logic.parser.inputpatterns.TagAddParam;
import seedu.address.logic.parser.inputpatterns.TagDeleteParam;
import seedu.address.logic.parser.inputpatterns.TagEditParam;
import seedu.address.logic.parser.inputpatterns.Token;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser extends Parser<TagCommand> {

    /**
     * Defines the InputPattern of the parser for TagCommand
     *
     * @return An InputPattern
     */
    @Override
    InputPattern createInputPattern() {
        ArrayList<Token> tokens = new ArrayList<Token>(List.of(
                new IntegerToken("index" , 1, 100)
        ));

        ArrayList<Param> params = new ArrayList<>(List.of(
                new TagAddParam(0, 100),
                new TagEditParam(0, 100),
                new TagDeleteParam(0, 100)
        ));

        return new InputPattern("tag", tokens, params);
    }

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    TagCommand parse(String args) throws ParseException {
        try {
            InputPattern inputPattern = createInputPattern();
            inputPattern.assignSegmentsFromArgs(args.strip());

            Token indexToken = inputPattern.getTokenWithId("index");
            Index index = ParserUtil.parseIndex(indexToken.getAssignedSegment());
            Param tagAddParam = inputPattern.getParamWithId(PARAM_ID_TAG_ADD);
            ArrayList<String> addTagStrings = tagAddParam.getValues();
            List<Tag> addTags = addTagStrings.stream().map(Tag::new).toList();

            Param tagEditParam = inputPattern.getParamWithId(PARAM_ID_TAG_EDIT);
            ArrayList<String> editTagStrings = tagEditParam.getValues();
            List<Tag> editTags = editTagStrings.stream().map(Tag::new).toList();

            Param tagDeleteParam = inputPattern.getParamWithId(PARAM_ID_TAG_DELETE);
            ArrayList<String> deleteTagStrings = tagDeleteParam.getValues();
            List<Tag> deleteTags = deleteTagStrings.stream().map(str -> new Tag(str + ":dummy")).toList();

            return new TagCommand(index, addTags, editTags, deleteTags);
        } catch (ParseException e) {
            if (e.getMessage().contains("is not a valid value")) {
                throw new ParseException(DELIMITER_MESSAGE_CONSTRAINTS);
            }
            throw e;
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
