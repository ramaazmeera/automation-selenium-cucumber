package automation.testing.framework.amazon.utils;

import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SnsMessageClassItem {
    @JsonProperty("MessageId")
    private UUID messageId;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("MessageAttributes")
    private Map<String, MessageAttributeValue> messageAttributes;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("TopicArn")
    private String topicArn;

    @JsonProperty("Subject")
    private String subject;

    public SnsMessageClassItem() {
        // for serialisation
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageAttributes(Map<String, SnsInternalAttributeItem> messageAttributes) {
        Map<String, MessageAttributeValue> convertedMap = new HashMap<>();
        for (Map.Entry<String, SnsInternalAttributeItem> item : messageAttributes.entrySet()) {
            convertedMap.put(item.getKey(), new MessageAttributeValue().withDataType(item.getValue().getType()).withStringValue(item.getValue().getValue()));
        }

        this.messageAttributes = convertedMap;
    }

    public Map<String, MessageAttributeValue> getMessageAttributes() {
        return messageAttributes;
    }

    public String getType() {
        return type;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public String getSubject() {
        return subject;
    }

    @JsonIgnore
    public SnsMessageClassItem buildMessageClassItem(String json) throws IOException {
        return new ObjectMapper().readValue(json, SnsMessageClassItem.class);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


