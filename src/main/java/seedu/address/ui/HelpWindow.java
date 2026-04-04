package seedu.address.ui;

import java.util.Map;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.CommandInfo;
import seedu.address.commons.core.CommandRegistry;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s2-cs2103t-t16-1.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;
    public static final String HELP_TEXT = buildHelpText();

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";
    private static final double DEFAULT_WIDTH = 960;
    private static final double DEFAULT_HEIGHT = 670;

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private TextArea helpText;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        helpText.setText(HELP_TEXT);
        root.setWidth(DEFAULT_WIDTH);
        root.setHeight(DEFAULT_HEIGHT);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Builds the help text displaying information about how to use the application's commands.
     * @return help text to be shown in the help window
     */
    private static String buildHelpText() {
        int nameColumnWidth = CommandRegistry.getCommands().keySet().stream()
                .mapToInt(String::length)
                .max()
                .orElse(0) + 3; // padding between command name and description
        String commandIndentStr = " ".repeat(2);
        String exampleIndentStr = " ".repeat(4);

        StringBuilder sb = new StringBuilder();
        sb.append("ScamBook\n\n");
        sb.append("Usage: <COMMAND> <PARAMETERS>\n\n");
        sb.append("Commands:\n");

        for (Map.Entry<String, CommandInfo> mapElement : CommandRegistry.getCommands().entrySet()) {
            String commandName = mapElement.getKey();
            CommandInfo commandInfo = mapElement.getValue();
            sb.append(String.format("%s%-" + nameColumnWidth + "s%s%n",
                    commandIndentStr, commandName, commandInfo.getDescription()));
            commandInfo.getExample().ifPresent(example ->
                    sb.append(String.format("%s%" + nameColumnWidth + "se.g. %s%n",
                            exampleIndentStr, "", example))
            );
        }

        // Notes about how to read the help text
        sb.append("""

            Notes:
              Parameters in UPPER_CASE are user-supplied values.
              Parameters in [brackets] are optional.
              Parameters with ... can be repeated multiple times.
            """);
        return sb.toString();
    }
}
