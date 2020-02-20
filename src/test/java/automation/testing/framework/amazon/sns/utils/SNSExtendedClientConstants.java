package automation.testing.framework.amazon.sns.utils;

import automation.testing.framework.amazon.sns.extended.AmazonSNSExtendedClient;
import com.amazonaws.util.VersionInfoUtils;

public class SNSExtendedClientConstants {
    public static final String USER_AGENT_HEADER = AmazonSNSExtendedClient.class.getSimpleName() + "/" + VersionInfoUtils.getVersion();

    public static final String RESERVED_ATTRIBUTE_NAME = "SNSLargePayloadSize";
    public static final String S3_BUCKET_NAME_MARKER = "-..s3BucketName..-";
    public static final int LARGE_MESSAGE_THRESHOLD_BYTES = 32768;
    public static final String S3_KEY_MARKER = "-..s3Key..-";
    public static final int MAX_ALLOWED_ATTRIBUTES = 9;

    SNSExtendedClientConstants() {
        // prevent instantiation
    }
}
