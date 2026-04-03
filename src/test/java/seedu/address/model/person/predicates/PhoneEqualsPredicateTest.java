package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneEqualsPredicateTest {

    @Test
    public void equals() {
        PhoneEqualsPredicate firstPredicate = new PhoneEqualsPredicate(List.of("12345678"));
        PhoneEqualsPredicate secondPredicate = new PhoneEqualsPredicate(List.of("87654321"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneEqualsPredicate firstPredicateCopy = new PhoneEqualsPredicate(List.of("12345678"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different phone -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneMatches_returnsTrue() {
        PhoneEqualsPredicate predicate = new PhoneEqualsPredicate(List.of("12345678"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        predicate = new PhoneEqualsPredicate(Arrays.asList("00000000", "12345678"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_phoneDoesNotMatch_returnsFalse() {
        PhoneEqualsPredicate predicate = new PhoneEqualsPredicate(List.of("12345678"));

        // Different phone
        assertFalse(predicate.test(new PersonBuilder().withPhone("87654321").build()));

        // Missing phone
        assertFalse(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        PhoneEqualsPredicate predicate = new PhoneEqualsPredicate(List.of("12345678"));
        String expected = PhoneEqualsPredicate.class.getCanonicalName() + "{phone=[12345678]}";
        assertEquals(expected, predicate.toString());
    }
}
