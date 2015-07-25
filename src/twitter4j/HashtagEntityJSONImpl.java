package twitter4j;

/*package*/ class HashtagEntityJSONImpl extends EntityIndex implements HashtagEntity, SymbolEntity {
    private static final long serialVersionUID = -5317828991902848906L;
    private String text;


    /* package */ HashtagEntityJSONImpl(JSONObject json) throws TwitterException {
        super();
        init(json);
    }

    /* package */ HashtagEntityJSONImpl(int start, int end, String text) {
        super();
        setStart(start);
        setEnd(end);
        this.text = text;
    }

    /* For serialization purposes only. */
    /* package */ HashtagEntityJSONImpl() {

    }

    private void init(JSONObject json) throws TwitterException {
        try {
            JSONArray indicesArray = json.getJSONArray("indices");
            setStart(indicesArray.getInt(0));
            setEnd(indicesArray.getInt(1));

            if (!json.isNull("text")) {
                this.text = json.getString("text");
            }
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        }
    }

    @Override
    public String getText() {
        return text;
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

        HashtagEntityJSONImpl that = (HashtagEntityJSONImpl) o;

        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "HashtagEntityJSONImpl{" +
                "text='" + text + '\'' +
                '}';
    }
}