package org.example.pitest.Model;

import java.time.LocalDate;
import java.util.Objects;

public class Offre {
    private int id;
    private int nbPlaces;
    private User user;
    private double prix;
    private Parcours parcours;
    private LocalDate date;
    private boolean bagage;

    public Offre(int id, int nbPlaces, User user, double prix, Parcours parcours, LocalDate date, boolean bagage) {
        this.id = id;
        this.nbPlaces = nbPlaces;
        this.user = user;
        this.prix = prix;
        this.parcours = parcours;
        this.date = date;
        this.bagage = bagage;
    }

    public Offre() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Parcours getParcours() {
        return parcours;
    }

    public void setParcours(Parcours parcours) {
        this.parcours = parcours;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isBagage() {
        return bagage;
    }

    public void setBagage(boolean bagage) {
        this.bagage = bagage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offre offre)) return false;
        return id == offre.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Offre{" +
                "id=" + id +
                ", nbPlaces=" + nbPlaces +
                ", user=" + user +
                ", prix=" + prix +
                ", parcours=" + parcours +
                ", date=" + date +
                ", bagage=" + bagage +
                '}';
    }
}
