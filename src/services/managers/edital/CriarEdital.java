package services.managers.edital;

import java.io.IOException;

import dao.EditaisDAO;
import profiles.Editais;

public class CriarEdital implements Runnable {
    private volatile boolean closeThread;

    private static EditaisDAO repository;
    private static Editais edital;

    private static EditalUtils utils;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new EditaisDAO();
                utils = new EditalUtils();
            } catch (IOException e) {
                e.printStackTrace();
            }
            edital = new Editais();
            this.start();
        }
    }

    public void start() {
        Integer id = null;
        while (id == null){
            id = (repository.count() + 1);
        }
        while ((utils.editalExists(id)) != null) {
            id++;
        }
        edital.setId(id);
        edital.setStatus(false);
        repository.add(edital);
        System.out.println("\nEdital N° "+edital.getId()+" foi criado com sucesso.\n");
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}