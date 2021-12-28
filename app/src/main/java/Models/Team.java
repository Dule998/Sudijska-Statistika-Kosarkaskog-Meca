package Models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Team implements Serializable {

    private int Id;
    private String Name;

    //Complex
    private ArrayList<Player> players;

    public Team() {

    }

    //call from DB
    public Team(int id, String name) {
        this.Id = id;
        this.Name = name;
    }

    //region Get/Set props
    public int getId() {
        return this.Id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return this.Name;
    }
    //endregion

    //region Complex Get/Set
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    //endregion
}//[Class]
