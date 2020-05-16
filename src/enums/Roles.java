package enums;

public enum Roles {
    ALUNO(0), PROFESSOR(1), ADMINISTRADOR(3);

    private int value;

    Roles(int opt) {
        value = opt;
    }

    public int getValue() {
        return value;
    }

    public String getRole() {
        return this.name();
    }

    public String getRole(int opt) {
        value = opt;
        return this.name();
    }

    public String getEventName(int id) {
        for (Roles role : Roles.values()){
            if (role.getValue() == id){
                return role.getRole();
            }
        }
        return null;
    }
}