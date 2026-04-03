package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class NameContainsPredicateTest {

    @Test
    public void equals() {
        NameContainsPredicate firstPredicate = new NameContainsPredicate(Collections.singletonList("first"));
        NameContainsPredicate secondPredicate = new NameContainsPredicate(Collections.singletonList("second"));

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        NameContainsPredicate firstPredicateCopy = new NameContainsPredicate(Collections.singletonList("first"));
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);
        assertFalse(firstPredicate.equals(new EmailContainsPredicate(Collections.singletonList("first"))));

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different person -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsPredicate predicate = new NameContainsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(ALICE));

        // Partial substring
        predicate = new NameContainsPredicate(Collections.singletonList("lic"));
        assertTrue(predicate.test(ALICE));

        // Multiple keywords
        predicate = new NameContainsPredicate(Arrays.asList("Alice", "Benson"));
        assertTrue(predicate.test(ALICE));

        // Only one matching keyword
        predicate = new NameContainsPredicate(Arrays.asList("Benson", "Carol"));
        assertTrue(predicate.test(BENSON));

        // Mixed-case keywords
        predicate = new NameContainsPredicate(Arrays.asList("aLIce", "bEnSoN"));
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsPredicate predicate = new NameContainsPredicate(Collections.emptyList());
        assertFalse(predicate.test(ALICE));

        // Non-matching keyword
        predicate = new NameContainsPredicate(Collections.singletonList("Carol"));
        assertFalse(predicate.test(ALICE));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(ALICE));

        // Multiple keywords with no substring match
        predicate = new NameContainsPredicate(Arrays.asList("xyz", "nomatch"));
        assertFalse(predicate.test(BENSON));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Alice", "lic");
        NameContainsPredicate predicate = new NameContainsPredicate(keywords);

        String expected = NameContainsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
