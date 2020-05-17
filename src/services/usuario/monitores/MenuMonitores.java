package services.usuario.monitores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import services.managers.monitor.FinalizarMonitoria;
import services.managers.monitor.IniciarMonitoria;
import services.managers.monitor.RelatorioMonitoria;

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
                System.out.println("\nOpções:\n\n1 → Bater ponto diario.\n2 → Registrar atendimento.\n3 → Finalizar monitoria."+
                "\n\nInsira a opção desejada:");
                final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    output = Integer.parseInt(br.readLine());
                } catch (NumberFormatException | IOException e) {
                    System.out.println("Insira um formato valido");
                }
            }
        }
        if (output != null){
            inUse();
        }
        Thread t = null;
        if (output == 1) {
            IniciarMonitoria iniciarMonitoria = new IniciarMonitoria();
            t = new Thread(iniciarMonitoria);
        }
        if (output == 2) {
            RelatorioMonitoria relatorioMonitoria = new RelatorioMonitoria();
            t = new Thread(relatorioMonitoria);
        }
        if (output == 3) {
            FinalizarMonitoria finalizarMonitoria = new FinalizarMonitoria();
            t = new Thread(finalizarMonitoria);
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