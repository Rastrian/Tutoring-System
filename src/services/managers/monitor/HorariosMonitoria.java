package services.managers.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.MonitoresDAO;
import profiles.Monitores;
import services.usuario.UsuarioUtils;

public class HorariosMonitoria implements Runnable {
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
        String output = null;
        while (output == null) {
            System.out.print("\nPara sair insira: SAIR\n\nInsira seus horarios de monitoria: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = br.readLine();
            } catch (IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        if (output == "SAIR"){
            shutdown();
            return;
        }
        Integer userId = UsuarioUtils.lastUserId;
        Monitores monitor = null;
        for (Monitores m : repository.getAll()){
            if (m.getIdusuario().equals(userId)){
                monitor = m;
            }
        }
        if (monitor != null){
            repository.remove(monitor);
            monitor.setHorarios(output);
            System.out.println("Horarios atualizados.");
            repository.add(monitor);
        }
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}