package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsPredicateTest {

    @Test
    public void equals() {
        PhoneContainsPredicate firstPredicate = new PhoneContainsPredicate(List.of("12345678"));
        PhoneContainsPredicate secondPredicate = new PhoneContainsPredicate(List.of("87654321"));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsPredicate firstPredicateCopy = new PhoneContainsPredicate(List.of("12345678"));
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
        PhoneContainsPredicate predicate = new PhoneContainsPredicate(List.of("12345678"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        predicate = new PhoneContainsPredicate(List.of("3456"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        predicate = new PhoneContainsPredicate(Arrays.asList("00000000", "3456"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));
    }

    @Test
    public void test_phoneDoesNotMatch_returnsFalse() {
        PhoneContainsPredicate predicate = new PhoneContainsPredicate(List.of("9999"));

        // Different phone
        assertFalse(predicate.test(new PersonBuilder().withPhone("87654321").build()));

        // Missing phone
        assertFalse(predicate.test(new PersonBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        PhoneContainsPredicate predicate = new PhoneContainsPredicate(List.of("12345678"));
        String expected = PhoneContainsPredicate.class.getCanonicalName() + "{phoneSubstrings=[12345678]}";
        assertEquals(expected, predicate.toString());
    }
}
