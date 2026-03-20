package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name and value are valid as declared in
 * {@link #isValidTagName(String)} and {@link #isValidTagValue(String)}
 */
public class Tag {

    public static final String TAG_DELIMITER = ":";
    public static final String WHITESPACE_REGEX = ".*\\S.*";
    public static final String NO_DELIMITER_REGEX = ".[^" + TAG_DELIMITER + "]*$";
    public static final String ONE_DELIMITER_REGEX =
            String.format("^[^%s]*%s[^%s]*$", TAG_DELIMITER, TAG_DELIMITER, TAG_DELIMITER);

    public static final int MAX_LENGTH = 50;

    public static final String DELIMITER_MESSAGE_CONSTRAINTS =
            "Tag name-value pairs should contain exactly one occurrence of the delimiter " + TAG_DELIMITER
            + "\nTags to be deleted should be represented by only its name, without any occurrence of the delimiter";
    public static final String NAME_MESSAGE_CONSTRAINTS =
            "Tag names should contain at least one non-whitespace character"
            + " and must not equal \"name\", \"phone\" or \"email\".";
    public static final String VALUE_MESSAGE_CONSTRAINTS =
            "Tag values should contain at least one non-whitespace character.";
    public static final String FORMAT_MESSAGE_CONSTRAINTS =
            "Tags should be in the format of <tag name>:<tag value>.";
    public static final String NAME_LENGTH_MESSAGE_CONSTRAINTS =
            "Tag names should not exceed " + MAX_LENGTH + " characters"
            + " (after removing leading and trailing whitespace).";
    public static final String VALUE_LENGTH_MESSAGE_CONSTRAINTS =
            "Tag values should not exceed " + MAX_LENGTH + " characters"
            + " (after removing leading and trailing whitespace).";

    public final String tagName;
    public final String tagValue;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagString A valid tag string, with name, exactly one delimiter and value.
     */
    public Tag(String tagString) {
        requireNonNull(tagString);
        checkArgument(Tag.isValidTagString(tagString), DELIMITER_MESSAGE_CONSTRAINTS);

        String tagName = Tag.getNameFromRaw(tagString);
        String tagValue = Tag.getValueFromRaw(tagString);

        requireNonNull(tagName);
        requireNonNull(tagValue);
        checkArgument(Tag.isValidLength(tagName), NAME_LENGTH_MESSAGE_CONSTRAINTS);
        checkArgument(Tag.isValidLength(tagValue), VALUE_LENGTH_MESSAGE_CONSTRAINTS);
        checkArgument(Tag.isValidTagName(tagName), NAME_MESSAGE_CONSTRAINTS);
        checkArgument(Tag.isValidTagValue(tagValue), VALUE_MESSAGE_CONSTRAINTS);

        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    /**
     * Retrieves tag name from raw string representing the tag.
     * @param tag Raw tag string.
     * @return Tag name.
     */
    public static String getNameFromRaw(String tag) {
        return tag.split(TAG_DELIMITER)[0].trim();
    }

    /**
     * Retrieves tag value from raw string representing the tag.
     * @param tag Raw tag string.
     * @return Tag value.
     */
    public static String getValueFromRaw(String tag) {
        return tag.split(TAG_DELIMITER)[1].trim();
    }

    /**
     * Checks if a given strings contains at most maxLength number of characters.
     * @param test The string to be tested.
     * @return Boolean indicating if the string is short enough.
     */
    public static boolean isValidLength(String test) {
        return test.length() <= Tag.MAX_LENGTH;
    }

    /**
     * Checks if a given string contains exactly one occurrence of the tag delimiter.
     * @param test The tag string to be tested.
     * @return Boolean indicating validity of the string.
     */
    public static boolean isValidTagString(String test) {
        return test.matches(ONE_DELIMITER_REGEX) && test.split(TAG_DELIMITER).length == 2;
    }

    /**
     * Checks if a given string is a valid tag name.
     * @param test The name to be tested.
     * @return Boolean indicating validity of the name.
     */
    public static boolean isValidTagName(String test) {
        return isValidLength(test) && test.matches(WHITESPACE_REGEX) && test.matches(NO_DELIMITER_REGEX)
                && !test.equals("name") && !test.equals("phone") && !test.equals("email");
    }

    /**
     * Checks if a given string contains the tag delimiter.
     * @param test The string to be tested.
     * @return Boolean indicating validity of the string.
     */
    public static boolean noDelimiter(String test) {
        return !test.contains(TAG_DELIMITER);
    }

    /**
     * Checks if a given string is a valid tag value.
     * @param test The value to be tested.
     * @return Boolean indicating validity of the value.
     */
    public static boolean isValidTagValue(String test) {
        return isValidLength(test) && test.matches(WHITESPACE_REGEX) && test.matches(NO_DELIMITER_REGEX);
    }

    /**
     * Checks if a given string is a fully valid tag.
     * @param test Tag string to be tested.
     * @return Boolean indicating validity of the tag string.
     */
    public static boolean isValidTagPair(String test) {
        if (!Tag.isValidTagString(test)) {
            return false;
        }

        String tagName = Tag.getNameFromRaw(test);
        String tagValue = Tag.getValueFromRaw(test);

        return Tag.isValidTagName(tagName) && Tag.isValidTagValue(tagValue);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName) && tagValue.equals(otherTag.tagValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName, tagValue);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return tagName + ": " + tagValue;
    }

}
