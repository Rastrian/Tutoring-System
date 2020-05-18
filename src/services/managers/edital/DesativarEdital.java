package services.managers.edital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.EditaisDAO;
import profiles.Editais;

public class DesativarEdital implements Runnable {
    private volatile boolean closeThread;

    private static EditaisDAO repository;
    private static Editais edital;

    private static EditalUtils utils;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new EditaisDAO();
                utils = new EditalUtils();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.start();
        }
    }

    public void start() {
        Integer output = null;
        System.out.println("Insira -1 para sair.");
        System.out.print("\nInsira o ID do edital: ");
        while (output == null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        if (output.equals(-1)){
            shutdown();
            return;
        }
        edital = utils.editalExists(output);
        if (edital == null){
            System.out.println("Edital não encontrado.");
            shutdown();
            return;
        }
        repository.remove(edital);
        edital.setStatus(false);
        repository.add(edital);
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}