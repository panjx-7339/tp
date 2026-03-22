package seedu.address.model.person;

/**
 * Represents the status of a person.
 */
public enum Status {
    NONE {
        @Override
        public String getStatusRepresentation() {
            return "";
        }
    }, TARGET {
        @Override
        public String getStatusRepresentation() {
            return "🎯";
        }
    }, SCAMMED {
        @Override
        public String getStatusRepresentation() {
            return "✅";
        }
    }, IGNORE {
        @Override
        public String getStatusRepresentation() {
            return "❌";
        }
    };

    public abstract String getStatusRepresentation();
}
