package services.usuario;

import java.util.List;

import dao.UsuarioDAO;
import profiles.Usuario;

public class UsuarioUtils {
    private UsuarioDAO repository;

    public Usuario getUsuario(Integer id) {
        List<Usuario> usuarios = repository.getAll();
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }
}