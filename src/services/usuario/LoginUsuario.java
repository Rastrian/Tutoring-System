package services.usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import dao.MonitoresDAO;
import dao.UsuarioDAO;
import enums.Roles;
import profiles.Monitores;
import profiles.Usuario;
import services.MainService;
import services.usuario.administracao.MenuAdministrador;

public class LoginUsuario extends MainService {
    private volatile boolean closeThread;

    private UsuarioDAO repository;
    private Integer lastUserId = 0;
    private MonitoresDAO repositoryMonitor;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new UsuarioDAO();
                repositoryMonitor = new MonitoresDAO();
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
                    System.out.println(u.getId()+" - "+u.getNome()+" ("+getRoleName(u.getRole())+")");
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
        this.setLastLogin(output);
        if (u.getRole().equals(0)){
            if (isMonitor(output) == false){
                System.out.println("Bem vindo "+u.getNome()+", ao menu de Alunos.");
            }else{
                System.out.println("Bem vindo "+u.getNome()+", ao menu de Monitores.");
            }
        }
        if (u.getRole().equals(2) || u.getRole().equals(1)){
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

    public void setLastLogin(Integer id) {
        lastUserId = id;
    }

    public Integer getLastUserId(){
        return lastUserId;
    }

    public boolean isMonitor(Integer userId){
        List<Monitores> monitores = repositoryMonitor.getAll();
        for (Monitores m : monitores){
            if (m.getIdusuario().equals(userId)){
                return true;
            }
        }
        return false;
    }

    public String getRoleName(Integer id) {
        ArrayList<Roles> cargos = new ArrayList<Roles>(EnumSet.allOf(Roles.class));
        for (Roles role : cargos){
            if (role.getValue().equals(id)){
                return role.getRole();
            }
        }
        return null;
    }

    public void shutdown() {
        closeThread = true;
    }
}