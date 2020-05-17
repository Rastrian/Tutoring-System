package services.managers.edital;

import dao.EditaisDAO;
import profiles.Editais;

public class UtilsEdital {
    private static EditaisDAO repository;

    public Editais editalExists(Integer id){
        for (Editais e : repository.getAll()){
            if (e.getId() == id){
                return e;
            }
        }
        return null;
    }
}