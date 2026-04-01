package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.CommandInfo;
import seedu.address.commons.core.CommandRegistry;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    public void setFormatTooltipFromPartialCommand(String partialCommand) {

        partialCommand = partialCommand.stripLeading();
        String[] segments = partialCommand.split(" ");

        if (partialCommand.isEmpty()) {
            return;
        }
        if (segments.length == 0) {
            return;
        }

        String commandWord = segments[0];
        Optional<CommandInfo> commandInfo = CommandRegistry.getCommandInfo(commandWord);

        if (commandInfo.isPresent()) {
            // if command found
            String line1 = "Format:";
            String line2 = commandWord + " " + commandInfo.get().getDescription();
            setFeedbackToUser(line1 + "\n" + line2);
        } else {
            if (partialCommand.contains(" ")) {
                setFeedbackToUser("Warning:\n" + commandWord + " is not a valid command!");
            }
            // else do nothing as the command is not fully inputted anw
        }

    }
}
