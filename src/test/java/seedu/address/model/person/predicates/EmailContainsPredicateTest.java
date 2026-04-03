package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("alice");
        List<String> secondPredicateKeywordList = Arrays.asList("alice", "bob");

        EmailContainsPredicate firstPredicate = new EmailContainsPredicate(firstPredicateKeywordList);
        EmailContainsPredicate secondPredicate = new EmailContainsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        EmailContainsPredicate firstPredicateCopy = new EmailContainsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);
        assertFalse(firstPredicate.equals(new NameContainsPredicate(Collections.singletonList("alice"))));

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different keywords -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword - exact match
        EmailContainsPredicate predicate = new EmailContainsPredicate(
                Collections.singletonList("alice@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // One keyword - partial substring match
        predicate = new EmailContainsPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // One keyword - domain match
        predicate = new EmailContainsPredicate(Collections.singletonList("email.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Multiple keywords - first matches
        predicate = new EmailContainsPredicate(Arrays.asList("alice", "bob"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Multiple keywords - second matches
        predicate = new EmailContainsPredicate(Arrays.asList("bob", "gmail.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("test@gmail.com").build()));

        // Keyword matches middle of email
        predicate = new EmailContainsPredicate(Collections.singletonList("@e"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Mixed-case email still matches because comparison is case-insensitive
        predicate = new EmailContainsPredicate(Collections.singletonList("alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("ALICE@EMAIL.COM").build()));
    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsPredicate predicate = new EmailContainsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsPredicate(Collections.singletonList("bob"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Multiple non-matching keywords
        predicate = new EmailContainsPredicate(Arrays.asList("bob", "carol"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Keyword is case-sensitive
        predicate = new EmailContainsPredicate(Collections.singletonList("ALICE"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Person has no email
        predicate = new EmailContainsPredicate(Collections.singletonList("alice"));
        assertFalse(predicate.test(new PersonBuilder().build()));

        // Partial match doesn't exist
        predicate = new EmailContainsPredicate(Collections.singletonList("xyz"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Blank-ish mismatch still returns false when email is present
        predicate = new EmailContainsPredicate(Collections.singletonList(" "));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("alice", "bob");
        EmailContainsPredicate predicate = new EmailContainsPredicate(keywords);

        String expected = EmailContainsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
