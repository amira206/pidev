package piDev.GestionLIvariason.Livraison.entities;

public class packag {
    int id_packag;
    int weight_packag;
    String description_packag;

    public packag() {
    }
    public packag(int id_packag, int weight_packag, String description_packag) {
        this.id_packag = id_packag;
        this.weight_packag = weight_packag;
        this.description_packag = description_packag;
    }
    public packag(int weight_packag, String description_packag) {
        this.weight_packag = weight_packag;
        this.description_packag = description_packag;
    }
    public int getId_packag() {
        return id_packag;
    }
    public void setId_packag(int id_packag) {
        this.id_packag = id_packag;
    }
    public int getWeight_packag() {
        return weight_packag;
    }
    public void setWeight_packag(int weight_packag) {
        this.weight_packag = weight_packag;
    }
    public String getDescription_packag() {
        return description_packag;
    }
    public void setDescription_packag(String description_packag) {
        this.description_packag = description_packag;
    }
}
