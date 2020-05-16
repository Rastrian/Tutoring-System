package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import services.usuario.CadastroUsuario;
import services.usuario.LoginUsuario;

public class MainService implements Runnable {
    private volatile boolean closeThread;
    private static boolean inUse;

    @Override
    public void run() {
        while (!closeThread) {
            this.start();
        }
    }

    public void start() {
        System.out.println("\nOpções:\n\n1 → Entrar com Usuario\n2 → Cadastrar usuario\n\nInsira a opção desejada:");
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
        if (output == 1) {
            inUse();
            System.out.println("Iniciando processo de login...");
            LoginUsuario Login = new LoginUsuario();
            Thread t = new Thread(Login);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            inUse();
        }
        if (output == 2) {
            inUse();
            System.out.println("Iniciando processo de cadastro...");
            CadastroUsuario cadastro = new CadastroUsuario();
            Thread t = new Thread(cadastro);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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