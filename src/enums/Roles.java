package enums;

public enum Roles {
    ALUNO(0), PROFESSOR(1), ADMINISTRADOR(3);
     
    private final int value;

    Roles(int opt){
        value = opt;
    }

    public int getRole(){
        return value;
    }
}