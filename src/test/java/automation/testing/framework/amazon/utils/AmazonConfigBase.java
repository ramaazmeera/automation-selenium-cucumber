package automation.testing.framework.amazon.utils;

import com.amazonaws.regions.Regions;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AmazonConfigBase {

    @JsonProperty("pathStyleAccessEnabled")
    private boolean pathStyleAccessEnabled;

    @JsonProperty("alwaysThroughS3")
    private boolean alwaysThroughS3;

    @JsonProperty("largePayloadSupportEnabled")
    private boolean largePayloadSupportEnabled;

    @JsonProperty("endpointOverride")
    private String endpointOverride = null;

    @JsonProperty("sqsReadFrequencyMillis")
    private long sqsReadFrequencyMillis;

    @JsonProperty("s3EndpointOverride")
    private String s3EndpointOverride = null;

    @JsonProperty("s3BucketName")
    private String s3BucketName;

    @JsonProperty("region")
    private Regions region;

    public AmazonConfigBase() {
        this.setS3BucketName(UUID.randomUUID().toString());
        this.setSqsReadFrequencyMillis(1000);
        this.setRegion(Regions.EU_WEST_2);
    }

    public boolean isPathStyleAccessEnabled() {
        return pathStyleAccessEnabled;
    }

    public void setPathStyleAccessEnabled(boolean pathStyleAccessEnabled) {
        this.pathStyleAccessEnabled = pathStyleAccessEnabled;
    }

    public boolean isAlwaysThroughS3() {
        return alwaysThroughS3;
    }

    public void setAlwaysThroughS3(boolean alwaysThroughS3) {
        this.alwaysThroughS3 = alwaysThroughS3;
    }

    public boolean isLargePayloadSupportEnabled() {
        return largePayloadSupportEnabled;
    }

    public void setLargePayloadSupportEnabled(boolean largePayloadSupportEnabled) {
        this.largePayloadSupportEnabled = largePayloadSupportEnabled;
    }

    public String getEndpointOverride() {
        return endpointOverride;
    }

    public void setEndpointOverride(String endpointOverride) {
        this.endpointOverride = endpointOverride;
    }

    public String getS3EndpointOverride() {
        return s3EndpointOverride;
    }

    public void setS3EndpointOverride(String s3EndpointOverride) {
        this.s3EndpointOverride = s3EndpointOverride;
    }

    public String getS3BucketName() {
        return s3BucketName;
    }

    public void setS3BucketName(String s3BucketName) {
        this.s3BucketName = s3BucketName;
    }

    public Regions getRegion() {
        return region;
    }

    public void setRegion(Regions region) {
        this.region = region;
    }

    public long getSqsReadFrequencyMillis() {
        return sqsReadFrequencyMillis;
    }

    public void setSqsReadFrequencyMillis(long sqsReadFrequencyMillis) {
        this.sqsReadFrequencyMillis = sqsReadFrequencyMillis;
    }
}
