package com.example.matchstatistics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import Adapters.PlayersAdapter;
import Adapters.TeamsAdapter;
import Database.DBHelper;
import Models.Player;
import Models.Team;
import Models.User;

public class TeamsActivity extends AppCompatActivity {


    ListView teamsListView;

    ArrayList<Team> teamsList = new ArrayList<>();
    TeamsAdapter teamsAdapter;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        currentUser = (User) getIntent().getSerializableExtra("user_object");
        LoadTeams();

        teamsListView = findViewById(R.id.listView_teams);
        teamsAdapter = new TeamsAdapter(TeamsActivity.this,R.layout.layout_team_list_row,teamsList, currentUser);
        teamsListView.setAdapter(teamsAdapter);
    }


    private void LoadTeams() {
        DBHelper db = new DBHelper(TeamsActivity.this);
        if (db != null) {
            ArrayList<Team> tmpTeams = db.GetTeams();
            if (tmpTeams != null && tmpTeams.size() > 0) {
                teamsList.addAll(tmpTeams);
            }else{

            }
        }else{

        }
    }
}//[Class]