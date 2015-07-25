package twitter4j;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public interface HttpClientConfiguration {

    String getHttpProxyHost();

    int getHttpProxyPort();

    String getHttpProxyUser();

    String getHttpProxyPassword();

    int getHttpConnectionTimeout();

    int getHttpReadTimeout();

    int getHttpRetryCount();

    int getHttpRetryIntervalSeconds();

    int getHttpMaxTotalConnections();

    int getHttpDefaultMaxPerRoute();

    boolean isPrettyDebugEnabled();

    boolean isGZIPEnabled();
}