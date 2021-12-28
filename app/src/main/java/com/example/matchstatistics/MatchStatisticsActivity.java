package com.example.matchstatistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Toast;

import com.example.matchstatistics.ui.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import Adapters.SectionsPagerAdapter;
import Fragments.MatchStatisticsMainFragment;
import Fragments.TeamStatisticsFragment;
import Models.Helpers.FragmentTabItemModel;
import Models.MatchStatistic;
import Models.User;

public class MatchStatisticsActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    SectionsPagerAdapter pagerAdapter;
    final ArrayList<FragmentTabItemModel> fragments = new ArrayList<>();

    MatchStatistic matchStatistic;
    User user;
    Boolean editable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_statistics);

        try {
            user = (User) getIntent().getSerializableExtra("user_object");
            matchStatistic = (MatchStatistic) getIntent().getSerializableExtra("matchStatistic");
            editable = getIntent().getBooleanExtra("isEditable", true);

            //region debug
            String textZaIspis = "";
            if (matchStatistic != null) {
                textZaIspis += "MatchStatistic postoji.\n";
                if (matchStatistic.getTeam1() != null) {
                    textZaIspis += "Team 1 postoji:" + matchStatistic.getTeam1().getName() + "\n";
                } else {
                    textZaIspis += "Team 1 ne postoji.\n";
                }
                if (matchStatistic.getTeam2() != null) {
                    textZaIspis += "Team 2 postoji:" + matchStatistic.getTeam2().getName() + "\n";
                } else {
                    textZaIspis += "Team 2 ne postoji.\n";
                }
                if (matchStatistic.getPlayersStatisticsTeam1() != null && matchStatistic.getPlayersStatisticsTeam1().size() > 0) {
                    textZaIspis += "Team 1 statistika igraca postoji:" + matchStatistic.getPlayersStatisticsTeam1().size() + "\n";
                } else {
                    textZaIspis += "Team 1 statistika igraca  NE postoji:\n";
                }
            }
            //Toast.makeText(MatchStatisticsActivity.this, textZaIspis, Toast.LENGTH_SHORT).show();
            //endregion

            tabLayout = findViewById(R.id.tabLayout_main);
            viewPager = findViewById(R.id.viewPager_matchStatistics);

            tabLayout.setupWithViewPager(viewPager);

            CreateFragments();
            pagerAdapter = new SectionsPagerAdapter(MatchStatisticsActivity.this, getSupportFragmentManager(), fragments);
            viewPager.setAdapter(pagerAdapter);

        } catch (Exception e) {
            Toast.makeText(MatchStatisticsActivity.this, "Exception on onCreate(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void CreateFragments() {
        try {
            TeamStatisticsFragment frTeam1 = TeamStatisticsFragment.newInstance(user, matchStatistic.getPlayersStatisticsTeam1(),editable);
            TeamStatisticsFragment frTeam2 = TeamStatisticsFragment.newInstance(user, matchStatistic.getPlayersStatisticsTeam2(),editable);
            MatchStatisticsMainFragment frMatch = MatchStatisticsMainFragment.newInstance(user, matchStatistic);


            fragments.add(new FragmentTabItemModel("Meč", frMatch));
            fragments.add(new FragmentTabItemModel("Domaćin", frTeam1));
            fragments.add(new FragmentTabItemModel("Gost", frTeam2));
        } catch (Exception e) {
            Toast.makeText(MatchStatisticsActivity.this, "Exception on CreateFragments(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public TeamStatisticsFragment getFragment1Props() {
        return null;
    }

    public TeamStatisticsFragment getFragment2Props() {
        return null;
    }

    public MatchStatisticsMainFragment getMatchStatisticProps() {
        return null;
    }
}//[Class]