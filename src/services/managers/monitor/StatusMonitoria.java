package services.managers.monitor;

import java.io.IOException;

import dao.MonitoresDAO;
import profiles.Monitores;
import services.usuario.UsuarioUtils;

public class StatusMonitoria implements Runnable {
    private volatile boolean closeThread;

    private static MonitoresDAO repository;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new MonitoresDAO();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.start();
        }
    }

    public void start() {
        Monitores monitor = null;
        Integer userId = UsuarioUtils.lastUserId;
        for (Monitores m : repository.getAll()){
            if (m.getIdusuario().equals(userId)){
                monitor = m;
            }
        }
        if (monitor != null){
            repository.remove(monitor);
            if (monitor.getStatus() == true){
                monitor.setStatus(false);
                System.out.println("Sua monitoria foi encerrada");
            }else{
                monitor.setStatus(true);
                System.out.println("A sua monitoria acaba de começar.");
            }
            repository.add(monitor);
        }
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}