package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView statusImage;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        statusImage.setImage(person.getStatus().getStatusImage());

        if (person.hasPhone()) {
            phone.setText("phone: " + person.getPhone().value);
        } else {
            phone.setText("phone: ");
        }

        if (person.hasEmail()) {
            email.setText("email: " + person.getEmail().value);
        } else {
            email.setText("email: ");
        }

        List<String> tagList = person.getPrintableTags();

        if (!tagList.isEmpty()) {
            tags.setVisible(true);
            tagList.forEach(tag -> {
                Label tagLabel = new Label(tag);
                tags.getChildren().add(tagLabel);
            });
        }
    }
}
