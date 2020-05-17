package profiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Editais implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private List<Vagas> vagas = new ArrayList<Vagas>();
    private boolean status;

    public Editais() {
    }

    public Editais(Integer id, List<Vagas> vagas, boolean status) {
        this.id = id;
        this.vagas = vagas;
        this.status = status;
    }

    public Editais(Integer id) {
        this.id = id;
        this.vagas = null;
        this.status = true;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Vagas> getVagas() {
        return this.vagas;
    }

    public void addVaga(Vagas vaga){
        if (!vagas.contains(vaga)){
            vagas.add(vaga);
        }
    }

    public void removeVaga(Vagas vaga){
        if (vagas.contains(vaga)){
            vagas.remove(vaga);
        }
    }

    public void setVagas(List<Vagas> vagas) {
        this.vagas = vagas;
    }

    public boolean isStatus() {
        return this.status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Editais id(Integer id) {
        this.id = id;
        return this;
    }

    public Editais vagas(List<Vagas> vagas) {
        this.vagas = vagas;
        return this;
    }

    public Editais status(boolean status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Editais)) {
            return false;
        }
        Editais editais = (Editais) o;
        return Objects.equals(id, editais.id) && Objects.equals(vagas, editais.vagas) && status == editais.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vagas, status);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", vagas='" + getVagas() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }

}