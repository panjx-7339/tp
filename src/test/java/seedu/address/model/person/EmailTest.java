package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    public void helperAssertThrows(String test) {
        Exception e = assertThrows(IllegalValueException.class, () -> Email.validateEmail(test));
        assertEquals(test + Email.INVALID_STRING, e.getMessage());
    }

    @Test
    public void validateEmail() throws IllegalValueException {
        // null email
        assertThrows(NullPointerException.class, () -> Email.validateEmail(null));

        // blank email
        helperAssertThrows(""); // empty string
        helperAssertThrows(" "); // spaces only

        // missing parts
        helperAssertThrows("@example.com"); // local part
        helperAssertThrows("peterjackexample.com"); // '@' symbol
        helperAssertThrows("peterjack@"); // domain name

        // invalid parts
        helperAssertThrows("peterjack@-"); // invalid domain name
        helperAssertThrows("peterjack@exam_ple.com"); // underscore in domain name
        helperAssertThrows("peter jack@example.com"); // spaces in local part
        helperAssertThrows("peterjack@exam ple.com"); // spaces in domain name
        helperAssertThrows(" peterjack@example.com"); // leading space
        helperAssertThrows("peterjack@example.com "); // trailing space
        helperAssertThrows("peterjack@@example.com"); // double '@' symbol
        helperAssertThrows("peter@jack@example.com"); // '@' symbol in local part
        helperAssertThrows("-peterjack@example.com"); // local part starts with a hyphen
        helperAssertThrows("peterjack-@example.com"); // local part ends with a hyphen
        helperAssertThrows("peter..jack@example.com"); // local part has two consecutive periods
        helperAssertThrows("peterjack@example@com"); // '@' symbol in domain name
        helperAssertThrows("peterjack@.example.com"); // domain name starts with a period
        helperAssertThrows("peterjack@example.com."); // domain name ends with a period
        helperAssertThrows("peterjack@-example.com"); // domain name starts with a hyphen
        helperAssertThrows("peterjack@example.com-"); // domain name ends with a hyphen
        helperAssertThrows("peterjack@example.c"); // top level domain has less than two chars

        // valid email
        assertTrue(Email.validateEmail("PeterJack_1190@example.com")); // underscore in local part
        assertTrue(Email.validateEmail("PeterJack.1190@example.com")); // period in local part
        assertTrue(Email.validateEmail("PeterJack+1190@example.com")); // '+' symbol in local part
        assertTrue(Email.validateEmail("PeterJack-1190@example.com")); // hyphen in local part
        assertTrue(Email.validateEmail("a@bc")); // minimal
        assertTrue(Email.validateEmail("test@localhost")); // alphabets only
        assertTrue(Email.validateEmail("123@145")); // numeric local part and domain name
        assertTrue(Email.validateEmail("a1+be.d@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Email.validateEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Email.validateEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
        assertTrue(Email.validateEmail("e1234567@u.nus.edu")); // more than one period in domain
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email");

        // same values -> returns true
        assertTrue(email.equals(new Email("valid@email")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new Email("other.valid@email")));
    }
}
