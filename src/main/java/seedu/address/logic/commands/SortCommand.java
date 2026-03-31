package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the current view by a specified field.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String EXAMPLE_ONE = COMMAND_WORD + "\n";
    public static final String EXAMPLE_TWO = COMMAND_WORD + " phone --desc --NUMBER";
    public static final String EXAMPLE_THREE = COMMAND_WORD + " income --alpha";

    public static final String MESSAGE_USAGE = COMMAND_WORD
         + ": Sorts current view by field.\n"
         + "Syntax: sort {NAME,phone,email,<tagName>} [{--ASC,--desc}] [{--NUMBER,--alpha}]\n"
         + "Defaults when omitted: NAME, --ASC, --NUMBER\n"
         + "Examples:\n"
         + "  " + EXAMPLE_ONE
         + "  " + EXAMPLE_TWO + "\n"
         + "  " + EXAMPLE_THREE;

    /**
     * Defines the possible fields to sort by and the sort order.
     */
    public enum SortTargetType { NAME, PHONE, EMAIL, TAG }

    /**
     * Defines the possible sort orders.
     */
    public enum SortOrder { ASC, DESC }

    /**
     * Defines the possible sort modes for string fields.
     */
    public enum SortMode { NUMBER, ALPHA }

    /**
     * Encapsulates the parameters for sorting.
     */
    public static class SortSpec {
        public final SortTargetType targetType;
        public final String tagName;
        public final SortOrder order;
        public final SortMode mode;

        /**
         * Constructs a SortSpec object.
         *
         * @param targetType The type of field to sort by (NAME, PHONE, EMAIL, or TAG).
         * @param tagName The name of the tag to sort by if targetType is TAG; otherwise null.
         * @param order The sort order (ASC or DESC).
         * @param mode The sort mode (NUMBER or ALPHA).
         */
        public SortSpec(SortTargetType targetType, String tagName, SortOrder order, SortMode mode) {
            this.targetType = targetType;
            this.tagName = tagName;
            this.order = order;
            this.mode = mode;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof SortSpec)) {
                return false;
            }
            SortSpec otherSpec = (SortSpec) other;
            return targetType == otherSpec.targetType
                    && Objects.equals(tagName, otherSpec.tagName)
                    && order == otherSpec.order
                    && mode == otherSpec.mode;
        }

        @Override
        public int hashCode() {
            return Objects.hash(targetType, tagName, order, mode);
        }
    }

    private final SortSpec spec;

    /**
     * Creates a SortCommand to sort by the specified field.
     *
     * @param spec The specification of how to sort, including target field, order, and mode.
     */
    public SortCommand(SortSpec spec) {
        requireNonNull(spec);
        this.spec = spec;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Comparator<Person> comparator = buildComparator(spec);

        model.updateSortedPersonList(comparator);
        if (!model.isFilteredViewActive()) {
            model.sortMasterPersonList(comparator);
        }

        return new CommandResult(String.format("Sorted %d person(s).", model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return spec.equals(otherCommand.spec);
    }

    private Comparator<Person> buildComparator(SortSpec sortSpec) {
        Comparator<Person> coreComparator;

        switch (sortSpec.targetType) {
        case NAME:
            coreComparator = buildStringComparator(person -> person.getName().fullName, sortSpec.mode,
                    sortSpec.order);
            break;
        case PHONE:
            coreComparator = buildStringComparator(person -> person.hasPhone() ? person.getPhone().value : null,
                    sortSpec.mode, sortSpec.order);
            break;
        case EMAIL:
            coreComparator = buildStringComparator(person -> person.hasEmail() ? person.getEmail().value : null,
                    sortSpec.mode, sortSpec.order);
            break;
        case TAG:
            coreComparator = buildStringComparator(person -> getTagValue(person, sortSpec.tagName), sortSpec.mode,
                    sortSpec.order);
            break;
        default:
            throw new IllegalStateException("Unsupported sort target: " + sortSpec.targetType);
        }

        Comparator<Person> withTieBreaker = coreComparator.thenComparing(
                person -> person.getName().fullName,
                sortSpec.order == SortOrder.DESC
                        ? String.CASE_INSENSITIVE_ORDER.reversed()
                        : String.CASE_INSENSITIVE_ORDER
        );

        return withTieBreaker;
    }

    private Comparator<Person> buildStringComparator(ValueExtractor extractor, SortMode mode, SortOrder order) {
        return (left, right) -> {
            String leftValue = extractor.extract(left);
            String rightValue = extractor.extract(right);

            // Nulls always go last, regardless of sort order
            if (leftValue == null && rightValue == null) {
                return 0;
            }
            if (leftValue == null) {
                return 1;
            }
            if (rightValue == null) {
                return -1;
            }

            int result;
            if (mode == SortMode.NUMBER) {
                Long leftNumber = parseLongOrNull(leftValue);
                Long rightNumber = parseLongOrNull(rightValue);

                if (leftNumber != null && rightNumber != null) {
                    result = Long.compare(leftNumber, rightNumber);
                    return order == SortOrder.DESC ? -result : result;
                }
                if (leftNumber != null) {
                    result = -1;
                    return order == SortOrder.DESC ? -result : result;
                }
                if (rightNumber != null) {
                    result = 1;
                    return order == SortOrder.DESC ? -result : result;
                }
            }

            result = leftValue.compareToIgnoreCase(rightValue);
            return order == SortOrder.DESC ? -result : result;
        };
    }

    private String getTagValue(Person person, String tagName) {
        return person.getTags().filterTagCaseInsensitive(tagName).orElse(null);
    }

    private Long parseLongOrNull(String value) {
        String trimmedValue = value.trim();
        if (!trimmedValue.matches("[-+]?\\d+")) {
            return null;
        }

        try {
            return Long.parseLong(trimmedValue);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @FunctionalInterface
    private interface ValueExtractor {
        String extract(Person person);
    }

}
