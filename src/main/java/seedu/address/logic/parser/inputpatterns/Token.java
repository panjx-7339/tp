package seedu.address.logic.parser.inputpatterns;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * An abstract class for representing a single token
 * A command consists of several tokens following an InputPattern
 * id - a String that identifies the token when its part of an InputPattern
 * assignedSegment - a String, which is a substring of the input argument
 * that has been assigned to this token
 */
public abstract class Token {


    private final String id;
    private String assignedSegment;

    public Token(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getAssignedSegment() {
        return assignedSegment;
    }

    public void setAssignedSegment(String assignedSegment) {
        this.assignedSegment = assignedSegment;
    }

    /**
     * @return the string that should appear as a suggestion
     */
    public abstract String getPreview();

    /**
     * @param segment a string entered for a token
     * @return whether the segment is a valid string matching the token's requirements
     */
    public abstract boolean matches(String segment) throws IllegalValueException;

    /**
     * @return whether this token allow spaces, which by default is false
     */
    public boolean allowSpaces() {
        return false;
    }



}
