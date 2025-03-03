/*package tests;

import models.User;
import services.UserService;

import java.sql.SQLException;

public class Main {

    public static void main(String [] args){


        //MyDabase d=MyDabase.getInstance();

        UserService ps=new UserService();

        try {
            ps.add(new User("test","test@gmail.com","test","ADMIN"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            ps.update(new User(1,"test","test@gmail.com","test","ADMIN"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        try {
           System.out.println(ps.list());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
*/