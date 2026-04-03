package seedu.address.model.person.predicates;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} contains any of the given substrings.
 */
public class PhoneContainsPredicate implements Predicate<Person> {
    private final List<String> phoneSubstrings;

    public PhoneContainsPredicate(List<String> phoneSubstrings) {
        this.phoneSubstrings = phoneSubstrings;
    }

    @Override
    public boolean test(Person person) {
        if (!person.hasPhone()) {
            return false;
        }

        String personPhone = person.getPhone().value;
        return phoneSubstrings.stream().anyMatch(personPhone::contains);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PhoneContainsPredicate)) {
            return false;
        }

        PhoneContainsPredicate otherPhoneContainsPredicate = (PhoneContainsPredicate) other;
        return Objects.equals(phoneSubstrings, otherPhoneContainsPredicate.phoneSubstrings);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("phoneSubstrings", phoneSubstrings).toString();
    }
}
