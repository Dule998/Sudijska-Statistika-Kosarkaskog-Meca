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
import Models.User;

public class UsersAdapter extends ArrayAdapter<User> {

    private Context context;
    private int resource;
    AlertDialog.Builder builder;
    User loggedUser;

    public UsersAdapter(@NonNull Context context, int resource, @NotNull ArrayList<User> users, User loggedUser) {
        super(context, resource, users);

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
        User tmpUsr = getItem(position);

        ImageView removeImg = convertView.findViewById(R.id.layout_user_removeImg);
        if (removeImg != null) {
            if(loggedUser != null && loggedUser.getId()==tmpUsr.getId() && tmpUsr.getUsername().equals("admin")){
                removeImg.setVisibility(View.INVISIBLE);
            }else{
                removeImg.setOnClickListener(v -> {
                    //deleteBookmark(getItem(position));
                    userForDelete = tmpUsr;
                    AlertDialog dialog = builder.setTitle("Brisanje naloga")
                            .setMessage("Da li ste sigurni da želite da obrišete nalog " + userForDelete.getUsername() + "?")
                            .setPositiveButton("Da", dialogClickListener)
                            .setNegativeButton("Ne", dialogClickListener)
                            .show();
                });
            }
        }

        TextView fullName = convertView.findViewById(R.id.layout_user_fullname);
        if (fullName != null) {
            fullName.setText(tmpUsr.getName() + " " + tmpUsr.getSurname());
        }

        TextView email = convertView.findViewById(R.id.layout_user_mail);
        if (email != null) {
            email.setText(tmpUsr.getEmail());
        }

        TextView admRole = convertView.findViewById(R.id.layout_user_role);
        if (admRole != null) {
            if (tmpUsr.getAdminRole().equals(HelperModel.roleAdmin)) {
                admRole.setText("Administrator");
            } else if (tmpUsr.getAdminRole().equals(HelperModel.roleJudge)) {
                admRole.setText("Sudija");
            }
        }


        return convertView;
    }

    private User userForDelete;

    public void deleteUser(User user) {
        DBHelper db = new DBHelper(getContext());
        if (db != null) {
            boolean result = db.DeleteUser(user);
            if (result) {
                remove(user);
                notifyDataSetChanged();
                userForDelete = null;
            }
        }
    }

    public void addUser(User user) {
        DBHelper db = new DBHelper(getContext());
        if (db != null) {
            boolean result = db.AddUser(user);
            if (result) {
                add(user);
                notifyDataSetChanged();
            }
        }
    }

    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                //Toast.makeText(context, "Debug: Click yes on dialog", Toast.LENGTH_SHORT).show();
                if (userForDelete != null) {
                    deleteUser(userForDelete);
                }
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //Toast.makeText(context, "Debug: Click no onDialog", Toast.LENGTH_SHORT).show();
                break;
        }
    };
}//[Class]
