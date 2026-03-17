package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class TagListTest {
    private static final String tagString1 = "job:engineer";
    private static final String tagString2 = "salary:10000";
    private static final String tagString3 = "faculty:FASS";
    private static final String tagString4 = "faculty:SoC";

    private static final Tag tag1 = new Tag(tagString1);
    private static final Tag tag2 = new Tag(tagString2);
    private static final Tag tag3 = new Tag(tagString3);
    private static final Tag tag4 = new Tag(tagString4);

    @Test
    public void equivalent_constructors() {
        TagList tagList1 = new TagList(tagString1, tagString2, tagString3);
        TagList tagList2 = new TagList(List.of(tag1, tag2, tag3));
        TagList tagList3 = new TagList(tagList1);

        assertEquals(tagList1, tagList2);
        assertEquals(tagList2, tagList3);
        assertEquals(tagList3, tagList1);
    }

    @Test
    public void add_tag_success() {
        TagList tagList1 = new TagList(tagString1, tagString3);
        TagList tagList2 = new TagList(tagString1);
        tagList2.addTag(tag3);

        assertEquals(tagList1, tagList2);
    }

    @Test
    public void edit_tag_success() {
        TagList tagList1 = new TagList(tagString1, tagString3);
        TagList tagList2 = new TagList(tagString1, tagString4);
        tagList2.editTag(tag3);

        assertEquals(tagList1, tagList2);
    }

    @Test
    public void delete_tag_success() {
        TagList tagList1 = new TagList(tagString1, tagString3);
        TagList tagList2 = new TagList(tagString1);
        tagList1.deleteTag(tag3);

        assertEquals(tagList1, tagList2);
    }

    @Test
    public void contains_tag_success() {
        TagList tagList1 = new TagList(tagString1, tagString3);

        assertTrue(tagList1.containsTagName("job"));
        assertFalse(tagList1.containsTagName("salary"));
    }

    @Test
    public void equals_with_self() {
        TagList tagList1 = new TagList(tagString1, tagString3);
        assertEquals(tagList1, tagList1);
    }

    @Test
    public void not_equals_success() {
        TagList tagList1 = new TagList(tagString1, tagString3);
        assertFalse(tagList1.equals(null));
        assertFalse(tagList1.equals(List.of(tagString1, tagString2)));
    }
}
