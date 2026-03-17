package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes local data artifacts and exits the program.
 */
public class NukeCommand extends Command {

    public static final String COMMAND_WORD = "nuke";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the data folder and application jar,"
            + "then exits.\nExample: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Nuked. Exiting...";
    public static final String MESSAGE_FAILURE = "Unable to nuke. An error occurred: %s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            deleteDataDirectory(model.getAddressBookFilePath());
            deleteJar();
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()), e);
        }

        return new CommandResult(MESSAGE_SUCCESS, false, true, true);
    }

    private void deleteDataDirectory(Path addressBookFilePath) throws IOException {
        if (addressBookFilePath == null) {
            return;
        }

        Path parent = addressBookFilePath.getParent();
        Path dataDirectory = parent.toAbsolutePath().normalize();
        if (!Files.exists(dataDirectory)) {
            return;
        }

        deleteRecursively(dataDirectory);
    }

    private void deleteJar() throws IOException {
        Path jarPath = resolveCurrentJarPath();
        if (jarPath == null || !Files.exists(jarPath) || !Files.isRegularFile(jarPath)) {
            return;
        }

        Files.deleteIfExists(jarPath);
    }

    private Path resolveCurrentJarPath() {
        try {
            return Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private void deleteRecursively(Path directory) throws IOException {
        try (Stream<Path> walk = Files.walk(directory)) {
            walk.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }
}
