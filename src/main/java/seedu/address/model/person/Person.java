package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Optional<Phone> phone;
    private final Optional<Email> email;

    // Data fields
    private final TagList tags;

    /**
     * Name and tag list must be present and not null (tag list can be empty).
     */
    public Person(Name name, Phone phone, Email email, TagList tags) {
        requireAllNonNull(name, tags);

        this.name = name;
        this.phone = Optional.ofNullable(phone);
        this.email = Optional.ofNullable(email);
        this.tags = new TagList(tags);
    }

    public Name getName() {
        return name;
    }

    public boolean hasPhone() {
        return phone.isPresent();
    }

    public Phone getPhone() {
        assert (hasPhone());
        return phone.get();
    }

    public boolean hasEmail() {
        return email.isPresent();
    }

    public Email getEmail() {
        assert (hasEmail());
        return email.get();
    }

    public TagList getTags() {
        return this.tags;
    }

    /**
     * Returns a list of printable tag name-value pairs, collapsed into Strings.
     */
    public List<String> getPrintableTags() {
        return this.tags.viewTags();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("tags", tags)
                .toString();
    }

}
