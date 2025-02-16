package org.example.pitest.Model;

import java.util.Objects;

public class Reservation {
    private int id;
    private User user;
    private Offre offre;
    private double price;

    public Reservation(int id, User user, Offre offre, double price) {
        this.id = id;
        this.user = user;
        this.offre = offre;
        this.price = price;
    }

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user +
                ", offre=" + offre +
                ", price=" + price +
                '}';
    }
}
