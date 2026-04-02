package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import seedu.address.commons.exceptions.IllegalValueException;

public class TagTest {

    @Test
    public void constructor_tagIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagString_throwsIllegalArgumentException() {
        String invalidTagString = "no_delimiter_tag";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagString));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "  ";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName + ":investment banker"));
    }

    @Test
    public void constructor_invalidTagValue_throwsIllegalArgumentException() {
        String invalidTagValue = "  ";
        assertThrows(IllegalArgumentException.class, () -> new Tag("job:" + invalidTagValue));
    }

    @Test
    public void constructor_validInputs_success() {
        // alphanumeric name and value
        Tag tag = new Tag("job:engineer");
        assertEquals("job", tag.tagName);
        assertEquals("engineer", tag.tagValue);

        // name and value with spaces
        Tag tagWithSpaces = new Tag("job title:investment banker");
        assertEquals("job title", tagWithSpaces.tagName);
        assertEquals("investment banker", tagWithSpaces.tagValue);

        // value with special characters
        Tag tagWithSpecial = new Tag("income:$200,000");
        assertEquals("income", tagWithSpecial.tagName);
        assertEquals("$200,000", tagWithSpecial.tagValue);

        // leading and trailing whitespace
        Tag tagWithWhitespace = new Tag("here is a tab\t:   and many spaces");
        assertEquals("here is a tab", tagWithWhitespace.tagName);
        assertEquals("and many spaces", tagWithWhitespace.tagValue);
    }

    public void helperAssertThrows(Executable executable, String expectedMessage) {
        Exception e = assertThrows(IllegalValueException.class, executable);
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    public void validityTagName() throws IllegalValueException {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.validateTagName(null));

        // all whitespace
        helperAssertThrows(() -> Tag.validateTagName(" \t\n"), Tag.WHITESPACE_NAME_CONSTRAINTS);

        // all whitespace with delimiter
        helperAssertThrows(() -> Tag.validateTagName(" \t:\n"), Tag.NAME_NO_DELIMITER_CONSTRAINT);

        // non-whitespace but has delimiter
        helperAssertThrows(() -> Tag.validateTagName("tab\t:newline\n"), Tag.NAME_NO_DELIMITER_CONSTRAINT);

        // too long
        helperAssertThrows(() -> Tag.validateTagName("a".repeat(100)),
                "a".repeat(100) + " is too long, it should not exceed "
                        + Tag.MAX_LENGTH + " characters.");

        // contains "name", "phone" or "email"
        helperAssertThrows(() -> Tag.validateTagName("name"), Tag.ILLEGAL_NAME_CONSTRAINTS);
        helperAssertThrows(() -> Tag.validateTagName("phone"), Tag.ILLEGAL_NAME_CONSTRAINTS);
        helperAssertThrows(() -> Tag.validateTagName("email"), Tag.ILLEGAL_NAME_CONSTRAINTS);

        // non-whitespace with no delimiter
        assertTrue(Tag.validateTagName("job"));
    }

    @Test
    public void validityTagValue() throws IllegalValueException {
        // null tag value
        assertThrows(NullPointerException.class, () -> Tag.validateTagValue(null));

        // all whitespace
        helperAssertThrows(() -> Tag.validateTagValue(" \t\n"), Tag.WHITESPACE_VALUE_CONSTRAINTS);

        // all whitespace with delimiter
        helperAssertThrows(() -> Tag.validateTagValue(" \t:\n"), Tag.VALUE_NO_DELIMITER_CONSTRAINT);

        // non-whitespace but has delimiter
        helperAssertThrows(() -> Tag.validateTagValue("tab\t:newline\n"), Tag.VALUE_NO_DELIMITER_CONSTRAINT);

        // too long
        helperAssertThrows(() -> Tag.validateTagValue("a".repeat(100)),
                "a".repeat(100) + " is too long, it should not exceed "
                        + Tag.MAX_LENGTH + " characters.");

        // non-whitespace with no delimiter
        assertTrue(Tag.validateTagValue("engineer"));
    }

    @Test
    public void invalidTagPair() throws IllegalValueException {
        // invalid tag string
        helperAssertThrows(() -> Tag.validateTagPair("too:many:delimiters"), Tag.ONE_DELIMITER_CONSTRAINT);

        // invalid tag name
        helperAssertThrows(() -> Tag.validateTagPair("\t\n :valid value"), Tag.WHITESPACE_NAME_CONSTRAINTS);

        // valid tag name, invalid tag value
        helperAssertThrows(() -> Tag.validateTagPair("valid name:\n\t "), Tag.WHITESPACE_VALUE_CONSTRAINTS);

        // valid tag name and value
        assertTrue(Tag.validateTagPair("valid name:valid value"));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Tag tag = new Tag("job:engineer");
        assertTrue(tag.equals(tag));
    }

    @Test
    public void equals_null_returnsFalse() {
        Tag tag = new Tag("tagname:value");
        assertNotEquals(null, tag);
    }

    @Test
    public void equals_nonTagClass_returnsFalse() {
        String tagString = "tagname:value";
        Tag tag = new Tag(tagString);
        assertFalse(tag.equals(tagString));
    }



    @Test
    public void equals_sameNameAndValue_returnsTrue() {
        Tag tag1 = new Tag("job:engineer");
        Tag tag2 = new Tag("job:engineer");
        assertEquals(tag1, tag2);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Tag tag1 = new Tag("job:engineer");
        Tag tag2 = new Tag("job:doctor");
        assertNotEquals(tag1, tag2);
    }

    @Test
    public void equals_differentName_returnsFalse() {
        Tag tag1 = new Tag("job:engineer");
        Tag tag2 = new Tag("occupation:engineer");
        assertNotEquals(tag1, tag2);
    }

    @Test
    public void hashCode_equalTags_sameHashCode() {
        Tag tag1 = new Tag("job:engineer");
        Tag tag2 = new Tag("job:engineer");
        assertEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void toString_validTag_correctFormat() {
        Tag tag = new Tag("job:engineer");
        assertEquals("job: engineer", tag.toString());

        Tag tagWithSpecialChars = new Tag("income:$200,000");
        assertEquals("income: $200,000", tagWithSpecialChars.toString());
    }
}
