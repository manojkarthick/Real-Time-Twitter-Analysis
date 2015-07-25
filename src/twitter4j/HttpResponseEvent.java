package twitter4j;

public final class HttpResponseEvent {

    private final HttpRequest request;

    private final HttpResponse response;

    private final TwitterException twitterException;

    HttpResponseEvent(HttpRequest request, HttpResponse response, TwitterException te) {
        this.request = request;
        this.response = response;
        this.twitterException = te;
    }

    /**
     * returns the request associated with the event
     *
     * @return the request associated with the event
     */
    public HttpRequest getRequest() {
        return request;
    }

    /**
     * returns the response associated with the event
     *
     * @return the response associated with the event
     */
    public HttpResponse getResponse() {
        return response;
    }

    /**
     * returns the TwitterException associated with the event
     *
     * @return the TwitterException associated with the event
     */
    public TwitterException getTwitterException() {
        return twitterException;
    }

    public boolean isAuthenticated() {
        return request.getAuthorization().isEnabled();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpResponseEvent that = (HttpResponseEvent) o;

        if (request != null ? !request.equals(that.request) : that.request != null)
            return false;
        if (response != null ? !response.equals(that.response) : that.response != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = request != null ? request.hashCode() : 0;
        result = 31 * result + (response != null ? response.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HttpResponseEvent{" +
                "request=" + request +
                ", response=" + response +
                '}';
    }
}