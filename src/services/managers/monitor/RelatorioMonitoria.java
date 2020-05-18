package services.managers.monitor;

public class RelatorioMonitoria implements Runnable {
    private volatile boolean closeThread;

    @Override
    public void run() {
        while (!closeThread) {
            this.start();
        }
    }

    public void start() {
        /* TODO */
    }

    public void shutdown() {
        closeThread = true;
    }
}