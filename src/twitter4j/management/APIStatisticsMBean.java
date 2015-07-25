package twitter4j.management;

import java.util.Map;

/**
 * Simple MBean interface for APIStatistics. Method-level statistics are exposed
 * as a Map of formatted strings
 *
 * @author Nick Dellamaggiore (nick.dellamaggiore <at> gmail.com)
 * @see APIStatisticsOpenMBean for a dynamic version of this mbean with tabular representation
 */
public interface APIStatisticsMBean extends InvocationStatistics {
    public Map<String, String> getMethodLevelSummariesAsString();

    public String getMethodLevelSummary(String methodName);

    public Iterable<? extends InvocationStatistics> getInvocationStatistics();
}