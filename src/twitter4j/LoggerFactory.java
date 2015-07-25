package twitter4j;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.1
 */
public abstract class LoggerFactory {

    /**
     * Returns a logger associated with the specified class.
     *
     * @param clazz class
     * @return a logger instance
     */
    public abstract Logger getLogger(Class<?> clazz);
}