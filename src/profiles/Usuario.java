package profiles;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import enums.Roles;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private Integer role;
    private Set<Cursos> disciplinas;

    public Usuario() {
    }

    public Usuario(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
        this.role = 0;
    }

    public Usuario(Integer id, String nome, Integer role) {
        this.id = id;
        this.nome = nome;
        this.role = role;
    }

    public Usuario(Integer id, String nome, Integer role, Set<Cursos> disciplinas) {
        this.id = id;
        this.nome = nome;
        this.role = role;
        this.disciplinas = disciplinas;
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

    public Integer getRole() {
        return this.role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Set<Cursos> getDisciplinas() {
        return this.disciplinas;
    }

    public void setDisciplinas(Set<Cursos> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Usuario id(Integer id) {
        this.id = id;
        return this;
    }

    public Usuario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public Usuario role(Integer role) {
        this.role = role;
        return this;
    }

    public Usuario disciplinas(Set<Cursos> disciplinas) {
        this.disciplinas = disciplinas;
        return this;
    }

    public String getRoleName() {
        for (Roles role : Roles.values()){
            if (role.getValue() == this.id){
                return role.getRole();
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(nome, usuario.nome) && Objects.equals(role, usuario.role) && Objects.equals(disciplinas, usuario.disciplinas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, role, disciplinas);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nome='" + getNome() + "'" +
            ", role='" + getRole() + "'" +
            ", disciplinas='" + getDisciplinas() + "'" +
            "}";
    }

}