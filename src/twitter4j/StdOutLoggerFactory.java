package twitter4j;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.1
 */
final class StdOutLoggerFactory extends LoggerFactory {
    private static final Logger SINGLETON = new StdOutLogger();

    @Override
    public Logger getLogger(Class<?> clazz) {
        return SINGLETON;
    }
}