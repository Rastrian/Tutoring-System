package services.usuario.administracao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import services.MainService;
import services.managers.cursos.CriarCurso;
import services.managers.cursos.DeletarCurso;
import services.managers.cursos.ListarCursos;
import services.managers.edital.CriarEdital;
import services.managers.edital.DesativarEdital;
import services.managers.edital.InfoEdital;
import services.managers.edital.ListarEditais;
import services.managers.edital.ListarEditaisDesativados;
import services.managers.vagas.AdicionarVagaEdital;
import services.managers.vagas.CriarVaga;
import services.managers.vagas.DeletarVaga;

public class MenuAdministrador extends MainService {
    private volatile boolean closeThread;
    private static boolean inUse;

    @Override
    public void run() {
        while (!closeThread) {
            this.start();
        }
    }

    public void start() {
        System.out.println("\nOpções:\n\n1 → Criar Curso.\n2 → Deletar Curso.\n3 → Listar cursos."
                + "\n4 → Criar Edital.\n5 → Desativar Edital.\n6 → Informações sobre um Edital."
                + "\n7 → Listar Editais Disponiveis.\n8 → Listar Editais Desativados."
                + "\n9 → Criar Vaga.\n10 → Deletar Vaga.\n11 → Adicionar Vaga a Edital."
                + "\n\nInsira a opção desejada:");
        Integer output = null;
        while (output == null) {
            if (!inUse) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    output = Integer.parseInt(br.readLine());
                } catch (NumberFormatException | IOException e) {
                    System.out.println("Insira um formato valido");
                }
            }
        }
        if (output != null){
            inUse();
        }
        Thread t = null;
        if (output == 1) {
            CriarCurso criarCurso = new CriarCurso();
            t = new Thread(criarCurso);
        }
        if (output == 2) {
            DeletarCurso deletarCurso = new DeletarCurso();
            t = new Thread(deletarCurso);
        }
        if (output == 3) {
            ListarCursos listarCursos = new ListarCursos();
            t = new Thread(listarCursos);
        }
        if (output == 4) {
            CriarEdital criarEdital = new CriarEdital();
            t = new Thread(criarEdital);
        }
        if (output == 5) {
            DesativarEdital desativarEdital = new DesativarEdital();
            t = new Thread(desativarEdital);
        }
        if (output == 6) {
            InfoEdital infoEdital = new InfoEdital();
            t = new Thread(infoEdital);
        }
        if (output == 7) {
            ListarEditais listarEdital = new ListarEditais();
            t = new Thread(listarEdital);
        }
        if (output == 8) {
            ListarEditaisDesativados listarEditalDesativado = new ListarEditaisDesativados();
            t = new Thread(listarEditalDesativado);
        }
        if (output == 9) {
            CriarVaga criarVaga = new CriarVaga();
            t = new Thread(criarVaga);
        }
        if (output == 10) {
            DeletarVaga deletarVaga = new DeletarVaga();
            t = new Thread(deletarVaga);

        }
        if (output == 11) {
            AdicionarVagaEdital adcVaga = new AdicionarVagaEdital();
            t = new Thread(adcVaga);
        }
        if (t != null){
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (output != null){
            inUse();
        }
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