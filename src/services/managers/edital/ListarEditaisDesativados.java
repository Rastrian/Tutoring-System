package services.managers.edital;

import java.io.IOException;

import dao.EditaisDAO;
import profiles.Editais;

public class ListarEditaisDesativados implements Runnable {
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
        System.out.println("\nLista de Editais Desativados:\n");
        for (Editais e : repository.getAll()){
            if (e.getStatus() == false)
                System.out.println("ID: "+e.getId()+" - Nome: Edital #"+e.getId());
        }
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}