package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Status;
import seedu.address.testutil.PersonBuilder;

public class StatusEqualsPredicateTest {

    @Test
    public void equals() {
        List<Status> firstPredicateStatuses = Collections.singletonList(Status.TARGET);
        List<Status> secondPredicateStatuses = Arrays.asList(Status.TARGET, Status.SCAM);

        StatusEqualsPredicate firstPredicate = new StatusEqualsPredicate(firstPredicateStatuses);
        StatusEqualsPredicate secondPredicate = new StatusEqualsPredicate(secondPredicateStatuses);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StatusEqualsPredicate firstPredicateCopy = new StatusEqualsPredicate(firstPredicateStatuses);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different statuses -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasMatchingStatus_returnsTrue() {
        // Single status matches
        StatusEqualsPredicate predicate = new StatusEqualsPredicate(Collections.singletonList(Status.TARGET));
        assertTrue(predicate.test(new PersonBuilder().withStatus(Status.TARGET).build()));

        // Multiple statuses - first matches
        predicate = new StatusEqualsPredicate(Arrays.asList(Status.TARGET, Status.SCAM));
        assertTrue(predicate.test(new PersonBuilder().withStatus(Status.TARGET).build()));

        // Multiple statuses - second matches
        predicate = new StatusEqualsPredicate(Arrays.asList(Status.TARGET, Status.SCAM));
        assertTrue(predicate.test(new PersonBuilder().withStatus(Status.SCAM).build()));

        // NONE status matches
        predicate = new StatusEqualsPredicate(Collections.singletonList(Status.NONE));
        assertTrue(predicate.test(new PersonBuilder().withStatus(Status.NONE).build()));
    }

    @Test
    public void test_personDoesNotHaveMatchingStatus_returnsFalse() {
        // Zero statuses
        StatusEqualsPredicate predicate = new StatusEqualsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withStatus(Status.TARGET).build()));

        // Non-matching status
        predicate = new StatusEqualsPredicate(Collections.singletonList(Status.SCAM));
        assertFalse(predicate.test(new PersonBuilder().withStatus(Status.TARGET).build()));

        // Multiple statuses with no match
        predicate = new StatusEqualsPredicate(Arrays.asList(Status.SCAM, Status.IGNORE));
        assertFalse(predicate.test(new PersonBuilder().withStatus(Status.TARGET).build()));
    }

    @Test
    public void toStringMethod() {
        List<Status> statuses = Arrays.asList(Status.TARGET, Status.SCAM);
        StatusEqualsPredicate predicate = new StatusEqualsPredicate(statuses);

        String expected = StatusEqualsPredicate.class.getCanonicalName() + "{statuses=" + statuses + "}";
        assertEquals(expected, predicate.toString());
    }
}
