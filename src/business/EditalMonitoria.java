package business;

import java.io.Serializable;

public class EditalMonitoria implements Serializable{

	private static final long serialVersionUID = 1L;
	String nome;
	String disciplina;
	String turno;
	double cargaHoraria;
	Aluno[] candidatos;
	
	public EditalMonitoria(String nome, String disciplina, String turno, double cargaHoraria) {
		this.nome = nome;
		this.disciplina = disciplina;
		this.turno = turno;
		this.cargaHoraria = cargaHoraria;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public double getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(double cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Aluno[] getCandidatos() {
		return candidatos;
	}

	public void addCandidato(Aluno candidato) {
		
	}	
	
}
