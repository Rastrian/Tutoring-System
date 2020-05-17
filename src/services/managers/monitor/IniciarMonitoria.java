package services.managers.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.MonitoresDAO;
import dao.UsuarioDAO;
import profiles.Monitores;
import profiles.Usuario;

public class IniciarMonitoria implements Runnable {
    private volatile boolean closeThread;

    private static Monitores monitores;
    private static MonitoresDAO repository;
    private static Usuario usuario;
    private static UsuarioDAO repositoryUsuario;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new MonitoresDAO();
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