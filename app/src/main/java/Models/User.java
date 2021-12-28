package Models;

import java.io.Serializable;

public class User extends Person implements Serializable {
    private int Id;
    private String Username;
    private String Email;
    private String Password;
    private String AdminRole;

    public User() {

    }
    //call from DB
    public User(int id, String name, String surname, String username, String email, String password, String adminRole) {
    super(name,surname);
    this.Id = id;
    this.Username = username;
    this.Email = email;
    this.Password = password;
    this.AdminRole = adminRole;
    }

    //region Get/Set props
    public int getId(){
        return this.Id;
    }

    public void setUsername(String username){
        this.Username = username;
    }
    public String getUsername(){
        return this.Username;
    }

    public void setEmail(String email){
        this.Email = email;
    }
    public String getEmail(){
        return this.Email;
    }

    public void setPassword(String password){
        this.Password = password;
    }
    public String getPassword(){
        return this.Password;
    }

    public void setAdminRole(String adminRole) {
        AdminRole = adminRole;
    }

    public String getAdminRole() {
        return AdminRole;
    }
    //endregion

}//[Class]
