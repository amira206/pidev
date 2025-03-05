package GestionEvenement3a16.Entity;
import com.google.gson.annotations.Expose;

import java.sql.Date;
import java.util.Collection;

public class Evenement {
    @Expose
    private int id;
    @Expose
    private String nom;
    @Expose
    private String description;
    @Expose
    private String lieu;
    @Expose
    private Date dateEvenement;
    @Expose
    private String imageEvenement;
    @Expose
    private Collection<Moyen_De_Transport> moyen_de_transports;

    public Evenement() {
    }

    public Evenement(String nom, String description, String lieu, Date dateEvenement, String imageEvenement) {
        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.dateEvenement = dateEvenement;
        this.imageEvenement = imageEvenement;
    }

    public Evenement(int id, String nom, String description, String lieu, Date dateEvenement, String imageEvenement) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.lieu = lieu;
        this.dateEvenement = dateEvenement;
        this.imageEvenement = imageEvenement;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getLieu() {
        return lieu;
    }

    public Date getDateEvenement() {
        return dateEvenement;
    }

    public Collection<Moyen_De_Transport> getTickets() {
        return moyen_de_transports;
    }

    public String getImageEvenement() {
        return imageEvenement;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDateEvenement(Date dateEvenement) {
        this.dateEvenement = dateEvenement;
    }
/*
    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }*/

    public void setImageEvenement(String imageEvenement) {
        this.imageEvenement = imageEvenement;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                ", dateEvenement=" + dateEvenement +
                ", imageEvenement='" + imageEvenement + '\'' +
                '}';
    }
}