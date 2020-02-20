package automation.testing.framework.amazon.utils;

import automation.testing.framework.amazon.sns.extended.AmazonSNSExtendedClient;
import automation.testing.framework.amazon.sns.extended.LargeMessageUtils;
import automation.testing.framework.amazon.sns.extended.MessageS3Pointer;
import automation.testing.framework.amazon.sns.utils.SNSExtendedClientConstants;
import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.*;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmazonQueueUtilities {
    private static final Logger LOG = LoggerFactory.getLogger(AmazonQueueUtilities.class.getName());
    private static final String OPTIONAL_PARAMETER_LOG = "setting optional replyTo header {}";
    private AmazonS3 s3Sns;
    private AmazonS3 s3Sqs;
    private AmazonSNS sns;
    private AmazonSQS sqs;

    public AmazonQueueUtilities(AmazonConfigBase amazonSqsConfig, AmazonConfigBase amazonSnsConfig) {

        if (amazonSqsConfig.getS3EndpointOverride() != null) {
            s3Sqs = AmazonS3ClientBuilder
                    .standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonSqsConfig.getS3EndpointOverride(), amazonSqsConfig.getRegion().getName()))
                    .withPathStyleAccessEnabled(amazonSqsConfig.isPathStyleAccessEnabled())
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .build();
        } else {
            s3Sqs = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .withPathStyleAccessEnabled(amazonSqsConfig.isPathStyleAccessEnabled())
                    .withRegion(amazonSqsConfig.getRegion())
                    .build();
        }

        if (amazonSnsConfig.getS3EndpointOverride() != null) {
            s3Sns = AmazonS3ClientBuilder
                    .standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonSnsConfig.getS3EndpointOverride(), amazonSnsConfig.getRegion().getName()))
                    .withPathStyleAccessEnabled(amazonSnsConfig.isPathStyleAccessEnabled())
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .build();
        } else {
            s3Sns = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .withPathStyleAccessEnabled(amazonSnsConfig.isPathStyleAccessEnabled())
                    .withRegion(amazonSnsConfig.getRegion())
                    .build();
        }

        if (amazonSqsConfig.getS3BucketName() != null) {
            getS3Sqs().createBucket(amazonSqsConfig.getS3BucketName());
        }
        if (amazonSnsConfig.getS3BucketName() != null) {
            getS3Sns().createBucket(amazonSnsConfig.getS3BucketName());
        }

        buildSQSObject(getS3Sqs(), amazonSqsConfig);
        buildSNSObject(getS3Sns(), amazonSnsConfig);
    }

    private void buildSQSObject(AmazonS3 amazonS3, AmazonConfigBase amazonSqsConfig) {
        ExtendedClientConfiguration extendedClientConfigurationSQS = new ExtendedClientConfiguration();
        extendedClientConfigurationSQS.setAlwaysThroughS3(amazonSqsConfig.isAlwaysThroughS3());

        if (amazonSqsConfig.isLargePayloadSupportEnabled()) {
            extendedClientConfigurationSQS.setLargePayloadSupportEnabled(amazonS3, amazonSqsConfig.getS3BucketName());
        } else {
            extendedClientConfigurationSQS.setLargePayloadSupportDisabled();
        }

        AmazonSQS localSqs;
        if (amazonSqsConfig.getEndpointOverride() != null) {
            localSqs = AmazonSQSClientBuilder
                    .standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonSqsConfig.getEndpointOverride(), amazonSqsConfig.getRegion().getName()))
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .build();

        } else {
            localSqs = AmazonSQSClientBuilder
                    .standard()
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .withRegion(amazonSqsConfig.getRegion())
                    .build();
        }

        sqs = new AmazonSQSExtendedClient(localSqs, extendedClientConfigurationSQS);
    }

    private void buildSNSObject(AmazonS3 amazonS3, AmazonConfigBase amazonSnsConfig) {
        ExtendedClientConfiguration extendedClientConfigurationSNS = new ExtendedClientConfiguration();
        extendedClientConfigurationSNS.setAlwaysThroughS3(amazonSnsConfig.isAlwaysThroughS3());

        if (amazonSnsConfig.isLargePayloadSupportEnabled()) {
            extendedClientConfigurationSNS.setLargePayloadSupportEnabled(amazonS3, amazonSnsConfig.getS3BucketName());
        } else {
            extendedClientConfigurationSNS.setLargePayloadSupportDisabled();
        }

        AmazonSNS localSns;
        if (amazonSnsConfig.getEndpointOverride() != null) {
            localSns = AmazonSNSClientBuilder
                    .standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonSnsConfig.getEndpointOverride(), amazonSnsConfig.getRegion().getName()))
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .build();

        } else {
            localSns = AmazonSNSClientBuilder
                    .standard()
                    .withCredentials(new DefaultAWSCredentialsProviderChain())
                    .withRegion(amazonSnsConfig.getRegion())
                    .build();
        }

        sns = new AmazonSNSExtendedClient(localSns, extendedClientConfigurationSNS);
    }

    public boolean createQueue(String queueName) {
        return createQueue(queueName, 60);
    }

    public boolean deleteQueue(String queueName) {
        boolean queueDeleted = false;

        if (resolveQueueUrl(queueName) != null) {
            LOG.info("Deleting queue '{}", queueName);
            sqs.deleteQueue(resolveQueueUrl(queueName));
            queueDeleted = true;
        }

        return queueDeleted;
    }

    public boolean createQueue(String queueName, int visibilityTimeout) {
        boolean newQueueCreated = false;

        if (resolveQueueUrl(queueName) == null) {
            LOG.info("Created queue '{}'", queueName);
            sqs.createQueue(queueName);
            newQueueCreated = true;
        }

        Map<String, String> mapItems = new HashMap<>();
        mapItems.put(QueueAttributeName.VisibilityTimeout.toString(), String.valueOf(visibilityTimeout));

        SetQueueAttributesRequest request = new SetQueueAttributesRequest().withQueueUrl(resolveQueueUrl(queueName)).withAttributes(mapItems);
        sqs.setQueueAttributes(request);
        return newQueueCreated;
    }

    public boolean purgeQueue(String queueName) {
        boolean queuePurged = false;

        if (resolveQueueUrl(queueName) != null) {
            LOG.info("purging queue '{}'", queueName);
            sqs.purgeQueue(new PurgeQueueRequest(resolveQueueUrl(queueName)));
            queuePurged = true;
        }

        return queuePurged;
    }

    public boolean createTopic(String topicName) {
        boolean topicCreated = false;

        if (resolveTopicArn(topicName) == null) {
            CreateTopicResult result = sns.createTopic(new CreateTopicRequest(topicName));
            LOG.info("Created topic '{}' with arn '{}'", topicName, result.getTopicArn());
            topicCreated = true;
        }

        return topicCreated;
    }

    public void subscribeQueueToTopic(String queueName, String topicName) {
        Topics.subscribeQueue(sns, sqs, resolveTopicArn(topicName), resolveQueueUrl(queueName), false);
        LOG.info("Created subscription to topic '{}' for queue '{}'", topicName, queueName);
    }


    public int unsubscribeQueueFromTopic(String queueName, String topicName) {
        List<Subscription> subscriptionList = sns.listSubscriptions().getSubscriptions();
        int subscriptions = 0;

        for (Subscription item : subscriptionList) {
            if ((item.getTopicArn().equals(resolveTopicArn(topicName))) && (item.getEndpoint().endsWith(queueName))) {
                sns.unsubscribe(item.getSubscriptionArn());
                subscriptions++;
            }
        }

        LOG.debug("unsubscribed {} existing items from topic '{}' for queue '{}'", subscriptions, topicName, queueName);
        return subscriptions;
    }

    public List<Message> receiveMessages(String queueName, AmazonS3 s3) throws IOException {
        ReceiveMessageRequest request = new ReceiveMessageRequest().withMessageAttributeNames("All").withQueueUrl(resolveQueueUrl(queueName)).withMaxNumberOfMessages(10);
        List<Message> msgList = sqs.receiveMessage(request).getMessages();

        for (Message item : msgList) {
            LOG.debug("{} message(s) received -> receiptHandle '{}', consumerTag '{}', body size '{}'", msgList.size(), item.getReceiptHandle(), item.getMessageId(), item.getBody().length());

            if ((item.getMessageAttributes() == null) || (item.getMessageAttributes().size() <= 0)) {
                SnsMessageClassItem snsMessageClass = new SnsMessageClassItem().buildMessageClassItem(item.getBody());

                if (snsMessageClass.getMessageAttributes().get(SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME) != null) {
                    MessageS3Pointer s3Pointer = LargeMessageUtils.readMessageS3PointerFromJSON(snsMessageClass.getMessage());
                    ObjectMapper mapper = new ObjectMapper();

                    LOG.info("large message detected with message content in bucket {} with key {}", s3Pointer.getS3BucketName(), s3Pointer.getS3Key());
                    snsMessageClass.setMessage(LargeMessageUtils.getTextFromS3(s3, s3Pointer.getS3BucketName(), s3Pointer.getS3Key()));

                    LOG.info("writing large message back to the original SNS message");
                    Map<String, Object> originalSnsMessage = mapper.readValue(item.getBody(), Map.class);
                    originalSnsMessage.put("Message", snsMessageClass.getMessage());
                    item.setBody(mapper.writeValueAsString(originalSnsMessage));
                }
            }
        }

        return msgList;
    }


    public void publishMessageToTopic(String topicName, Map<String, com.amazonaws.services.sns.model.MessageAttributeValue> mapItems, String messageSubject, String messageBody) {
        PublishRequest request = new PublishRequest()
                .withTopicArn(resolveTopicArn(topicName))
                .withMessageAttributes(mapItems)
                .withSubject(messageSubject)
                .withMessage(messageBody);

        sns.publish(request);
        LOG.info("published message to topic '{}' with subject '{}'", topicName, messageSubject);
    }

    public void deleteMessageFromQueue(String queueName, String messageReceiptHandle) {
        sqs.deleteMessage(new DeleteMessageRequest(sqs.getQueueUrl(queueName).getQueueUrl(), messageReceiptHandle));
        LOG.info("deleted message handle '{}' from queue '{}'", messageReceiptHandle, queueName);
    }

    public int getS3BucketMessageCount(AmazonS3 s3, String bucketName) {
        ObjectListing list = s3.listObjects(bucketName);
        return list.getObjectSummaries().size();
    }

    private String resolveTopicArn(String topicName) {
        String topicArn = null;

        ListTopicsResult res = sns.listTopics();
        for (Topic topic : res.getTopics()) {
            if (topic.getTopicArn().endsWith(topicName)) {
                topicArn = topic.getTopicArn();
            }
        }

        return topicArn;
    }

    private String resolveQueueUrl(String queueName) {
        String queueUrl = null;
        try {
            queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();

        } catch (QueueDoesNotExistException e) {
            LOG.debug(e.getClass().getName(), e);
            LOG.error(e.getMessage());
        }

        return queueUrl;
    }



    public AmazonS3 getS3Sns() {
        return s3Sns;
    }

    public AmazonS3 getS3Sqs() {
        return s3Sqs;
    }
}
