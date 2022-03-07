package dev.trailsgroup.trailsproject.entities.enums;

public enum UserProfiles {
    USER(1, "ROLE_USER"),
    PROFESSOR(2, "ROLE_PROFESSOR"),
    ADMIN(3, "ROLE_ADMIN");

    private int cod;
    private String description;


    UserProfiles(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static UserProfiles toEnum(Integer cod) {
        if (cod == null)
            return null;

        for(UserProfiles x : UserProfiles.values()){
            if(cod.equals(x.getCod())){
                return x;
            }
        }
        throw new IllegalArgumentException("Tipo inválido:" + cod);
    }

}
