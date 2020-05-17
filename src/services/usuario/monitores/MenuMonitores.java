package services.usuario.monitores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        System.out.println("\nOpções:\n\n1 → Bater ponto diario.\n2 → Registrar atendimento.\n3 → Finalizar monitoria."+
        "\n\nInsira a opção desejada:");
        Integer output = null;
        while (output == null) {
            if (!inUse) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    output = Integer.parseInt(br.readLine());
                } catch (NumberFormatException | IOException e) {
                    System.out.println("Insira um formato valido");
                }
            }
        }
        if (output == 1) {}
        if (output == 2) {}
        if (output == 3) {}
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