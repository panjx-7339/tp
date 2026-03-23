package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    private CommandHistory history;

    @BeforeEach
    public void setUp() {
        history = new CommandHistory();
    }

    @Test
    public void add_nullCommand_notAdded() {
        history.add(null);
        // navigateUp should return empty string (currentText) since history is empty
        assertEquals("", history.navigateUp(""));
    }

    @Test
    public void add_emptyCommand_notAdded() {
        history.add("");
        history.add("   ");
        assertEquals("test", history.navigateUp("test"));
    }

    @Test
    public void add_validCommand_addedToHistory() {
        history.add("list");
        assertEquals("list", history.navigateUp(""));
    }

    @Test
    public void navigateUp_emptyHistory_returnsCurrentText() {
        assertEquals("current", history.navigateUp("current"));
    }

    @Test
    public void navigateUp_singleCommand_returnsThatCommand() {
        history.add("add n/John");
        assertEquals("add n/John", history.navigateUp(""));
    }

    @Test
    public void navigateUp_multipleCommands_navigatesBackward() {
        history.add("first");
        history.add("second");
        history.add("third");

        assertEquals("third", history.navigateUp(""));
        assertEquals("second", history.navigateUp(""));
        assertEquals("first", history.navigateUp(""));
        // Should stay at first command
        assertEquals("first", history.navigateUp(""));
    }

    @Test
    public void navigateUp_savesCurrentInput() {
        history.add("list");
        history.add("help");

        // Start navigating with some typed input
        history.navigateUp("partial input");

        // Navigate back down to get the saved input
        assertEquals("partial input", history.navigateDown());
    }

    @Test
    public void navigateDown_emptyHistory_returnsCurrentInput() {
        assertEquals("", history.navigateDown());
    }

    @Test
    public void navigateDown_atEndOfHistory_returnsCurrentInput() {
        history.add("command");
        // Don't navigate up first
        assertEquals("", history.navigateDown());
    }

    @Test
    public void navigateDown_afterNavigatingUp_returnsNextCommand() {
        history.add("first");
        history.add("second");
        history.add("third");

        // Navigate to first
        history.navigateUp(""); // third
        history.navigateUp(""); // second
        history.navigateUp(""); // first

        // Navigate back down
        assertEquals("second", history.navigateDown());
        assertEquals("third", history.navigateDown());
        assertEquals("", history.navigateDown()); // Back to current input
    }

    @Test
    public void navigateUpAndDown_mixedNavigation_correctBehavior() {
        history.add("cmd1");
        history.add("cmd2");
        history.add("cmd3");

        assertEquals("cmd3", history.navigateUp("typing")); // First UP gets last command
        assertEquals("cmd2", history.navigateUp(""));
        assertEquals("cmd3", history.navigateDown());
        assertEquals("cmd2", history.navigateUp(""));
        assertEquals("cmd1", history.navigateUp(""));
        assertEquals("cmd2", history.navigateDown());
        assertEquals("cmd3", history.navigateDown());
        assertEquals("typing", history.navigateDown()); // Returns saved input
    }

    @Test
    public void resetPointer_afterNavigation_resetsToEnd() {
        history.add("first");
        history.add("second");

        history.navigateUp("");
        history.navigateUp("");
        history.resetPointer();

        // After reset, navigateUp should return the last command
        assertEquals("second", history.navigateUp(""));
    }

    @Test
    public void add_afterNavigation_resetsPointer() {
        history.add("first");
        history.add("second");

        history.navigateUp("");
        history.navigateUp("");

        // Adding new command should reset pointer
        history.add("third");

        assertEquals("third", history.navigateUp(""));
    }
}
