package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.matchstatistics.MatchStatisticsActivity;
import com.example.matchstatistics.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Database.DBHelper;
import Models.PlayerStatistic;
import Models.Team;
import Models.User;

public class PlayerStatisticAdapter extends ArrayAdapter<PlayerStatistic> {

    private Context context;
    private int resource;
    AlertDialog.Builder builder;
    User loggedUser;
    boolean isEditable;

    public PlayerStatisticAdapter(@NonNull Context context, int resource, @NotNull ArrayList<PlayerStatistic> playerStatistics, User loggedUser,boolean isEditable) {
        super(context, resource, playerStatistics);

        this.context = context;
        this.resource = resource;
        this.loggedUser = loggedUser;
        this.isEditable = isEditable;
        builder = new AlertDialog.Builder(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        PlayerStatistic tmpPlayerStatistic = getItem(position);

        try {
            TextView playerName = convertView.findViewById(R.id.layout_ps_name);
            if(playerName != null){
                playerName.setText(tmpPlayerStatistic.getPlayer().getFullName());
            }

            EditText points = convertView.findViewById(R.id.layout_ps_points_textEdit);
            if(points != null){
                points.setEnabled(isEditable);
                points.setText(String.valueOf(tmpPlayerStatistic.getScore()));
                if(isEditable){
                    points.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            try {
                                int num = Integer.parseInt(points.getText().toString());
                                tmpPlayerStatistic.setScore(num);
                            } catch(NumberFormatException nfe) {
                                points.setError("Unesite ceo broj");
                            }
                        }
                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }

            EditText fouls = convertView.findViewById(R.id.layout_ps_foults_textEdits);
            if(fouls != null){
                fouls.setEnabled(isEditable);
                fouls.setText(String.valueOf(tmpPlayerStatistic.getFouls()));
                if(isEditable){
                    fouls.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            try {
                                int num = Integer.parseInt(fouls.getText().toString());
                                tmpPlayerStatistic.setFouls(num);
                            } catch(NumberFormatException nfe) {
                                fouls.setError("Unesite ceo broj");
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }

            EditText jumps = convertView.findViewById(R.id.layout_ps_jumps_textEdit);
            if(jumps != null){
                jumps.setEnabled(isEditable);
                jumps.setText(String.valueOf(tmpPlayerStatistic.getJumps()));
                if(isEditable){
                    jumps.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            try {
                                int num = Integer.parseInt(jumps.getText().toString());
                                tmpPlayerStatistic.setJumps(num);
                            } catch(NumberFormatException nfe) {
                                jumps.setError("Unesite ceo broj");
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            }
        }catch (Exception e){
            Toast.makeText(context, "PlayerStatisticAdapter - Exception on getView(): " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return convertView;
    }

    private PlayerStatistic statisticForDelete;
    public void deleteStatistic(PlayerStatistic statistic) {
        DBHelper db = new DBHelper(getContext());
        if (db != null) {
            //Not implemented
        }
    }

    public void addStatistic(PlayerStatistic statistic) {
        DBHelper db = new DBHelper(getContext());
        if (db != null) {
            //Not implemented
        }
    }

    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (statisticForDelete != null) {
                    //Not implemented
                }
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    };
}//[Class]