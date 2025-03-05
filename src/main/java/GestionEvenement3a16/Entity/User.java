

package GestionEvenement3a16.Entity;

import java.util.Collection;

public class User {
    private  int id;
    private String userName;
    private String email;
    private String password;
    private Role role;
    private Collection<Moyen_De_Transport> moyenDeTransports;

    public User() {
    }

    public static User CurrentUser;

    public User(String userName, String email, String password, Role role){
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User(int id, String userName, String email, String password, Role role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Moyen_De_Transport> getMoyenDeTransports() {
        return moyenDeTransports;
    }

    public void setMoyenDeTransports(Collection<Moyen_De_Transport> moyenDeTransports) {
        this.moyenDeTransports = moyenDeTransports;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public static User getCurrentUser() {
        return CurrentUser;
    }

    public static void setCurrentUser(User currentUser) {
        CurrentUser = currentUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

}