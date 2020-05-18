package services.managers.edital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import dao.EditaisDAO;
import dao.MonitoresDAO;
import profiles.Cursos;
import profiles.Editais;
import profiles.Monitores;
import profiles.Vagas;
import services.managers.cursos.CursoUtils;
import services.usuario.UsuarioUtils;

public class ResultadoEditais implements Runnable {
    private volatile boolean closeThread;
    private static Editais edital;

    private static EditaisDAO repository;
    private static MonitoresDAO repositoryMonitores;

    private static UsuarioUtils utils;
    private static EditalUtils utilse;
    private static CursoUtils utilsc;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new EditaisDAO();
                repositoryMonitores = new MonitoresDAO();
                utils = new UsuarioUtils();
                utilse = new EditalUtils();
                utilsc = new CursoUtils();
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
        System.out.println("\nLista de Editais:\n");
        for (Editais e: repository.getAll()) {
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
        edital = utilse.editalExists(output);
        if (edital == null) {
            System.out.println("Edital não encontrado.");
            shutdown();
            return;
        }
        this.listResultsByVagas();
    }

    public void listResultsByVagas(){
        List < Vagas > vagas = edital.getVagas();
        System.out.println("\nResultados do Edital selecionado:\n");
        vagas.forEach(v -> {
            Cursos c = utilsc.cursoExists(v.getIdcurso());
            if (c != null)
                System.out.println(v.getId() + " - Curso: " + c.getNome() + " (Turno: " +
                    v.getTurnoName(v.getTurno()) + "/ Carga Horaria: " + v.getCarga_horaria() +
                    " Horas)");
                Monitores monitor = null;
                if (v.getStatus() == false){
                    for (Monitores m : repositoryMonitores.getAll()){
                        if (m.getIdvaga().equals(v.getId())){
                            monitor = m;
                        }
                    }
                }
                if (monitor == null){
                    System.out.println("Resultado: Não escolhido.\n");
                }else{
                    System.out.println("Resultado: "+utils.getUsuario(monitor.getIdusuario()).getNome()+"\n");
                }
        });
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}