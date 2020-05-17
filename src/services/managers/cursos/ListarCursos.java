package services.managers.cursos;

import java.io.IOException;

import dao.CursosDAO;
import profiles.Cursos;

public class ListarCursos implements Runnable {
    private volatile boolean closeThread;

    private static CursosDAO repository;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new CursosDAO();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.start();
        }
    }

    public void start() {
        System.out.println("\nLista de cursos:\n");
        for (Cursos c : repository.getAll()){
            System.out.println("ID: "+c.getId()+" - Nome: "+c.getNome());
        }
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}