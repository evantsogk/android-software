package gr.aueb.softeng.project1805.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gr.aueb.softeng.project1805.R;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.memorydao.ReadData;
import gr.aueb.softeng.project1805.utils.SystemDate;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class StatisticsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    User user;
    ReadData readData = new ReadData();
    List<float[]> statistics;
    ArrayList<BarEntry> entries=new ArrayList<>();
    ArrayList<String> labels=new ArrayList<>();
    TextView txtDate, txtAmount, txtDataVolume, txtTalktime, txtSMS;
    String date, amount, dataVolume, talktime, sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user=(User) getIntent().getSerializableExtra("User");

        statistics=readData.getStatisticsDAO().findAll(user.getUid());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_statistics);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.drawer_username);
        navUsername.setText(user.getUsername());

        headerView = navigationView.getHeaderView(0);
        TextView navPhone = headerView.findViewById(R.id.drawer_phone);
        navPhone.setText(user.getPhoneNum());

        final BarChart barChart = findViewById(R.id.barchart);
        calculateBarData();
        BarDataSet bardataset = new BarDataSet(entries, "");
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);

        final View rootView = getWindow().getDecorView().getRootView();
        ViewTreeObserver.OnGlobalLayoutListener listener =
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        txtDate=findViewById(R.id.date);
                        txtAmount=findViewById(R.id.amount);
                        txtDataVolume=findViewById(R.id.data);
                        txtTalktime=findViewById(R.id.talktime);
                        txtSMS=findViewById(R.id.sms);

                        if (entries.size()!=0) {
                            date=getFullMonth(statistics.get(entries.size()-3)[1]+1)+" "+(int) statistics.get(entries.size()-3)[0];
                            txtDate.setText(date);
                            amount=String.format("%,.2f", statistics.get(entries.size()-3)[2])+"€";
                            txtAmount.setText(amount);
                            dataVolume=(int) statistics.get(entries.size()-3)[3]+" MB";
                            txtDataVolume.setText(dataVolume);
                            talktime=(int) statistics.get(entries.size()-3)[4]+" Λεπτά";
                            txtTalktime.setText(talktime);
                            sms=(int) statistics.get(entries.size()-3)[5]+" SMS";
                            txtSMS.setText(sms);
                        }
                        else {
                            Calendar calendar= SystemDate.getSystemDate();
                            date=getFullMonth(calendar.get(MONTH)+1)+" "+getFullMonth(calendar.get(YEAR));
                            txtDate.setText(date);
                            amount="0 €";
                            txtAmount.setText(amount);
                            dataVolume="0 MB";
                            txtDataVolume.setText(dataVolume);
                            talktime="0 Λεπτά";
                            txtTalktime.setText(talktime);
                            sms="0 SMS";
                            txtSMS.setText(sms);
                        }

                        rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                };
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(listener);


        barChart.highlightValue(entries.size()-2, 0);

        barChart.setOnChartValueSelectedListener( new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                int i=entries.indexOf(e);

                if (i!=0 && i!=entries.size()-1) {
                    date=getFullMonth(statistics.get(i-1)[1]+1)+" "+(int) statistics.get(i-1)[0];
                    txtDate.setText(date);
                    amount=String.format("%,.2f", statistics.get(i-1)[2])+"€";
                    txtAmount.setText(amount);
                    dataVolume=(int) statistics.get(i-1)[3]+" MB";
                    txtDataVolume.setText(dataVolume);
                    talktime=(int) statistics.get(i-1)[4]+" Λεπτά";
                    txtTalktime.setText(talktime);
                    sms=(int) statistics.get(i-1)[5]+" SMS";
                    txtSMS.setText(sms);
                }
            }

            @Override
            public void onNothingSelected() {}
        });

        bardataset.setColor(getResources().getColor(R.color.barColor));
        barChart.animateY(1500);
        barChart.setVisibleXRangeMaximum(5);
        bardataset.setBarSpacePercent(50f);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.moveViewToX(entries.size());
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setDescription("");
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
        barChart.getXAxis().setTextSize(13f);
        barChart.getXAxis().setTextSize(13f);
        bardataset.setValueTextSize(10f);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_statistics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_packages) {
            Intent packages = new Intent(StatisticsActivity.this, PackagesActivity.class);
            packages.putExtra("User", user);
            startActivity(packages);
            return true;
        }
        else if (id == R.id.action_transfer) {
            Intent transfer = new Intent(StatisticsActivity.this, TransferActivity.class);
            transfer.putExtra("User", user);
            startActivity(transfer);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent home = new Intent(StatisticsActivity.this, Home.class);
            home.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(home);
        } else if (id == R.id.nav_packages) {
            Intent packages = new Intent(StatisticsActivity.this, PackagesActivity.class);
            packages.putExtra("User", user);
            startActivity(packages);
        } else if (id == R.id.nav_transfer) {
            Intent transfer = new Intent(StatisticsActivity.this, TransferActivity.class);
            transfer.putExtra("User", user);
            startActivity(transfer);
        } else if (id == R.id.nav_statistics) {

        } else if (id == R.id.nav_history) {
            Intent history = new Intent(StatisticsActivity.this, HistoryActivity.class);
            history.putExtra("User", user);
            startActivity(history);
        } else if (id == R.id.nav_update) {
            Intent update = new Intent(StatisticsActivity.this, UpdateActivity.class);
            update.putExtra("User", user);
            startActivity(update);
        } else if (id == R.id.nav_logout) {
            readData.eraseData();
            readData.readUsers(getResources().openRawResource(R.raw.users));
            Intent login = new Intent(StatisticsActivity.this, LoginActivity.class);
            startActivity(login);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void calculateBarData() {
        if (statistics.size()!=0) {
            labels.add(getMonth(statistics.get(0)[1]));
            for (int i=0; i<statistics.size(); i++) {
                labels.add(getMonth(statistics.get(i)[1]+1));
            }
            labels.add(getMonth(statistics.get(statistics.size()-1)[1]+2));

            entries.add(new BarEntry(0f, 0));
            for (int i=1; i<labels.size()-1; i++) {
                entries.add(new BarEntry(statistics.get(i-1)[2], i));
            }
            entries.add(new BarEntry(0f, labels.size()-1));
        }
    }

    public String getMonth(float month) {
        switch((int) month) {
            case 0: return "Δεκ";
            case 1: return "Ιαν";
            case 2: return "Φεβ";
            case 3: return "Μάρ";
            case 4: return "Απρ";
            case 5: return "Μάι";
            case 6: return "Ιούν";
            case 7: return "Ιούλ";
            case 8: return "Αύγ";
            case 9: return "Σεπ";
            case 10: return "Οκτ";
            case 11: return "Νοέμ";
            case 12: return "Δεκ";
            default: return "";
        }
    }

    public String getFullMonth(float month) {
        switch((int) month) {
            case 1: return "Ιανουάριος";
            case 2: return "Φεβρουάριος";
            case 3: return "Μάρτιος";
            case 4: return "Απρίλιος";
            case 5: return "Μάιος";
            case 6: return "Ιούνιος";
            case 7: return "Ιούλιος";
            case 8: return "Αύγουστος";
            case 9: return "Σεπτέμβριος";
            case 10: return "Οκτώβριος";
            case 11: return "Νοέμβριος";
            case 12: return "Δεκέμβριος";
            default: return "";
        }
    }
}
