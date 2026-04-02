package seedu.address.logic.parser.inputpatterns;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * A series of tokens that represent a valid command structure
 */
public class InputPattern {

    public static final String MESSAGE_TOO_FEW_FIELDS = "Too few fields inputted (TODO MORE DESCRIPTIVE)";
    public static final String MESSAGE_TOO_MANY_FIELDS = "Too many fields inputted (TODO MORE DESCRIPTIVE)";
    /**
     * The list of tokens that form the first part of the input arguments
     */
    private ArrayList<Token> tokens;

    /**
     * The list of parameters that can come after all the tokens
     */
    private ArrayList<Param> params;

    /**
     * The label of this input pattern,
     * typically refers to the command this input pattern handles
     */
    private final String label;

    /**
     * @param label the label of the InputPattern
     * @param tokens a List of tokens that make up this InputPattern
     * @param params a List of params that can be specified at the end of this InputPattern
     */
    public InputPattern(String label, ArrayList<Token> tokens, ArrayList<Param> params) {
        this.tokens = tokens;
        this.label = label;
        this.params = params;
    }

    /**
     * Constructor for no parameters
     *
     * @param label the label of the InputPattern
     * @param tokens a List of tokens that make up this InputPattern
     */
    public InputPattern(String label, ArrayList<Token> tokens) {
        this(label, tokens, new ArrayList<Param>());
    }


    /**
     * @param id the id of the token to find
     * @return the token with corresponding id within the InputPattern
     */
    public Token getTokenWithId(String id) {
        for (Token token : this.tokens) {
            if (token.getId().equals(id)) {
                return token;
            }
        }
        return null;
    }

    /**
     * @param id the id of the param to find
     * @return the param with the corresponding id within the InputPattern
     */
    public Param getParamWithId(String id) {
        for (Param param : this.params) {
            if (param.getId().equals(id)) {
                return param;
            }
        }
        return null;
    }

    /**
     * @param args the entire string after the first command word
     *             This function will attempt to break args into segments and assign them
     *             to the corresponding token & param
     *             if successful, each token have their assignedSegment set
     *             and each param have their list of values set
     *             if unsuccessful, it will throw a ParseException
     *
     * @throws ParseException
     */
    public void assignSegmentsFromArgs(String args) throws ParseException {
        int tokenParamSplitPoint = args.length();

        for (Param param : params) {
            int thisSplitPoint = args.indexOf(param.getId());

            // cannot find that param
            if (thisSplitPoint == -1) {
                continue;
            }

            // choose the earliest occurrence of any param to split
            tokenParamSplitPoint = Math.min(thisSplitPoint, tokenParamSplitPoint);
        }

        String tokenArgs = args.substring(0, tokenParamSplitPoint).strip();
        String paramArgs = args.substring(tokenParamSplitPoint).strip();

        /// Settle the tokenArgs

        ArrayList<String> combinedSegments = getCombinedSegments(tokenArgs);

        if (combinedSegments.size() < this.tokens.size()) {
            throw new ParseException(MESSAGE_TOO_FEW_FIELDS);
        } else if (combinedSegments.size() > this.tokens.size()) {
            throw new ParseException(MESSAGE_TOO_MANY_FIELDS);
        }

        for (int i = 0; i < combinedSegments.size(); i++) {
            String segment = combinedSegments.get(i);
            Token token = this.tokens.get(i);

            try {
                token.matches(segment);
                token.setAssignedSegment(segment);
            } catch (IllegalValueException e) {
                throw new ParseException(e.getMessage());
            }
        }

        /// settle the paramsArgs
        String[] paramSegments = paramArgs.split(" --");
        for (int i = 0; i < paramSegments.length; i++) {
            String segment = paramSegments[i];
            if (segment.isEmpty()) {
                continue;
            }

            // since splitting by " --" removes the --, we add it back
            if (i != 0) {
                segment = "--" + segment;
            }
            segment = segment.strip();

            boolean hasMatchingSegment = false;
            for (Param param : params) {
                if (!param.idMatches(segment)) {
                    continue;
                }

                try {
                    param.matches(segment);
                    param.addValueFromSegment(segment);
                    hasMatchingSegment = true;
                    break;
                } catch (IllegalValueException e) {
                    throw new ParseException(e.getMessage());
                }
            }

            if (!hasMatchingSegment) {
                throw new ParseException("unknown parameter: " + segment);
            }
        }

        for (Param param : params) {
            param.checkIfAppropriateNumberOfValues();
        }
    }

    /**
     * Splits the input text into space seperated segments
     * Based on the input pattern, detect where to join segments together
     *
     * @param input the string as input
     * @return the list of segments that matches the token pattern
     */
    private ArrayList<String> getCombinedSegments(String input) {
        String strippedInput = input.strip();

        if (strippedInput.isEmpty()) {
            // empty ArrayList
            return new ArrayList<>();
        }

        ArrayList<String> rawSegments = new ArrayList<String>();
        rawSegments.addAll(List.of(strippedInput.split(" ")));
        if (!strippedInput.isEmpty() && strippedInput.charAt(strippedInput.length() - 1) == ' ') {
            rawSegments.add("");
        }

        ArrayList<String> combinedSegments = new ArrayList<>();

        int segmentPointer = 0;
        for (int i = 0; i < this.tokens.size(); i++) {
            if (segmentPointer == rawSegments.size()) {
                break;
            }

            Token token = this.tokens.get(i);
            if (token.allowSpaces()) {
                ArrayList<String> segmentsToJoin = new ArrayList<>();

                if (i != this.tokens.size() - 1) {
                    // case 1: there is a next token
                    // find the position and add everything in between
                    Token nextToken = this.tokens.get(i + 1);

                    while (segmentPointer < rawSegments.size()) {
                        String nextSegment = rawSegments.get(segmentPointer);

                        try {
                            if (nextToken.matches(nextSegment)) {
                                break;
                            }
                        } catch (IllegalValueException ignored) {
                            // Nothing is supposed to happen
                        }

                        segmentsToJoin.add(nextSegment);
                        segmentPointer += 1;
                    }
                } else {
                    // case 2: there is no next token
                    // just keep appending all the way to the end

                    while (segmentPointer < rawSegments.size()) {
                        String nextSegment = rawSegments.get(segmentPointer);

                        segmentsToJoin.add(nextSegment);
                        segmentPointer += 1;
                    }
                }

                String segmentToAdd = String.join(" ", segmentsToJoin);
                combinedSegments.add(segmentToAdd);
            } else {
                combinedSegments.add(rawSegments.get(segmentPointer));
                segmentPointer += 1;
            }
        }

        while (segmentPointer < rawSegments.size()) {
            combinedSegments.add(rawSegments.get(segmentPointer));
            segmentPointer += 1;
        }

        return combinedSegments;
    }
}
