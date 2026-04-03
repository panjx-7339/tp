package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand.FilterType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.predicates.EmailContainsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PhoneEqualsPredicate;
import seedu.address.model.person.predicates.TagContainsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FilterCommand.
 * Tests cover all filter types (name, phone, email, tag), their combinations, and edge cases.
 */
public class FilterCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    // ===== HELPER METHODS =====

    /**
     * Creates a FilterCommand with the given parameter filters and tag filters.
     */
    private FilterCommand createFilterCommand(Map<FilterType, List<String>> paramFilters,
                                              List<Tag> tagFilters) {
        return new FilterCommand(paramFilters, tagFilters);
    }

    /**
     * Helper to create a single-entry parameter filter map.
     */
    private Map<FilterType, List<String>> singleParamFilter(FilterType type, String... values) {
        Map<FilterType, List<String>> map = new HashMap<>();
        map.put(type, Arrays.asList(values));
        return map;
    }

    /**
     * Asserts that the filtered list matches expected persons after executing a filter command.
     */
    private void assertFilterResult(FilterCommand command, Model model, List<?> expectedPersons) {
        assertCommandSuccess(command, model, FilterCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(expectedPersons, model.getFilteredPersonList());
    }

    @Test
    public void execute_noCriteria_showsAll() {
        FilterCommand command = createFilterCommand(new HashMap<>(), Collections.emptyList());
        assertFilterResult(command, model, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    public void execute_emptyNameCriteria_showsAll() {
        Map<FilterType, List<String>> criteria = singleParamFilter(FilterType.NAME);
        FilterCommand command = createFilterCommand(criteria, Collections.emptyList());
        assertFilterResult(command, model, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    @Test
    public void execute_singleNameCriteria_filtersCorrectly() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.NAME, "Alice"),
                Collections.emptyList());

        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(List.of("Alice")));
        assertFilterResult(command, model, List.of(ALICE));
    }

    @Test
    public void execute_multipleNameCriteria_filtersMatching() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.NAME, "Alice", "Benson"),
                Collections.emptyList());

        expectedModel.updateFilteredPersonList(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Benson")));
        assertFilterResult(command, model, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_nameMatchesNoOne_emptyList() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.NAME, "Nonexistent"),
                Collections.emptyList());

        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(List.of("Nonexistent")));
        assertFilterResult(command, model, Collections.emptyList());
    }

    @Test
    public void execute_partialNameMatch_filtersMatching() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.NAME, "Meier"),
                Collections.emptyList());

        expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(List.of("Meier")));
        assertFilterResult(command, model, Arrays.asList(BENSON, DANIEL));
    }

    @Test
    public void execute_singlePhoneCriteria_filtersCorrectly() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.PHONE, "94351253"),
                Collections.emptyList());

        expectedModel.updateFilteredPersonList(new PhoneEqualsPredicate(List.of("94351253")));
        assertFilterResult(command, model, List.of(ALICE));
    }

    @Test
    public void execute_multiplePhoneCriteria_filtersMatching() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.PHONE, "94351253", "98765432"),
                Collections.emptyList());

        expectedModel.updateFilteredPersonList(new PhoneEqualsPredicate(Arrays.asList("94351253", "98765432")));
        assertFilterResult(command, model, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_phoneMatchesNoOne_emptyList() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.PHONE, "99999999"),
                Collections.emptyList());

        expectedModel.updateFilteredPersonList(new PhoneEqualsPredicate(List.of("99999999")));
        assertFilterResult(command, model, Collections.emptyList());
    }

    @Test
    public void execute_singleEmailCriteria_filtersCorrectly() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.EMAIL, "alice@example.com"),
                Collections.emptyList());

        expectedModel.updateFilteredPersonList(new EmailContainsPredicate(List.of("alice@example.com")));
        assertFilterResult(command, model, List.of(ALICE));
    }

    @Test
    public void execute_multipleEmailCriteria_filtersMatching() {
        FilterCommand command = createFilterCommand(
                singleParamFilter(FilterType.EMAIL, "alice@example.com", "johnd@example.com"),
                Collections.emptyList());

        expectedModel.updateFilteredPersonList(
                new EmailContainsPredicate(Arrays.asList("alice@example.com", "johnd@example.com")));
        assertFilterResult(command, model, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_emailMatchesNoOne_emptyList() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.EMAIL, "nonexistent@example.com"),
                Collections.emptyList());

        expectedModel.updateFilteredPersonList(new EmailContainsPredicate(List.of("nonexistent@example.com")));
        assertFilterResult(command, model, Collections.emptyList());
    }

    @Test
    public void execute_singleTagCriteria_filtersCorrectly() {
        List<Tag> tagFilters = List.of(new Tag("job:manager"));
        FilterCommand command = createFilterCommand(new HashMap<>(), tagFilters);

        expectedModel.updateFilteredPersonList(new TagContainsPredicate(tagFilters));
        assertFilterResult(command, model, Arrays.asList(BENSON, CARL));
    }

    @Test
    public void execute_multipleTagFilters_filtersMatchingAll() {
        // TagContainsPredicate uses AND logic: person must have ALL specified tags
        List<Tag> tagFilters = List.of(new Tag("status:scammed"), new Tag("job:manager"));
        FilterCommand command = createFilterCommand(new HashMap<>(), tagFilters);

        expectedModel.updateFilteredPersonList(new TagContainsPredicate(tagFilters));
        // Only BENSON has both status:scammed AND job:manager
        assertFilterResult(command, model, List.of(BENSON));
    }

    @Test
    public void execute_tagMatchesNoOne_emptyList() {
        List<Tag> tagFilters = List.of(new Tag("nonexistent:tag"));
        FilterCommand command = createFilterCommand(new HashMap<>(), tagFilters);

        expectedModel.updateFilteredPersonList(new TagContainsPredicate(tagFilters));
        assertFilterResult(command, model, Collections.emptyList());
    }

    @Test
    public void execute_statusTagCriteria_filtersCorrectly() {
        List<Tag> tagFilters = List.of(new Tag("status:scammed"));
        FilterCommand command = createFilterCommand(new HashMap<>(), tagFilters);

        expectedModel.updateFilteredPersonList(new TagContainsPredicate(tagFilters));
        assertFilterResult(command, model, List.of(BENSON));
    }

    @Test
    public void execute_nameAndPhoneCriteria_filtersByBoth() {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, List.of("Meier"));
        criteria.put(FilterType.PHONE, List.of("98765432"));
        FilterCommand command = createFilterCommand(criteria, Collections.emptyList());

        expectedModel.updateFilteredPersonList(
                new NameContainsKeywordsPredicate(List.of("Meier"))
                        .and(new PhoneEqualsPredicate(List.of("98765432"))));
        assertFilterResult(command, model, List.of(BENSON));
    }

    @Test
    public void execute_nameAndEmailCriteria_filtersByBoth() {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, List.of("Alice"));
        criteria.put(FilterType.EMAIL, List.of("alice@example.com"));
        FilterCommand command = createFilterCommand(criteria, Collections.emptyList());

        expectedModel.updateFilteredPersonList(
                new NameContainsKeywordsPredicate(List.of("Alice"))
                        .and(new EmailContainsPredicate(List.of("alice@example.com"))));
        assertFilterResult(command, model, List.of(ALICE));
    }

    @Test
    public void execute_emailAndTagCriteria_filtersByBoth() {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.EMAIL, List.of("johnd@example.com"));
        List<Tag> tagFilters = List.of(new Tag("job:manager"));
        FilterCommand command = createFilterCommand(criteria, tagFilters);

        expectedModel.updateFilteredPersonList(
                new EmailContainsPredicate(List.of("johnd@example.com"))
                        .and(new TagContainsPredicate(tagFilters)));
        assertFilterResult(command, model, List.of(BENSON));
    }

    @Test
    public void execute_namePhoneEmailTagCriteria_filtersByAll() {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, List.of("Benson"));
        criteria.put(FilterType.PHONE, List.of("98765432"));
        criteria.put(FilterType.EMAIL, List.of("johnd@example.com"));
        List<Tag> tagFilters = List.of(new Tag("job:manager"));
        FilterCommand command = createFilterCommand(criteria, tagFilters);

        expectedModel.updateFilteredPersonList(
                new NameContainsKeywordsPredicate(List.of("Benson"))
                        .and(new PhoneEqualsPredicate(List.of("98765432")))
                        .and(new EmailContainsPredicate(List.of("johnd@example.com")))
                        .and(new TagContainsPredicate(tagFilters)));
        assertFilterResult(command, model, List.of(BENSON));
    }

    @Test
    public void execute_conflictingCriteria_emptyList() {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, List.of("Alice"));
        criteria.put(FilterType.PHONE, List.of("98765432")); // Phone belongs to Benson
        FilterCommand command = createFilterCommand(criteria, Collections.emptyList());

        expectedModel.updateFilteredPersonList(
                new NameContainsKeywordsPredicate(List.of("Alice"))
                        .and(new PhoneEqualsPredicate(List.of("98765432"))));
        assertFilterResult(command, model, Collections.emptyList());
    }

    @Test
    public void execute_multipleValuesAcrossTypes_filtersCorrectly() {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, Arrays.asList("Alice", "Benson"));
        criteria.put(FilterType.EMAIL, Arrays.asList("alice@example.com", "johnd@example.com"));
        FilterCommand command = createFilterCommand(criteria, Collections.emptyList());

        expectedModel.updateFilteredPersonList(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Benson"))
                        .and(new EmailContainsPredicate(Arrays.asList("alice@example.com", "johnd@example.com"))));
        assertFilterResult(command, model, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_emptyFilteredList_selectedPersonIsNull() {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, List.of("None"));
        FilterCommand command = createFilterCommand(criteria, Collections.emptyList());

        command.execute(model);
        assertNull(model.getSelectedPerson().getValue());
    }

    @Test
    public void execute_nonEmptyFilteredList_selectsCorrectPerson() throws CommandException {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, List.of("Meier"));
        FilterCommand command = createFilterCommand(criteria, Collections.emptyList());

        command.execute(model);
        assertEquals(BENSON, model.getSelectedPerson().getValue());

    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Map<FilterType, List<String>> criteria = singleParamFilter(FilterType.NAME, "Alice");
        List<Tag> tags = Collections.emptyList();

        FilterCommand command1 = createFilterCommand(criteria, tags);
        FilterCommand command2 = createFilterCommand(criteria, tags);

        assertTrue(command1.equals(command2));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.NAME, "Alice"),
                Collections.emptyList());
        assertTrue(command.equals(command));
    }

    @Test
    public void equals_differentParameterFilters_returnsFalse() {
        FilterCommand command1 = createFilterCommand(singleParamFilter(FilterType.NAME, "Alice"),
                Collections.emptyList());
        FilterCommand command2 = createFilterCommand(singleParamFilter(FilterType.NAME, "Bob"),
                Collections.emptyList());

        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentFilterTypes_returnsFalse() {
        FilterCommand command1 = createFilterCommand(singleParamFilter(FilterType.NAME, "Alice"),
                Collections.emptyList());
        FilterCommand command2 = createFilterCommand(singleParamFilter(FilterType.PHONE, "94351253"),
                Collections.emptyList());

        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentTagFilters_returnsFalse() {
        FilterCommand command1 = createFilterCommand(new HashMap<>(),
                List.of(new Tag("job:manager")));
        FilterCommand command2 = createFilterCommand(new HashMap<>(),
                List.of(new Tag("status:called")));

        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_emptyVsNonEmpty_returnsFalse() {
        FilterCommand command1 = createFilterCommand(new HashMap<>(), Collections.emptyList());
        FilterCommand command2 = createFilterCommand(singleParamFilter(FilterType.NAME, "Alice"),
                Collections.emptyList());

        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.NAME, "Alice"),
                Collections.emptyList());
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        FilterCommand command = createFilterCommand(singleParamFilter(FilterType.NAME, "Alice"),
                Collections.emptyList());
        assertFalse(command.equals(5));
    }

    @Test
    public void toString_containsFilterInformation() {
        Map<FilterType, List<String>> criteria = singleParamFilter(FilterType.NAME, "Alice");
        List<Tag> tags = List.of(new Tag("job:manager"));
        FilterCommand command = createFilterCommand(criteria, tags);
        String result = command.toString();

        assertTrue(result.contains("FilterCommand"));
        assertTrue(result.contains("filterCriteria"));
        assertTrue(result.contains("tagFilters"));
    }
}
