package services.managers.cursos;

import java.io.IOException;

import dao.CursosDAO;
import profiles.Cursos;

public class CursoUtils {
    private static CursosDAO repository;

    public CursoUtils() {
        try {
            repository = new CursosDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Cursos cursoExists(Integer id) {
        for (Cursos c : repository.getAll()){
            if (c.getId().equals(id)){
                return c;
            }
        }
        return null;
    }    
}