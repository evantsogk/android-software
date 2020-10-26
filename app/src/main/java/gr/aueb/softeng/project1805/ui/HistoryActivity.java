package gr.aueb.softeng.project1805.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gr.aueb.softeng.project1805.R;
import gr.aueb.softeng.project1805.domain.ActivePackage;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.Transfer;
import gr.aueb.softeng.project1805.domain.TransferType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.domain.Package;
import gr.aueb.softeng.project1805.memorydao.ReadData;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class HistoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    User user;
    ReadData readData = new ReadData();
    TabLayout history_tabs;
    ViewPager history_page;
    Fragment pagePurchases, pageTransfers;
    HistoryViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = (User) getIntent().getSerializableExtra("User");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_history);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.drawer_username);
        navUsername.setText(user.getUsername());

        headerView = navigationView.getHeaderView(0);
        TextView navPhone = headerView.findViewById(R.id.drawer_phone);
        navPhone.setText(user.getPhoneNum());

        history_tabs = findViewById(R.id.history_tabs);
        history_page = findViewById(R.id.history_pager);

        history_tabs.setupWithViewPager(history_page);
        setUpViewPager(history_page);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_history);

        final View rootView = getWindow().getDecorView().getRootView();
        ViewTreeObserver.OnGlobalLayoutListener listener =
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        pagePurchases = adapter.getItem(0);
                        pageTransfers = adapter.getItem(1);

                        ((HistoryPagePurchases) pagePurchases).clearView();
                        ((HistoryPageTransfers) pageTransfers).clearView();

                        Package pack;
                        String name, price, date;
                        Calendar calendar;
                        int count=1;
                        for (ActivePackage activePackage : readData.getActivePackageDAO().findAllPackages(user.getUid())) {
                            if (count>100) break;

                            pack=readData.getPackageDAO().find(activePackage.getPackageId());
                            name=pack.getName();
                            price=String.format("%,.2f", pack.getPrice())+"€";
                            calendar=activePackage.getActivationDate();
                            date=calendar.get(DATE)+"/"+String.valueOf(calendar.get(MONTH)+1)+"/"+calendar.get(YEAR);

                            ((HistoryPagePurchases) pagePurchases).addItem(name, price, date);

                            count++;
                        }

                        String phone, quantity;
                        count=1;

                        for (Transfer transfer : readData.getTransferDAO().findAll(user.getUid())) {
                            if (count>100) break;

                            if (transfer.getTransferType()== TransferType.Incoming) phone=transfer.getSenderNum();
                            else phone=transfer.getReceiverNum();

                            calendar=transfer.getDate();
                            date=date=calendar.get(DATE)+"/"+String.valueOf(calendar.get(MONTH)+1)+"/"+calendar.get(YEAR);
                            quantity=String.valueOf(transfer.getQuantity());
                            if (transfer.getServiceType()== ServiceType.Data) quantity+=" MB";
                            else if (transfer.getServiceType()== ServiceType.TalkTime) quantity+=" Λεπτά";
                            else quantity+=" SMS";

                            ((HistoryPageTransfers) pageTransfers).addItem(phone, quantity, date, transfer.getTransferType());

                            count++;
                        }


                        rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                };
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
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
            Intent packages = new Intent(HistoryActivity.this, PackagesActivity.class);
            packages.putExtra("User", user);
            startActivity(packages);
            return true;
        }
        else if (id == R.id.action_transfer) {
            Intent transfer = new Intent(HistoryActivity.this, TransferActivity.class);
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
            Intent home = new Intent(HistoryActivity.this, Home.class);
            home.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(home);
        } else if (id == R.id.nav_packages) {
            Intent packages = new Intent(HistoryActivity.this, PackagesActivity.class);
            packages.putExtra("User", user);
            startActivity(packages);
        } else if (id == R.id.nav_transfer) {
            Intent transfer = new Intent(HistoryActivity.this, TransferActivity.class);
            transfer.putExtra("User", user);
            startActivity(transfer);
        } else if (id == R.id.nav_statistics) {
            Intent statistics = new Intent(HistoryActivity.this, StatisticsActivity.class);
            statistics.putExtra("User", user);
            startActivity(statistics);
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_update) {
            Intent update = new Intent(HistoryActivity.this, UpdateActivity.class);
            update.putExtra("User", user);
            startActivity(update);
        } else if (id == R.id.nav_logout) {
            readData.eraseData();
            readData.readUsers(getResources().openRawResource(R.raw.users));
            Intent login = new Intent(HistoryActivity.this, LoginActivity.class);
            startActivity(login);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setUpViewPager(ViewPager viewPager) {
        adapter = new HistoryViewPagerAdapter(getSupportFragmentManager());

        pagePurchases=new HistoryPagePurchases();
        adapter.addFragmentPage(pagePurchases, "ΑΓΟΡΕΣ ΠΑΚΕΤΩΝ");
        pageTransfers=new HistoryPageTransfers();
        adapter.addFragmentPage(pageTransfers, "ΠΑΡΑΧΩΡΗΣΕΙΣ");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
    }

    public class HistoryViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments=new ArrayList<>();
        private List<String> pageTitles=new ArrayList<>();

        public HistoryViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);

        }

        public void addFragmentPage(android.support.v4.app.Fragment fragment, String title) {
            fragments.add(fragment);
            pageTitles.add(title);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitles.get(position);
        }


        @Override
        public int getCount() {
            return 2;
        }

    }
}
