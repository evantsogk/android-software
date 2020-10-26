package gr.aueb.softeng.project1805.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import gr.aueb.softeng.project1805.R;
import gr.aueb.softeng.project1805.memorydao.ReadData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ReadData readData=new ReadData();
        readData.readUsers(getResources().openRawResource(R.raw.users));

        Handler handler = new Handler();
        handler.postDelayed(startLoginActivity, 2000);
    }

    private Runnable startLoginActivity=new Runnable() {
        public void run() {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
            finish();
        }
    };
}
