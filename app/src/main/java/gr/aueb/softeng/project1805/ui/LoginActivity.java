package gr.aueb.softeng.project1805.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import gr.aueb.softeng.project1805.R;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.service.UserAuthentication;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText, passwordEditText;
    String username, password;
    TextInputLayout usernameLayout, passwordLayout;
    TextView link_register;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        link_register=findViewById(R.id.link_register);
        link_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                usernameLayout=findViewById(R.id.input_username_widget);
                passwordLayout=findViewById(R.id.input_password_widget);

                usernameEditText = findViewById(R.id.input_username);
                username=usernameEditText.getText().toString();

                usernameEditText.addTextChangedListener(new TextWatcher()  {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s)  {
                        if (username.length() >= 0) {
                            usernameLayout.setError(null);
                        }
                    }
                });

                passwordEditText = findViewById(R.id.input_password);
                password=passwordEditText.getText().toString();

                passwordEditText.addTextChangedListener(new TextWatcher()  {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s)  {
                        if (password.length() >= 0) {
                            passwordLayout.setError(null);
                        }
                    }
                });

                if (validate()) {
                    btn_login.setEnabled(false);

                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                            R.style.Theme_AppCompat_DayNight_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("'Ελεγχος...");
                    progressDialog.show();


                    UserAuthentication authentication=new UserAuthentication();
                    user=authentication.login(username, password);

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    if (user!=null) {
                                        progressDialog.dismiss();
                                        Intent loading = new Intent(LoginActivity.this, LoadingActivity.class);
                                        loading.putExtra("User", user);
                                        startActivity(loading);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getBaseContext(), "Εσφαλμένος συνδυασμός στοιχείων", Toast.LENGTH_LONG).show();
                                        btn_login.setEnabled(true);
                                        progressDialog.dismiss();
                                    }
                                }
                            }, 1000);

                }
            }

        });
    }

    public boolean validate() {
        boolean valid=true;

        if (username.trim().equals("")) {
            usernameLayout.setError("*Συμπληρώστε το όνομα χρήστη σας!");
            valid=false;
        }
        if (password.trim().equals("")) {
            passwordLayout.setError("*Συμπληρώστε τον κωδικό σας!");
            valid=false;
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

