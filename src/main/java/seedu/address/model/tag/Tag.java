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

    public static final String NAME_MESSAGE_CONSTRAINTS =
            "Tag names should contain at least one non-whitespace character";
    public static final String VALUE_MESSAGE_CONSTRAINTS =
            "Tag values should contain at least one non-whitespace character";
    public static final String FORMAT_MESSAGE_CONSTRAINTS =
            "Tags should be in the format of <tag name>:<tag value>";

    public static final String VALIDATION_REGEX = ".*\\S.*";

    public final String tagName;
    public final String tagValue;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     * @param tagName A valid tag value.
     */
    public Tag(String tagName, String tagValue) {
        requireNonNull(tagName);
        requireNonNull(tagValue);
        checkArgument(isValidTagName(tagName), NAME_MESSAGE_CONSTRAINTS);
        checkArgument(isValidTagValue(tagValue), VALUE_MESSAGE_CONSTRAINTS);

        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagValue(String test) {
        return test.matches(VALIDATION_REGEX);
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
