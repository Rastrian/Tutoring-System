package services.usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.MonitoresDAO;
import dao.UsuarioDAO;
import enums.Roles;
import profiles.Monitores;
import profiles.Usuario;
import profiles.tmp.LastLogin;
import services.MainService;
import services.usuario.administracao.MenuAdministrador;

public class LoginUsuario extends MainService {
    private volatile boolean closeThread;

    private UsuarioDAO repository;
    private MonitoresDAO repositoryMonitor;
    private LastLogin lastLogin;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new UsuarioDAO();
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
                    System.out.println(u.getId()+" - "+u.getNome()+" ("+u.getRoleName()+")");
                }
                return;
            }
            Usuario u  = this.getUsuario(output);
            if (u == null){
                output = null;
                System.out.println("Usuario não encontrado.");
            }
        }
        Usuario u  = getUsuario(output);
        if (u.getRole() != null){
            lastLogin.setLastLogin(output);
        }
        if (u.getRole() == 0){
            if (getMonitor(output) == null){
                System.out.println("Bem vindo "+u.getNome()+", ao menu de Alunos.");
            }else{
                System.out.println("Bem vindo "+u.getNome()+", ao menu de Monitores.");
            }
        }
        if (u.getRole() == 2 || u.getRole() == 1){
            System.out.println("Bem vindo "+u.getNome()+", ao menu de Coordenador.");
            MenuAdministrador MAdmin = new MenuAdministrador();
            Thread t = new Thread(MAdmin);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Monitores getMonitor(Integer userId){
        for (Monitores m : repositoryMonitor.getAll()){
            if (m.getIdusuario() == userId){
                return m;
            }
        }
        return null;
    }

    public Usuario getUsuario(Integer id){
        for (Usuario u : repository.getAll()){
            if (u.getId() == id){
                return u;
            }
        }
        return null;
    }

    public String getRoleName(Integer id) {
        for (Roles role : Roles.values()){
            if (role.getValue() == id){
                return role.getRole();
            }
        }
        return null;
    }

    public void shutdown() {
        closeThread = true;
    }
}