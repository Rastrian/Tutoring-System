package services.usuario.alunos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
         System.out.println("\nOpções:\n\n1 → Ver Editais"+
                             "\n2 → Resultado de Editais"+
                             "\n3 → Adicionar disciplinas"+
                             "\n4 → Remover disciplinas"+
                             "\n5 → Ver monitorias disponiveis"+
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
