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
import Models.Helpers.HelperModel;
import Models.Player;
import Models.Team;
import Models.User;

public class PlayersAdapter extends ArrayAdapter<Player> {

    private Context context;
    private int resource;
    AlertDialog.Builder builder;
    User loggedUser;

    public PlayersAdapter(@NonNull Context context, int resource, @NotNull ArrayList<Player> players, User loggedUser) {
        super(context, resource, players);

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
        Player tmpPlayer = getItem(position);

        ImageView removeImg = convertView.findViewById(R.id.layout_user_removeImg);
        if (removeImg != null) {
            removeImg.setVisibility(View.INVISIBLE);
        }

        TextView fullName = convertView.findViewById(R.id.layout_user_fullname);
        if (fullName != null) {
            fullName.setText(tmpPlayer.getName() + " " + tmpPlayer.getSurname());
        }

        TextView email = convertView.findViewById(R.id.layout_user_mail); //email in this case is position
        if (email != null) {
            email.setText("Position: "+tmpPlayer.getPosition());
        }

        TextView admRole = convertView.findViewById(R.id.layout_user_role); //adm role in this case is position
        if (admRole != null) {
            admRole.setText("Team: " + getParentTeamName(tmpPlayer.getTeamId()));
        }


        return convertView;
    }

    private User playerForDelete;
    public String getParentTeamName(int parentTeam){
        DBHelper db = new DBHelper(getContext());
        if (db != null) {
            Team toRet = db.GetTeam(parentTeam);
            if(toRet != null){
                return toRet.getName();
            }
        }
        return "";
    }

    public void deletePlayer(Player player) {
        DBHelper db = new DBHelper(getContext());
        if (db != null) {
          //Not implemented
        }
    }

    public void addPlayer(Player player) {
        DBHelper db = new DBHelper(getContext());
        if (db != null) {
        //Not implemented
        }
    }

    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (playerForDelete != null) {

                }
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    };
}//[Class]