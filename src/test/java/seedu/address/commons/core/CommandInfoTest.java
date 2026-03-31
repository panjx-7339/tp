package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class CommandInfoTest {

    private static final String VALID_NAME = "add";
    private static final String VALID_DESCRIPTION = "Adds a person to the address book";
    private static final String VALID_EXAMPLE = "add n/John Doe p/98765432";

    @Test
    public void constructor_threeArguments_setsAllFields() {
        CommandInfo commandInfo = new CommandInfo(VALID_NAME, VALID_DESCRIPTION, VALID_EXAMPLE);

        assertEquals(VALID_NAME, commandInfo.getName());
        assertEquals(VALID_DESCRIPTION, commandInfo.getDescription());
        assertTrue(commandInfo.getExample().isPresent());
        assertEquals(VALID_EXAMPLE, commandInfo.getExample().get());
    }

    @Test
    public void constructor_twoArguments_setsExampleToNull() {
        CommandInfo commandInfo = new CommandInfo(VALID_NAME, VALID_DESCRIPTION);

        assertEquals(VALID_NAME, commandInfo.getName());
        assertEquals(VALID_DESCRIPTION, commandInfo.getDescription());
        assertFalse(commandInfo.getExample().isPresent());
        assertEquals(Optional.empty(), commandInfo.getExample());
    }

    @Test
    public void getName_returnsCorrectName() {
        CommandInfo commandInfo = new CommandInfo(VALID_NAME, VALID_DESCRIPTION);
        assertEquals(VALID_NAME, commandInfo.getName());
    }

    @Test
    public void getDescription_returnsCorrectDescription() {
        CommandInfo commandInfo = new CommandInfo(VALID_NAME, VALID_DESCRIPTION);
        assertEquals(VALID_DESCRIPTION, commandInfo.getDescription());
    }

    @Test
    public void getExample_returnsCorrectExample() {
        CommandInfo commandInfo = new CommandInfo(VALID_NAME, VALID_DESCRIPTION, VALID_EXAMPLE);
        assertEquals(VALID_EXAMPLE, commandInfo.getExample().orElse(""));
    }

    @Test
    public void getExample_nullExample_returnsEmptyOptional() {
        CommandInfo commandInfo = new CommandInfo(VALID_NAME, VALID_DESCRIPTION, null);
        assertTrue(commandInfo.getExample().isEmpty());
    }

    @Test
    public void toString_returnsCorrectString() {
        String resultString = new CommandInfo(VALID_NAME, VALID_DESCRIPTION, VALID_EXAMPLE).toString();
        String expectedString = "add: Adds a person to the address book\nExample: add n/John Doe p/98765432";
        assertEquals(expectedString, resultString);
    }

    @Test
    public void toString_exampleIsNull_returnsCorrectString() {
        String resultString = new CommandInfo(VALID_NAME, VALID_DESCRIPTION).toString();
        String expectedString = "add: Adds a person to the address book";
        assertEquals(expectedString, resultString);
    }
}
