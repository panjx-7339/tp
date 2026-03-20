package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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

    @Test
    public void validity_of_tagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // all whitespace
        assertFalse(Tag.isValidTagName(" \t\n"));

        // all whitespace with delimiter
        assertFalse(Tag.isValidTagName(" \t:\n"));

        // non-whitespace but has delimiter
        assertFalse(Tag.isValidTagName("tab\t:newline\n"));

        // too long
        assertFalse(Tag.isValidTagName("a".repeat(100)));

        // contains "phone" or "email"
        assertFalse(Tag.isValidTagName("name:John Doe"));
        assertFalse(Tag.isValidTagName("phone:87654321"));
        assertFalse(Tag.isValidTagName("email:hello@gmail.com"));

        // non-whitespace with no delimiter
        assertTrue(Tag.isValidTagName("job"));
    }

    @Test
    public void validity_of_tagValue() {
        // null tag value
        assertThrows(NullPointerException.class, () -> Tag.isValidTagValue(null));

        // all whitespace
        assertFalse(Tag.isValidTagValue(" \t\n"));

        // all whitespace with delimiter
        assertFalse(Tag.isValidTagValue(" \t:\n"));

        // non-whitespace but has delimiter
        assertFalse(Tag.isValidTagValue("tab\t:newline\n"));

        // too long
        assertFalse(Tag.isValidTagValue("a".repeat(100)));

        // non-whitespace with no delimiter
        assertTrue(Tag.isValidTagValue("engineer"));
    }

    @Test
    public void invalidTagPair() {
        // invalid tag string
        assertFalse(Tag.isValidTagPair("too:many:delimiters"));

        // invalid tag name
        assertFalse(Tag.isValidTagPair("\t\n :valid value"));

        // valid tag name, invalid tag value
        assertFalse(Tag.isValidTagPair("valid name:\n\t "));

        // valid tag name and value
        assertTrue(Tag.isValidTagPair("valid name:valid value"));
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
