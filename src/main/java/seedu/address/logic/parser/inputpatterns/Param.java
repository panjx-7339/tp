package seedu.address.logic.parser.inputpatterns;

import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Param is a special kind of Token that takes in parameters
 * Parameters have the form of -(id) value
 * Parameters can be inputted in any order
 */
public abstract class Param extends Token {

    /**
     * The minimum and maximum occurrences this param can have in an input
     */
    private int minOccurences;
    private int maxOccurences;

    private ArrayList<String> values;

    /**
     * A param represents a part of an inputPattern that comes after the tokens
     * Each param takes in a segment of the form "--id value"
     * Some params can occur several times, and the range of no of values
     * must be between minOccurences and maxOccurences
     *
     * @param id the param id, and starts with --
     * @param minOccurences the min number of times this param should appear in an inputPattern
     * @param maxOccurences the max number of times this param should appear in an inputPattern
     */
    public Param(String id, int minOccurences, int maxOccurences) {
        super(id);
        assert(id.startsWith("--"));
        assert(minOccurences <= maxOccurences);

        this.minOccurences = minOccurences;
        this.maxOccurences = maxOccurences;

        this.values = new ArrayList<>();
    }

    /**
     * @param segment a string entered for the param, including the -id
     * @return whether it matches
     */
    @Override
    public boolean matches(String segment) throws IllegalValueException {
        if (!idMatches(segment)) {
            return false;
        }

        String value = getValueFromSegment(segment);
        return valueMatches(value);
    }

    /**
     * Given a segment, determine if the segment contains the id string as a prefix
     *
     * @param segment part of the input string
     * @return whether it matches
     */
    public boolean idMatches(String segment) {
        String strippedSegment = segment.strip();
        return strippedSegment.matches(getId() + " .*") || (strippedSegment.equals(getId()));
    }

    String getValueFromSegment(String segment) {
        String strippedSegment = segment.strip();
        String value = strippedSegment.substring(getId().length()).strip();
        return value;
    }

    abstract boolean valueMatches(String value) throws IllegalValueException;

    /**
     * Appends the value to the ArrayList of values
     *
     * @param segment the segment of text to add from
     */
    public void addValueFromSegment(String segment) {
        String value = getValueFromSegment(segment);
        values.add(value);
    }

    /**
     * Checks if the number of values associated with this param
     * is between minOccurnces and maxOccurences
     * If not, throws the ParseException
     * @throws ParseException
     */
    public void checkIfAppropriateNumberOfValues() throws ParseException {
        if (values.size() < minOccurences) {
            throw new ParseException("Expected at least " + minOccurences + " parameter for " + getId());
        }
        if (values.size() > maxOccurences) {
            throw new ParseException(values.size() + " parameters of " + getId() + " inputted"
                    + " expected at most " + maxOccurences + " only");
        }
    }

    public ArrayList<String> getValues() {
        return this.values;
    }


}
