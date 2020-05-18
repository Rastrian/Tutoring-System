package profiles;

import java.io.Serializable;
import java.util.Objects;

public class Candidatos implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer idVaga;
    private Integer idUsuario;

    public Candidatos() {
    }

    public Candidatos(Integer id, Integer idVaga, Integer idUsuario) {
        this.id = id;
        this.idVaga = idVaga;
        this.idUsuario = idUsuario;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdVaga() {
        return this.idVaga;
    }

    public void setIdVaga(Integer idVaga) {
        this.idVaga = idVaga;
    }

    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Candidatos id(Integer id) {
        this.id = id;
        return this;
    }

    public Candidatos idVaga(Integer idVaga) {
        this.idVaga = idVaga;
        return this;
    }

    public Candidatos idUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Candidatos)) {
            return false;
        }
        Candidatos candidatos = (Candidatos) o;
        return Objects.equals(id, candidatos.id) && Objects.equals(idVaga, candidatos.idVaga) && Objects.equals(idUsuario, candidatos.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idVaga, idUsuario);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", idVaga='" + getIdVaga() + "'" +
            ", idUsuario='" + getIdUsuario() + "'" +
            "}";
    }

}