//package automation.testing.framework.amazon.sqs;
//
//import automation.testing.framework.amazon.utils.AmazonConfigBase;
//import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
//import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
//import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.sns.model.MessageAttributeValue;
//import com.amazonaws.services.sqs.AmazonSQS;
//import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
//import com.amazonaws.services.sqs.model.DeleteMessageRequest;
//import com.amazonaws.services.sqs.model.Message;
//import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.event.EventConstants;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MessageReceiver {
//
//    private static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class.getName());
//    private static final String EMPTY_HEADERS = "Message attributes are null";
//
//    private final CryptoDataManager messageCryptography;
//    private final AmazonConfigBase configuration;
//    private AmazonSQS sqs = null;
//    private AmazonS3 s3 = null;
//
//    public MessageReceiver(CryptoDataManager cryptoDataManager, AmazonConfigBase config) {
//        this.messageCryptography = cryptoDataManager;
//        this.configuration = config;
//    }
//
//    private synchronized AmazonSQS getConnection() {
//        if (configuration.isLargePayloadSupportEnabled()) {
//            s3 = LargeMessageUtils.initialiseAmazonS3(s3, configuration, LOG);
//        }
//
//        if (sqs == null) {
//            ExtendedClientConfiguration extendedClientConfiguration = new ExtendedClientConfiguration();
//            extendedClientConfiguration.setAlwaysThroughS3(configuration.isAlwaysThroughS3());
//
//            if (configuration.isLargePayloadSupportEnabled()) {
//                LOG.info("setting large payload support for SQS using S3 with bucket name '{}'", configuration.getS3BucketName());
//                extendedClientConfiguration.setLargePayloadSupportEnabled(s3, configuration.getS3BucketName());
//
//            } else {
//                LOG.info("disabling large payload support");
//                extendedClientConfiguration.setLargePayloadSupportDisabled();
//            }
//
//            AmazonSQS localSqs;
//            if (configuration.getEndpointOverride() != null) {
//                LOG.debug("overriding default SQS endpoint to '{}' with region {}", configuration.getEndpointOverride(), configuration.getRegion());
//                localSqs = AmazonSQSClientBuilder
//                        .standard()
//                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(configuration.getEndpointOverride(), configuration.getRegion().getName()))
//                        .withCredentials(new DefaultAWSCredentialsProviderChain())
//                        .build();
//
//            } else {
//                LOG.debug("using default SQS endpoints and region");
//                localSqs = AmazonSQSClientBuilder
//                        .standard()
//                        .withCredentials(new DefaultAWSCredentialsProviderChain())
//                        .withRegion(configuration.getRegion())
//                        .build();
//            }
//
//            sqs = new AmazonSQSExtendedClient(localSqs, extendedClientConfiguration);
//        }
//
//        return sqs;
//    }
//
//    private String resolveQueueUrl(String queueName) {
//        return getConnection().getQueueUrl(queueName).getQueueUrl();
//    }
//
//    public EventMessage receiveMessage(String queueName) throws IOException, CryptoException {
//        ReceiveMessageRequest msgRequest = new ReceiveMessageRequest().withMessageAttributeNames("All").withQueueUrl(resolveQueueUrl(queueName));
//        List<Message> messages = getConnection().receiveMessage(msgRequest).getMessages();
//        EventMessage receivedMessage = null;
//
//        if (messages.size() == 1) {
//
//            receivedMessage = new EventMessage();
//            Message message = messages.get(0);
//
//            String receiptHandle = message.getReceiptHandle();
//            LOG.debug("intialising receipt handle as '{}'", receiptHandle);
//
//            if ((message.getMessageAttributes() == null) || (message.getMessageAttributes().size() <= 0)) {
//
//                SnsMessageClassItem snsMessageClass = new SnsMessageClassItem().buildMessageClassItem(message.getBody());
//                LOG.debug("SNS {} message received -> receiptHandle '{}', consumerTag '{}', body size '{}'", messages.size(), message.getReceiptHandle(), message.getMessageId(), message.getBody().length());
//
//                if (snsMessageClass.getMessageAttributes().get(SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME) != null) {
//                    MessageS3Pointer s3Pointer = LargeMessageUtils.readMessageS3PointerFromJSON(snsMessageClass.getMessage());
//
//                    LOG.info("large message detected with message content in bucket {} with key {}", s3Pointer.getS3BucketName(), s3Pointer.getS3Key());
//                    snsMessageClass.setMessage(LargeMessageUtils.getTextFromS3(s3, s3Pointer.getS3BucketName(), s3Pointer.getS3Key()));
//
//                    LOG.info("remove large message marker ('{}') from headers", SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME);
//                    snsMessageClass.getMessageAttributes().remove(SNSExtendedClientConstants.RESERVED_ATTRIBUTE_NAME);
//                }
//
//                LOG.debug("Resolving standard items from SNS message attribute header");
//                receivedMessage.setMetaData(decodeMetadataFromSnsHeader(receiptHandle, snsMessageClass.getMessageAttributes()));
//                receivedMessage.setBodyContents(MessageDecoder.decodeIncomingMessage(getMessageCryptography(), snsMessageClass.getMessageAttributes(), snsMessageClass.getMessage().getBytes()));
//
//            } else {
//                LOG.debug("standard SQS message received with {} headers", message.getMessageAttributes().size());
//
//                LOG.debug("Resolving standard items from SQS message attribute header");
//                receivedMessage.setMetaData(decodeMetadataFromSqsHeader(receiptHandle, message.getMessageAttributes()));
//                receivedMessage.setBodyContents(MessageDecoder.decodeIncomingMessage(getMessageCryptography(), message.getMessageAttributes(), message.getBody().getBytes()));
//            }
//
//            if (!receivedMessage.isContentValid()) {
//                throw new IOException("EventMessage contains invalid items and could not be validated, rejecting...");
//            }
//
//            LOG.debug("Message contents decoded in full");
//        }
//
//        return receivedMessage;
//    }
//
//    public void deleteMessageFromQueue(String queueName, String receiptHandle) {
//        getConnection().deleteMessage(new DeleteMessageRequest(resolveQueueUrl(queueName), receiptHandle));
//    }
//
//    private CryptoDataManager getMessageCryptography() {
//        return messageCryptography;
//    }
//
//    private MetaData decodeMetadataFromSnsHeader(String receiptHandle, Map<String, MessageAttributeValue> messageHeaders) throws IOException {
//        if (messageHeaders == null) {
//            throw new IOException(EMPTY_HEADERS);
//        }
//
//        Map<String, String> headerMap = new HashMap<>();
//        for (Map.Entry<String, com.amazonaws.services.sns.model.MessageAttributeValue> item : messageHeaders.entrySet()) {
//            headerMap.put(item.getKey(), item.getValue().getStringValue());
//        }
//
//        return decodeMetadataFromBasicHeader(receiptHandle, headerMap);
//    }
//
//    private MetaData decodeMetadataFromSqsHeader(String receiptHandle, Map<String, com.amazonaws.services.sqs.model.MessageAttributeValue> messageHeaders) throws IOException {
//        if (messageHeaders == null) {
//            throw new IOException(EMPTY_HEADERS);
//        }
//
//        Map<String, String> headerMap = new HashMap<>();
//        for (Map.Entry<String, com.amazonaws.services.sqs.model.MessageAttributeValue> item : messageHeaders.entrySet()) {
//            headerMap.put(item.getKey(), item.getValue().getStringValue());
//        }
//
//        return decodeMetadataFromBasicHeader(receiptHandle, headerMap);
//    }
//
//    private MetaData decodeMetadataFromBasicHeader(String receiptHandle, Map<String, String> messageHeaders) throws IOException {
//        for (String attribute : Arrays.asList(EventConstants.ROUTING_KEY_MARKER, EventConstants.MESSAGE_CREATED_DT_STRING, EventConstants.TRIGGERED_BY_SERIALISED_LIST)) {
//            if (messageHeaders.get(attribute) == null) {
//                throw new IOException(String.format("Required attribute '%s' not found", attribute));
//            }
//        }
//
//        MetaData metaData = new MetaData(new ObjectMapper().readValue(messageHeaders.get(EventConstants.TRIGGERED_BY_SERIALISED_LIST), List.class));
//        metaData.setDateCreated(messageHeaders.get(EventConstants.MESSAGE_CREATED_DT_STRING));
//        metaData.setRoutingKey(messageHeaders.get(EventConstants.ROUTING_KEY_MARKER));
//        metaData.setReceiptHandle(receiptHandle);
//
//        if ((messageHeaders.get(EventConstants.REPLY_TO_TOPIC_NAME) != null) && (messageHeaders.get(EventConstants.REPLY_TO_ROUTING_KEY) != null)) {
//            metaData.setReplySnsParameters(
//                    messageHeaders.get(EventConstants.REPLY_TO_TOPIC_NAME),
//                    messageHeaders.get(EventConstants.REPLY_TO_ROUTING_KEY)
//            );
//        }
//
//        if (messageHeaders.get(EventConstants.CORRELATION_ID) != null) {
//            metaData.setCorrelationId(messageHeaders.get(EventConstants.CORRELATION_ID));
//        }
//
//        if (!metaData.isContentValid()) {
//            throw new IOException("Rejecting MetaData :: headers contain invalid attribute contents");
//        }
//
//        return metaData;
//    }
//}
