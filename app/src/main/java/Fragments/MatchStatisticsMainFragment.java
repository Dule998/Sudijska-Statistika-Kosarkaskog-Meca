package Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matchstatistics.R;
import com.example.matchstatistics.databinding.FragmentHomeBinding;
import com.example.matchstatistics.databinding.FragmentMatchStatisticsMainBinding;

import java.util.ArrayList;

import Database.DBHelper;
import Models.Helpers.HelperModel;
import Models.MatchStatistic;
import Models.PlayerStatistic;
import Models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchStatisticsMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchStatisticsMainFragment extends Fragment {


    private FragmentMatchStatisticsMainBinding binding;

    TextView team1points, team1fouls, team1jumps, team1assists, team2points, team2fouls, team2jumps, team2assists,
            judgeName, date, team1mvp, team2mvp, team1Name, team2Name;

    Button saveMatchButton;

    AlertDialog.Builder builder;


    private static final String ARG_USER = "user_object";
    private static final String ARG_MATCH_STATISTIC = "match_statistic";


    private MatchStatistic matchStatistic;
    private User user;

    public MatchStatisticsMainFragment() {

    }


    public static MatchStatisticsMainFragment newInstance(User user, MatchStatistic matchStatistic) {
        MatchStatisticsMainFragment fragment = new MatchStatisticsMainFragment();
        try {
            Bundle args = new Bundle();
            args.putSerializable(ARG_USER, user);
            args.putSerializable(ARG_MATCH_STATISTIC, matchStatistic);
            fragment.setArguments(args);
        } catch (Exception e) {
            Toast.makeText(fragment.getActivity(), "Exception on newInstance(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getArguments() != null) {
                this.user = (User) getArguments().getSerializable(ARG_USER);
                this.matchStatistic = (MatchStatistic) getArguments().getSerializable(ARG_MATCH_STATISTIC);
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Exception on onCreate(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMatchStatisticsMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        team1mvp = binding.frMsStatisticMvp1;
        team1points = binding.frMsTeam1score;
        team1fouls = binding.frMsStatisticFouls1;
        team1jumps = binding.frMsStatisticJumps1;
        team1assists = binding.frMsStatisticAsists1;
        team1assists.setText(String.valueOf(HelperModel.getRandomNumber(10, 35)));

        team2mvp = binding.frMsStatisticMvp2;
        team2points = binding.frMsTeam2score;
        team2fouls = binding.frMsStatisticFouls2;
        team2jumps = binding.frMsStatisticJumps2;
        team2assists = binding.frMsStatisticAsists2;
        team2assists.setText(String.valueOf(HelperModel.getRandomNumber(10, 35)));

        judgeName = binding.frMsStatisticJudge;
        team1Name = binding.frMsTeam1Name;
        team2Name = binding.frMsTeam2Name;
        date = binding.frMsStatisticMatchDate;

        saveMatchButton = binding.saveMatchButton;
        if (saveMatchButton != null) {
            saveMatchButton.setOnClickListener(v -> {
                builder = new AlertDialog.Builder(getContext());
                AlertDialog dialog = builder.setTitle("Čuvanje meča")
                        .setMessage("Da li ste sigurni da želite da sačuvate meč?")
                        .setPositiveButton("Da", dialogClickListener)
                        .setNegativeButton("Ne", dialogClickListener)
                        .show();
            });
        }


        SetMatchInfo();
        RecalculateMatchAndPopulateStatistic();

        return root;
        //return inflater.inflate(R.layout.fragment_match_statistics_main, container, false);
    }



    private void SetMatchInfo() {
        if (matchStatistic != null && matchStatistic.getTeam1() != null
                && matchStatistic.getTeam2() != null && user != null) {
            date.setText(matchStatistic.getDate().toString());
            judgeName.setText(user.getFullName());
            team1Name.setText(matchStatistic.getTeam1().getName());
            team2Name.setText(matchStatistic.getTeam2().getName());
        }
    }


    private void RecalculateMatchAndPopulateStatistic() {
        try {
            if (matchStatistic != null && matchStatistic.getPlayersStatisticsTeam1() != null
                    && matchStatistic.getPlayersStatisticsTeam2() != null) {

                //region Team1
                ArrayList<PlayerStatistic> team1 = matchStatistic.getPlayersStatisticsTeam1();
                int team1sumPoints = 0;
                int team1sumJumps = 0;
                int team1sumFouls = 0;
                String team1mvpName = "";

                int tmpMvpScore = 0;
                int tmpMvpJumps = 0;

                for (PlayerStatistic playerS :
                        team1) {
                    team1sumPoints += playerS.getScore();
                    team1sumJumps += playerS.getJumps();
                    team1sumFouls += playerS.getFouls();
                    if (playerS.getScore() > tmpMvpScore) {
                        if (playerS.getPlayer() != null) {
                            team1mvpName = playerS.getPlayer().getFullName();
                            tmpMvpScore = playerS.getScore();
                            tmpMvpJumps = playerS.getJumps();
                        }
                    } else if (playerS.getScore() == tmpMvpScore) {
                        if (playerS.getJumps() >= tmpMvpJumps) {
                            if (playerS.getPlayer() != null) {
                                team1mvpName = playerS.getPlayer().getFullName();
                                tmpMvpJumps = playerS.getJumps();
                            }

                        }
                    }
                }
                team1mvp.setText(team1mvpName);
                team1points.setText(String.valueOf(team1sumPoints));
                team1jumps.setText(String.valueOf(team1sumJumps));
                team1fouls.setText(String.valueOf(team1sumFouls));
                //endregion

                //region Team2
                ArrayList<PlayerStatistic> team2 = matchStatistic.getPlayersStatisticsTeam2();
                int team2sumPoints = 0;
                int team2sumJumps = 0;
                int team2sumFouls = 0;
                String team2mvpName = "";

                int tmpMvpScore2 = 0;
                int tmpMvpJumps2 = 0;

                for (PlayerStatistic playerS :
                        team2) {
                    team2sumPoints += playerS.getScore();
                    team2sumJumps += playerS.getJumps();
                    team2sumFouls += playerS.getFouls();
                    if (playerS.getScore() > tmpMvpScore2) {
                        if (playerS.getPlayer() != null) {
                            team2mvpName = playerS.getPlayer().getFullName();
                            tmpMvpScore2 = playerS.getScore();
                            tmpMvpJumps2 = playerS.getJumps();
                        }
                    } else if (playerS.getScore() == tmpMvpScore) {
                        if (playerS.getJumps() >= tmpMvpJumps2) {
                            if (playerS.getPlayer() != null) {
                                team2mvpName = playerS.getPlayer().getFullName();
                                tmpMvpJumps2 = playerS.getJumps();
                            }

                        }
                    }
                }
                team2mvp.setText(team2mvpName);
                team2points.setText(String.valueOf(team2sumPoints));
                team2jumps.setText(String.valueOf(team2sumJumps));
                team2fouls.setText(String.valueOf(team2sumFouls));
                //endregion
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Exception on RecalculateMatch(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                boolean success = SaveMatch();
                if(success){
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
                else{
                    Toast.makeText(getActivity(), "Nije uspesno sacuvano", Toast.LENGTH_LONG).show();
                }
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    };

    private boolean SaveMatch() {
        try {
            if (matchStatistic != null && matchStatistic.getPlayersStatisticsTeam1() != null && matchStatistic.getPlayersStatisticsTeam2() != null) {
                DBHelper db = new DBHelper(getContext());
                if (db != null) {
                    for (PlayerStatistic ps :
                            matchStatistic.getPlayersStatisticsTeam1()) {
                        db.AddPlayerStatistics(ps);
                    }
                    for (PlayerStatistic ps :
                            matchStatistic.getPlayersStatisticsTeam2()) {
                        db.AddPlayerStatistics(ps);
                    }
                    return db.AddMatchStatistics(matchStatistic);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Exception on SaveMatch(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }

}//[Class]