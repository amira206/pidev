package piDev.GestionLIvariason.Livraison.entities;

public class livraison {
    int id_livrai;
    String start_location;
    String delivery_location;
    boolean is_delivered;

    public livraison() {
    }
    public livraison(  String start_location, String delivery_location, boolean is_delivered) {
        this.start_location = start_location;
        this.delivery_location = delivery_location;
        this.is_delivered = is_delivered;
    }
    public livraison(int id_livrai, String start_location, String delivery_location, boolean is_delivered) {
        this.id_livrai = id_livrai;
        this.start_location = start_location;
        this.delivery_location = delivery_location;
        this.is_delivered = is_delivered;
    }
    public int getId_livrai() {
        return id_livrai;
    }

    public void setId_livrai(int id_livrai) {
        this.id_livrai = id_livrai;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getDelivery_location() {
        return delivery_location;
    }

    public void setDelivery_location(String delivery_location) {
        this.delivery_location = delivery_location;
    }

    public boolean isIs_delivered() {
        return is_delivered;
    }

    public void setIs_delivered(boolean is_delivered) {
        this.is_delivered = is_delivered;
    }

    @Override
    public String toString() {
        return "livraison{" +
                "id_livrai=" + id_livrai +
                ", start_location='" + start_location + '\'' +
                ", delivery_location='" + delivery_location + '\'' +
                ", is_delivered=" + is_delivered +
                '}';
    }
}
