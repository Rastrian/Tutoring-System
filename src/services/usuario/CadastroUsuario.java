package services.usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumSet;

import dao.UsuarioDAO;
import enums.Roles;
import profiles.Usuario;
import services.MainService;

public class CadastroUsuario extends MainService {
    private volatile boolean closeThread;

    private UsuarioDAO repository;

    private static Usuario newUser;

    @Override
    public void run() {
        while (!closeThread) {
            try {
                repository = new UsuarioDAO();
            } catch (IOException e) {
                e.printStackTrace();
            }
            newUser = new Usuario();
            this.chooseName();
        }
    }

    public void chooseName() {
        String output = null;
        while (output == null) {
            System.out.println("\nCadastrar novo usuario\n\nInsira o nome deste usuario:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = br.readLine();
            } catch (IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        newUser.setNome(output);

        this.chooseRole();
    }

    public void chooseRole(){
        ArrayList<Roles> cargos = new ArrayList<Roles>(EnumSet.allOf(Roles.class));;
        cargos.forEach(c -> System.out.println(c.getValue() + " - " + c.getRole()));
        Integer output = null;
        while (output == null){
            System.out.println("\nInsira o ID do cargo que este usuario deve possuir:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        newUser.setRole(output);

        this.chooseMatricula();
    }

    public void chooseMatricula(){
        Integer output = null;
        while (output == null){
            System.out.println("\n(Se você inserir 0 um numero é gerado automáticamente)");
            System.out.println("Insira qual será o ID de matricula:");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                output = Integer.parseInt(br.readLine());
                if (output == 0){
                    output = (int)(Math.random() * (Integer.MAX_VALUE + 1));
                }
                if (output < 0){
                    output = output * -1;
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Insira um formato valido");
            }
        }
        newUser.setId(output);                
        System.out.println("\nDados do novo usuario:\n\nMatricula: "+newUser.getId()
            +" \nNome: "+newUser.getNome()+
            "\nCargo: " + newUser.getRoleName());
    
        repository.add(newUser);
        shutdown();
    }

    public void shutdown() {
        closeThread = true;
    }
}