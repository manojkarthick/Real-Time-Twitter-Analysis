package Manoj;
public interface IBrokerReader {

    GlobalPartitionInformation getCurrentBrokers();

    void close();
}