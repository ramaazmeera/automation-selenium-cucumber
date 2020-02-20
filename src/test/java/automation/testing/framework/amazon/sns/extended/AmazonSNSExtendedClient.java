package automation.testing.framework.amazon.sns.extended;


import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import com.amazonaws.services.sns.AmazonSNS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AmazonSNSExtendedClient extends AmazonSNSExtendedClientBase implements AmazonSNS {
    private static final Logger LOG = LoggerFactory.getLogger(AmazonSNSExtendedClient.class.getName());
    private ExtendedClientConfiguration clientConfiguration;

    public AmazonSNSExtendedClient(AmazonSNS snsClient, ExtendedClientConfiguration extendedClientConfiguration) {
        super(snsClient);
        this.clientConfiguration = extendedClientConfiguration;
    }


}
