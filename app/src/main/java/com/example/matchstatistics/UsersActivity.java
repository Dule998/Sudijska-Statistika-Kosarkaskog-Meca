package com.example.matchstatistics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.matchstatistics.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import Adapters.UsersAdapter;
import Database.DBHelper;
import Models.Helpers.HelperModel;
import Models.User;

public class UsersActivity extends AppCompatActivity {

    FloatingActionButton addUserFloat;
    ListView usersListView;

    ArrayList<User> usersList = new ArrayList<>();
    UsersAdapter usersAdapter;

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        currentUser = (User) getIntent().getSerializableExtra("user_object");

        addUserFloat = findViewById(R.id.fab_addUser);
        addUserFloat.setOnClickListener(v -> {
            User usr = new User();
            Intent intent = new Intent(UsersActivity.this, register.class);
            intent.putExtra("user_object", usr);
            startActivityForResult(intent, HelperModel.ResultRegisteer_AddUser);
        });


        LoadUsers();
        usersListView = findViewById(R.id.listView_users);
        usersAdapter = new UsersAdapter(UsersActivity.this,R.layout.layout_user_list_row,usersList, currentUser);
        usersListView.setAdapter(usersAdapter);
    }

    private void LoadUsers() {
        DBHelper db = new DBHelper(UsersActivity.this);
        if (db != null) {
            ArrayList<User> tmpUsers = db.GetUsers();
            if (tmpUsers != null && tmpUsers.size() > 0) {
                usersList.addAll(tmpUsers);
            }else{
                //Toast.makeText(getActivity(), "Nisu ucitani nalozi", Toast.LENGTH_SHORT).show();
            }
        }else{
            //Toast.makeText(GroupGallery.this, "Problem sa bazom podataka.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HelperModel.ResultRegisteer_AddUser) {
            if (resultCode == Activity.RESULT_OK) {
                User user = (User) data.getSerializableExtra("user_object");
                if (user != null) {
                    if (usersAdapter != null) {
                        usersAdapter.addUser(user);
                    }
                }
            }
        }
    }
}//[Class]