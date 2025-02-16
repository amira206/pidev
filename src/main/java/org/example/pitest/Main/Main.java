package org.example.pitest.Main;

import org.example.pitest.Model.Offre;
import org.example.pitest.Model.Parcours;
import org.example.pitest.Model.Reservation;
import org.example.pitest.Model.User;
import org.example.pitest.Services.OffreService;
import org.example.pitest.Services.ParcoursService;
import org.example.pitest.Services.ReservationService;
import org.example.pitest.Utils.DataSource;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DataSource ds = new DataSource();
        User u = new User(1,"user");
        Parcours p = new Parcours("test",1,"a","b");
        Offre o = new Offre(1,2,u,12,p, LocalDate.now(),true);
        Reservation r = new Reservation(1,u,o,12);
        OffreService os = new OffreService();
        ParcoursService ps = new ParcoursService();
        ReservationService rs = new ReservationService();
        os.add(o);
        ps.add(p);
        rs.add(r);
    }
}
