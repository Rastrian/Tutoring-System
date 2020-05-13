package enums;

public enum Turnos {
    MANHA(0), TARDE(1), NOITE(3);
     
    private final int value;

    Turnos(int opt){
        value = opt;
    }

    public int getValue(){
        return value;
    }
}