package twitter4j;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.2
 */
final class DispatcherFactory {
    private final String dispatcherImpl;
    private final Configuration conf;

    public DispatcherFactory(Configuration conf) {
        dispatcherImpl = conf.getDispatcherImpl();
        this.conf = conf;
    }

    public DispatcherFactory() {
        this(ConfigurationContext.getInstance());
    }

    /**
     * returns a Dispatcher instance.
     *
     * @return dispatcher instance
     */
    public Dispatcher getInstance() {
        try {
            return (Dispatcher) Class.forName(dispatcherImpl)
                    .getConstructor(Configuration.class).newInstance(conf);
        } catch (InstantiationException e) {
            throw new AssertionError(e);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (ClassNotFoundException e) {
            throw new AssertionError(e);
        } catch (ClassCastException e) {
            throw new AssertionError(e);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }
}