package GestionEvenement3a16.Entity;

public class Moyen_De_Transport {
    private Integer id;
    private Integer prix;
    private String type;
    private Integer nbrePlaces;

    private Integer evenementId;
    //private Collection<User> users;

    public Moyen_De_Transport() {
    }

    public Moyen_De_Transport(Integer id, Integer prix, String type, Integer nbrePlaces, Integer evenementId) {
        this.id = id;
        this.prix = prix;
        this.type = type;
        this.nbrePlaces = nbrePlaces;
        this.evenementId = evenementId;
    }

    public Moyen_De_Transport(Integer id, Integer prix, String type, Integer nbrePlaces) {
        this.id = id;
        this.prix = prix;
        this.type = type;
        this.nbrePlaces = nbrePlaces;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getnbrePlaces() {
        return nbrePlaces;
    }

    public void setnbrePlaces(Integer nbrePlaces) {
        this.nbrePlaces = nbrePlaces;
    }

    public Integer getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(Integer evenementId) {
        this.evenementId = evenementId;
    }

    // public Collection<User> getUsers() {
    //     return users;
    // }

    // public void setUsers(Collection<User> users) {
    //     this.users = users;
    // }


    @Override
    public String toString() {
        return "Moyen_De_Transport{" +
                "id=" + id +
                ", prix=" + prix +
                ", type='" + type + '\'' +
                ", nbrePlaces=" + nbrePlaces +
                ", evenementId=" + evenementId +
                '}';
    }

}