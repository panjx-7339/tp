package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;

public class CommandRegistryTest {

    @Test
    public void getCommands_preservesInsertionOrder() {
        Map<String, CommandInfo> commands = CommandRegistry.getCommands();
        String firstKey = commands.keySet().iterator().next();
        assertEquals(AddCommand.COMMAND_WORD, firstKey); // add is registered first
    }

    @Test
    public void getCommands_modifyAttempt_throwsUnsupportedOperationException() {
        Map<String, CommandInfo> commands = CommandRegistry.getCommands();

        assertThrows(UnsupportedOperationException.class, () ->
                commands.put("malicious", new CommandInfo("name", "desc"))
        );

        assertThrows(UnsupportedOperationException.class, () ->
                commands.clear()
        );
    }

    @Test
    public void getCommands_containsExpectedCommands() {
        Map<String, CommandInfo> commands = CommandRegistry.getCommands();
        assertEquals(15, commands.size());
    }

    @Test
    public void getCommandInfo_validCommandName_returnsCommandInfo() {
        Optional<CommandInfo> info = CommandRegistry.getCommandInfo(AddCommand.COMMAND_WORD);
        assertTrue(info.isPresent());
    }

    @Test
    public void getCommandInfo_invalidCommandName_returnsEmptyOptional() {
        Optional<CommandInfo> info = CommandRegistry.getCommandInfo("nonExistentCommand");
        assertTrue(info.isEmpty());
    }

    @Test
    public void getCommandInfo_wrongCase_returnsEmptyOptional() {
        Optional<CommandInfo> info = CommandRegistry.getCommandInfo(AddCommand.COMMAND_WORD.toUpperCase());
        assertTrue(info.isEmpty());
    }
}
