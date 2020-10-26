package gr.aueb.softeng.project1805.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gr.aueb.softeng.project1805.R;
import gr.aueb.softeng.project1805.domain.Call;
import gr.aueb.softeng.project1805.domain.Data;
import gr.aueb.softeng.project1805.domain.SMS;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.ReadData;
import gr.aueb.softeng.project1805.service.MonitorMobileEvents;

public class UpdateActivity extends AppCompatActivity {

    User user;
    EditText txtData, txtTalktime,txtCalledPhone, txtReceiverPhone;
    TextInputLayout dataQuantityLayout, talktimeQuantityLayout,calledPhoneLayout,receiverPhoneLayout;
    String data,talkTime,calledPhone,receiverPhone;
    MonitorMobileEvents mobileEvents;
    ReadData readData;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        readData=new ReadData();

        user = (User) getIntent().getSerializableExtra("User");

        mobileEvents=new MonitorMobileEvents(readData.getUserDAO().find(user.getUid()));

        dataQuantityLayout=findViewById(R.id.input_data_quantity_widget);
        txtData = findViewById(R.id.input_dataquantity);
        talktimeQuantityLayout=findViewById(R.id.input_talktime_quantity_widget);
        txtTalktime = findViewById(R.id.input_quantity);
        calledPhoneLayout=findViewById(R.id.input_calledphone_widget);
        txtCalledPhone = findViewById(R.id.input_calledphone);
        receiverPhoneLayout=findViewById(R.id.input_receiverphone_widget);
        txtReceiverPhone = findViewById(R.id.input_receiverphone);

        dialogBuilder= new AlertDialog.Builder(UpdateActivity.this,R.style.AlertDialogTheme);
        dialogBuilder.setMessage("H ενημέρωση πραγματοποιήθηκε.");
        dialogBuilder.setCancelable(true);
        dialogBuilder.setPositiveButton(
                "Ok",null);


        final Button btn_data_update = findViewById(R.id.btn_data_update);
        btn_data_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                data=txtData.getText().toString();
                if (data.length()!=0) {
                    Data newData = mobileEvents.newData(Integer.valueOf(data));
                    if (newData == null) {
                        Toast.makeText(getBaseContext(), "Μη επαρκές υπόλοιπο!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        alert=dialogBuilder.create();
                        alert.show();
                        txtData.setText("");
                    }
                }
                else {
                    dataQuantityLayout.setError("*Συμπληρώστε την ποσότητα!");
                }

                txtData.addTextChangedListener(new TextWatcher()  {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s)  {
                        if (data.length() >= 0) {
                            dataQuantityLayout.setError(null);
                        }
                    }
                });
            }
        });

        final Button btn_talktime_update = findViewById(R.id.btn_talktime_update);
        btn_talktime_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                talkTime=txtTalktime.getText().toString();
                calledPhone=txtCalledPhone.getText().toString();
                boolean notEmpty=false;
                if (talkTime.length()!=0) notEmpty=true;
                else talktimeQuantityLayout.setError("*Συμπληρώστε την ποσότητα!");
                if (validate(calledPhone, calledPhoneLayout) && notEmpty) {
                    Call call = mobileEvents.newCall(Integer.valueOf(talkTime), calledPhone);
                    if (call==null) {
                        Toast.makeText(getBaseContext(), "Μη επαρκές υπόλοιπο!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        alert=dialogBuilder.create();
                        alert.show();
                        txtTalktime.setText("");
                        txtCalledPhone.setText("");
                    }
                }

                txtCalledPhone.addTextChangedListener(new TextWatcher()  {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s)  {
                        if (calledPhone.length() >= 0) {
                            calledPhoneLayout.setError(null);
                        }
                    }
                });

                txtTalktime.addTextChangedListener(new TextWatcher()  {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s)  {
                        if (talkTime.length() >= 0) {
                            talktimeQuantityLayout.setError(null);
                        }
                    }
                });
            }
        });

        final Button btn_sms_update=findViewById(R.id.btn_sms_update);
        btn_sms_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiverPhone=txtReceiverPhone.getText().toString();
                if (validate(receiverPhone, receiverPhoneLayout)) {
                    SMS sms=mobileEvents.newSMS(receiverPhone);
                    if (sms==null) {
                        Toast.makeText(getBaseContext(), "Μη επαρκές υπόλοιπο!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        alert=dialogBuilder.create();
                        alert.show();
                        txtReceiverPhone.setText("");
                    }
                }
                txtReceiverPhone.addTextChangedListener(new TextWatcher()  {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s)  {
                        if (receiverPhone.length() >= 0) {
                            receiverPhoneLayout.setError(null);
                        }
                    }
                });
            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public boolean validate(String phone, TextInputLayout phoneLayout) {
        boolean valid=true;

        if (phone.trim().equals("")) {
            phoneLayout.setError("*Συμπληρώστε τον αριθμό τηλεφώνου!");
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

        if (valid && phone.equals(user.getPhoneNum())) {
            phoneLayout.setError("*Μη έγκυρος αριθμός!");
            valid=false;
        }

        return valid;
    }

}
