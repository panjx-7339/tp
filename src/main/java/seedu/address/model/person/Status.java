package seedu.address.model.person;

import java.util.Arrays;

import javafx.scene.image.Image;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the status of a person.
 */
public enum Status {
    NONE {
        @Override
        public Image getStatusImage() {
            return null;
        }
    }, TARGET {
        @Override
        public Image getStatusImage() {
            return targetImage;
        }
    }, SCAM {
        @Override
        public Image getStatusImage() {
            return scamImage;
        }
    }, IGNORE {
        @Override
        public Image getStatusImage() {
            return ignoreImage;
        }
    };

    private static final Image targetImage = new Image(Status.class.getResourceAsStream("/images/target_status.png"));
    private static final Image scamImage = new Image(Status.class.getResourceAsStream("/images/scam_status.png"));
    private static final Image ignoreImage = new Image(Status.class.getResourceAsStream("/images/ignore_status.png"));

    /**
     * Returns whether the given string corresponds to a valid {@code Status} value.
     * Matching is case-insensitive.
     *
     * @param status The candidate status string.
     * @return {@code true} if the string maps to a status in this enum, {@code false} otherwise.
     */
    public static boolean isValidStatus(String status) {
        return Arrays.stream(values()).anyMatch(v -> v.name().equalsIgnoreCase(status));
    }

    /**
     * Parses the given string into its corresponding {@code Status} value.
     * Matching is case-insensitive.
     *
     * @param status The status string to parse.
     * @return The matching {@code Status}.
     * @throws IllegalValueException If the given string does not correspond to any valid status.
     */
    public static Status parseStatus(String status) throws IllegalValueException {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new IllegalValueException("Invalid status: " + status));
    }

    public abstract Image getStatusImage();
}
