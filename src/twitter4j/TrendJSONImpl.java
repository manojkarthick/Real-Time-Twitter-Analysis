package twitter4j;

/**
 * A data class representing Trend.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.0.2
 */
/*package*/ final class TrendJSONImpl implements Trend, java.io.Serializable {
    private static final long serialVersionUID = -4353426776065521132L;
    private final String name;
    private String url = null;
    private String query = null;

    /*package*/ TrendJSONImpl(JSONObject json, boolean storeJSON) {
        this.name = ParseUtil.getRawString("name", json);
        this.url = ParseUtil.getRawString("url", json);
        this.query = ParseUtil.getRawString("query", json);
        if (storeJSON) {
            TwitterObjectFactory.registerJSONObject(this, json);
        }
    }

    /*package*/ TrendJSONImpl(JSONObject json) {
        this(json, false);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trend)) return false;

        Trend trend = (Trend) o;

        if (!name.equals(trend.getName())) return false;
        if (query != null ? !query.equals(trend.getQuery()) : trend.getQuery() != null)
            return false;
        if (url != null ? !url.equals(trend.getURL()) : trend.getURL() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (query != null ? query.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TrendJSONImpl{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", query='" + query + '\'' +
                '}';
    }
}