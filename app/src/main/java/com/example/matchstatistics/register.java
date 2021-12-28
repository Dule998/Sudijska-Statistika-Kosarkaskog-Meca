package com.example.matchstatistics;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matchstatistics.ui.login.LoginActivity;

import Database.DBHelper;
import Models.Helpers.HelperModel;
import Models.User;

public class register extends AppCompatActivity {

    private User user;

    EditText name, surname, username, email, password, repeatPassword;
    Button register, registerAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = (User) getIntent().getSerializableExtra("user_object");

        name = findViewById(R.id.reg_name);
        surname = findViewById(R.id.reg_suername);
        username = findViewById(R.id.reg_username);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_password);
        repeatPassword = findViewById(R.id.reg_repeatPassword);

        register = findViewById(R.id.reg_registerButton);
        if (register != null) {
            register.setOnClickListener(v -> {
                boolean isValid = CheckValidity();
                if (isValid) {
                    ApplyValuesAndAddUser(HelperModel.roleJudge);
                    //Toast.makeText(this, "Uspesno registrovan", Toast.LENGTH_LONG).show();
                }
            });
        }

        registerAdmin = findViewById(R.id.reg_registerButtonAdmin);
        if (registerAdmin != null) {
            registerAdmin.setOnClickListener(v -> {
                boolean isValid = CheckValidity();
                if (isValid) {
                    //Toast.makeText(this, "Uspesno registrovan admin", Toast.LENGTH_LONG).show();
                    ApplyValuesAndAddUser(HelperModel.roleAdmin);
                }
            });
        }
    }


    private boolean CheckValidity() {
        if (name.getText().toString().isEmpty()) {
            name.setError("Unesite ime");
            return false;
        }
        if (surname.getText().toString().isEmpty()) {
            surname.setError("Unesite prezime");
            return false;
        }
        if (username.getText().toString().isEmpty()) {
            username.setError("Unesite korisnicko ime");
            return false;
        }
        if (email.getText().toString().isEmpty()) {
            email.setError("Unesite ispravanu E-adresu");
            return false;
        }
        if (password.getText().toString().isEmpty()) {
            password.setError("Unesite lozinku");
            return false;
        }
        if (password.getText().toString().length() <= 5) {
            password.setError("Lozinka mora imati vise od 5 karaktera");
            return false;
        }
        if (repeatPassword.getText().toString().isEmpty() || !repeatPassword.getText().toString().equals(password.getText().toString())) {
            password.setError("Lozinke se ne poklapaju");
            return false;
        }
        return true;
    }

    private void ApplyValuesAndAddUser(String adminRole) {
        if (user != null) {
            user.setName(name.getText().toString());
            user.setSurname(surname.getText().toString());
            user.setEmail(email.getText().toString());
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            user.setAdminRole(adminRole);

            if (false) {
                DBHelper db = new DBHelper(register.this);
                if (db != null) {
                    boolean successAdd = db.AddUser(user);
                    if (successAdd) {
                        finish();
                    }
                }
            }

            if (true) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("user_object", user);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }
    }


}//[Class]