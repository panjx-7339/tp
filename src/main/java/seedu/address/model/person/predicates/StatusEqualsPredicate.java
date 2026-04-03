package seedu.address.model.person.predicates;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;

/**
 * Tests that a {@code Person}'s {@code Status} matches any of the given statuses.
 */
public class StatusEqualsPredicate implements Predicate<Person> {
    private final List<Status> statuses;

    public StatusEqualsPredicate(List<Status> statuses) {
        this.statuses = statuses;
    }

    @Override
    public boolean test(Person person) {
        return statuses.contains(person.getStatus());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusEqualsPredicate otherStatusEqualsPredicate)) {
            return false;
        }

        return Objects.equals(statuses, otherStatusEqualsPredicate.statuses);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("statuses", statuses).toString();
    }
}
