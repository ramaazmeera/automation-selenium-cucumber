//package automation.testing.framework.amazon.sqs;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
//public class MessageConsumer {
//
//    private static final Logger LOG = LoggerFactory.getLogger(MessageConsumer.class.getName());
//    private final MessageReceiver messageReceiver;
//    private final String queueName;
//
//    private SqsReceivedMessageEvent callback;
//    private long readFrequencyMillis = 1000;
//    private boolean receivingMessages;
//
//    public MessageConsumer(MessageReceiver messageReceiver, String queueName, SqsReceivedMessageEvent callback) {
//        this.messageReceiver = messageReceiver;
//        this.receivingMessages = true;
//        this.queueName = queueName;
//
//        LOG.debug("default read frequency to {} milliseconds", this.readFrequencyMillis);
//        registerCallback(callback);
//        initialise();
//    }
//
//    public void registerCallback(SqsReceivedMessageEvent callbackClass) {
//        this.callback = callbackClass;
//    }
//
//    private void raiseEvent(EventMessage eventMessage) {
//        if (this.callback != null) {
//            try {
//
//                LOG.debug("received message from sqs queue ({}), propagate event", getQueueName());
//                callback.handleMessage(eventMessage);
//
//                LOG.debug("no exception thrown by consuming event, delete message from sqs queue ({})", getQueueName());
//                getMessageReceiver().deleteMessageFromQueue(getQueueName(), eventMessage.getMetaData().getReceiptHandle());
//
//            } catch (IOException e) {
//                LOG.debug(String.format("error thrown, message will not be deleted from queue '%s'", getQueueName()), e);
//                LOG.error(e.getMessage());
//            }
//        }
//    }
//
//    private void initialise() {
//        new Thread(() -> {
//            EventMessage receivedMessage;
//
//            while (isReceivingMessages()) {
//                try {
//
//                    receivedMessage = getMessageReceiver().receiveMessage(getQueueName());
//                    if (receivedMessage != null) {
//                        raiseEvent(receivedMessage);
//                    }
//
//                } catch (Exception e) {
//                    LOG.debug(e.getClass().getName(), e);
//                    LOG.error(e.getMessage());
//                }
//
//                try {
//                    TimeUnit.MILLISECONDS.sleep(getReadFrequencyMillis());
//
//                } catch (Exception e) {
//                    LOG.debug(e.getClass().getName(), e);
//                    LOG.error(e.getMessage());
//                }
//            }
//        }, "sqs message listener").start();
//    }
//
//    public boolean isReceivingMessages() {
//        return receivingMessages;
//    }
//
//    public void setReceivingMessages(boolean receivingMessages) {
//        this.receivingMessages = receivingMessages;
//    }
//
//    public String getQueueName() {
//        return queueName;
//    }
//
//    private MessageReceiver getMessageReceiver() {
//        return messageReceiver;
//    }
//
//    public long getReadFrequencyMillis() {
//        return readFrequencyMillis;
//    }
//
//    public void setReadFrequencyMillis(long readFrequencyMillis) {
//        LOG.debug("setting new read frequency to {} milliseconds", readFrequencyMillis);
//        this.readFrequencyMillis = readFrequencyMillis;
//    }
//}
