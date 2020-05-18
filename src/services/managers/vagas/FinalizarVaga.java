package services.managers.vagas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import dao.CandidatosDAO;
import dao.MonitoresDAO;
import dao.VagasDAO;
import profiles.Candidatos;
import profiles.Monitores;
import profiles.Usuario;
import profiles.Vagas;
import services.usuario.UsuarioUtils;

public class FinalizarVaga implements Runnable {
    private volatile boolean closeThread;

    private static Vagas vaga;
    private static VagasDAO repository;
    private static Monitores monitor;
    private static MonitoresDAO repositoryMonitores;
    private static CandidatosDAO repositoryCandidatos;

    private static UsuarioUtils utils;
    private static VagaUtils utilsv;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new VagasDAO();
                repositoryMonitores = new MonitoresDAO();
                repositoryCandidatos = new CandidatosDAO();
                utils = new UsuarioUtils();
                utilsv = new VagaUtils();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.start();
        }
    }

    public void start() {
        Integer output = null;
        while (output == null) {
            System.out.println("Insira -1 para sair.");
            System.out.print("\nInsira o ID da Vaga: ");
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
        vaga = utilsv.vagaExists(output);
        if (vaga == null){
            System.out.println("Vaga não encontrada.");
            shutdown();
            return;
        }
        if (vaga.getStatus() != true){
            System.out.println("Esta vaga está desativada.");
            shutdown();
            return;
        }
        this.chooseCandidatosFromVaga();
    }

    private void chooseCandidatosFromVaga(){
        ArrayList<Usuario> candidatos = new ArrayList<Usuario>();
        for (Candidatos c : repositoryCandidatos.getAll()){
            if (c.getIdVaga().equals(vaga.getId())){
                Usuario u = utils.getUsuario(c.getIdUsuario());
                if (u != null)
                    candidatos.add(u);
            }
        }
        System.out.println("\nListando Candidatos:\n");
        if (candidatos.size() > 0){
            candidatos.forEach(c -> {
                System.out.println(c.getId()+" - "+c.getNome());
            });
        }else{
            System.out.println("Sem candidatos.");
            shutdown();
            return;
        }
        Integer output = null;
        while (output == null) {
            System.out.println("Insira -1 para sair.");
            System.out.print("\nInsira o ID do Candidato escolhido: ");
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
        Integer id = output;
        candidatos.forEach(c -> {
            if (c.getId().equals(id)){
                monitor = new Monitores();
                monitor.setIdusuario(c.getId());
                monitor.setIdvaga(vaga.getId());
                monitor.setStatus(false);
                repositoryMonitores.add(monitor);
                repository.remove(vaga);
                vaga.setStatus(false);
                repository.add(vaga);
                System.out.println("O monitor desta vaga foi definido como o "+c.getNome());
                shutdown();
                return;
            }
        });
        System.out.println("Candidato invalido e/ou não encontrado.");
        shutdown();
        return;
    }

    public void shutdown() {
        closeThread = true;
    }
}