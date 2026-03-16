package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private final String tag;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(String tag) {
        this.tag = tag;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {
        tag = source.tagName + ":" + source.tagValue;
    }

    @JsonValue
    public String getTag() {
        return tag;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        String[] parts = tag.split(":", 2);
        if (parts.length < 2) {
            throw new IllegalValueException(Tag.FORMAT_MESSAGE_CONSTRAINTS);
        }
        String tagName = parts[0].trim();
        String tagValue = parts[1].trim();

        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalValueException(Tag.NAME_MESSAGE_CONSTRAINTS);
        }

        if (!Tag.isValidTagValue(tagValue)) {
            throw new IllegalValueException(Tag.VALUE_MESSAGE_CONSTRAINTS);
        }

        return new Tag(tagName, tagValue);
    }

}
