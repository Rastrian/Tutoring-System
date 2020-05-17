package services.managers.edital;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import dao.CursosDAO;
import dao.EditaisDAO;
import profiles.Cursos;
import profiles.Editais;
import profiles.Vagas;

public class InfoEdital implements Runnable {
    private volatile boolean closeThread;

    private static Editais edital;
    private static EditaisDAO repository;
    private static CursosDAO repositoryCursos;

    public Cursos cursoExists(Integer id) {
        for (Cursos c : repositoryCursos.getAll()){
            if (c.getId().equals(id)){
                return c;
            }
        }
        return null;
    }

    @Override
    public void run() {
        while (!closeThread) {
            edital = new Editais();
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
        edital = editalExists(output);
        if (edital != null){
            Set<Vagas> vagas = edital.getVagas();
            System.out.println("Informações do Edital:\n\nID: "+edital.getId()
                +"\nVagas:");
            if (vagas != null)
                vagas.forEach(v -> {
                    Cursos c = cursoExists(v.getIdcurso());
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

    public Editais editalExists(Integer id){
        for (Editais e : repository.getAll()){
            if (e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }

    public void shutdown() {
        closeThread = true;
    }
}