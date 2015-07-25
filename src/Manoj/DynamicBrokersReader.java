package Manoj;

import Manoj.GlobalPartitionInformation;
import backtype.storm.Config;
import backtype.storm.utils.Utils;

import com.google.common.base.Preconditions;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.RetryNTimes;

import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;

public class DynamicBrokersReader {

    public static final Logger LOG = LoggerFactory.getLogger(DynamicBrokersReader.class);

    private CuratorFramework _curator;
    private String _zkPath;
    private String _topic;

    public DynamicBrokersReader(Map conf, String zkStr, String zkPath, String topic) {
        // Check required parameters
        Preconditions.checkNotNull(conf, "conf cannot be null");

        validateConfig(conf);

        Preconditions.checkNotNull(zkStr,"zkString cannot be null");
        Preconditions.checkNotNull(zkPath, "zkPath cannot be null");
        Preconditions.checkNotNull(topic, "topic cannot be null");

        _zkPath = zkPath;
        _topic = topic;
        try {
            _curator = CuratorFrameworkFactory.newClient(
                    zkStr,
                    Utils.getInt(conf.get(Config.STORM_ZOOKEEPER_SESSION_TIMEOUT)),
                    Utils.getInt(conf.get(Config.STORM_ZOOKEEPER_CONNECTION_TIMEOUT)),
                    new RetryNTimes(Utils.getInt(conf.get(Config.STORM_ZOOKEEPER_RETRY_TIMES)),
                            Utils.getInt(conf.get(Config.STORM_ZOOKEEPER_RETRY_INTERVAL))));
            _curator.start();
        } catch (Exception ex) {
            LOG.error("Couldn't connect to zookeeper", ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Get all partitions with their current leaders
     */
    public GlobalPartitionInformation getBrokerInfo() throws SocketTimeoutException {
      GlobalPartitionInformation globalPartitionInformation = new GlobalPartitionInformation();
        try {
            int numPartitionsForTopic = getNumPartitions();
            String brokerInfoPath = brokerPath();
            for (int partition = 0; partition < numPartitionsForTopic; partition++) {
                int leader = getLeaderFor(partition);
                String path = brokerInfoPath + "/" + leader;
                try {
                    byte[] brokerData = _curator.getData().forPath(path);
                    Broker hp = getBrokerHost(brokerData);
                    globalPartitionInformation.addPartition(partition, hp);
                } catch (org.apache.zookeeper.KeeperException.NoNodeException e) {
                    LOG.error("Node {} does not exist ", path);
                }
            }
        } catch (SocketTimeoutException e) {
					throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LOG.info("Read partition info from zookeeper: " + globalPartitionInformation);
        return globalPartitionInformation;
    }


    private int getNumPartitions() {
        try {
            String topicBrokersPath = partitionPath();
            List<String> children = _curator.getChildren().forPath(topicBrokersPath);
            return children.size();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String partitionPath() {
        return _zkPath + "/topics/" + _topic + "/partitions";
    }

    public String brokerPath() {
        return _zkPath + "/ids";
    }

    /**
     * get /brokers/topics/distributedTopic/partitions/1/state
     * { "controller_epoch":4, "isr":[ 1, 0 ], "leader":1, "leader_epoch":1, "version":1 }
     *
     * @param partition
     * @return
     */
    private int getLeaderFor(long partition) {
        try {
            String topicBrokersPath = partitionPath();
            byte[] hostPortData = _curator.getData().forPath(topicBrokersPath + "/" + partition + "/state");
            Map<Object, Object> value = (Map<Object, Object>) JSONValue.parse(new String(hostPortData, "UTF-8"));
            Integer leader = ((Number) value.get("leader")).intValue();
            if (leader == -1) {
                throw new RuntimeException("No leader found for partition " + partition);
            }
            return leader;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        _curator.close();
    }

    /**
     * [zk: localhost:2181(CONNECTED) 56] get /brokers/ids/0
     * { "host":"localhost", "jmx_port":9999, "port":9092, "version":1 }
     *
     * @param contents
     * @return
     */
    private Broker getBrokerHost(byte[] contents) {
        try {
            Map<Object, Object> value = (Map<Object, Object>) JSONValue.parse(new String(contents, "UTF-8"));
            String host = (String) value.get("host");
            Integer port = ((Long) value.get("port")).intValue();
            return new Broker(host, port);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validate required parameters in the input configuration Map
     * @param conf
     */
    private void validateConfig(final Map conf) {
        Preconditions.checkNotNull(conf.get(Config.STORM_ZOOKEEPER_SESSION_TIMEOUT),
                "%s cannot be null", Config.STORM_ZOOKEEPER_SESSION_TIMEOUT);
        Preconditions.checkNotNull(conf.get(Config.STORM_ZOOKEEPER_CONNECTION_TIMEOUT),
                "%s cannot be null", Config.STORM_ZOOKEEPER_CONNECTION_TIMEOUT);
        Preconditions.checkNotNull(conf.get(Config.STORM_ZOOKEEPER_RETRY_TIMES),
                "%s cannot be null", Config.STORM_ZOOKEEPER_RETRY_TIMES);
        Preconditions.checkNotNull(conf.get(Config.STORM_ZOOKEEPER_RETRY_INTERVAL),
                "%s cannot be null", Config.STORM_ZOOKEEPER_RETRY_INTERVAL);
    }

}