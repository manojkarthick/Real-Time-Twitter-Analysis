package twitter4j;

/**
 * @author Alessandro Bahgat - ale.bahgat at gmail.com
 */
public class TimeZoneJSONImpl implements TimeZone {
    private static final long serialVersionUID = 81958969762484144L;
    private final String NAME;
    private final String TZINFO_NAME;
    private final int UTC_OFFSET;

    TimeZoneJSONImpl(JSONObject jSONObject) throws TwitterException {
        try {
            UTC_OFFSET = ParseUtil.getInt("utc_offset", jSONObject);
            NAME = jSONObject.getString("name");
            TZINFO_NAME = jSONObject.getString("tzinfo_name");
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String tzinfoName() {
        return TZINFO_NAME;
    }

    @Override
    public int utcOffset() {
        return UTC_OFFSET;
    }

}