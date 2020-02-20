package automation.testing.framework.amazon.sns.extended;

public class MessageS3Pointer {
    private String s3BucketName;
    private String s3Key;

    MessageS3Pointer(String s3BucketName, String s3Key) {
        this.s3BucketName = s3BucketName;
        this.s3Key = s3Key;
    }

    MessageS3Pointer() {
        // for serialisation
    }

    public String getS3BucketName() {
        return this.s3BucketName;
    }

    public void setS3BucketName(String s3BucketName) {
        this.s3BucketName = s3BucketName;
    }

    public String getS3Key() {
        return this.s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }
}
