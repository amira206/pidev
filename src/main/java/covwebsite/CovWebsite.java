package covwebsite;
import piDev.GestionLIvariason.Livraison.services.ServiceLivraison;
import piDev.GestionLIvariason.Livraison.services.ServicePackag;
import piDev.GestionLIvariason.Livraison.tools.DataSource;
import piDev.GestionLIvariason.Livraison.entities.livraison;
import piDev.GestionLIvariason.Livraison.entities.packag;
import piDev.GestionLIvariason.Livraison.entities.DistanceCalculator;

public class CovWebsite {
    public static void main(String[] args) {

        //DataSource.getInstance();
       // livraison l = new livraison("7777","777",true);
      // ServiceLivraison sl = new ServiceLivraison();
        // sl.ajouter(l);

        //livraison l2 = new livraison(5,"lll","lll",true);
        // sl.modifier(l2);
       // sl.supprimer(l2);
        packag p = new packag(7,"ssss",18);
        ServicePackag sp = new ServicePackag();
        sp.ajouter(p);
       /* try {
            String city1 = "manouba";
            String city2 = "ariana";
            String apiKey = "e2c53ae1de5946e78e463a924771741d"; // Replace with your actual OpenCage API key

            // Call the method to get the distance between two cities
            int distance = DistanceCalculator.getDistanceBetweenCities(city1, city2, apiKey);
            System.out.println("The distance between " + city1 + " and " + city2 + " is: " + distance + " km");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }*/


    }
}
