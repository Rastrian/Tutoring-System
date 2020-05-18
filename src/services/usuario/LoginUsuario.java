package services.usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.UsuarioDAO;
import profiles.Usuario;
import services.MainService;
import services.usuario.administracao.MenuAdministrador;
import services.usuario.alunos.MenuAlunos;
import services.usuario.monitores.MenuMonitores;

public class LoginUsuario extends MainService {
    private volatile boolean closeThread;

    private UsuarioDAO repository;
    private static UsuarioUtils utils;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new UsuarioDAO();
                utils = new UsuarioUtils();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.login();
        }
    }

    public void login(){
        Integer output = null;
        System.out.println("\nPara voltar ao menu principal insira -1\nPara ver todos os logins use -2\n");
        while (output == null){
            System.out.print("\nInsira seu ID de matricula: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira um formato valido");
            }
            if (output == -1){
                shutdown();
                return;
            }
            if (output == -2){
                for (Usuario u : repository.getAll()){
                    System.out.println(u.getId()+" - "+u.getNome()+" ("+utils.getRoleName(u.getRole())+")");
                }
                return;
            }
        }
        Usuario u  = null;
        for (Usuario user : repository.getAll()){
            if (user.getId().equals(output)){
                u = user;
            }
        }
        if (u == null){
            output = null;
            System.out.println("Usuario não encontrado.");
            shutdown();
            return;
        }
        utils.setLastLogin(output);

        Thread t = null;
        if (u.getRole().equals(0)){
            if (utils.isMonitor(output) == false){
                System.out.println("Bem vindo "+u.getNome()+", ao menu de Alunos.");
                MenuAlunos menuAlunos = new MenuAlunos();
                t = new Thread(menuAlunos);
            }else{
                System.out.println("Bem vindo "+u.getNome()+", ao menu de Monitores.");
                MenuMonitores menuMonitores = new MenuMonitores();
                t = new Thread(menuMonitores);
            }
        }
        if (u.getRole().equals(2) || u.getRole().equals(1)){
            System.out.println("Bem vindo "+u.getNome()+", ao menu de Coordenador.");
            MenuAdministrador MAdmin = new MenuAdministrador();
            t = new Thread(MAdmin);
        }
        if (t != null){
            t.start();
            try {
                t.join();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        closeThread = true;
    }
}