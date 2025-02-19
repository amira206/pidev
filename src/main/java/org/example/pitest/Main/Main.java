package org.example.pitest.Main;

import org.example.pitest.Model.Offre;
import org.example.pitest.Model.Parcours;
import org.example.pitest.Model.Reservation;
import org.example.pitest.Model.User;
import org.example.pitest.Services.OffreService;
import org.example.pitest.Services.ParcoursService;
import org.example.pitest.Services.ReservationService;
import org.example.pitest.Services.UserService;
import org.example.pitest.Utils.DataSource;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DataSource ds = new DataSource();

        // Initialisation des services
        UserService userService = UserService.getInstance();
        ParcoursService parcoursService = new ParcoursService();
        OffreService offreService = new OffreService();
        ReservationService reservationService = new ReservationService();

        // Création d'un utilisateur et ajout dans la base
        User u = new User(1, "user");
        userService.add(u);

        // Vérification que l'utilisateur a bien été ajouté
        User fetchedUser = userService.find(1);
        System.out.println("Utilisateur récupéré : " + fetchedUser);

        // Création d'un parcours et ajout
        Parcours p = new Parcours("test", 1, "a", "b");
        parcoursService.add(p);

        // Création d'une offre et ajout
        Offre o = new Offre(1, 2, u, 12, p, LocalDate.now(), true);
        offreService.add(o);

        // Création d'une réservation et ajout
        Reservation r = new Reservation(1, u, o, 12);
        reservationService.add(r);
    }
}
