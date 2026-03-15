package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_tagNameIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null, "investment banker"));
    }

    @Test
    public void constructor_tagValueIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag("job", null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName, "investment banker"));
    }

    @Test
    public void constructor_invalidTagValue_throwsIllegalArgumentException() {
        String invalidTagValue = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag("job", invalidTagValue));
    }

    @Test
    public void constructor_validInputs_success() {
        // alphanumeric name and value
        Tag tag = new Tag("job", "engineer");
        assertEquals("job", tag.tagName);
        assertEquals("engineer", tag.tagValue);

        // name and value with spaces
        Tag tagWithSpaces = new Tag("job title", "investment banker");
        assertEquals("job title", tagWithSpaces.tagName);
        assertEquals("investment banker", tagWithSpaces.tagValue);

        // value with special characters
        Tag tagWithSpecial = new Tag("income", "$200,000");
        assertEquals("income", tagWithSpecial.tagName);
        assertEquals("$200,000", tagWithSpecial.tagValue);
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void isValidTagValue() {
        // null tag value
        assertThrows(NullPointerException.class, () -> Tag.isValidTagValue(null));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Tag tag = new Tag("job", "engineer");
        assertEquals(tag, tag);
    }

    @Test
    public void equals_sameNameAndValue_returnsTrue() {
        Tag tag1 = new Tag("job", "engineer");
        Tag tag2 = new Tag("job", "engineer");
        assertEquals(tag1, tag2);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Tag tag1 = new Tag("job", "engineer");
        Tag tag2 = new Tag("job", "doctor");
        assertNotEquals(tag1, tag2);
    }

    @Test
    public void equals_differentName_returnsFalse() {
        Tag tag1 = new Tag("job", "engineer");
        Tag tag2 = new Tag("occupation", "engineer");
        assertNotEquals(tag1, tag2);
    }

    @Test
    public void hashCode_equalTags_sameHashCode() {
        Tag tag1 = new Tag("job", "engineer");
        Tag tag2 = new Tag("job", "engineer");
        assertEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void toString_validTag_correctFormat() {
        Tag tag = new Tag("job", "engineer");
        assertEquals("job: engineer", tag.toString());

        Tag tagWithSpecialChars = new Tag("income", "$200,000");
        assertEquals("income: $200,000", tagWithSpecialChars.toString());
    }
}
