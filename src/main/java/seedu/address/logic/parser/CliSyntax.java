package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* tag id definitions */
    public static final String PARAM_ID_NAME = "--name";
    public static final String PARAM_ID_PHONE = "--phone";
    public static final String PARAM_ID_EMAIL = "--email";
    public static final String PARAM_ID_TAG = "--tag";
    public static final String PARAM_ID_TAG_ADD = "--add";
    public static final String PARAM_ID_TAG_EDIT = "--edit";
    public static final String PARAM_ID_TAG_DELETE = "--delete";
}
