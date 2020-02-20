package automation.testing.framework.journeys.Pages;

import automation.testing.framework.amazon.utils.AmazonConfigBase;
import automation.testing.framework.amazon.utils.AmazonQueueUtilities;
import automation.testing.framework.config.EnvironmentHandler;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.Message;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;

public class AmazonPage {

    Map<String, MessageAttributeValue> attributes;
    UUID uuid = uuid = UUID.randomUUID();
    AmazonQueueUtilities queueUtilities;
    private final EnvironmentHandler env;

    public AmazonPage(EnvironmentHandler env){
        this.env = env;
    }


    public void getConnection() {
        System.setProperty("aws.accessKeyId", env.valueOf("aws.accessKeyId"));
        System.setProperty("aws.secretKey", env.valueOf("aws.secretKey"));

        AmazonConfigBase sqsConfig = new AmazonConfigBase();
        AmazonConfigBase snsConfig = new AmazonConfigBase();
        snsConfig.setS3BucketName(null);
        sqsConfig.setS3BucketName(null);

        queueUtilities = new AmazonQueueUtilities(sqsConfig, snsConfig);
    }

    public void publishMessage() {

        attributes = new HashMap<>();
        attributes.put("version", new MessageAttributeValue().withDataType("String").withStringValue("v1"));
        String messageBody = "{\"id\": \"" + uuid.toString() + "\" }";
        System.out.println(messageBody);
        queueUtilities.publishMessageToTopic("topic", attributes, "test", messageBody);
    }

  public List<Message> getMessagesFromQueue() throws IOException {
    List<Message> messages =
        queueUtilities.receiveMessages(
            "queue name", queueUtilities.getS3Sqs());
    return messages;
        }

    public void assertMessageQueue() throws IOException{
        boolean consumedTheMessage = false;
        for (Message message : getMessagesFromQueue()) {

            if (message.getBody().contains(uuid.toString())) {

                System.out.println(message);

                consumedTheMessage = true;
            }
        }

        Assert.assertThat(consumedTheMessage, is(true));
    }

    public void assertMessageContent(DataTable table)throws IOException {
        for (Message message : getMessagesFromQueue())
            for (String item : table.asList()) {
                Assert.assertTrue(message.getBody().contains(item));
            }
    }
    }

