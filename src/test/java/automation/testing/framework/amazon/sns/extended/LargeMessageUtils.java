package automation.testing.framework.amazon.sns.extended;

import automation.testing.framework.amazon.utils.AmazonConfigBase;
import automation.testing.framework.amazon.sns.utils.SNSExtendedClientConstants;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.io.IOException;

public class LargeMessageUtils {
    private LargeMessageUtils() {
        // prevent instantiation
    }

    public static AmazonS3 initialiseAmazonS3(AmazonS3 s3, AmazonConfigBase configuration, Logger logger) {
        if (s3 == null) {
            if (configuration.getS3EndpointOverride() != null) {
                logger.debug("overriding default S3 endpoint to '{}' with region {}", configuration.getS3EndpointOverride(), configuration.getRegion());
                s3 = AmazonS3ClientBuilder
                        .standard()
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(configuration.getS3EndpointOverride(), configuration.getRegion().getName()))
                        .withCredentials(new DefaultAWSCredentialsProviderChain())
                        .withPathStyleAccessEnabled(configuration.isPathStyleAccessEnabled())
                        .build();

            } else {
                logger.debug("using default S3 endpoints and region");
                s3 = AmazonS3ClientBuilder
                        .standard()
                        .withCredentials(new DefaultAWSCredentialsProviderChain())
                        .withPathStyleAccessEnabled(configuration.isPathStyleAccessEnabled())
                        .withRegion(configuration.getRegion())
                        .build();
            }

            if (configuration.getS3BucketName() != null) {
                logger.info("checking S3 bucket name '{}' with path style access '{}'", configuration.getS3BucketName(), configuration.isPathStyleAccessEnabled());
                if (!s3.doesBucketExistV2(configuration.getS3BucketName())) {
                    throw new AmazonS3Exception(String.format("Bucket '%s' does not exist", configuration.getS3BucketName()));
                }

            } else {
                logger.info("no S3 bucket configured");
            }
        }

        return s3;
    }

    public static MessageS3Pointer readMessageS3PointerFromJSON(String messageBody) {
        MessageS3Pointer s3Pointer;

        try {
            s3Pointer = new ObjectMapper().readValue(messageBody, MessageS3Pointer.class);

        } catch (IOException e) {
            throw new AmazonClientException("Failed to deserialise S3 object pointer from SQS message.", e);
        }

        return s3Pointer;
    }

    public static String getTextFromS3(AmazonS3 s3, String s3BucketName, String s3Key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(s3BucketName, s3Key);
        String embeddedText;
        S3Object obj;

        try {
            obj = s3.getObject(getObjectRequest);

        } catch (AmazonServiceException e) {
            String errorMessage = "Failed to get the S3 object which contains the message payload. Message was not received.";
            throw new AmazonServiceException(errorMessage, e);

        } catch (AmazonClientException e) {
            String errorMessage = "Failed to get the S3 object which contains the message payload. Message was not received.";
            throw new AmazonClientException(errorMessage, e);

        }

        try (S3ObjectInputStream is = obj.getObjectContent()) {
            embeddedText = IOUtils.toString(is);

        } catch (IOException e) {
            String errorMessage = "Failure when handling the message which was read from S3 object. Message was not received.";
            throw new AmazonClientException(errorMessage, e);
        }

        return embeddedText;
    }

    public static String embedS3PointerInReceiptHandle(String receiptHandle, String s3MsgBucketName, String s3MsgKey) {
        return SNSExtendedClientConstants.S3_BUCKET_NAME_MARKER + s3MsgBucketName
                + SNSExtendedClientConstants.S3_BUCKET_NAME_MARKER + SNSExtendedClientConstants.S3_KEY_MARKER
                + s3MsgKey + SNSExtendedClientConstants.S3_KEY_MARKER + receiptHandle;
    }
}
