package Models;

import java.io.Serializable;
import java.util.Date;

public class Person implements Serializable {
    private String Name;
    private String Surname;
    private Date DateOfBirth;

    public Person() {

    }
    //call from DB
    public Person(String Name, String Surname) {
        this.Name = Name;
        this.Surname = Surname;
    }

    //region Get/Set props
    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return this.Name;
    }

    public void setSurname(String surname) {
        this.Surname = surname;
    }

    public String getSurname() {
        return this.Surname;
    }


    public String getFullName(){
        return Name + " " + Surname;
    }
    //endregion
}//[Class]
