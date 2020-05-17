package services.managers.edital;

import java.io.IOException;

import dao.EditaisDAO;
import profiles.Editais;

public class VerEditais implements Runnable {
    private volatile boolean closeThread;

    private static EditaisDAO repository;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new EditaisDAO();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.start();
        }
    }

    public void start() {

        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}