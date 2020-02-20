package automation.testing.framework.amazon.utils;

import automation.testing.framework.config.EnvironmentHandler;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class AWSClients {

    private final EnvironmentHandler env;

    public AWSClients(EnvironmentHandler env) {
        this.env = env;
    }

    public AmazonSQS getSQSClient() {

        return AmazonSQSClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(env.valueOf("sqsServiceUrl"), env.valueOf("region")))
                .build();
    }

    public AmazonS3 getS3Client() {

        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(env.valueOf("s3ServiceUrl"), env.valueOf("region")))
                .build();
    }
}
