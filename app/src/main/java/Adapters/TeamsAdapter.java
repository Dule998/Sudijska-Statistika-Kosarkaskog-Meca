package Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.matchstatistics.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import Database.DBHelper;
import Models.Player;
import Models.Team;
import Models.User;

public class TeamsAdapter extends ArrayAdapter<Team> {

    private Context context;
    private int resource;
    AlertDialog.Builder builder;
    User loggedUser;

    public TeamsAdapter(@NonNull Context context, int resource, @NotNull ArrayList<Team> teams, User loggedUser) {
        super(context, resource, teams);

        this.context = context;
        this.resource = resource;
        this.loggedUser = loggedUser;
        builder = new AlertDialog.Builder(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        Team tmpTeam = getItem(position);

        TextView name = convertView.findViewById(R.id.layout_team_name);
        if (name != null) {
            name.setText(tmpTeam.getName());
        }

        ImageView img = convertView.findViewById(R.id.layout_team_img);

        return convertView;
    }

    private Team teamForDelete;
    public void deleteTeam(Team team) {
        DBHelper db = new DBHelper(getContext());
        if (db != null) {
            //Not implemented
        }
    }

    public void addTeam(Team team) {
        DBHelper db = new DBHelper(getContext());
        if (db != null) {
            //Not implemented
        }
    }

    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (teamForDelete != null) {

                }
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    };
}//[Class]