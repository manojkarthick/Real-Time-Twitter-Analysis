package twitter4j;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.4
 */
class StringUtil {
    private StringUtil() {
        throw new AssertionError();
    }

    public static String join(long[] follows) {
        StringBuilder buf = new StringBuilder(11 * follows.length);
        for (long follow : follows) {
            if (0 != buf.length()) {
                buf.append(",");
            }
            buf.append(follow);
        }
        return buf.toString();
    }

    public static String join(String[] track) {
        StringBuilder buf = new StringBuilder(11 * track.length);
        for (String str : track) {
            if (0 != buf.length()) {
                buf.append(",");
            }
            buf.append(str);
        }
        return buf.toString();
    }

}