package services.managers.cursos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.CursosDAO;
import profiles.Cursos;

public class CriarCurso implements Runnable {
    private volatile boolean closeThread;

    private static Cursos cursos;
    private static CursosDAO repository;

    public Cursos cursoExists(Integer id) {
        for (Cursos c : repository.getAll()){
            if (c.getId().equals(id)){
                return c;
            }
        }
        return null;
    }

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new CursosDAO();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cursos = new Cursos();
            this.start();
        }
    }

    public void start() {
        Integer id = null;
        String output = null;
        System.out.print("\nInsira o nome do Curso: ");
        while (output == null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = br.readLine();
            } catch (IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        cursos.setNome(output);
        while (id == null) {
            id = (repository.count() + 1);
        }
        while ((cursoExists(id)) != null) {
            id++;
        }
        cursos.setId(id);
        System.out.println("\nInformações sobre o curso:\nID: " + cursos.getId() + "\nNome: " + cursos.getNome());
        repository.add(cursos);
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}