package services.usuario.monitores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import services.managers.monitor.HorariosMonitoria;
import services.managers.monitor.StatusMonitoria;

public class MenuMonitores implements Runnable {
    private volatile boolean closeThread;
    private static boolean inUse;

    @Override
    public void run() {
        while (!closeThread) {
            this.start();
        }
    }

    public void start() {
        Integer output = null;
        while (output == null) {
            if (!inUse) {
                System.out.println("\nOpções:\n\n0 → Deslogar.\n1 → Alterar status da monitoria.\n2 → Registrar atendimento.\n3 → Definir horarios."+
                "\n\nInsira a opção desejada:");
                final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    output = Integer.parseInt(br.readLine());
                } catch (NumberFormatException | IOException e) {
                    System.out.println("Insira um formato valido");
                }
            }
        }
        if (output == 0) {
            shutdown();
            return;
        }
        if (output != null){
            inUse();
        }
        Thread t = null;
        if (output == 1) {
            StatusMonitoria statusMonitoria = new StatusMonitoria();
            t = new Thread(statusMonitoria);
        }
        if (output == 2) {
            System.out.println("Não implementado - TODO");
        }
        if (output == 3) {
            HorariosMonitoria horariosMonitoria = new HorariosMonitoria();
            t = new Thread(horariosMonitoria);
        }
        if (t != null){
            t.start();
            try {
                t.join();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (output != null){
            inUse();
        }
    }

    public void shutdown() {
        closeThread = true;
    }

    public void inUse(){
        if (inUse){
            inUse = false;
            return;
        }
        inUse = true;
    }
}