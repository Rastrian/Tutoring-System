package services.usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import dao.MonitoresDAO;
import dao.UsuarioDAO;
import enums.Roles;
import profiles.Monitores;
import profiles.Usuario;

public class UsuarioUtils {
    private static UsuarioDAO repository;
    private static MonitoresDAO repositoryMonitor;

    public static Integer lastUserId;

    public UsuarioUtils() {
        try {
            repository = new UsuarioDAO();
            repositoryMonitor = new MonitoresDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Usuario getUsuario(Integer id) {
        Usuario u  = null;
        for (Usuario user : repository.getAll()){
            if (user.getId().equals(id)){
                u = user;
                return u;
            }
        }
        return null;
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
}