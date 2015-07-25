package twitter4j;


import twitter4j.auth.Authorization;

import java.util.Map;

/**
 * A utility class to handle HTTP request/response.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public interface HttpClient {

    void addDefaultRequestHeader(String name, String value);

    Map<String, String> getRequestHeaders();

    HttpResponse request(HttpRequest req) throws TwitterException;

    HttpResponse request(HttpRequest req, HttpResponseListener listener) throws TwitterException;

    HttpResponse get(String url, HttpParameter[] parameters
            , Authorization authorization, HttpResponseListener listener) throws TwitterException;

    HttpResponse get(String url) throws TwitterException;

    HttpResponse post(String url, HttpParameter[] parameters
            , Authorization authorization, HttpResponseListener listener) throws TwitterException;

    HttpResponse post(String url) throws TwitterException;

    HttpResponse delete(String url, HttpParameter[] parameters
            , Authorization authorization, HttpResponseListener listener) throws TwitterException;

    HttpResponse delete(String url) throws TwitterException;

    HttpResponse head(String url) throws TwitterException;

    HttpResponse put(String url, HttpParameter[] parameters
            , Authorization authorization, HttpResponseListener listener) throws TwitterException;

    HttpResponse put(String url) throws TwitterException;
}