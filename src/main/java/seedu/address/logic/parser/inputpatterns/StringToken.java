package seedu.address.logic.parser.inputpatterns;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * A Token that takes in any non-empty String as an input
 */
public class StringToken extends Token {
    private String tokenPreview;

    /**
     * @param tokenPreview the description of what this string should be
     *                      e.g. task_name
     */
    public StringToken(String id, String tokenPreview) {
        super(id);
        this.tokenPreview = tokenPreview;
    }

    @Override
    public String getPreview() {
        return tokenPreview;
    }

    @Override
    public boolean matches(String segment) throws IllegalValueException {
        if (segment.isEmpty()) {
            throw new IllegalValueException(tokenPreview + " cannot be empty");
        }
        return true;
    }

    @Override
    public boolean allowSpaces() {
        return true;
    }
}
