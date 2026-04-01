package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final CommandHistory commandHistory;

    private final ResultDisplayUpdater resultHistoryUpdater;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor, ResultDisplayUpdater resultDisplayUpdater) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandHistory = new CommandHistory();
        this.resultHistoryUpdater = resultDisplayUpdater;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);

        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            onUserInputUpdated(newValue);
        });
    }

    private void onUserInputUpdated(String newValue) {
        resultHistoryUpdater.updateResultDisplay(newValue);
    }

    /**
     * Handles key press events for command history navigation.
     */
    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            String prevCommand = commandHistory.navigateUp(commandTextField.getText());
            commandTextField.setText(prevCommand);
            commandTextField.positionCaret(prevCommand.length());
            resultHistoryUpdater.updateResultDisplay(prevCommand);
            event.consume();
            break;
        case DOWN:
            String nextCommand = commandHistory.navigateDown();
            commandTextField.setText(nextCommand);
            commandTextField.positionCaret(nextCommand.length());
            resultHistoryUpdater.updateResultDisplay(nextCommand);
            event.consume();
            break;
        default:
            break;
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }
        commandHistory.add(commandText);

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Represents a function that can update the help commannd
     */
    @FunctionalInterface
    public interface ResultDisplayUpdater {
        /**
         * updates the text in the result display
         */
        void updateResultDisplay(String newText);
    }
}
