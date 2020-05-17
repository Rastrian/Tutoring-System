package enums;

public enum Roles {
    ALUNO(0), PROFESSOR(1), COORDENADOR(2);

    private Integer value;

    Roles(Integer opt) {
        value = opt;
    }

    public Integer getValue() {
        return value;
    }

    public String getRole() {
        return this.name();
    }

    public String getRole(Integer opt) {
        value = opt;
        return this.name();
    }

    public String getEventName(Integer id) {
        for (Roles role : Roles.values()){
            if (role.getValue() == id){
                return role.getRole();
            }
        }
        return null;
    }
}