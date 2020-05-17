package profiles;

import java.io.Serializable;
import java.util.Objects;

import enums.Turnos;

public class Vagas implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer idcurso;
    private Integer turno;
    private Integer carga_horaria;
    private boolean status;

    public Vagas() {
    }

    public Vagas(Integer id, Integer idcurso, Integer turno, Integer carga_horaria, boolean status) {
        this.id = id;
        this.idcurso = idcurso;
        this.turno = turno;
        this.carga_horaria = carga_horaria;
        this.status = status;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdcurso() {
        return this.idcurso;
    }

    public void setIdcurso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public Integer getTurno() {
        return this.turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public Integer getCarga_horaria() {
        return this.carga_horaria;
    }

    public void setCarga_horaria(Integer carga_horaria) {
        this.carga_horaria = carga_horaria;
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

    public Vagas id(Integer id) {
        this.id = id;
        return this;
    }

    public Vagas idcurso(Integer idcurso) {
        this.idcurso = idcurso;
        return this;
    }

    public Vagas turno(Integer turno) {
        this.turno = turno;
        return this;
    }

    public Vagas carga_horaria(Integer carga_horaria) {
        this.carga_horaria = carga_horaria;
        return this;
    }

    public Vagas status(boolean status) {
        this.status = status;
        return this;
    }

    public String getTurnoName(Integer id) {
        for (Turnos t : Turnos.values()){
            if (t.getValue() == this.id){
                return t.getTurno(id);
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Vagas)) {
            return false;
        }
        Vagas vagas = (Vagas) o;
        return Objects.equals(id, vagas.id) && Objects.equals(idcurso, vagas.idcurso) && Objects.equals(turno, vagas.turno) && Objects.equals(carga_horaria, vagas.carga_horaria) && status == vagas.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idcurso, turno, carga_horaria, status);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", idcurso='" + getIdcurso() + "'" +
            ", turno='" + getTurno() + "'" +
            ", carga_horaria='" + getCarga_horaria() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }

}