package services.managers.edital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import dao.CandidatosDAO;
import dao.CursosDAO;
import dao.EditaisDAO;
import dao.VagasDAO;
import profiles.Candidatos;
import profiles.Cursos;
import profiles.Editais;
import profiles.Vagas;
import services.usuario.UsuarioUtils;

public class VerEditais implements Runnable {
    private volatile boolean closeThread;

    private static Editais edital;
    private static Vagas vaga;
    private static Candidatos candidato;

    private static CandidatosDAO repositoryCandidatos;
    private static CursosDAO repositoryCursos;
    private static VagasDAO repositoryVagas;
    private static EditaisDAO repository;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new EditaisDAO();
                repositoryCursos = new CursosDAO();
                repositoryVagas = new VagasDAO();
                repositoryCandidatos = new CandidatosDAO();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.start();
        }
    }

    public void start() {
        this.chooseEdital();
    }

    public void chooseEdital() {
        System.out.println("\nLista de Editais Disponiveis:\n");
        for (Editais e: repository.getAll()) {
            if (e.getStatus() == true)
                System.out.println("ID: " + e.getId() + " - Nome: Edital #" + e.getId());
        }
        Integer output = null;
        while (output == null) {
            System.out.println("Insira -1 para sair.");
            System.out.print("\nInsira o ID do edital: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        if (output.equals(-1)) {
            shutdown();
            return;
        }
        edital = editalExists(output);
        if (edital == null) {
            System.out.println("Edital não encontrado.");
            shutdown();
            return;
        }
        this.chooseCursoFromEdital();
    }

    public void chooseCursoFromEdital() {
        List < Vagas > vagas = edital.getVagas();
        if (vagas == null) {
            System.out.println("Este edital não tem nenhuma vaga disponivel.");
            shutdown();
            return;
        }

        Integer output = null;
        while (output == null) {
            System.out.println("\nVagas do Edital selecionado:\n");
            vagas.forEach(v -> {
                Cursos c = cursoExists(v.getIdcurso());
                if (c != null)
                    System.out.println(v.getId() + " - Curso: " + c.getNome() + " (Turno: " +
                        v.getTurnoName(v.getTurno()) + "/ Carga Horaria: " + v.getCarga_horaria() +
                        " Horas)");
            });
            System.out.println("\nInsira -1 para sair.\n");
            System.out.print("Insira o ID da Vaga para se candidatar: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        if (output.equals(-1)) {
            shutdown();
            return;
        }
        vaga = vagaExists(output);
        if (vaga == null || vagas.equals(vaga)){
            System.out.println("Vaga não encontrada");
            shutdown();
            return;
        }
        Integer userId = UsuarioUtils.lastUserId;
        for (Candidatos c : repositoryCandidatos.getAll()){
            if (c.getIdUsuario().equals(userId)){
                if (c.getIdVaga().equals(vaga.getId())){
                    System.out.println("Você já se candidatou para esta vaga.");
                    shutdown();
                    return;
                }
            }
        }
        candidato = new Candidatos();
        candidato.setIdUsuario(userId);
        candidato.setIdVaga(vaga.getId());
        repositoryCandidatos.add(candidato);
        System.out.println("Você acaba se candidatar a vaga:");
        Cursos c = cursoExists(vaga.getIdcurso());
        System.out.println(vaga.getId() + " - Curso: " + c.getNome() + " (Turno: " +
        vaga.getTurnoName(vaga.getTurno()) + "/ Carga Horaria: " + vaga.getCarga_horaria() +
        " Horas)");
        shutdown();
    }

    public Editais editalExists(Integer id) {
        for (Editais e: repository.getAll()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public Cursos cursoExists(Integer id) {
        for (Cursos c : repositoryCursos.getAll()){
            if (c.getId().equals(id)){
                return c;
            }
        }
        return null;
    }

    public Vagas vagaExists(Integer id) {
        for (Vagas v : repositoryVagas.getAll()){
            if (v.getId().equals(id)){
                return v;
            }
        }
        return null;
    }

    public void shutdown() {
        closeThread = true;
    }
}