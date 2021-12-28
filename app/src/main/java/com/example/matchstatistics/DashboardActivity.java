package com.example.matchstatistics;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matchstatistics.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.matchstatistics.databinding.ActivityDashboardBinding;

import org.w3c.dom.Text;

import Models.Helpers.HelperModel;
import Models.User;

public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardBinding binding;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    View headerView;

    private User user;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        builder = new AlertDialog.Builder(DashboardActivity.this);
        user = (User) getIntent().getSerializableExtra("user_object");
        if (user != null) {
           // Toast.makeText(getApplicationContext(), "Korsinik se ulogovao " + user.getUsername(), Toast.LENGTH_LONG).show();
        }


        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navView;
        headerView = navigationView.getHeaderView(0);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawerLayout)
                .build();

        final MenuItem menuItem = (MenuItem) navigationView.getMenu().findItem(R.id.nav_logout);
        if (menuItem != null) {
            menuItem.setOnMenuItemClickListener(item -> {
                openLogOutDialog();

                return false;
            });
        }


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        InitializeUserInfo();
    }

    private void openLogOutDialog() {
        AlertDialog dialog = builder.setTitle("Log out")
                .setMessage("Are you sure to want log out?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();
    }

    private void InitializeUserInfo() {
        if (headerView != null && user != null) {
            TextView textRole = (TextView) headerView.findViewById(R.id.nav_header_adminRole);
            if (textRole != null) {
                if (user.getAdminRole().equals(HelperModel.roleAdmin)) {
                    textRole.setText("Administrator");
                } else if (user.getAdminRole().equals(HelperModel.roleJudge)) {
                    textRole.setText("Sudija");
                }
            }
            TextView textName = (TextView) headerView.findViewById(R.id.nav_header_name);
            if (textName != null) {
                textName.setText(user.getName() + " " + user.getSurname());
            }
            TextView textMail = (TextView) headerView.findViewById(R.id.nav_header_email);
            if (textMail != null) {
                textMail.setText(user.getEmail());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {

        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //Toast.makeText(DashboardActivity.this, "Nothing", Toast.LENGTH_SHORT).show();
                break;
        }
    };

    public User getCurrentUser(){return user;}


}//[Class]
