package seedu.address.commons.core;

import java.util.Optional;

/**
 * Stores information about a command.
 */
public class CommandInfo {

    private final String name;
    private final String description;
    private final String example;

    /**
     * Creates a CommandInfo with the command name, description and example.
     */
    public CommandInfo(String name, String description, String example) {
        this.name = name;
        this.description = description;
        this.example = example;
    }

    /**
     * Creates a CommandInfo with the command name and description.
     */
    public CommandInfo(String name, String description) {
        this(name, description, null);
    }

    /**
     * Returns the command name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the command description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the command example.
     */
    public Optional<String> getExample() {
        return Optional.ofNullable(example);
    }

    @Override
    public String toString() {
        return name + ": " + description + (example == null ? "" : "\nExample: " + example);
    }
}
