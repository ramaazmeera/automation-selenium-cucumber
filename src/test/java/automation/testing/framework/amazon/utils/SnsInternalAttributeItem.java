package automation.testing.framework.amazon.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SnsInternalAttributeItem {
    @JsonProperty("Type")
    private String type;

    @JsonProperty("Value")
    private String value;

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
