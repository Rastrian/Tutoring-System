package services.managers.vagas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import dao.EditaisDAO;
import dao.VagasDAO;
import profiles.Editais;
import profiles.Vagas;
import services.managers.edital.UtilsEdital;

public class AdicionarVagaEdital implements Runnable {
    private volatile boolean closeThread;

    private static EditaisDAO repository;
    private static VagasDAO repositoryVagas;

    private static Vagas vaga;
    private static Editais edital;

    private static UtilsVagas utils;
    private static UtilsEdital utilsEdital;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new EditaisDAO();
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
        if ((utils.vagaExists(output)) == null){
            System.out.println("Vaga não encontrada.");
            shutdown();
            return;
        } 
        vaga = utils.vagaExists(output);
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
        if ((utilsEdital.editalExists(output)) == null){
            System.out.println("Edital não encontrado.");
            shutdown();
        } 
        edital = utilsEdital.editalExists(output);
        Set<Vagas> vagas = edital.getVagas();
        repositoryVagas.remove(vaga);
        vaga.setStatus(true);
        repositoryVagas.add(vaga);
        vagas.add(vaga);
        edital.setStatus(true);
        edital.setVagas(vagas);
        repository.update(edital);
        System.out.println("\nEdital #"+edital.getId()+" atualizado.\n");
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}