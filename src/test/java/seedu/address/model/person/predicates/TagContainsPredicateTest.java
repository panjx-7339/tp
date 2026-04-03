package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.TagFilter;
import seedu.address.testutil.PersonBuilder;

public class TagContainsPredicateTest {

    @Test
    public void equals() {
        List<TagFilter> firstPredicateTags = Collections.singletonList(new TagFilter("department:engineering"));
        List<TagFilter> secondPredicateTags =
                Arrays.asList(new TagFilter("department:engineering"), new TagFilter("level:senior"));

        TagContainsPredicate firstPredicate = new TagContainsPredicate(firstPredicateTags);
        TagContainsPredicate secondPredicate = new TagContainsPredicate(secondPredicateTags);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsPredicate firstPredicateCopy = new TagContainsPredicate(firstPredicateTags);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different tags -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_personHasMatchingTags_returnsTrue() {
        // Single tag matches
        TagContainsPredicate predicate = new TagContainsPredicate(
                Collections.singletonList(new TagFilter("department:engineering")));
        assertTrue(predicate.test(new PersonBuilder().withTags("department:engineering").build()));

        // Multiple tags required - person has all of them
        predicate = new TagContainsPredicate(Arrays.asList(
                new TagFilter("department:engineering"),
                new TagFilter("level:senior")));
        assertTrue(predicate.test(new PersonBuilder()
                .withTags("department:engineering", "level:senior").build()));

        // Multiple tags required - person has all plus extra
        predicate = new TagContainsPredicate(Arrays.asList(
                new TagFilter("department:engineering"),
                new TagFilter("level:senior")));
        assertTrue(predicate.test(new PersonBuilder()
                .withTags("department:engineering", "level:senior", "project:webapp").build()));

        // Tag name matches case-insensitively
        predicate = new TagContainsPredicate(
                Collections.singletonList(new TagFilter("Department:engineering")));
        assertTrue(predicate.test(new PersonBuilder().withTags("department:engineering").build()));

        // Tag value matches case-insensitively
        predicate = new TagContainsPredicate(
                Collections.singletonList(new TagFilter("department:ENGINEERING")));
        assertTrue(predicate.test(new PersonBuilder().withTags("department:engineering").build()));

        // Bare tag name checks for existence only
        predicate = new TagContainsPredicate(Collections.singletonList(new TagFilter("department")));
        assertTrue(predicate.test(new PersonBuilder().withTags("department:engineering").build()));
    }

    @Test
    public void test_personDoesNotHaveMatchingTags_returnsFalse() {
        // Zero tags in predicate
        TagContainsPredicate predicate = new TagContainsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withTags("department:engineering").build()));

        // Non-matching tag name
        predicate = new TagContainsPredicate(
                Collections.singletonList(new TagFilter("role:manager")));
        assertFalse(predicate.test(new PersonBuilder().withTags("department:engineering").build()));

        // Non-matching tag value
        predicate = new TagContainsPredicate(
                Collections.singletonList(new TagFilter("department:sales")));
        assertFalse(predicate.test(new PersonBuilder().withTags("department:engineering").build()));

        // Multiple tags required but person has only one
        predicate = new TagContainsPredicate(Arrays.asList(
                new TagFilter("department:engineering"),
                new TagFilter("level:senior")));
        assertFalse(predicate.test(new PersonBuilder().withTags("department:engineering").build()));

        // Multiple tags required but person doesn't have any of them
        predicate = new TagContainsPredicate(Arrays.asList(
                new TagFilter("department:sales"),
                new TagFilter("level:junior")));
        assertFalse(predicate.test(new PersonBuilder().withTags("department:engineering", "level:senior").build()));

        // Multiple tags required but person has only one matching
        predicate = new TagContainsPredicate(Arrays.asList(
                new TagFilter("department:engineering"),
                new TagFilter("level:junior")));
        assertFalse(predicate.test(new PersonBuilder().withTags("department:engineering", "level:senior").build()));

        // Person has no tags
        predicate = new TagContainsPredicate(
                Collections.singletonList(new TagFilter("department:engineering")));
        assertFalse(predicate.test(new PersonBuilder().build()));

        // Bare tag name does not match when tag is absent
        predicate = new TagContainsPredicate(Collections.singletonList(new TagFilter("department")));
        assertFalse(predicate.test(new PersonBuilder().withTags("role:engineering").build()));
    }

    @Test
    public void toStringMethod() {
        List<TagFilter> tags = Arrays.asList(
                new TagFilter("department:engineering"),
                new TagFilter("level:senior"));
        TagContainsPredicate predicate = new TagContainsPredicate(tags);

        String expected = TagContainsPredicate.class.getCanonicalName() + "{tagFilters=" + tags + "}";
        assertEquals(expected, predicate.toString());
    }
}
