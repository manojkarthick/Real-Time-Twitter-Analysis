package twitter4j;

import java.util.ArrayList;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.3
 */
class ResponseListImpl<T> extends ArrayList<T> implements ResponseList<T> {
    private static final long serialVersionUID = 9105950888010803544L;
    private transient RateLimitStatus rateLimitStatus = null;
    private transient int accessLevel;

    ResponseListImpl(HttpResponse res) {
        super();
        init(res);
    }

    ResponseListImpl(int size, HttpResponse res) {
        super(size);
        init(res);
    }

    ResponseListImpl(RateLimitStatus rateLimitStatus, int accessLevel) {
        super();
        this.rateLimitStatus = rateLimitStatus;
        this.accessLevel = accessLevel;
    }

    private void init(HttpResponse res) {
        this.rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res);
        accessLevel = ParseUtil.toAccessLevel(res);
    }

    @Override
    public RateLimitStatus getRateLimitStatus() {
        return rateLimitStatus;
    }

    @Override
    public int getAccessLevel() {
        return accessLevel;
    }
}