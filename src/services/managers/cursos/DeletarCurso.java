package services.managers.cursos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.CursosDAO;
import profiles.Cursos;

public class DeletarCurso implements Runnable {
    private volatile boolean closeThread;

    private static CursosDAO repository;
    private static Cursos cursos;
    private static UtilsCurso utils;

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
        Integer output = null;
        System.out.println("Insira -1 para sair.");
        System.out.print("\nInsira o ID do curso: ");
        while (output == null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        if (output == -1) {
            shutdown();
            return;
        }
        cursos = utils.cursoExists(output);
        if (cursos == null) {
            System.out.println("Curso não encontrado.");
        }
        repository.remove(cursos);
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}