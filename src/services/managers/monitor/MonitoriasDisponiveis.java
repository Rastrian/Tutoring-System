package services.managers.monitor;

import java.io.IOException;

import dao.MonitoresDAO;
import profiles.Cursos;
import profiles.Monitores;
import profiles.Usuario;
import profiles.Vagas;
import services.managers.cursos.CursoUtils;
import services.managers.vagas.VagaUtils;
import services.usuario.UsuarioUtils;

public class MonitoriasDisponiveis implements Runnable {
    private volatile boolean closeThread;

    private static MonitoresDAO repository;

    private static UsuarioUtils utils;
    private static VagaUtils utilsv;
    private static CursoUtils utilsc;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new MonitoresDAO();
                utils = new UsuarioUtils();
                utilsv = new VagaUtils();
                utilsc = new CursoUtils();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.start();
        }
    }

    public void start() {
        System.out.println("\nMonitorias Disponiveis:\n");
        if (repository.getAll().size() > 0){
            for (Monitores m : repository.getAll()){
                if (m.getStatus() == true){
                    String horarios = m.getHorarios();
                    if (horarios == null){
                        horarios = "Sem nenhum horario definido.";
                    }
                    Usuario u = utils.getUsuario(m.getIdusuario());
                    Vagas v = utilsv.vagaExists(m.getIdvaga());
                    Cursos c = utilsc.cursoExists(v.getIdcurso());
                    if (u != null || v != null || c != null)
                        System.out.println("\n"+u.getNome() + " - Curso: " + c.getNome() + " (Turno: " +
                        v.getTurnoName(v.getTurno()) + "/ Carga Horaria: " + v.getCarga_horaria() +
                        " Horas)\nHorarios: "+m.getHorarios()+"\n");
                }
            }
        }else{
            System.out.println("Nenhuma monitoria disponivel.");
        }
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}