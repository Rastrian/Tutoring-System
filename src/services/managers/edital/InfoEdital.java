package services.managers.edital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import dao.EditaisDAO;
import profiles.Cursos;
import profiles.Editais;
import profiles.Vagas;
import services.managers.cursos.CursoUtils;

public class InfoEdital implements Runnable {
    private volatile boolean closeThread;

    private static EditaisDAO repository;

    private static CursoUtils utils;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new EditaisDAO();
                utils = new CursoUtils();
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
        if (output.equals(-1)) {
            shutdown();
            return;
        }
        Editais edital = null;
        for (Editais e : repository.getAll()){
            if (e.getId().equals(output)){
                edital = e;
            }
        }
        if (edital != null){
            List<Vagas> vagas = edital.getVagas();
            System.out.println("Informações do Edital:\n\nID: "+edital.getId()
                +"\nVagas:");
            if (vagas != null)
                vagas.forEach(v -> {
                    Cursos c = utils.cursoExists(v.getIdcurso());
                    if (c != null)
                        System.out.println(v.getId()+" - Curso: "+c.getNome()+" (Turno: "+
                        v.getTurnoName(v.getTurno())+"/ Carga Horaria: "+v.getCarga_horaria()
                        +" Horas)");
                });
            else
                System.out.println("Não há vagas disponiveis para este edital.");
        }else{
            System.out.println("Edital não encontrado.");
        }
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}