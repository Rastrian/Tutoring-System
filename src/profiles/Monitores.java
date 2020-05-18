package profiles;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Monitores implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idusuario;
    private Integer idvaga;
    private Set<Relatorios> relatorios = new HashSet<Relatorios>();
    private String horarios;
    private boolean status;

    public Monitores() {
    }


    public Monitores(Integer idusuario, Integer idvaga, Set<Relatorios> relatorios, String horarios, boolean status) {
        this.idusuario = idusuario;
        this.idvaga = idvaga;
        this.relatorios = relatorios;
        this.horarios = horarios;
        this.status = status;
    }

    public Integer getIdusuario() {
        return this.idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdvaga() {
        return this.idvaga;
    }

    public void setIdvaga(Integer idvaga) {
        this.idvaga = idvaga;
    }

    public Set<Relatorios> getRelatorios() {
        return this.relatorios;
    }

    public void setRelatorios(Set<Relatorios> relatorios) {
        this.relatorios = relatorios;
    }

    public String getHorarios() {
        return this.horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
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

    public Monitores idusuario(Integer idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public Monitores idvaga(Integer idvaga) {
        this.idvaga = idvaga;
        return this;
    }

    public Monitores relatorios(Set<Relatorios> relatorios) {
        this.relatorios = relatorios;
        return this;
    }

    public Monitores horarios(String horarios) {
        this.horarios = horarios;
        return this;
    }

    public Monitores status(boolean status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Monitores)) {
            return false;
        }
        Monitores monitores = (Monitores) o;
        return Objects.equals(idusuario, monitores.idusuario) && Objects.equals(idvaga, monitores.idvaga) && Objects.equals(relatorios, monitores.relatorios) && Objects.equals(horarios, monitores.horarios) && status == monitores.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idusuario, idvaga, relatorios, horarios, status);
    }

    @Override
    public String toString() {
        return "{" +
            ", idusuario='" + getIdusuario() + "'" +
            ", idvaga='" + getIdvaga() + "'" +
            ", relatorios='" + getRelatorios() + "'" +
            ", horarios='" + getHorarios() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }


}