package GestionEvenement3a16.Entity;


public enum Role {
    ADMIN,
    CONDUCTEUR,
    LIVREUR,
    PASSAGER;

    public static Role fromString(String role) {
        switch (role.toUpperCase()){
            case "ADMIN":
                return ADMIN;
            case "CONDUCTEUR":
                return CONDUCTEUR;
            case "LIVREUR":
                return LIVREUR;
            case "PASSAGER":
                return PASSAGER;
            default:
                throw new IllegalArgumentException("Role inconnu: " + role);
        }
    }

}