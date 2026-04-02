package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void validateName() throws IllegalValueException {
        // null name
        assertThrows(NullPointerException.class, () -> Name.validateName(null));

        // invalid name
        // empty string, spaces only, only non-alphanumeric characters, contains non-alphanumeric characters
        for (String name : new String[]{"", " ", "^", "peter*"}) {
            Exception e = assertThrows(IllegalValueException.class, () -> Name.validateName(name));
            assertEquals('"' + name + '"' + " is not a valid name.\n" + Name.MESSAGE_CONSTRAINTS, e.getMessage());
        }

        // valid name
        assertTrue(Name.validateName("peter jack")); // alphabets only
        assertTrue(Name.validateName("12345")); // numbers only
        assertTrue(Name.validateName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.validateName("Capital Tan")); // with capital letters
        assertTrue(Name.validateName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.validateName("Jia` En")); // check for `
        assertTrue(Name.validateName("Kong Kar Lok, Donald (Kong Jia Le)")); // check for , ()
        assertTrue(Name.validateName("Davinder Singh Sachdev s/o Amar Singh")); // check for /
        assertTrue(Name.validateName("George H.W. Bush")); // check for .
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
