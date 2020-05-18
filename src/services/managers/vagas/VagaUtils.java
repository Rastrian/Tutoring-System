package services.managers.vagas;

import java.io.IOException;

import dao.VagasDAO;
import profiles.Vagas;

public class VagaUtils {
    private static VagasDAO repository;

    public VagaUtils() {
        try {
            repository = new VagasDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vagas vagaExists(Integer id){
        for (Vagas e : repository.getAll()){
            if (e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }
}