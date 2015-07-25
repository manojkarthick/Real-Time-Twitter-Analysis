package twitter4j;


import twitter4j.RateLimitStatus;
import twitter4j.TwitterResponse;
import twitter4j.HttpResponse;
import twitter4j.z_T4JInternalParseUtil;


/**
 * Super interface of Twitter Response data interfaces which indicates that rate limit status is available.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @see twitter4j.DirectMessage
 * @see twitter4j.Status
 * @see twitter4j.User
 */
/*package*/ abstract class TwitterResponseImpl implements TwitterResponse, java.io.Serializable {

    private transient RateLimitStatus rateLimitStatus = null;
    private static final long serialVersionUID = -7284708239736552059L;
    private transient int accessLevel;

    public TwitterResponseImpl() {
        accessLevel = NONE;
    }

    public TwitterResponseImpl(HttpResponse res) {
        this.rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res);
        accessLevel = z_T4JInternalParseUtil.toAccessLevel(res);
    }

    /**
     * {@inheritDoc}
     */
    public RateLimitStatus getRateLimitStatus() {
        return rateLimitStatus;
    }

    /**
     * {@inheritDoc}
     */
    public int getAccessLevel() {
        return accessLevel;
    }
}