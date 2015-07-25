package twitter4j;

public class ScopesImpl implements Scopes {

    private final String[] placeIds;
    
    /* Only for serialization purposes. */
    /*package*/ ScopesImpl() {
        this.placeIds = new String[0];
    }

    public ScopesImpl(final String[] placeIds) {
        this.placeIds = placeIds;
    }

    @Override
    public String[] getPlaceIds() {
        return placeIds;
    }

}