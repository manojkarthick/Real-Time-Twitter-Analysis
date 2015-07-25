package twitter4j;

import twitter4j.conf.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.2
 */
final class DispatcherImpl implements Dispatcher {
    private final ExecutorService executorService;
    private static final Logger logger = Logger.getLogger(DispatcherImpl.class);
    private static final long SHUTDOWN_TIME = 5000;

    public DispatcherImpl(final Configuration conf) {
        executorService = Executors.newFixedThreadPool(conf.getAsyncNumThreads(),
                new ThreadFactory() {
                    int count = 0;

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName(String.format("Twitter4J Async Dispatcher[%d]", count++));
                        thread.setDaemon(conf.isDaemonEnabled());
                        return thread;
                    }
                }
        );
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                executorService.shutdown();
            }
        });
    }

    @Override
    public synchronized void invokeLater(Runnable task) {
        executorService.execute(task);
    }

    @Override
    public synchronized void shutdown() {
        executorService.shutdown();
        try {
        	if (!executorService.awaitTermination(SHUTDOWN_TIME, TimeUnit.MILLISECONDS)) {
        		executorService.shutdownNow();
        	}
        } catch (InterruptedException e) {
        	logger.warn(e.getMessage());
        }
    }
}