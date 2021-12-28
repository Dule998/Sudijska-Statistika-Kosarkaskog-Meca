package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.matchstatistics.MatchStatisticsActivity;
import com.example.matchstatistics.R;
import com.example.matchstatistics.databinding.FragmentHomeBinding;
import com.example.matchstatistics.databinding.FragmentTeamStatisticsBinding;

import java.util.ArrayList;

import Adapters.PlayerStatisticAdapter;
import Models.PlayerStatistic;
import Models.Team;
import Models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamStatisticsFragment extends Fragment {

    FragmentTeamStatisticsBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user_object";
    private static final String ARG_PLAYERSTATISTIC_LIST = "playerStatistic_list";
    private static final String ARG_EDITABLE ="editable";


    Boolean isEditable;
    User user;
    ArrayList<PlayerStatistic> playerStatisticArrayList;
    PlayerStatisticAdapter adapter;

    public TeamStatisticsFragment() {
        // Required empty public constructor
    }


    public static TeamStatisticsFragment newInstance(User user, ArrayList<PlayerStatistic> playerStatistics, boolean isEditable) {
        TeamStatisticsFragment fragment = new TeamStatisticsFragment();
        try{
            Bundle args = new Bundle();
            args.putSerializable(ARG_USER, user);
            args.putSerializable(ARG_PLAYERSTATISTIC_LIST, playerStatistics);
            args.putBoolean(ARG_EDITABLE, isEditable);
            fragment.setArguments(args);
        }catch (Exception e){
            Toast.makeText(fragment.getActivity(), "Exception on newInstance(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getArguments() != null) {
                user = (User) getArguments().getSerializable(ARG_USER);
                playerStatisticArrayList = (ArrayList<PlayerStatistic>) getArguments().getSerializable(ARG_PLAYERSTATISTIC_LIST);
                isEditable = getArguments().getBoolean(ARG_EDITABLE);

            }
        }catch (Exception e){
            Toast.makeText(getContext(), "Exception on onCreate(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            binding = FragmentTeamStatisticsBinding.inflate(inflater, container, false);
            View root = binding.getRoot();

            ListView playersStatisticLV = (ListView)binding.frListViewPlayersStatistics;
            if (playersStatisticLV != null) {
                adapter = new PlayerStatisticAdapter(getActivity(), R.layout.layout_player_statistic_row, playerStatisticArrayList, user,isEditable);
                playersStatisticLV.setAdapter(adapter);
            }
            return root;

        } catch (Exception e) {
            Toast.makeText(getContext(), "Exception on onCreateView(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_statistics, container, false);
    }
}//[Class]