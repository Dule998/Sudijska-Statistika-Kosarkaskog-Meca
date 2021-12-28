package com.example.matchstatistics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import Adapters.PlayersAdapter;
import Adapters.UsersAdapter;
import Database.DBHelper;
import Models.Player;
import Models.Team;
import Models.User;

public class PlayersActivity extends AppCompatActivity {

    ListView playersListView;

    ArrayList<Player> playersList = new ArrayList<>();
    PlayersAdapter playersAdapter;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        currentUser = (User) getIntent().getSerializableExtra("user_object");
        LoadPlayers();

        playersListView = findViewById(R.id.listView_players);
        playersAdapter = new PlayersAdapter(PlayersActivity.this,R.layout.layout_user_list_row,playersList, currentUser);
        playersListView.setAdapter(playersAdapter);

    }

    private void LoadPlayers() {
        DBHelper db = new DBHelper(PlayersActivity.this);
        if (db != null) {
            ArrayList<Player> tmpPlayers = db.GetPlayers();
            if (tmpPlayers != null && tmpPlayers.size() > 0) {
                playersList.addAll(tmpPlayers);
            }else{

            }
        }else{

        }
    }

}//[Class]