package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.service.autofill.FieldClassification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.matchstatistics.MatchStatisticsActivity;
import com.example.matchstatistics.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

import Database.DBHelper;
import Models.Helpers.HelperModel;
import Models.MatchStatistic;
import Models.Player;
import Models.Team;
import Models.User;

public class MatchStatisticAdapter extends ArrayAdapter<MatchStatistic> {

    private Context context;
    private int resource;

    MatchStatistic clickedItem;

    public MatchStatisticAdapter(@NonNull Context context, int resource, @NotNull ArrayList<MatchStatistic> matchStatistics) {
        super(context, resource, matchStatistics);

        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        convertView.setOnClickListener(v -> {
            clickedItem = getItem(position);
            OpenMatchStatistic(clickedItem);
        });

        MatchStatistic tmpMatchStatistic = getItem(position);

        try {
            TextView team1Name = convertView.findViewById(R.id.ly_ms_team1name);
            TextView team1Score = convertView.findViewById(R.id.ly_ms_team1score);
            TextView team2Name = convertView.findViewById(R.id.ly_ms_team2name);
            TextView team2Score = convertView.findViewById(R.id.ly_ms_team2score);

            team1Name.setText(tmpMatchStatistic.getTeam1().getName());
            team1Score.setText(String.valueOf(tmpMatchStatistic.CalculateScoreTeam(1)));
            team2Name.setText(tmpMatchStatistic.getTeam2().getName());
            team2Score.setText(String.valueOf(tmpMatchStatistic.CalculateScoreTeam(2)));

        } catch (Exception e) {
            Toast.makeText(context, "Exception on getView(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return convertView;
    }

    private void OpenMatchStatistic(MatchStatistic clickedItem) {
        Intent intent = new Intent(getContext(), MatchStatisticsActivity.class);
        //intent.putExtra("user_object", user);
        intent.putExtra("matchStatistic", clickedItem);
        intent.putExtra("isEditable", false);
        getContext().startActivity(intent);
    }


    private MatchStatistic matchStatisicForDelete;

    public void deleteMatchStatisic(MatchStatistic player) {
        DBHelper db = new DBHelper(getContext());
        if (db != null) {
            //Not implemented
        }
    }


    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    };
}//[Class]