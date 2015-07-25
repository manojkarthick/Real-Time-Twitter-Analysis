package twitter4j;

import twitter4j.DataObjectFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * provides public access to package private methods of twitter4j.json.DataObjectFactory class.<br>
 * This class is not intended to be used by Twitter4J client.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class DataObjectFactoryUtil {
    private DataObjectFactoryUtil() {
        throw new AssertionError("not intended to be instantiated.");
    }

    private static final Method CLEAR_THREAD_LOCAL_MAP;
    private static final Method REGISTER_JSON_OBJECT;

    static {
        Method[] methods = DataObjectFactory.class.getDeclaredMethods();
        Method clearThreadLocalMap = null;
        Method registerJSONObject = null;
        for (Method method : methods) {
            if (method.getName().equals("clearThreadLocalMap")) {
                clearThreadLocalMap = method;
                clearThreadLocalMap.setAccessible(true);
            } else if (method.getName().equals("registerJSONObject")) {
                registerJSONObject = method;
                registerJSONObject.setAccessible(true);
            }
        }
        if (null == clearThreadLocalMap || null == registerJSONObject) {
            throw new AssertionError();
        }
        CLEAR_THREAD_LOCAL_MAP = clearThreadLocalMap;
        REGISTER_JSON_OBJECT = registerJSONObject;
    }

    /**
     * provides a public access to {DAOFactory#clearThreadLocalMap}
     */
    public static void clearThreadLocalMap() {
        try {
            CLEAR_THREAD_LOCAL_MAP.invoke(null);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * provides a public access to {DAOFactory#registerJSONObject}
     */
    public static <T> T registerJSONObject(T key, Object json) {
        try {
            return (T) REGISTER_JSON_OBJECT.invoke(null, key, json);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }
}