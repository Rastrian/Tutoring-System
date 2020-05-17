package profiles;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Monitores implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer idusuario;
    private Integer idvaga;
    private Set<Relatorios> relatorios = new HashSet<Relatorios>();
    private Set<Date> presenca = new HashSet<Date>();
    private boolean status;

    public Monitores() {
    }

    public Monitores(Integer id, Integer idusuario, Integer idvaga, Set<Relatorios> relatorios, Set<Date> presenca, boolean status) {
        this.id = id;
        this.idusuario = idusuario;
        this.idvaga = idvaga;
        this.relatorios = relatorios;
        this.presenca = presenca;
        this.status = status;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<Date> getPresenca() {
        return this.presenca;
    }

    public void setPresenca(Set<Date> presenca) {
        this.presenca = presenca;
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

    public Monitores id(Integer id) {
        this.id = id;
        return this;
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

    public Monitores presenca(Set<Date> presenca) {
        this.presenca = presenca;
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
        return Objects.equals(id, monitores.id) && Objects.equals(idusuario, monitores.idusuario) && Objects.equals(idvaga, monitores.idvaga) && Objects.equals(relatorios, monitores.relatorios) && Objects.equals(presenca, monitores.presenca) && status == monitores.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idusuario, idvaga, relatorios, presenca, status);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", idusuario='" + getIdusuario() + "'" +
            ", idvaga='" + getIdvaga() + "'" +
            ", relatorios='" + getRelatorios() + "'" +
            ", presenca='" + getPresenca() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }

}