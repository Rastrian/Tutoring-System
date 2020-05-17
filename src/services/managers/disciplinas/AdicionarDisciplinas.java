package services.managers.disciplinas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.UsuarioDAO;
import dao.VagasDAO;
import profiles.Usuario;
import profiles.Vagas;

public class AdicionarDisciplinas implements Runnable {
    private volatile boolean closeThread;

    private static Vagas vagas;
    private static VagasDAO repository;
    private static Usuario usuario;
    private static UsuarioDAO repositoryUsuario;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new VagasDAO();
                repositoryUsuario = new UsuarioDAO();
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