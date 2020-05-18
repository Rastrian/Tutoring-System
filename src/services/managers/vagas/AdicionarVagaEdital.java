package services.managers.vagas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.EditaisDAO;
import dao.VagasDAO;
import profiles.Editais;
import profiles.Vagas;
import services.managers.edital.EditalUtils;

public class AdicionarVagaEdital implements Runnable {
    private volatile boolean closeThread;

    private static EditaisDAO repository;
    private static VagasDAO repositoryVagas;

    private static Vagas vaga;
    private static Editais edital;

    private static VagaUtils utils;
    private static EditalUtils utilse;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repositoryVagas = new VagasDAO();
                utils = new VagaUtils();
                utilse = new EditalUtils();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.start();
        }
    }

    public void start() {
        this.chooseVaga();
    }

    private void chooseVaga(){
        Integer output = null;
        System.out.println("Insira -1 para sair.");
        System.out.print("\nInsira o ID da vaga: ");
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
        vaga = utils.vagaExists(output);
        if (vaga == null){
            System.out.println("Vaga não encontrada.");
            shutdown();
            return;
        } 
        if (vaga.getStatus() == false){
            System.out.println("Esta vaga já foi adicionada em outro edital");
            shutdown();
            return;
        }
        this.chooseEdital();
    }

    private void chooseEdital(){
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
        if (output == -1){
            shutdown();
            return;
        }
        if ((utilse.editalExists(output)) == null){
            System.out.println("Edital não encontrado.");
            shutdown();
            return;
        } 
        edital = utilse.editalExists(output);
        vaga.setStatus(true);
        repositoryVagas.remove(vaga);
        repositoryVagas.add(vaga);
        edital.addVaga(vaga);
        edital.setStatus(true);
        repository.update(edital);
        System.out.println("\nEdital #"+edital.getId()+" atualizado.\n");
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}