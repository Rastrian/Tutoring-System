package services.managers.edital;

import java.io.IOException;

import dao.EditaisDAO;
import profiles.Editais;

public class CriarEdital implements Runnable {
    private volatile boolean closeThread;

    private static EditaisDAO repository;
    private static Editais edital;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new EditaisDAO();
            } catch (IOException e) {
                e.printStackTrace();
            }
            edital = new Editais();
            this.start();
        }
    }

    public void start() {
        Integer id = null;
        while (id.equals(null)){
            id = (repository.count() + 1);
        }
        while ((editalExists(id)) != null) {
            id++;
        }
        edital.setId(id);
        edital.setStatus(false);
        repository.add(edital);
        System.out.println("\nEdital N° "+edital.getId()+" foi criado com sucesso.\n");
        shutdown();
    }

    public Editais editalExists(Integer id){
        for (Editais e : repository.getAll()){
            if (e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }

    public void shutdown() {
        closeThread = true;
    }
}