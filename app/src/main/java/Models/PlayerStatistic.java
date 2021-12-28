package Models;

import android.content.Context;

import com.example.matchstatistics.ui.login.LoginActivity;

import java.io.Serializable;

import Database.DBHelper;

public class PlayerStatistic implements Serializable {
    private int id;
    private int playerId;
    private long matchId;

    private int score;
    private int fouls;
    private int jumps;
    private String gameDescription;

    //Complex
    private Player player;

    public PlayerStatistic() {

    }

    //call from DB
    public PlayerStatistic(int id, int playerId, int matchId, int score, int fouls, int jumps, String gameDescription) {
        this.id = id;
        this.playerId = playerId;
        this.matchId = matchId;
        this.score = score;
        this.fouls = fouls;
        this.jumps = jumps;
        this.gameDescription = gameDescription;

        //GeneratePlayer();
    }

    public void GeneratePlayer(Context context) {
        try {
            DBHelper db = new DBHelper(context);
            if(db != null){
                this.player = db.GetPlayer(playerId);
            }
        }catch (Exception e){

        }
    }

    //region Get/Set props
    public int getId() {
        return this.id;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getMatchId() {
        return this.matchId;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setFouls(int fouls) {
        this.fouls = fouls;
    }

    public int getFouls() {
        return this.fouls;
    }

    public void setJumps(int jumps) {
        this.jumps = jumps;
    }

    public int getJumps() {
        return this.jumps;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }
    public String getGameDescription() {
        return this.gameDescription;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    //endregion
}//[Class]
