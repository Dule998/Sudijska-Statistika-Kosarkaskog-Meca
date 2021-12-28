package Models;

import java.io.Serializable;

public class Player extends Person implements Serializable {

    private int Id;
    private int TeamId;
    private String Position;

    public Player(){

    }
    //call from DB
    public Player(int id, int teamId,String position, String name, String surname){
        super(name,surname);
        this.Id = id;
        this.TeamId = teamId;
        this.Position = position;
    }

    //region Get/Set props
    public int getId(){
        return this.Id;
    }

    public void setTeamId(int teamId){
        this.TeamId = teamId;
    }
    public int getTeamId(){
        return this.TeamId;
    }

    public void setPosition(String position){
        this.Position = position;
    }
    public String getPosition(){
        return this.Position;
    }
    //endregion
}//[Class]
