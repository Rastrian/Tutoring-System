package profiles;

import java.io.Serializable;
import java.util.Objects;

public class Relatorios implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer idmonitor;
    private Integer idusuario;
    private String comment;

    public Relatorios() {
    }

    public Relatorios(Integer id, Integer idmonitor, Integer idusuario, String comment) {
        this.id = id;
        this.idmonitor = idmonitor;
        this.idusuario = idusuario;
        this.comment = comment;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdmonitor() {
        return this.idmonitor;
    }

    public void setIdmonitor(Integer idmonitor) {
        this.idmonitor = idmonitor;
    }

    public Integer getIdusuario() {
        return this.idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Relatorios id(Integer id) {
        this.id = id;
        return this;
    }

    public Relatorios idmonitor(Integer idmonitor) {
        this.idmonitor = idmonitor;
        return this;
    }

    public Relatorios idusuario(Integer idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public Relatorios comment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Relatorios)) {
            return false;
        }
        Relatorios relatorios = (Relatorios) o;
        return Objects.equals(id, relatorios.id) && Objects.equals(idmonitor, relatorios.idmonitor) && Objects.equals(idusuario, relatorios.idusuario) && Objects.equals(comment, relatorios.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idmonitor, idusuario, comment);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", idmonitor='" + getIdmonitor() + "'" +
            ", idusuario='" + getIdusuario() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }

}