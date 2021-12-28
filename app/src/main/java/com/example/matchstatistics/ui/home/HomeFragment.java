package com.example.matchstatistics.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.matchstatistics.DashboardActivity;
import com.example.matchstatistics.MatchStatisticsActivity;
import com.example.matchstatistics.PlayersActivity;
import com.example.matchstatistics.R;
import com.example.matchstatistics.TeamsActivity;
import com.example.matchstatistics.UsersActivity;
import com.example.matchstatistics.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import Database.DBHelper;
import Models.Helpers.HelperModel;
import Models.MatchStatistic;
import Models.Player;
import Models.PlayerStatistic;
import Models.Team;
import Models.User;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;


    private User user;

    Button userList_button, nextMatch_button, teams_button, players_button;
    TextView nextMatch_textView;

    MatchStatistic tmpMatch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userList_button = binding.homeButtUsers;
        userList_button.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UsersActivity.class);
            intent.putExtra("user_object", user);
            startActivity(intent);
        });
        nextMatch_textView = binding.homeTextViewNextMatch;
        nextMatch_button = binding.homeButtNextMatch;
        nextMatch_button.setOnClickListener(v -> {
            if(tmpMatch != null){
                Intent intent = new Intent(getContext(), MatchStatisticsActivity.class);
                intent.putExtra("user_object", user);
                intent.putExtra("matchStatistic", tmpMatch);
                startActivityForResult(intent,HelperModel.ResultRegisteer_NewMatchStatistic);
            }
        });

        teams_button = binding.homeButtTeams;
        teams_button.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TeamsActivity.class);
            intent.putExtra("user_object", user);
            startActivity(intent);
        });
        players_button = binding.homeButtPlayers;
        players_button.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PlayersActivity.class);
            intent.putExtra("user_object", user);
            startActivity(intent);
        });



        DashboardActivity activity = (DashboardActivity) getActivity();
        if (activity != null) {
            user = activity.getCurrentUser();
            if (user != null) {
                UpdateUserSettings();
            }
        }

        return root;
    }

    private void SetNextMatchText() {
        nextMatch_textView.setText("Sleći meč: " + tmpMatch.getTeam1().getName() + " : " + tmpMatch.getTeam2().getName());
    }

    private void UpdateUserSettings() {
        if (user.getAdminRole().equals(HelperModel.roleAdmin)) {
            nextMatch_textView.setVisibility(View.GONE);
            nextMatch_button.setVisibility(View.GONE);

        } else if (user.getAdminRole().equals(HelperModel.roleJudge)) {
            userList_button.setVisibility(View.GONE);
            tmpMatch = GenerateRandomMatch();
            if(tmpMatch != null){
                SetNextMatchText();
            }
        }
    }

    //region Mockup Generate random match function
    private MatchStatistic GenerateRandomMatch() {
        try{
            DBHelper db = new DBHelper(getContext());
            if (db != null) {
                int team2Id = -1;
                int team1Id = HelperModel.getRandomNumber(1, 6);
                while (team2Id == team1Id || team2Id == -1) {
                    team2Id = HelperModel.getRandomNumber(1, 6);
                }
               // Toast.makeText(getContext(), "Teams ids: "+team1Id+ " - "+ team2Id, Toast.LENGTH_SHORT).show();
                Team team1 = db.GetTeam(team1Id);
                Team team2 = db.GetTeam(team2Id);
                if ((team1 != null && team2 != null) || false) {
                    MatchStatistic toRet = new MatchStatistic();
                    toRet.setTeam1Id(team1Id);
                    toRet.setTeam1(team1);
                    toRet.setTeam2Id(team2Id);
                    toRet.setTeam2(team2);
                    toRet.setJudge(user);
                    toRet.setJudgeId(user.getId());

                    ArrayList<Player> tmpTeam1Players = db.GetPlayers(team1Id);
                    if(tmpTeam1Players != null && tmpTeam1Players.size()>0){
                        team1.setPlayers(tmpTeam1Players);
                        ArrayList<PlayerStatistic> tmpTeam1PlayersStatistic = new ArrayList<>();
                        for (Player player :
                                tmpTeam1Players) {
                            PlayerStatistic tmpPlayerStatistic = new PlayerStatistic();
                            tmpPlayerStatistic.setPlayerId(player.getId());
                            tmpPlayerStatistic.setMatchId(toRet.getId());
                            tmpPlayerStatistic.setPlayer(player);

                            tmpTeam1PlayersStatistic.add(tmpPlayerStatistic);
                        }
                        toRet.setPlayersStatisticsTeam1(tmpTeam1PlayersStatistic);
                    }

                    ArrayList<Player> tmpTeam2Players = db.GetPlayers(team2Id);
                    if(tmpTeam2Players != null && tmpTeam2Players.size()>0){
                        team1.setPlayers(tmpTeam2Players);
                        ArrayList<PlayerStatistic> tmpTeam2PlayersStatistic = new ArrayList<>();
                        for (Player player :
                                tmpTeam2Players) {
                            PlayerStatistic tmpPlayerStatistic = new PlayerStatistic();
                            tmpPlayerStatistic.setPlayerId(player.getId());
                            tmpPlayerStatistic.setMatchId(toRet.getId());
                            tmpPlayerStatistic.setPlayer(player);

                            tmpTeam2PlayersStatistic.add(tmpPlayerStatistic);
                        }
                        toRet.setPlayersStatisticsTeam2(tmpTeam2PlayersStatistic);
                    }

                    return toRet;
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(), "Exception on GenerateRandomMatch(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return null;
    }
    //endregion

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HelperModel.ResultRegisteer_NewMatchStatistic) {
            if (resultCode == Activity.RESULT_OK) {
                tmpMatch = GenerateRandomMatch();
                if(tmpMatch != null){
                    SetNextMatchText();
                }
            }
        }
    }


}//[Class]