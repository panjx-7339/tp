package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

public class JsonAdaptedTagTest {
    private static final Tag tag = new Tag("job:engineer");

    @Test
    public void correctTagString() {
        JsonAdaptedTag adaptedTag = new JsonAdaptedTag(tag);
        assertEquals("job:engineer", adaptedTag.getTag());
    }

    @Test
    public void toModelType() throws IllegalValueException {
        // null tag string
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTag((String) null).toModelType());

        // invalid tag string
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTag("invalid:tag:pair").toModelType());

        // valid tag
        assertEquals(tag, new JsonAdaptedTag("job:engineer").toModelType());
    }
}
