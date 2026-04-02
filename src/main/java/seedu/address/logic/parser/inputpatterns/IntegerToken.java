package seedu.address.logic.parser.inputpatterns;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * A Token that takes in any integer
 * The range of valid integers are given by the Suppliers minValueSupplier and maxValueSupplier
 */
public class IntegerToken extends Token {
    public static final String INVALID_STRING =
            " is not a valid integer (or it exceeds the representable range of ["
            + Integer.MIN_VALUE + ", " + Integer.MAX_VALUE + "]).";

    private final int minValue;
    private final int maxValue;

    /**
     * @param minValue the minimum possible integer in this field
     * @param maxValue the maximum possible integer in this field
     */
    public IntegerToken(String id, int minValue, int maxValue) {
        super(id);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * Initializes an {@code IntegerToken} with maximum value of {@code Integer.MAX_VALUE}.
     * @param minValue the minimum possible integer in this field
     */
    public IntegerToken(String id, int minValue) {
        this(id, minValue, Integer.MAX_VALUE);
    }

    @Override
    public String getPreview() {
        return "[" + minValue + "..." + maxValue + "]";
    }

    @Override
    public boolean matches(String segment) throws IllegalValueException {
        try {
            int value = Integer.parseInt(segment);

            if (value < minValue) {
                throw new IllegalValueException(segment
                        + " is less than the minimum allowable value of " + minValue + ".");
            }
            if (value > maxValue) {
                throw new IllegalValueException(segment
                        + " is more than the maximum allowable value of " + maxValue + ".");
            }

            return true;
        } catch (NumberFormatException e) {
            throw new IllegalValueException(segment + INVALID_STRING);
        }
    }

}
