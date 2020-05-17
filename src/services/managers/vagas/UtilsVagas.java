package services.managers.vagas;

import dao.VagasDAO;
import profiles.Vagas;

public class UtilsVagas {
    private static VagasDAO repository;

    public Vagas vagaExists(Integer id){
        for (Vagas e : repository.getAll()){
            if (e.getId() == id){
                return e;
            }
        }
        return null;
    }
}