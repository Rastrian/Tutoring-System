package services.managers.edital;

import java.io.IOException;

import dao.EditaisDAO;
import profiles.Editais;

public class EditalUtils {
    private static EditaisDAO repository;

    public EditalUtils() {
        try {
            repository = new EditaisDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Editais editalExists(Integer id){
        for (Editais e : repository.getAll()){
            if (e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }
}