package com.example.matchstatistics.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matchstatistics.DashboardActivity;
import com.example.matchstatistics.MatchStatisticsActivity;
import com.example.matchstatistics.R;
import com.example.matchstatistics.register;
import com.example.matchstatistics.databinding.ActivityLoginBinding;

import Database.DBHelper;
import Models.Helpers.HelperModel;
import Models.MatchStatistic;
import Models.User;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    private ImageView imgLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DBHelper db = new DBHelper(LoginActivity.this);
        CreateAdminAccountIfNotExist();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.loginUsername;
        final EditText passwordEditText = binding.loginPassword;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        try{
            imgLogo = binding.imgLogo;
            if(imgLogo != null){
                imgLogo.setOnLongClickListener(v -> {
                    //User usr = new User();
                    //Intent intent = new Intent(LoginActivity.this, register.class);
                    //intent.putExtra("user_object", usr);

                    Intent intent = new Intent(LoginActivity.this, MatchStatisticsActivity.class);
                    startActivity(intent);
                    return false;
                });
            }

        }catch(Exception e){
            Toast.makeText(LoginActivity.this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(v -> {
            User tmpUser;
            DBHelper db = new DBHelper(LoginActivity.this);
            try {
                if (db != null) {
                    tmpUser = db.GetUser(usernameEditText.getText().toString());
                    if (tmpUser != null) {
                        if (tmpUser.getPassword().equals(passwordEditText.getText().toString())) {
                            goToDashboard(tmpUser);
                        } else {
                            Toast.makeText(LoginActivity.this, "Korisnicko ime i lozinka se ne poklapaju.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Ne postoji nalog sa unetom E-mail adresom ili korisničkim imenom.", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "Greska: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void CreateAdminAccountIfNotExist() {
        DBHelper db = new DBHelper(LoginActivity.this);
        User tmpUserAdmin = db.GetUser("admin");
        if(tmpUserAdmin == null){
            tmpUserAdmin = new User();
            tmpUserAdmin.setAdminRole(HelperModel.roleAdmin);
            tmpUserAdmin.setName("Super admin");
            tmpUserAdmin.setSurname("");
            tmpUserAdmin.setUsername("admin");
            tmpUserAdmin.setPassword("admin123");
            db.AddUser(tmpUserAdmin);
        }
    }

    private void goToDashboard(User tmpUser) {
        //Toast.makeText(getApplicationContext(), "Korisnik "+ tmpUser.getUsername()+ " se uspesno ulogovao", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user_object", tmpUser);
        startActivity(intent);
        finish();
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

}//[Class]