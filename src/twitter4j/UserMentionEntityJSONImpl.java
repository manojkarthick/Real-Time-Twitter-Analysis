package twitter4j;

/*package*/ class UserMentionEntityJSONImpl extends EntityIndex implements UserMentionEntity {
    private static final long serialVersionUID = 6060510953676673013L;
    private String name;
    private String screenName;
    private long id;

    /* package */ UserMentionEntityJSONImpl(JSONObject json) throws TwitterException {
        super();
        init(json);
    }

    /* package */ UserMentionEntityJSONImpl(int start, int end, String name, String screenName, long id) {
        super();
        setStart(start);
        setEnd(end);
        this.name = name;
        this.screenName = screenName;
        this.id = id;
    }

    /* For serialization purposes only. */
    /* package */ UserMentionEntityJSONImpl() {

    }

    private void init(JSONObject json) throws TwitterException {
        try {
            JSONArray indicesArray = json.getJSONArray("indices");
            setStart(indicesArray.getInt(0));
            setEnd(indicesArray.getInt(1));

            if (!json.isNull("name")) {
                this.name = json.getString("name");
            }
            if (!json.isNull("screen_name")) {
                this.screenName = json.getString("screen_name");
            }
            id = ParseUtil.getLong("id", json);
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        }
    }

    @Override
    public String getText() {
        return screenName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int getStart() {
        return super.getStart();
    }

    @Override
    public int getEnd() {
        return super.getEnd();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMentionEntityJSONImpl that = (UserMentionEntityJSONImpl) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (screenName != null ? !screenName.equals(that.screenName) : that.screenName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (screenName != null ? screenName.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "UserMentionEntityJSONImpl{" +
                "name='" + name + '\'' +
                ", screenName='" + screenName + '\'' +
                ", id=" + id +
                '}';
    }
}