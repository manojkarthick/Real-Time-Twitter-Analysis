package twitter4j;

import twitter4j.api.HelpResources;
import twitter4j.conf.Configuration;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.2.3
 */
public class LanguageJSONImpl implements HelpResources.Language {
    private String name;
    private String code;
    private String status;

    LanguageJSONImpl(JSONObject json) throws TwitterException {
        super();
        init(json);
    }

    private void init(JSONObject json) throws TwitterException {
        try {
            name = json.getString("name");
            code = json.getString("code");
            status = json.getString(("status"));

        } catch (JSONException jsone) {
            throw new TwitterException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getStatus() {
        return status;
    }

    static ResponseList<HelpResources.Language> createLanguageList(HttpResponse res, Configuration conf) throws TwitterException {
        return createLanguageList(res.asJSONArray(), res, conf);
    }

    /*package*/
    static ResponseList<HelpResources.Language> createLanguageList(JSONArray list, HttpResponse res
            , Configuration conf) throws TwitterException {
        if (conf.isJSONStoreEnabled()) {
            TwitterObjectFactory.clearThreadLocalMap();
        }
        try {
            int size = list.length();
            ResponseList<HelpResources.Language> languages =
                    new ResponseListImpl<HelpResources.Language>(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                HelpResources.Language language = new LanguageJSONImpl(json);
                languages.add(language);
                if (conf.isJSONStoreEnabled()) {
                    TwitterObjectFactory.registerJSONObject(language, json);
                }
            }
            if (conf.isJSONStoreEnabled()) {
                TwitterObjectFactory.registerJSONObject(languages, list);
            }
            return languages;
        } catch (JSONException jsone) {
            throw new TwitterException(jsone);
        }
    }
}