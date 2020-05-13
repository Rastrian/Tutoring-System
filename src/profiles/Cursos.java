package profiles;

import java.io.Serializable;
import java.util.Objects;

public class Cursos implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public Cursos() {
    }

    public Cursos(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cursos id(Integer id) {
        this.id = id;
        return this;
    }

    public Cursos nome(String nome) {
        this.nome = nome;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cursos)) {
            return false;
        }
        Cursos cursos = (Cursos) o;
        return Objects.equals(id, cursos.id) && Objects.equals(nome, cursos.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nome='" + getNome() + "'" +
            "}";
    }

}