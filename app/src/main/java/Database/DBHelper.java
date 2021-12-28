package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.service.autofill.FieldClassification;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import Models.MatchStatistic;
import Models.Player;
import Models.PlayerStatistic;
import Models.Team;
import Models.User;

public class DBHelper extends SQLiteOpenHelper {

    //Database properties
    private Context context;
    private static final String DATABASE_NAME = "MatchStatistics.db";
    private static final int DATABASE_VERSION = 1;

    //User table properties
    private static final String USER_TABLE_NAME = "User";
    private static final String UserTable_Column_Id = "Id";
    private static final String UserTable_Column_Name = "Name";
    private static final String UserTable_Column_Surname = "Surname";
    private static final String UserTable_Column_Email = "Email";
    private static final String UserTable_Column_Username = "Username";
    private static final String UserTable_Column_Password = "Password";
    private static final String UserTable_Column_AdminRole = "Admin";

    //Player properties
    private static final String PLAYER_TABLE_NAME = "Player";
    private static final String PlayerTable_Column_Id = "Id";
    private static final String PlayerTable_Column_TeamId = "TeamId";
    private static final String PlayerTable_Column_Name = "Name";
    private static final String PlayerTable_Column_Surname = "Surname";
    private static final String PlayerTable_Column_Position = "Position";

    //PlayerStatistics properties
    private static final String PLAYERSTATISTICS_TABLE_NAME = "PlayerStatistics";
    private static final String PlayerStatisticTable_Column_Id = "Id";
    private static final String PlayerStatisticTable_Column_PlayerId = "PlayerId";
    private static final String PlayerStatisticTable_Column_MachId = "MachId";
    private static final String PlayerStatisticTable_Column_Score = "Score";
    private static final String PlayerStatisticTable_Column_Fouls = "Fouls";
    private static final String PlayerStatisticTable_Column_Jumps = "Jumps";
    private static final String PlayerStatisticTable_Column_MatchDescription = "MatchDescription";


    //Team properties
    private static final String TEAM_TABLE_NAME = "Team";
    private static final String TeamTable_Column_Id = "Id";
    private static final String TeamTable_Column_Name = "Name";

