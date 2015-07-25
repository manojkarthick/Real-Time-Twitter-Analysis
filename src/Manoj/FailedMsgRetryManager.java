package Manoj;

public interface FailedMsgRetryManager {
    public void failed(Long offset);
    public void acked(Long offset);
    public void retryStarted(Long offset);
    public Long nextFailedMessageToRetry();
    public boolean shouldRetryMsg(Long offset);
}