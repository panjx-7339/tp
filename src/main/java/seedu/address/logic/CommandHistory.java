package seedu.address.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores command history and allows navigation through past commands.
 * Similar to bash shell history navigation with Up/Down arrow keys.
 */
public class CommandHistory {
    private final List<String> history = new ArrayList<>();
    private int pointer = 0;
    private String currentInput = "";

    /**
     * Adds a successfully executed command to history.
     * Resets the pointer to the end of history.
     */
    public void add(String command) {
        if (command == null || command.trim().isEmpty()) {
            return;
        }
        history.add(command);
        resetPointer();
    }

    /**
     * Navigates to the previous (older) command in history.
     * Saves the current input if at the end of history (so user can return to it).
     *
     * @param currentText The current text in the command box
     * @return The previous command, or currentText if no history exists
     */
    public String navigateUp(String currentText) {
        if (history.isEmpty()) {
            return currentText;
        }

        // Save current input when starting to navigate, then return last command
        if (pointer == history.size()) {
            currentInput = currentText;
            pointer--;
            return history.get(pointer);
        }

        if (pointer > 0) {
            pointer--;
        }

        return history.get(pointer);
    }

    /**
     * Navigates to the next (newer) command in history.
     *
     * @return The next command, or the saved current input if at the end
     */
    public String navigateDown() {
        if (history.isEmpty() || pointer >= history.size()) {
            return currentInput;
        }

        pointer++;

        if (pointer >= history.size()) {
            return currentInput;
        }

        return history.get(pointer);
    }

    /**
     * Resets the pointer to the end of history.
     * Called after a command is executed.
     */
    public void resetPointer() {
        pointer = history.size();
        currentInput = "";
    }
}
