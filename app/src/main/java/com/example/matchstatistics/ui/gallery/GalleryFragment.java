package com.example.matchstatistics.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.matchstatistics.DashboardActivity;
import com.example.matchstatistics.R;
import com.example.matchstatistics.databinding.FragmentGalleryBinding;

import java.util.ArrayList;

import Adapters.MatchStatisticAdapter;
import Adapters.TeamsAdapter;
import Database.DBHelper;
import Models.Helpers.HelperModel;
import Models.MatchStatistic;
import Models.Team;
import Models.User;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;


    ArrayList<MatchStatistic> matchStatistics = new ArrayList<>();
    MatchStatisticAdapter matchStatisticsAdapter;
    ListView listView_matchStatistics;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadMatchHistories();
        listView_matchStatistics = binding.listViewMatchHistories;
        matchStatisticsAdapter = new MatchStatisticAdapter(getActivity(), R.layout.layout_match_statistic_row, matchStatistics);
        listView_matchStatistics.setAdapter(matchStatisticsAdapter);

        return root;
    }

    private void LoadMatchHistories() {
        try {
            DBHelper db = new DBHelper(getActivity());
            if (db != null) {
                ArrayList<MatchStatistic> tmpMatchStatistics = db.GetMatchStatistics();
                if (tmpMatchStatistics != null) {
                    matchStatistics.addAll(tmpMatchStatistics);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Exception on LoadMatchHistories(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}//[Class]