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

public class RegisterActivity extends AppCompatActivity {
    EditText phoneEditText, usernameEditText, passwordEditText;
    String phone, username, password;
    TextInputLayout phoneLayout, usernameLayout, passwordLayout;
    User user;
    TextView link_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        link_login=findViewById(R.id.link_login);
        link_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                phoneLayout=findViewById(R.id.input_phone_widget);
                usernameLayout=findViewById(R.id.r_input_username_widget);
                passwordLayout=findViewById(R.id.r_input_password_widget);

                phoneEditText = findViewById(R.id.input_phone);
                phone=phoneEditText.getText().toString();

                phoneEditText.addTextChangedListener(new TextWatcher()  {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s)  {
                        if (phone.length() >= 0) {
                            phoneLayout.setError(null);
                        }
                    }
                });



                usernameEditText = findViewById(R.id.r_input_username);
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

                passwordEditText = findViewById(R.id.r_input_password);
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
                    btn_register.setEnabled(false);

                    final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                            R.style.Theme_AppCompat_DayNight_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("'Ελεγχος...");
                    progressDialog.show();


                    UserAuthentication authentication=new UserAuthentication();
                    user=authentication.signUp(username, password, phone);

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    if (user!=null) {
                                        progressDialog.dismiss();
                                        Intent loading = new Intent(RegisterActivity.this, LoadingActivity.class);
                                        loading.putExtra("User", user);
                                        startActivity(loading);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getBaseContext(), "Ο χρήστης υπάρχει ήδη", Toast.LENGTH_LONG).show();
                                        btn_register.setEnabled(true);
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

        if (phone.trim().equals("")) {
            phoneLayout.setError("*Συμπληρώστε το κινητό σας!");
            valid=false;
        }
        else{
            if (phone.length()!=10 || !((phone.substring(0, 2)).equals("69"))) {
                phoneLayout.setError("*Μη έγκυρος αριθμός!");
                valid = false;
            }
            if (phone.length()!=1) {
                try {
                    long phone_num = Long.parseLong(phone);
                } catch (NumberFormatException e) {
                    phoneLayout.setError("*Μη έγκυρος αριθμός!");
                    valid = false;
                }
            }
        }

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
}

