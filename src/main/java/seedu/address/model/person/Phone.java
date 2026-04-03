package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #validatePhone(String)}
 */
public class Phone {

    public static final int MIN_LENGTH = 3;
    public static final int MAX_LENGTH = 20;

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers (no spaces!), and it should be "
            + MIN_LENGTH + " to " + MAX_LENGTH + " digits long.";
    public static final String VALIDATION_REGEX = "\\d{" + MIN_LENGTH + "," + MAX_LENGTH + "}";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        try {
            validatePhone(phone);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean validatePhone(String test) throws IllegalValueException {
        if (test.matches(VALIDATION_REGEX)) {
            return true;
        } else {
            throw new IllegalValueException('"' + test + '"' + " is not a valid phone number.\n" + MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
