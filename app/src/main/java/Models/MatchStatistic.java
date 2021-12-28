package Models;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import Database.DBHelper;
import Models.Helpers.HelperModel;

public class MatchStatistic implements Serializable {
    private Long Id;
    private int JudgeId;
    private int Team1Id;
    private int Team2Id;
    private int Team1Score;
    private int Team2Score;
    private String Date;

    //Complex
    private Team team1;
    private ArrayList<PlayerStatistic> playersStatisticsTeam1;

    private Team team2;
    private ArrayList<PlayerStatistic> playersStatisticsTeam2;

    private User judge;


    public MatchStatistic() {
        this.Id = HelperModel.getTicks();
        this.Date = HelperModel.getDate().toString();
    }

    //call from DB
    public MatchStatistic(Long id, int judgeId, int team1Id, int team2Id, String date) {
        this.Id = id;
        this.Team1Id = team1Id;
        this.Team2Id = team2Id;
        this.Date = date;
        this.JudgeId = judgeId;

        //GenerateTeamsAndPlayerStatistics();
    }

    public void GenerateTeamsAndPlayerStatistics(Context context) {
        try {
            DBHelper db = new DBHelper(context);
            if (db != null) {
                team1 = db.GetTeam(Team1Id);
                team2 = db.GetTeam(Team2Id);

                ArrayList<PlayerStatistic> allStatistics = db.GetPlayerStatistics(this.Id);
                if (allStatistics != null && allStatistics.size() > 0) {
                    playersStatisticsTeam1 = new ArrayList<>();
                    playersStatisticsTeam2 = new ArrayList<>();
                    for (PlayerStatistic ps :
                            allStatistics) {
                        ps.GeneratePlayer(context);
                        if (ps.getPlayer() != null && ps.getPlayer().getTeamId() == Team1Id) {
                            playersStatisticsTeam1.add(ps);
                        } else if (ps.getPlayer() != null && ps.getPlayer().getTeamId() == Team2Id) {
                            playersStatisticsTeam2.add(ps);
                        }

                    }

                }
            }
        } catch (Exception e) {
            Toast.makeText(null, "Exception on GenerateTeamsAndPlayersStatisics(): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //region Get/Set props
    public Long getId() {
        return this.Id;
    }

    public void setTeam1Id(int team1Id) {
        this.Team1Id = team1Id;
    }

    public int getTeam1Id() {
        return this.Team1Id;
    }

    public void setTeam2Id(int team2Id) {
        this.Team2Id = team2Id;
    }

    public int getTeam2Id() {
        return this.Team2Id;
    }

    public void setTeam1Score(int team1Score) {
        this.Team1Score = team1Score;
    }

    public int getTeam1Score() {
        return this.Team1Score;
    }

    public void setTeam2Score(int team2Score) {
        this.Team2Score = team2Score;
    }

    public int getTeam2Score() {
        return this.Team2Score;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getDate() {
        return this.Date;
    }

    public void setJudgeId(int judgeId) {
        JudgeId = judgeId;
    }

    public int getJudgeId() {
        return JudgeId;
    }


    //Complex

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public ArrayList<PlayerStatistic> getPlayersStatisticsTeam1() {
        return playersStatisticsTeam1;
    }

    public void setPlayersStatisticsTeam1(ArrayList<PlayerStatistic> playersStatisticsTeam1) {
        this.playersStatisticsTeam1 = playersStatisticsTeam1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public ArrayList<PlayerStatistic> getPlayersStatisticsTeam2() {
        return playersStatisticsTeam2;
    }

    public void setPlayersStatisticsTeam2(ArrayList<PlayerStatistic> playersStatisticsTeam2) {
        this.playersStatisticsTeam2 = playersStatisticsTeam2;
    }

    public void setJudge(User judge) {
        this.judge = judge;
    }

    public User getJudge() {
        return judge;
    }
    //endregion

    //region Other functions
    public int CalculateScoreTeam(int team) {
        int toRet = 0;

        ArrayList<PlayerStatistic> tmpTeam = null;
        if (team == 1) {
            tmpTeam = playersStatisticsTeam1;
        } else if (team == 2) {
            tmpTeam = playersStatisticsTeam2;
        }

        if (tmpTeam != null && tmpTeam.size() > 0) {
            for (PlayerStatistic ps :
                    tmpTeam) {
                toRet += ps.getScore();
            }
        }

        return toRet;
    }
    //endregion
}//[Class]
