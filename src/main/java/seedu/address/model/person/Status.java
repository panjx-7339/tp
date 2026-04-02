package seedu.address.model.person;

import javafx.scene.image.Image;

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

    public abstract Image getStatusImage();
}
