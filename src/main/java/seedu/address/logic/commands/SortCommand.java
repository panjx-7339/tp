package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
         + ": Sorts current view by field.\n"
         + "Syntax: sort {NAME,phone,email,<tagName>} [{--ASC,--desc}] [{--NUMBER,--alpha}]\n"
         + "Defaults when omitted: NAME, --ASC, --NUMBER\n"
         + "Examples:\n"
         + "  " + COMMAND_WORD + "\n"
         + "  " + COMMAND_WORD + " phone --desc --NUMBER\n"
         + "  " + COMMAND_WORD + " income --alpha";

    public enum SortTargetType { NAME, PHONE, EMAIL, TAG }
    public enum SortOrder { ASC, DESC }
    public enum SortMode { NUMBER, ALPHA }

    public static class SortSpec {
        public final SortTargetType targetType;
        public final String tagName;
        public final SortOrder order;
        public final SortMode mode;

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
            coreComparator = buildStringComparator(person -> person.getName().fullName, sortSpec.mode);
            break;
        case PHONE:
            coreComparator = buildStringComparator(person -> person.hasPhone() ? person.getPhone().value : null,
                    sortSpec.mode);
            break;
        case EMAIL:
            coreComparator = buildStringComparator(person -> person.hasEmail() ? person.getEmail().value : null,
                    sortSpec.mode);
            break;
        case TAG:
            coreComparator = buildStringComparator(person -> getTagValue(person, sortSpec.tagName), sortSpec.mode);
            break;
        default:
            throw new IllegalStateException("Unsupported sort target: " + sortSpec.targetType);
        }

        Comparator<Person> withTieBreaker = coreComparator.thenComparing(
                person -> person.getName().fullName,
                String.CASE_INSENSITIVE_ORDER
        );

        return sortSpec.order == SortOrder.DESC ? withTieBreaker.reversed() : withTieBreaker;
    }

    private Comparator<Person> buildStringComparator(ValueExtractor extractor, SortMode mode) {
        return (left, right) -> {
            String leftValue = extractor.extract(left);
            String rightValue = extractor.extract(right);

            if (leftValue == null && rightValue == null) {
                return 0;
            }
            if (leftValue == null) {
                return 1;
            }
            if (rightValue == null) {
                return -1;
            }

            if (mode == SortMode.NUMBER) {
                Long leftNumber = parseLongOrNull(leftValue);
                Long rightNumber = parseLongOrNull(rightValue);

                if (leftNumber != null && rightNumber != null) {
                    return Long.compare(leftNumber, rightNumber);
                }
                if (leftNumber != null) {
                    return -1;
                }
                if (rightNumber != null) {
                    return 1;
                }
            }

            return leftValue.compareToIgnoreCase(rightValue);
        };
    }

    private String getTagValue(Person person, String tagName) {
        Optional<Tag> matchingTag = person.getTags().stream()
                .filter(tag -> tag.tagName.equalsIgnoreCase(tagName))
                .findFirst();
        return matchingTag.map(tag -> tag.tagValue).orElse(null);
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
