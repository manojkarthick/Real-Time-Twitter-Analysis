
package twitter4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.2
 */
public class HttpResponseImpl extends HttpResponse {
    private HttpURLConnection con;

    HttpResponseImpl(HttpURLConnection con, HttpClientConfiguration conf) throws IOException {
        super(conf);
        this.con = con;
        try {
            this.statusCode = con.getResponseCode();
        } catch (IOException e) {
          /*
           * If the user has revoked the access token in use, then Twitter naughtily returns a 401 with no "WWW-Authenticate" header.
           *
           * This causes an IOException in the getResponseCode() method call. See https://dev.twitter.com/issues/1114
           * This call can, however, me made a second time without exception.
           */
            if ("Received authentication challenge is null".equals(e.getMessage())) {
                this.statusCode = con.getResponseCode();
            } else {
                throw e;
            }
        }
        if (null == (is = con.getErrorStream())) {
            is = con.getInputStream();
        }
        if (is != null && "gzip".equals(con.getContentEncoding())) {
            // the response is gzipped
            is = new StreamingGZIPInputStream(is);
        }
    }

    // for test purpose
    /*package*/ HttpResponseImpl(String content) {
        super();
        this.responseAsString = content;
    }

    @Override
    public String getResponseHeader(String name) {
        return con.getHeaderField(name);
    }

    @Override
    public Map<String, List<String>> getResponseHeaderFields() {
        return con.getHeaderFields();
    }

    @Override
    public void disconnect() {
        con.disconnect();
    }
}