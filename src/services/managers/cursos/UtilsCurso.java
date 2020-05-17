package services.managers.cursos;

import dao.CursosDAO;
import profiles.Cursos;

public class UtilsCurso {
    private static CursosDAO repository;

    public Cursos cursoExists(Integer id) {
        for (Cursos c : repository.getAll()){
            if (c.getId() == id){
                return c;
            }
        }
        return null;
    }
}