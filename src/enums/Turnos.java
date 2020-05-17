package enums;

public enum Turnos {
    MANHA(0), TARDE(1), NOITE(2);
     
    private Integer value;

    Turnos(Integer opt){
        value = opt;
    }

    public Integer getValue(){
        return value;
    }

	public String getTurno(int opt) {
        value = opt;
		return this.name();
	}
}