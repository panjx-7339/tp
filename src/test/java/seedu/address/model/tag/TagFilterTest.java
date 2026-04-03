package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TagFilterTest {

    @Test
    public void constructor_filterIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagFilter(null));
    }

    @Test
    public void constructor_nameOnly_success() {
        TagFilter tagFilter = new TagFilter("job");

        assertEquals("job", tagFilter.tagName);
        assertFalse(tagFilter.hasTagValue());
        assertTrue(tagFilter.getTagValue().isEmpty());
    }

    @Test
    public void constructor_nameOnlyWithWhitespace_success() {
        TagFilter tagFilter = new TagFilter("  job title\t ");

        assertEquals("job title", tagFilter.tagName);
        assertFalse(tagFilter.hasTagValue());
        assertTrue(tagFilter.getTagValue().isEmpty());
    }

    @Test
    public void constructor_nameAndValue_success() {
        TagFilter tagFilter = new TagFilter("job:engineer");

        assertEquals("job", tagFilter.tagName);
        assertTrue(tagFilter.hasTagValue());
        assertEquals("engineer", tagFilter.getTagValue().orElseThrow());
    }

    @Test
    public void constructor_nameAndValueWithWhitespace_success() {
        TagFilter tagFilter = new TagFilter(" job title :  investment banker ");

        assertEquals("job title", tagFilter.tagName);
        assertTrue(tagFilter.hasTagValue());
        assertEquals("investment banker", tagFilter.getTagValue().orElseThrow());
    }

    @Test
    public void constructor_invalidNameOnly_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TagFilter("name"));
    }

    @Test
    public void constructor_invalidNameAndValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TagFilter("too:many:delimiters"));
    }

    @Test
    public void equals() {
        TagFilter firstTagFilter = new TagFilter("job");
        TagFilter secondTagFilter = new TagFilter("job:engineer");

        assertTrue(firstTagFilter.equals(firstTagFilter));
        assertTrue(firstTagFilter.equals(new TagFilter("job")));
        assertFalse(firstTagFilter.equals(1));
        assertFalse(firstTagFilter.equals(null));
        assertFalse(firstTagFilter.equals(secondTagFilter));
    }

    @Test
    public void equals_sameNameAndValue_returnsTrue() {
        TagFilter firstTagFilter = new TagFilter("job:engineer");
        TagFilter secondTagFilter = new TagFilter("job:engineer");

        assertEquals(firstTagFilter, secondTagFilter);
    }

    @Test
    public void equals_sameNameButDifferentValue_returnsFalse() {
        TagFilter firstTagFilter = new TagFilter("job:engineer");
        TagFilter secondTagFilter = new TagFilter("job:doctor");

        assertNotEquals(firstTagFilter, secondTagFilter);
    }

    @Test
    public void equals_nameOnlyAndNameWithValue_returnsFalse() {
        TagFilter firstTagFilter = new TagFilter("job");
        TagFilter secondTagFilter = new TagFilter("job:engineer");

        assertNotEquals(firstTagFilter, secondTagFilter);
    }

    @Test
    public void hashCode_equalFilters_sameHashCode() {
        TagFilter firstTagFilter = new TagFilter("job");
        TagFilter secondTagFilter = new TagFilter("job");

        assertEquals(firstTagFilter.hashCode(), secondTagFilter.hashCode());
    }

    @Test
    public void toString_nameOnly_returnsName() {
        TagFilter tagFilter = new TagFilter("job");

        assertEquals("job", tagFilter.toString());
    }

    @Test
    public void toString_nameAndValue_returnsFormattedString() {
        TagFilter tagFilter = new TagFilter("income:$200,000");

        assertEquals("income: $200,000", tagFilter.toString());
    }
}
