package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterCommand.FilterType;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagFilter;

/**
 * Contains unit tests for FilterCommandParser.
 * Tests cover single criteria, multiple criteria, and combinations of different filter types.
 */
public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    // ===== HELPER METHODS =====

    /**
     * Creates an expected FilterCommand with parameter filters and tag filters.
     */
    private FilterCommand createExpectedFilterCommand(Map<FilterType, List<String>> paramFilters,
                                                      List<TagFilter> tagFilters) {
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

    @Test
    public void parse_noCriteria_returnsFilterCommandWithEmptyMaps() throws Exception {
        Map<FilterType, List<String>> expectedCriteria = new HashMap<>();
        FilterCommand expected = createExpectedFilterCommand(expectedCriteria, Collections.emptyList());
        assertParseSuccess(parser, "", expected);
    }

    @Test
    public void parse_emptyString_returnsFilterCommandWithEmptyMaps() throws Exception {
        Map<FilterType, List<String>> expectedCriteria = new HashMap<>();
        FilterCommand expected = createExpectedFilterCommand(expectedCriteria, Collections.emptyList());
        assertParseSuccess(parser, "  ", expected);
    }

    @Test
    public void parse_singleNameCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(singleParamFilter(FilterType.NAME, "Alice"),
                Collections.emptyList());
        assertParseSuccess(parser, " --name Alice", expected);
    }

    @Test
    public void parse_multipleNameCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(
                singleParamFilter(FilterType.NAME, "Alice", "Benson"),
                Collections.emptyList());
        assertParseSuccess(parser, " --name Alice --name Benson", expected);
    }

    @Test
    public void parse_nameWithMultipleWords_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(singleParamFilter(FilterType.NAME, "Alice Pauline"),
                Collections.emptyList());
        assertParseSuccess(parser, " --name Alice Pauline", expected);
    }

    @Test
    public void parse_singlePhoneCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(singleParamFilter(FilterType.PHONE, "94351253"),
                Collections.emptyList());
        assertParseSuccess(parser, " --phone 94351253", expected);
    }

    @Test
    public void parse_multiplePhoneCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(
                singleParamFilter(FilterType.PHONE, "94351253", "98765432"),
                Collections.emptyList());
        assertParseSuccess(parser, " --phone 94351253 --phone 98765432", expected);
    }

    @Test
    public void parse_singleEmailCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(singleParamFilter(FilterType.EMAIL, "alice@example.com"),
                Collections.emptyList());
        assertParseSuccess(parser, " --email alice@example.com", expected);
    }

    @Test
    public void parse_partialPhoneCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(singleParamFilter(FilterType.PHONE, "3512"),
                Collections.emptyList());
        assertParseSuccess(parser, " --phone 3512", expected);
    }

    @Test
    public void parse_partialEmailCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(singleParamFilter(FilterType.EMAIL, "@example"),
                Collections.emptyList());
        assertParseSuccess(parser, " --email @example", expected);
    }

    @Test
    public void parse_singleStatusCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(singleParamFilter(FilterType.STATUS, "TARGET"),
                Collections.emptyList());
        assertParseSuccess(parser, " --status TARGET", expected);
    }

    @Test
    public void parse_multipleStatusCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(
                singleParamFilter(FilterType.STATUS, "TARGET", "IGNORE"),
                Collections.emptyList());
        assertParseSuccess(parser, " --status TARGET --status IGNORE", expected);
    }

    @Test
    public void parse_multipleEmailCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(
                singleParamFilter(FilterType.EMAIL, "alice@example.com", "benson@example.com"),
                Collections.emptyList());
        assertParseSuccess(parser, " --email alice@example.com --email benson@example.com", expected);
    }

    @Test
    public void parse_singleTagCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(new HashMap<>(),
                List.of(new TagFilter("job:manager")));
        assertParseSuccess(parser, " --tag job:manager", expected);
    }

    @Test
    public void parse_multipleTagCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(new HashMap<>(),
                Arrays.asList(new TagFilter("job:manager"), new TagFilter("rich:yes")));
        assertParseSuccess(parser, " --tag job:manager --tag rich:yes", expected);
    }

    @Test
    public void parse_tagWithColonDelimiter_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(new HashMap<>(),
                List.of(new TagFilter("income:$100,000")));
        assertParseSuccess(parser, " --tag income:$100,000", expected);
    }

    @Test
    public void parse_tagNameOnly_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(new HashMap<>(),
                List.of(new TagFilter("job")));
        assertParseSuccess(parser, " --tag job", expected);
    }

    @Test
    public void parse_mixedTagCriteria_returnsFilterCommand() throws Exception {
        FilterCommand expected = createExpectedFilterCommand(new HashMap<>(),
                Arrays.asList(new TagFilter("job"), new TagFilter("rich:yes")));
        assertParseSuccess(parser, " --tag job --tag rich:yes", expected);
    }

    @Test
    public void parse_nameAndPhoneCriteria_returnsFilterCommand() throws Exception {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, List.of("Bob"));
        criteria.put(FilterType.PHONE, List.of("98765432"));
        FilterCommand expected = createExpectedFilterCommand(criteria, Collections.emptyList());
        assertParseSuccess(parser, " --name Bob --phone 98765432", expected);
    }

    @Test
    public void parse_namePhoneEmailCriteria_returnsFilterCommand() throws Exception {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, List.of("Alice"));
        criteria.put(FilterType.PHONE, List.of("94351253"));
        criteria.put(FilterType.EMAIL, List.of("alice@example.com"));
        FilterCommand expected = createExpectedFilterCommand(criteria, Collections.emptyList());
        assertParseSuccess(parser, " --name Alice --phone 94351253 --email alice@example.com", expected);
    }

    @Test
    public void parse_nameAndStatusCriteria_returnsFilterCommand() throws Exception {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, List.of("Alice"));
        criteria.put(FilterType.STATUS, List.of("TARGET"));
        FilterCommand expected = createExpectedFilterCommand(criteria, Collections.emptyList());
        assertParseSuccess(parser, " --name Alice --status TARGET", expected);
    }

    @Test
    public void parse_nameAndTagCriteria_returnsFilterCommand() throws Exception {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, List.of("Benson"));
        List<TagFilter> tags = List.of(new TagFilter("job:manager"));
        FilterCommand expected = createExpectedFilterCommand(criteria, tags);
        assertParseSuccess(parser, " --name Benson --tag job:manager", expected);
    }

    @Test
    public void parse_phoneEmailTagCriteria_returnsFilterCommand() throws Exception {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.PHONE, List.of("98765432"));
        criteria.put(FilterType.EMAIL, List.of("johnd@example.com"));
        List<TagFilter> tags = List.of(new TagFilter("rich:yes"));
        FilterCommand expected = createExpectedFilterCommand(criteria, tags);
        assertParseSuccess(parser, " --phone 98765432 --email johnd@example.com --tag rich:yes", expected);
    }

    @Test
    public void parse_multipleValuesMultipleCriteria_returnsFilterCommand() throws Exception {
        Map<FilterType, List<String>> criteria = new HashMap<>();
        criteria.put(FilterType.NAME, Arrays.asList("Alice", "Benson"));
        criteria.put(FilterType.PHONE, Arrays.asList("94351253", "98765432"));
        criteria.put(FilterType.EMAIL, Arrays.asList("alice@example.com", "johnd@example.com"));
        criteria.put(FilterType.STATUS, Arrays.asList("TARGET", "SCAM"));
        List<TagFilter> tags = Arrays.asList(new TagFilter("job:manager"), new TagFilter("rich:yes"));
        FilterCommand expected = createExpectedFilterCommand(criteria, tags);
        assertParseSuccess(parser,
                " --name Alice --name Benson"
                        + " --phone 94351253 --phone 98765432"
                        + " --email alice@example.com --email johnd@example.com"
                        + " --status TARGET --status SCAM"
                        + " --tag job:manager --tag rich:yes",
                expected);
    }

    @Test
    public void parse_allFilterTypesTagsOnly_returnsFilterCommand() throws Exception {
        List<TagFilter> tags = Arrays.asList(new TagFilter("job:manager"), new TagFilter("income:$100,000"));
        FilterCommand expected = createExpectedFilterCommand(new HashMap<>(), tags);
        assertParseSuccess(parser, " --tag job:manager --tag income:$100,000", expected);
    }

    @Test
    public void parse_invalidTagNameOnly_throwsParseException() {
        assertParseFailure(parser, " --tag name", Tag.ILLEGAL_NAME_CONSTRAINTS);
    }
}
