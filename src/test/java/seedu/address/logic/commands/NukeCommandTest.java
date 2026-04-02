package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class NukeCommandTest {

    @TempDir
    public Path tempDir;

    @Test
    public void execute_dataDirectoryExistsEmpty_deletesDirectoryAndExits() throws Exception {
        Path tempFile = tempDir.resolve("test.jar");
        Files.createFile(tempFile);
        Path dataDirectory = tempDir.resolve("data");
        Files.createDirectory(dataDirectory);
        Path addressBookFile = dataDirectory.resolve("addressbook.json");
        Files.createFile(addressBookFile);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFile);
        Model model = new ModelManager();
        model.setUserPrefs(userPrefs);

        CommandResult result = new NukeCommand(() -> tempFile).execute(model);

        assertEquals(NukeCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(result.isExit());
        assertTrue(result.isSkipSave());
        assertFalse(Files.exists(dataDirectory));
        assertFalse(Files.exists(addressBookFile));
    }

    @Test
    public void execute_dataDirectoryExistsNonEmpty_deletesFileAndExits() throws Exception {
        Path tempFile = tempDir.resolve("test.jar");
        Files.createFile(tempFile);
        Path dataDirectory = tempDir.resolve("data");
        Files.createDirectories(dataDirectory.resolve("nested"));
        Path addressBookFile = dataDirectory.resolve("addressbook.json");
        Files.createFile(addressBookFile);
        Path tmp = dataDirectory.resolve("nested").resolve("cache.tmp");
        Files.createFile(tmp);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFile);
        Model model = new ModelManager();
        model.setUserPrefs(userPrefs);

        CommandResult result = new NukeCommand(() -> tempFile).execute(model);

        assertEquals(NukeCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(result.isExit());
        assertTrue(result.isSkipSave());
        assertTrue(Files.exists(dataDirectory));
        assertFalse(Files.exists(addressBookFile));
        assertTrue(Files.exists(tmp));
    }

    @Test
    public void execute_dataDirectoryMissing_failsAndExits() throws Exception {
        Path tempFile = tempDir.resolve("test.jar");
        Files.createFile(tempFile);
        Path addressBookFile = tempDir.resolve("data").resolve("addressbook.json");

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFile);
        Model model = new ModelManager();
        model.setUserPrefs(userPrefs);

        CommandResult result = new NukeCommand(() -> tempFile).execute(model);

        assertEquals(NukeCommand.MESSAGE_FAILURE, result.getFeedbackToUser());
        assertTrue(result.isExit());
        assertTrue(result.isSkipSave());
    }

    @Test
    void deleteJar_nullJarPath() throws Exception {
        NukeCommand command = new NukeCommand(() -> null);

        Path dataDirectory = tempDir.resolve("data");
        Files.createDirectory(dataDirectory);
        Path addressBookFile = dataDirectory.resolve("addressbook.json");
        Files.createFile(addressBookFile);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFile);
        Model model = new ModelManager();
        model.setUserPrefs(userPrefs);

        CommandResult result = command.execute(model);

        assertEquals(NukeCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertTrue(result.isExit());
        assertTrue(result.isSkipSave());
        assertFalse(Files.exists(dataDirectory));
        assertFalse(Files.exists(addressBookFile));
    }

    @Test
    void deleteJar_uriException() throws Exception {
        NukeCommand command = new NukeCommand(() -> {
            throw new URISyntaxException("bad", "simulated");
        });

        Path dataDirectory = tempDir.resolve("data");
        Files.createDirectory(dataDirectory);
        Path addressBookFile = dataDirectory.resolve("addressbook.json");
        Files.createFile(addressBookFile);

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(addressBookFile);
        Model model = new ModelManager();
        model.setUserPrefs(userPrefs);

        CommandResult result = command.execute(model);

        assertEquals(NukeCommand.MESSAGE_FAILURE, result.getFeedbackToUser());
        assertTrue(result.isExit());
        assertTrue(result.isSkipSave());
        assertFalse(Files.exists(dataDirectory));
        assertFalse(Files.exists(addressBookFile));
    }
}
