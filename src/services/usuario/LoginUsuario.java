package services.usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dao.UsuarioDAO;
import enums.Roles;
import profiles.Usuario;
import profiles.tmp.LastLogin;
import services.MainService;

public class LoginUsuario extends MainService {
    private volatile boolean closeThread;

    private UsuarioDAO repository;
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
        System.out.println("\nPara voltar ao menu principal insira -1\n");
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
            Usuario u  = getUsuario(output);
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
            System.out.println("Bem vindo "+u.getNome()+", ao menu de Alunos.");
        }
        if (u.getRole() == 1){
            System.out.println("Bem vindo "+u.getNome()+", ao menu de Professores.");
        }
        if (u.getRole() == 2){
            System.out.println("Bem vindo "+u.getNome()+", ao menu da Administração.");
        }
    }

    public Usuario getUsuario(Integer id){
        for (Usuario u : repository.getAll()){
            if (u.getId().equals(id)){
                return u;
            }
        }
        return null;
    }

    public String getRoleName(int id) {
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