package dev.trailsgroup.trailsproject.entities.enums;

public enum UserType {
    USER(1, "Usuário"),
    PROFESSOR(2, "Professor"),
    ADMIN(3, "Administrador");

    private int cod;
    private String description;


    UserType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static UserType toEnum(Integer cod) {
        if (cod == null)
            return null;

        for(UserType x : UserType.values()){
            if(cod.equals(x.getCod())){
                return x;
            }
        }
        throw new IllegalArgumentException("Tipo inválido:" + cod);
    }

}
