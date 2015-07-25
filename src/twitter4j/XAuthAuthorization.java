package twitter4j;

import twitter4j.auth.Authorization;
import twitter4j.auth.BasicAuthorization;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.3
 */
public class XAuthAuthorization implements Authorization, java.io.Serializable {
    private static final long serialVersionUID = -7260372598870697494L;
    private final BasicAuthorization basic;

    private String consumerKey;
    private String consumerSecret;

    public XAuthAuthorization(BasicAuthorization basic) {
        this.basic = basic;
    }

    @Override
    public String getAuthorizationHeader(HttpRequest req) {
        return basic.getAuthorizationHeader(req);
    }

    public String getUserId() {
        return basic.getUserId();
    }

    public String getPassword() {
        return basic.getPassword();
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public synchronized void setOAuthConsumer(String consumerKey, String consumerSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    @Override
    public boolean isEnabled() {
        return basic.isEnabled();
    }
}