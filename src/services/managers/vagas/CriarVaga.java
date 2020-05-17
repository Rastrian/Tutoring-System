package services.managers.vagas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumSet;

import dao.VagasDAO;
import enums.Turnos;
import profiles.Vagas;
import services.managers.cursos.UtilsCurso;

public class CriarVaga implements Runnable {
    private volatile boolean closeThread;
    private static boolean inUse;

    private static Vagas vaga;

    private static UtilsCurso utilsCurso;
    private static UtilsVagas utils;

    private static VagasDAO repository;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new VagasDAO();
            } catch (IOException e) {
                e.printStackTrace();
            }
            vaga = new Vagas();
            this.start();
        }
    }

    public void start() {
        this.chooseCurso();
    }

    private void chooseCurso(){
        Integer output = null;
        System.out.println("Insira -1 para sair.");
        System.out.print("\nInsira o ID do curso: ");
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
        if ((utilsCurso.cursoExists(output)) == null){
            System.out.println("Curso não encontrado.");
            shutdown();
            return;
        }
        vaga.setIdcurso(output);
        this.chooseTurno();
    }

    private void chooseTurno(){
        ArrayList<Turnos> cargos = new ArrayList<Turnos>(EnumSet.allOf(Turnos.class));;
        cargos.forEach(c -> System.out.println(c.getValue() + " - " + c.getTurno(c.getValue())));
        Integer output = null;
        while (output == null){
            System.out.println("\nInsira o ID do Turno desta vaga:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        vaga.setTurno(output);
        
    }

    public void chooseCargaHoraria(){
        Integer output = null;
        while (output == null){
            System.out.println("\nInsira a Carga Horaria desta vaga em HORAS:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        vaga.setCarga_horaria(output);
        this.close();
    }

    public void close(){
        Integer id = null;
        while (id == null){
            id = (repository.count() + 1);
        }
        while ((utils.vagaExists(id)) != null) {
            id++;
        }
        vaga.setId(id);
        vaga.setStatus(true);
        repository.remove(vaga);
        repository.add(vaga);
        AdicionarVagaEdital adcVaga = new AdicionarVagaEdital();
        Thread t = new Thread(adcVaga);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }

    public void inUse(){
        if (inUse){
            inUse = false;
            return;
        }
        inUse = true;
    }
}