    //MatchStatistics properties
    private static final String MATCHSTATISTICS_TABLE_NAME = "MatchStatistics";
    private static final String MatchStatisticsTable_Column_Id = "Id";
    private static final String MatchStatisticsTable_Column_JudgeId = "JudgeId";
    private static final String MatchStatisticsTable_Column_Team1Id = "Team1Id";
    private static final String MatchStatisticsTable_Column_Team2Id = "Team2Id";
    private static final String MatchStatisticsTable_Column_Team1Score = "Team1Score";
    private static final String MatchStatisticsTable_Column_Team2Score = "Team2Score";
    private static final String MatchStatisticsTable_Column_Date = "Date";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        SQLiteDatabase db = this.getWritableDatabase();
    }

    //region onCreate()
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String createUserTableQuery =
                    "CREATE TABLE " + USER_TABLE_NAME +
                            " (" + UserTable_Column_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            UserTable_Column_Name + " TEXT, " +
                            UserTable_Column_Surname + " TEXT, " +
                            UserTable_Column_Username + " TEXT UNIQUE, " +
                            UserTable_Column_Email + " TEXT UNIQUE, " +
                            UserTable_Column_Password + " TEXT, " +
                            UserTable_Column_AdminRole + " TEXT);";
            db.execSQL(createUserTableQuery);


            String createPlayerTableQuery =
                    "CREATE TABLE " + PLAYER_TABLE_NAME +
                            " (" + PlayerTable_Column_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            PlayerTable_Column_TeamId + " INTEGER, " +
                            PlayerTable_Column_Name + " TEXT, " +
                            PlayerTable_Column_Surname + " TEXT, " +
                            PlayerTable_Column_Position + " TEXT);";
            db.execSQL(createPlayerTableQuery);


            String createPlayerStatisticsTableQuery =
                    "CREATE TABLE " + PLAYERSTATISTICS_TABLE_NAME +
                            " (" + PlayerStatisticTable_Column_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            PlayerStatisticTable_Column_PlayerId + " INTEGER, " +
                            PlayerStatisticTable_Column_MachId + " LONG, " +
                            PlayerStatisticTable_Column_Score + " INTEGER, " +
                            PlayerStatisticTable_Column_Fouls + " INTEGER, " +
                            PlayerStatisticTable_Column_Jumps + " INTEGER, " +
                            PlayerStatisticTable_Column_MatchDescription + " TEXT);";
            db.execSQL(createPlayerStatisticsTableQuery);


            String createTeamTableQuery =
                    "CREATE TABLE " + TEAM_TABLE_NAME +
                            " (" + TeamTable_Column_Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            TeamTable_Column_Name + " TEXT);";
            db.execSQL(createTeamTableQuery);

            String createMatchStatsticTableQuery =
                    "CREATE TABLE " + MATCHSTATISTICS_TABLE_NAME +
                            " (" + MatchStatisticsTable_Column_Id + " LONG PRIMARY KEY, " +
                            MatchStatisticsTable_Column_JudgeId + " INTEGER, " +
                            MatchStatisticsTable_Column_Team1Id + " INTEGER, " +
                            MatchStatisticsTable_Column_Team2Id + " INTEGER, " +
                            MatchStatisticsTable_Column_Team1Score + " INTEGER, " +
                            MatchStatisticsTable_Column_Team2Score + " INTEGER, " +
                            MatchStatisticsTable_Column_Date + " TEXT);";

            db.execSQL(createMatchStatsticTableQuery);
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    //endregion

    //region onUpgrade()
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            String dropUserTableQuery = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
            String dropPlayerTableQuery = "DROP TABLE IF EXISTS " + PLAYER_TABLE_NAME;
            String dropPlayerStatisticsTableQuery = "DROP TABLE IF EXISTS " + PLAYERSTATISTICS_TABLE_NAME;
            String dropMatchStatisticsTableQuery = "DROP TABLE IF EXISTS " + MATCHSTATISTICS_TABLE_NAME;
            String dropTeamTableQuery = "DROP TABLE IF EXISTS " + TEAM_TABLE_NAME;

            db.execSQL(dropUserTableQuery);
            db.execSQL(dropPlayerTableQuery);
            db.execSQL(dropPlayerStatisticsTableQuery);
            db.execSQL(dropMatchStatisticsTableQuery);
            db.execSQL(dropTeamTableQuery);

            onCreate(db);
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    //endregion


    //region User context
    //region AddUser
    public boolean AddUser(User inUser) {
        try {
            if (inUser != null) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put(UserTable_Column_Name, inUser.getName());
                cv.put(UserTable_Column_Surname, inUser.getSurname());
                cv.put(UserTable_Column_Email, inUser.getEmail());
                cv.put(UserTable_Column_Password, inUser.getPassword());
                cv.put(UserTable_Column_AdminRole, inUser.getAdminRole());
                cv.put(UserTable_Column_Username, inUser.getUsername());

                long result = db.insert(USER_TABLE_NAME, null, cv);
                if (result != -1) {
                    Toast.makeText(context, "Nalog uspesno dodat", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    Toast.makeText(context, "Nalog sa unetom E-adresom ili korisnickim imenom vec postoji", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Nalog nije pravilno kreiran", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //endregion
    //region GetUser (by Email or Username) - for login
    public User GetUser(String inEmailOrUsername) {
        User toRet = null;
        try {
            String getUserQuery = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + UserTable_Column_Email + " ='" + inEmailOrUsername + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getUserQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(UserTable_Column_Id));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Name));
                    String surname = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Surname));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Email));
                    String password = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Password));
                    String username = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Username));
                    String adminRole = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_AdminRole));

                    toRet = new User(id, name, surname, username, email, password, adminRole);
                }
            }
            if (toRet == null) {
                //Toast.makeText(context, "Pokusaj drugi", Toast.LENGTH_LONG).show();
                getUserQuery = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + UserTable_Column_Username + " ='" + inEmailOrUsername + "'";
                db = this.getWritableDatabase();
                cursor = db.rawQuery(getUserQuery, null);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(UserTable_Column_Id));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Name));
                        String surname = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Surname));
                        String email = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Email));
                        String password = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Password));
                        String username = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Username));
                        String adminRole = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_AdminRole));

                        toRet = new User(id, name, surname, username, email, password, adminRole);
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return toRet;
    }

    //endregion
    //region GetUsers (all)
    public ArrayList<User> GetUsers() {
        ArrayList<User> toRet = null;
        try {
            String getUserQuery = "SELECT * FROM " + USER_TABLE_NAME + ";";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getUserQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                toRet = new ArrayList<>();
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(UserTable_Column_Id));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Name));
                    String surname = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Surname));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Email));
                    String password = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Password));
                    String username = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_Username));
                    String adminRole = cursor.getString(cursor.getColumnIndexOrThrow(UserTable_Column_AdminRole));

                    User tmpUsr = new User(id, name, surname, username, email, password, adminRole);

                    toRet.add(tmpUsr);
                }
            } else {
                //Toast.makeText(context, "Debug: Nothing found", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return toRet;
    }

    //endregion
    //region Delete user
    public boolean DeleteUser(User user) {
        return DeleteUser(user.getId());
    }

    public boolean DeleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE_NAME, UserTable_Column_Id + "=" + userId, null) > 0;
    }
    //endregion
    //endregion

    //region Teams context
    //region GetTeam (by Id)
    public Team GetTeam(int inId) {
        Team toRet = null;
        try {
            String getUserQuery = "SELECT * FROM " + TEAM_TABLE_NAME + " WHERE " + TeamTable_Column_Id + " = " + inId;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getUserQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(TeamTable_Column_Id));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(TeamTable_Column_Name));

                    toRet = new Team(id, name);
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return toRet;
    }

    //endregion
    //region GetTeams (all)
    public ArrayList<Team> GetTeams() {
        ArrayList<Team> toRet = null;
        try {
            String getUserQuery = "SELECT * FROM " + TEAM_TABLE_NAME + ";";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getUserQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                toRet = new ArrayList<>();
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(TeamTable_Column_Id));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(TeamTable_Column_Name));

                    Team tmpTeam = new Team(id, name);

                    toRet.add(tmpTeam);
                }
            } else {
                //Toast.makeText(context, "Debug: Nothing found", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return toRet;
    }
    //endregion
    //endregion

    //region Player context
    //region GetPlayers (all)
    public ArrayList<Player> GetPlayers() {
        ArrayList<Player> toRet = null;
        try {
            String getUserQuery = "SELECT * FROM " + PLAYER_TABLE_NAME + ";";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getUserQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                toRet = new ArrayList<>();
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerStatisticTable_Column_Id));
                    int teamId = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerTable_Column_TeamId));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable_Column_Name));
                    String surname = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable_Column_Surname));
                    String position = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable_Column_Position));


                    Player tmpUsr = new Player(id, teamId, position, name, surname);

                    toRet.add(tmpUsr);
                }
            } else {
                //Toast.makeText(context, "Debug: Nothing found", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return toRet;
    }

    //endregion
    //region GetPlayers (by team id)
    public ArrayList<Player> GetPlayers(int teamId) {
        ArrayList<Player> toRet = null;
        try {
            String getUserQuery = "SELECT * FROM " + PLAYER_TABLE_NAME + " WHERE " + PlayerTable_Column_TeamId + " = " + teamId;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getUserQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                toRet = new ArrayList<>();
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerStatisticTable_Column_Id));
                    int tmId = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerTable_Column_TeamId));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable_Column_Name));
                    String surname = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable_Column_Surname));
                    String position = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable_Column_Position));


                    Player tmpUsr = new Player(id, tmId, position, name, surname);

                    toRet.add(tmpUsr);
                }
            } else {
                //Toast.makeText(context, "Debug: Nothing found", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return toRet;
    }

    //endregion
    //region GetPlayer (by Id)
    public Player GetPlayer(int inId) {
        Player toRet = null;
        try {
            String getUserQuery = "SELECT * FROM " + PLAYER_TABLE_NAME + " WHERE " + PlayerTable_Column_Id + " = " + inId;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getUserQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerStatisticTable_Column_Id));
                    int tmId = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerTable_Column_TeamId));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable_Column_Name));
                    String surname = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable_Column_Surname));
                    String position = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable_Column_Position));

                    toRet = new Player(id, tmId, position, name, surname);

                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return toRet;
    }

    //endregion
    //endregion

    //region MatchStatistics Context
    //region GetMatchStatistics (all)
    public ArrayList<MatchStatistic> GetMatchStatistics() {
        ArrayList<MatchStatistic> toRet = null;
        try {
            String getUserQuery = "SELECT * FROM " + MATCHSTATISTICS_TABLE_NAME + ";";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getUserQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                toRet = new ArrayList<>();
                while (cursor.moveToNext()) {

                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(MatchStatisticsTable_Column_Id));
                    int judgeId = cursor.getInt(cursor.getColumnIndexOrThrow(MatchStatisticsTable_Column_JudgeId));
                    int team1id = cursor.getInt(cursor.getColumnIndexOrThrow(MatchStatisticsTable_Column_Team1Id));
                    int team2id = cursor.getInt(cursor.getColumnIndexOrThrow(MatchStatisticsTable_Column_Team2Id));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(MatchStatisticsTable_Column_Date));

                    MatchStatistic tmpMatch = new MatchStatistic(id, judgeId, team1id, team2id, date);
                    tmpMatch.GenerateTeamsAndPlayerStatistics(context);
                    toRet.add(tmpMatch);
                }
            } else {
                //Toast.makeText(context, "Debug: Nothing found", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return toRet;
    }

    //endregion
    //region AddMatchStatistics
    public boolean AddMatchStatistics(MatchStatistic matchStatistic) {
        try {
            if (matchStatistic != null) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put(MatchStatisticsTable_Column_Id, matchStatistic.getId());
                cv.put(MatchStatisticsTable_Column_JudgeId, matchStatistic.getJudgeId());
                cv.put(MatchStatisticsTable_Column_Date, matchStatistic.getDate());
                cv.put(MatchStatisticsTable_Column_Team1Id, matchStatistic.getTeam1Id());
                cv.put(MatchStatisticsTable_Column_Team2Id, matchStatistic.getTeam2Id());
                //cv.put(MatchStatisticsTable_Column_Id, matchStatistic.getUsername());

                long result = db.insert(MATCHSTATISTICS_TABLE_NAME, null, cv);
                if (result != -1) {
                    Toast.makeText(context, "Statistika meca uspesno dodata", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    //Toast.makeText(context, "Nalog sa unetom E-adresom ili korisnickim imenom vec postoji", Toast.LENGTH_SHORT).show();
                }
            } else {
                //Toast.makeText(context, "Nalog nije pravilno kreiran", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //endregion
    //endregion


    //region GetPlayerStatistics (by match id)
    public ArrayList<PlayerStatistic> GetPlayerStatistics(long matchId) {
        ArrayList<PlayerStatistic> toRet = null;
        try {
            String getUserQuery = "SELECT * FROM " + PLAYERSTATISTICS_TABLE_NAME + " WHERE " + PlayerStatisticTable_Column_MachId + " = " + matchId;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(getUserQuery, null);
            if (cursor != null && cursor.getCount() > 0) {
                toRet = new ArrayList<>();
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerStatisticTable_Column_Id));
                    int playerId = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerStatisticTable_Column_PlayerId));
                    int matchId_p = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerStatisticTable_Column_MachId));
                    int score = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerStatisticTable_Column_Score));
                    int fouls = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerStatisticTable_Column_Fouls));
                    int jumps = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerStatisticTable_Column_Jumps));


                    PlayerStatistic tmpStatistic = new PlayerStatistic(id, playerId, matchId_p, score, fouls, jumps, "");

                    toRet.add(tmpStatistic);
                }
            } else {
                //Toast.makeText(context, "Debug: Nothing found", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return toRet;
    }

    //region AddPlayerStatistics
    public boolean AddPlayerStatistics(PlayerStatistic playerStatistic) {
        try {
            if (playerStatistic != null) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put(PlayerStatisticTable_Column_MachId, playerStatistic.getMatchId());
                cv.put(PlayerStatisticTable_Column_PlayerId, playerStatistic.getPlayerId());
                cv.put(PlayerStatisticTable_Column_Score, playerStatistic.getScore());
                cv.put(PlayerStatisticTable_Column_Fouls, playerStatistic.getFouls());
                cv.put(PlayerStatisticTable_Column_Jumps, playerStatistic.getJumps());
                //cv.put(PlayerStatisticTable_Column_Id, matchStatistic.getUsername());

                long result = db.insert(PLAYERSTATISTICS_TABLE_NAME, null, cv);
                if (result != -1) {
                    //Toast.makeText(context, "Statistika meca uspesno dodata", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    //Toast.makeText(context, "Nalog sa unetom E-adresom ili korisnickim imenom vec postoji", Toast.LENGTH_SHORT).show();
                }
            } else {
                //Toast.makeText(context, "Nalog nije pravilno kreiran", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }
    //endregion


}//[Class]