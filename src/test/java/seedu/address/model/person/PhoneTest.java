package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void validatePhone() throws IllegalValueException {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.validatePhone(null));

        // invalid phone numbers
        // empty string, spaces only, less than 3 numbers, non-numeric, alphabets within digits, spaces within digits
        for (String phone : new String[]{"", " ", "91", "phone", "9011p041", "9312 1534"}) {
            Exception e = assertThrows(IllegalValueException.class, () -> Phone.validatePhone(phone));
            assertEquals('"' + phone + '"' + " is not a valid phone number.\n"
                    + Phone.MESSAGE_CONSTRAINTS, e.getMessage());
        }

        // exceeds max length (21 digits)
        String tooLongPhone = "123456789012345678901";
        Exception e = assertThrows(IllegalValueException.class, () -> Phone.validatePhone(tooLongPhone));
        assertEquals('"' + tooLongPhone + '"' + " is not a valid phone number.\n"
                + Phone.MESSAGE_CONSTRAINTS, e.getMessage());

        // valid phone numbers
        assertTrue(Phone.validatePhone("911")); // exactly 3 numbers
        assertTrue(Phone.validatePhone("93121534"));
        assertTrue(Phone.validatePhone("124293842033123")); // long phone numbers
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }
}
