package twitter4j.management;

/**
 * @author Nick Dellamaggiore (nick.dellamaggiore <at> gmail.com)
 */
public interface InvocationStatistics {
    public String getName();

    public long getCallCount();

    public long getErrorCount();

    public long getTotalTime();

    public long getAverageTime();

    public void reset();
}