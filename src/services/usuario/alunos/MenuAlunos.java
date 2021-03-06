package services.usuario.alunos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import services.managers.edital.ResultadoEditais;
import services.managers.edital.VerEditais;
import services.managers.monitor.MonitoriasDisponiveis;

public class MenuAlunos implements Runnable{
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
                System.out.println("\nOpções:\n\n0 → Deslogar.\n1 → Ver Editais"+
                "\n2 → Resultado de Editais"+
                "\n3 → Ver monitorias disponiveis"+
                "\n\nInsira a opção desejada:");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
            VerEditais verEditais = new VerEditais();
            t = new Thread(verEditais);
        }
        if (output == 2) {
            ResultadoEditais resultadoEditais = new ResultadoEditais();
            t = new Thread(resultadoEditais);
        }
        if (output == 3) {
            MonitoriasDisponiveis monitoriasDisponiveis = new MonitoriasDisponiveis();
            t = new Thread(monitoriasDisponiveis);
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
    
    public void inUse(){
        if (inUse){
            inUse = false;
            return;
        }
        inUse = true;
    }
    
    public void shutdown() {
        closeThread = true;
    }
}
