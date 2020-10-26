package gr.aueb.softeng.project1805.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import gr.aueb.softeng.project1805.R;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.ReadData;

public class LoadingActivity extends AppCompatActivity {

    int progress = 1;
    ProgressBar progressBar;
    ReadData readData=new ReadData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        readData.readPackages(getResources().openRawResource(R.raw.packages));
        readData.readActivePackages(getResources().openRawResource(R.raw.activepackages));
        readData.readTransfers(getResources().openRawResource(R.raw.transfers));
        readData.readServiceUsages(getResources().openRawResource(R.raw.service_usage));
        readData.readStatistics(getResources().openRawResource(R.raw.statistics));
        readData.readAccounts(getResources().openRawResource(R.raw.bankaccounts));

        progressBar = findViewById(R.id.progressBar);
        progressBar.getLayoutParams().width = getWindowManager().getDefaultDisplay().getWidth()-50;
        progressBar.invalidate();
        progressBar.setProgress(progress);

        increaseProgress();
    }

    public void increaseProgress() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 2; i <= 701; i++) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progress++;
                    progressBar.setProgress(progress);
                }
                Intent home = new Intent(LoadingActivity.this, Home.class);
                User user=(User) getIntent().getSerializableExtra("User");

                user=readData.getUser(user.getUid());

                home.putExtra("User", user);
                startActivity(home);
                finish();

            }
        });
        thread.start();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
