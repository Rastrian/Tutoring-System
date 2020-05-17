package services.managers.vagas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.VagasDAO;
import profiles.Vagas;

public class DeletarVaga implements Runnable {
    private volatile boolean closeThread;

    private static Vagas vaga;
    private static VagasDAO repository;

    public Vagas vagaExists(Integer id){
        for (Vagas e : repository.getAll()){
            if (e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new VagasDAO();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.start();
        }
    }

    public void start() {
        Integer output = null;
        System.out.println("Insira -1 para sair.");
        System.out.print("\nInsira o ID da Vaga: ");
        while (output == null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        if (output == -1){
            shutdown();
            return;
        }
        vaga = vagaExists(output);
        if (vaga == null){
            System.out.println("Vaga não encontrada.");
            shutdown();
            return;
        }
        repository.remove(vaga);
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}