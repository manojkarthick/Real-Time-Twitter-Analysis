package twitter4j;

public interface Dispatcher {

    void invokeLater(Runnable task);

    void shutdown();
}