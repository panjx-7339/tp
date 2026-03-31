package seedu.address.commons.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearStatusCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.IgnoreStatusCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NukeCommand;
import seedu.address.logic.commands.ScammedStatusCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TargetStatusCommand;

/**
 * A central registry of all commands supported by the application.
 *
 * <p>Each command is registered with its {@link CommandInfo}, which holds its
 * name, description and optional example. The registry preserves insertion order,
 * which determines the order commands appear in the help text.
 *
 * <p>This class is non-instantiable and should be accessed statically.
 *
 */
public class CommandRegistry {
    private static final Map<String, CommandInfo> COMMANDS = new LinkedHashMap<>();

    private CommandRegistry() {} // prevent instantiation

    static {
        register(AddCommand.COMMAND_WORD,
                "NAME [--phone PHONE] [--email EMAIL] [--tag NAME:VALUE]...",
                AddCommand.EXAMPLE);

        register(EditCommand.COMMAND_WORD,
                "INDEX [--name NAME] [--phone PHONE] [--email EMAIL]",
                EditCommand.EXAMPLE);

        register(DeleteCommand.COMMAND_WORD, "INDEX", DeleteCommand.EXAMPLE);

        register(TagCommand.COMMAND_WORD,
                "INDEX [--add NAME:VALUE]... [--edit NAME:VALUE]... [--delete TAGNAME]...",
                TagCommand.EXAMPLE);

        register(FilterCommand.COMMAND_WORD,
                "[--name NAME]... [--phone PHONE] [--email EMAIL] [--tag NAME:VALUE]...",
                FilterCommand.EXAMPLE);

        register(SortCommand.COMMAND_WORD,
                "[FIELD] [--asc|--desc] [--number|--alpha]",
                SortCommand.EXAMPLE_TWO);
        register(ClearStatusCommand.COMMAND_WORD, "INDEX", ClearStatusCommand.EXAMPLE);
        register(TargetStatusCommand.COMMAND_WORD, "INDEX", TargetStatusCommand.EXAMPLE);
        register(ScammedStatusCommand.COMMAND_WORD, "INDEX", ScammedStatusCommand.EXAMPLE);
        register(IgnoreStatusCommand.COMMAND_WORD, "INDEX", IgnoreStatusCommand.EXAMPLE);

        register(ListCommand.COMMAND_WORD, "List all contacts");
        register(ClearCommand.COMMAND_WORD, "Delete all contacts");
        register(NukeCommand.COMMAND_WORD, "Delete this app and all locally stored data");
        register(HelpCommand.COMMAND_WORD, "Show this help message");
        register(ExitCommand.COMMAND_WORD, "Exit the application");
    }

    private static void register(String name, String description, String example) {
        COMMANDS.put(name, new CommandInfo(name, description, example));
    }

    private static void register(String name, String description) {
        COMMANDS.put(name, new CommandInfo(name, description));
    }

    /**
     * Returns an unmodifiable view of the collection of commands and their {@code CommandInfo}.
     */
    public static Map<String, CommandInfo> getCommands() {
        return Collections.unmodifiableMap(COMMANDS);
    }

    /**
     * Returns the {@code CommandInfo} for a given command.
     */
    public static Optional<CommandInfo> getCommandInfo(String commandName) {
        return Optional.ofNullable(COMMANDS.get(commandName));
    }
}
