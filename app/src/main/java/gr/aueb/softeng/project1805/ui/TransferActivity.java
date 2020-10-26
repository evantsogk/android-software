package gr.aueb.softeng.project1805.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import gr.aueb.softeng.project1805.R;

import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.Transfer;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.ReadData;
import gr.aueb.softeng.project1805.service.TransferService;


public class TransferActivity extends AppCompatActivity {

    User user;
    TextInputLayout quantityLayout;
    TextInputLayout mobileLayout;
    EditText quantityEditText;
    EditText mobileEditText;
    String quantity;
    String mobile;
    TextView quantityType;
    ReadData readData=new ReadData();
    AlertDialog.Builder dialogBuilder,finalBuilder;
    AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user=(User) getIntent().getSerializableExtra("User");


        quantityLayout = findViewById(R.id.input_quantity_widget);
        quantityEditText = findViewById(R.id.input_quantity);

        final Button btn_data = findViewById(R.id.btn_data);
        final Button btn_minutes = findViewById(R.id.btn_minutes);
        final Button btn_sms = findViewById(R.id.btn_sms);

        btn_data.setPressed(true);
        btn_minutes.setPressed(false);
        btn_sms.setPressed(false);

        btn_data.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view,MotionEvent event) {
                btn_data.performClick();
                quantityType=findViewById(R.id.quantity_type);
                quantityType.setText("ΜΒ");
                btn_data.setPressed(true);
                btn_minutes.setPressed(false);
                btn_sms.setPressed(false);

                return true;
            }
        });

        btn_minutes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view,MotionEvent event) {
                btn_minutes.performClick();
                quantityType=findViewById(R.id.quantity_type);
                quantityType.setText("Λεπτά");
                btn_data.setPressed(false);
                btn_minutes.setPressed(true);
                btn_sms.setPressed(false);

                return true;
            }
        });


        btn_sms.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view,MotionEvent event) {
                btn_sms.performClick();
                quantityType=findViewById(R.id.quantity_type);
                quantityType.setText("SMS");
                btn_data.setPressed(false);
                btn_minutes.setPressed(false);
                btn_sms.setPressed(true);

                return true;
            }
        });


        final Button btn_transfer = findViewById(R.id.btn_transfer);
        btn_transfer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                quantityLayout = findViewById(R.id.input_quantity_widget);

                quantityEditText = findViewById(R.id.input_quantity);
                quantity = quantityEditText.getText().toString();

                quantityEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (quantity.length() >= 0) {
                            quantityLayout.setError(null);
                        }
                    }

                });

                mobileLayout = findViewById(R.id.input_mobile_widget);

                mobileEditText = findViewById(R.id.input_mobile);
                mobile = mobileEditText.getText().toString();

                mobileEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (mobile.length() >= 0) {
                            mobileLayout.setError(null);
                        }
                    }

                });


                if (validate()) {

                    final String quantitytype;
                    if(btn_data.isPressed()){
                        quantitytype=" MB";
                    }else if(btn_minutes.isPressed()){

                        if(Integer.valueOf(quantity)==1){
                            quantitytype=" λεπτό ομιλίας";
                        }else{
                            quantitytype=" λεπτά ομιλίας";
                        }
                    }else{
                        quantitytype=" SMS";
                    }

                    dialogBuilder= new AlertDialog.Builder(TransferActivity.this,R.style.AlertDialogTheme);
                    dialogBuilder.setMessage("Είστε σίγοuροι ότι θέλετε να παραχωρήσετε "+quantity+quantitytype+" στον αριθμό "+mobile+";");
                    dialogBuilder.setCancelable(true);

                    dialogBuilder.setPositiveButton(
                            "Ναι",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Transfer transf;
                                    TransferService transferService=new TransferService();
                                    int intquantity=Integer.parseInt(quantity);
                                    User u=readData.getUserDAO().find(user.getUid());

                                    if(quantitytype.equals(" MB")){

                                        transf= transferService.transfer(u, mobile, ServiceType.Data,intquantity);

                                    }else if(quantitytype.equals(" λεπτά ομιλίας")||quantitytype.equals(" λεπτό ομιλίας")){

                                        transf= transferService.transfer(u, mobile, ServiceType.TalkTime,intquantity);

                                    }else {
                                        transf = transferService.transfer(u, mobile, ServiceType.SMS, intquantity);
                                    }
                                    if (transf == null) {
                                        Toast.makeText(getBaseContext(), " Το υπόλοιπο σας δεν επαρκεί για την παραχώρηση", Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        quantityEditText.setText("");
                                        mobileEditText.setText("");

                                        dialog.cancel();
                                        finalBuilder = new AlertDialog.Builder(TransferActivity.this, R.style.AlertDialogTheme);
                                        finalBuilder.setMessage("Η παραχώρηση ολοκληρώθηκε με επιτυχία. ");
                                        finalBuilder.setCancelable(true);
                                        finalBuilder.setPositiveButton(
                                                "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        btn_data.setPressed(true);
                                                        quantityType = findViewById(R.id.quantity_type);
                                                        quantityType.setText("ΜΒ");
                                                    }
                                                });
                                        alert = finalBuilder.create();
                                        alert.show();
                                    }


                                }
                            });


                    dialogBuilder.setNegativeButton(
                            "Aκύρωση",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });


                    alert = dialogBuilder.create();
                    alert.show();
                    btn_data.setPressed(true);

                }

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

    public boolean validate() {
        boolean valid=true;
        readData=new ReadData();
        readData.readUsers(getResources().openRawResource(R.raw.users));

        if (quantity.trim().equals("")) {
            quantityLayout.setError("*Συμπληρώστε την επιθυμητή ποσότητα παραχώρησης!");
            valid=false;
        }
        else{
            try{
                int quant= Integer.parseInt(quantity);
            }catch(NumberFormatException e) {
                quantityLayout.setError("*Μη αποδεκτή ποσότητα!");
                valid = false;
            }
        }
        if (mobile.trim().equals("")) {
            mobileLayout.setError("*Συμπληρώστε τον αριθμό στον οποίο θα γίνει παραχώρηση!");
            valid=false;
        }
        else{
            if (mobile.length()!=10 || !((mobile.substring(0, 2)).equals("69"))) {
                mobileLayout.setError("*Μη έγκυρος αριθμός!");
                valid = false;
            }else {
                if(user.getPhoneNum().equals(mobile)){
                    valid=false;
                    mobileLayout.setError("*Δεν είναι δυνατή η παραχώρηση σε αυτό τον αριθμό!");
                }

                boolean existinguser=false;
                for(User user : readData.getUserDAO().findAll()){
                    if(user.getPhoneNum().equals(mobile)){
                        existinguser=true;
                        break;
                    }
                }
                if(!existinguser){
                    mobileLayout.setError("*Μη εγγεγραμμένος χρήστης!");
                    valid=false;
                }

            }
            if (mobile.length()!=1) {
                try {
                    long phone_num = Long.parseLong(mobile);
                } catch (NumberFormatException e) {
                    mobileLayout.setError("*Μη έγκυρος αριθμός!");
                    valid = false;
                }
            }
        }

        return valid;
    }
}
