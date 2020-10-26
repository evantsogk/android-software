package gr.aueb.softeng.project1805.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import gr.aueb.softeng.project1805.R;
import gr.aueb.softeng.project1805.domain.ActivePackage;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.Transfer;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.domain.Package;
import gr.aueb.softeng.project1805.memorydao.ReadData;
import gr.aueb.softeng.project1805.service.PackageExpiration;
import gr.aueb.softeng.project1805.service.TransferService;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout home_tabs;
    ViewPager home_page;
    Fragment pageData, pageTalkTime, pageSMS;
    User user;
    HomeViewPagerAdapter adapter;
    NavigationView navigationView;
    View pageDataAdd, pageTalkTimeAdd, pageSMSAdd;
    ReadData readData = new ReadData();
    AlertDialog.Builder dialogBuilder;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user=(User) getIntent().getSerializableExtra("User");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        home_tabs=findViewById(R.id.home_tabs);
        home_page=findViewById(R.id.home_page);

        home_tabs.setupWithViewPager(home_page);
        setUpViewPager(home_page);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.drawer_username);
        navUsername.setText(user.getUsername());

        headerView = navigationView.getHeaderView(0);
        TextView navPhone = headerView.findViewById(R.id.drawer_phone);
        navPhone.setText(user.getPhoneNum());

        PackageExpiration packageExpiration=new PackageExpiration();
        packageExpiration.checkPackageExpirations(readData.getUserDAO().find(user.getUid()));

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final View rootView = getWindow().getDecorView().getRootView();
        ViewTreeObserver.OnGlobalLayoutListener listener=
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        TransferService transferService=new TransferService();
                        ArrayList<Transfer> received = transferService.receive(readData.getUserDAO().find(user.getUid()));


                        String dialogText="";
                        if (!received.isEmpty()) {
                            final User u=readData.getUserDAO().find(user.getUid());
                            int data=u.getRemainingData();
                            int talktime=u.getRemainingTalkTime();
                            int sms=u.getRemainingSMS();
                            home_tabs.getTabAt(0).setText("Data\n"+toGB(data));
                            home_tabs.getTabAt(1).setText("Ομιλία\n"+talktime+"'");
                            home_tabs.getTabAt(2).setText("SMS\n"+sms);
                            for (Transfer transfer : received) {
                                dialogText+="Το "+transfer.getSenderNum()+" σας έστειλε "+transfer.getQuantity();
                                if (transfer.getServiceType()==ServiceType.Data) dialogText+=" ΜΒ";
                                else if (transfer.getServiceType()==ServiceType.TalkTime) dialogText+="'";
                                else dialogText+=" SMS";
                                dialogText+="\n";
                            }
                            dialogBuilder= new AlertDialog.Builder(Home.this,R.style.AlertDialogTheme);
                            dialogBuilder.setMessage(dialogText);

                            dialogBuilder.setPositiveButton(
                                    "Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                           fab.setVisibility(View.GONE);
                                        }
                                    });

                            fab.setVisibility(View.VISIBLE);
                            fab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alert = dialogBuilder.create();
                                    alert.show();
                                }
                            });
                        }

                        rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                };
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(listener);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            moveTaskToBack(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_home);
        final User u=readData.getUserDAO().find(user.getUid());
        int data=u.getRemainingData();
        int talktime=u.getRemainingTalkTime();
        int sms=u.getRemainingSMS();
        home_tabs.getTabAt(0).setText("Data\n"+toGB(data));
        home_tabs.getTabAt(1).setText("Ομιλία\n"+talktime+"'");
        home_tabs.getTabAt(2).setText("SMS\n"+sms);
        final View rootView = getWindow().getDecorView().getRootView();
        ViewTreeObserver.OnGlobalLayoutListener listener=
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        pageData = adapter.getItem(0);
                        pageTalkTime = adapter.getItem(1);
                        pageSMS = adapter.getItem(2);

                        ((HomePageData) pageData).clearView();
                        ((HomePageTalkTime) pageTalkTime).clearView();
                        ((HomePageSMS) pageSMS).clearView();

                        int dataFromPackages = 0;
                        int talktimeFromPackages = 0;
                        int smsFromPackages = 0;
                        Package pack;
                        String name, expiration, remaining;
                        Calendar date;

                        for (ActivePackage activePackage : u.findAllActive()) {
                            pack=readData.getPackageDAO().find(activePackage.getPackageId());
                            name=pack.getName();
                            remaining=String.valueOf(activePackage.getRemainingQuantity());
                            date=activePackage.getActivationDate();
                            date.add(DATE, pack.getDuration()+1);
                            expiration="Λήγει στις "+date.get(DATE)+"/"+String.valueOf(date.get(MONTH)+1)+"/"+date.get(YEAR);

                            if (pack.getServiceType()==ServiceType.Data) {
                                dataFromPackages += activePackage.getRemainingQuantity();
                                remaining+="MB";
                                ((HomePageData) pageData).addActivePackage(name, expiration, remaining);
                            }
                            else if (pack.getServiceType()==ServiceType.TalkTime) {
                                talktimeFromPackages += activePackage.getRemainingQuantity();
                                remaining+="'";
                                ((HomePageTalkTime) pageTalkTime).addActivePackage(name, expiration, remaining);
                            }
                            else {
                                smsFromPackages += activePackage.getRemainingQuantity();
                                ((HomePageSMS) pageSMS).addActivePackage(name, expiration, remaining);
                            }
                        }

                        ((HomePageData) pageData).setDataFromPackages(String.valueOf(dataFromPackages));
                        ((HomePageData) pageData).setDataFromFriends(String.valueOf(u.getRemainingData() - dataFromPackages));

                        ((HomePageTalkTime) pageTalkTime).setTalkTimeFromPackages(String.valueOf(talktimeFromPackages));
                        ((HomePageTalkTime) pageTalkTime).setTalkTimeFromFriends(String.valueOf(u.getRemainingTalkTime() - talktimeFromPackages));

                        ((HomePageSMS) pageSMS).setSMSFromPackages(String.valueOf(smsFromPackages));
                        ((HomePageSMS) pageSMS).setSMSFromFriends(String.valueOf(u.getRemainingSMS()-smsFromPackages));

                        pageDataAdd=((HomePageData) pageData).getAddView();
                        pageDataAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent packages = new Intent(Home.this, PackagesActivity.class);
                                packages.putExtra("User", user);
                                startActivity(packages);
                            }
                        });
                        pageTalkTimeAdd=((HomePageTalkTime) pageTalkTime).getAddView();
                        pageTalkTimeAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent packages = new Intent(Home.this, PackagesActivity.class);
                                packages.putExtra("User", user);
                                startActivity(packages);
                            }
                        });
                        pageSMSAdd=((HomePageSMS) pageSMS).getAddView();
                        pageSMSAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent packages = new Intent(Home.this, PackagesActivity.class);
                                packages.putExtra("User", user);
                                startActivity(packages);
                            }
                        });

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_packages) {
            Intent packages = new Intent(Home.this, PackagesActivity.class);
            packages.putExtra("User", user);
            startActivity(packages);
            return true;
        }
        else if (id == R.id.action_transfer) {
            Intent transfer = new Intent(Home.this, TransferActivity.class);
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

        } else if (id == R.id.nav_packages) {
            Intent packages = new Intent(Home.this, PackagesActivity.class);
            packages.putExtra("User", user);
            startActivity(packages);
        } else if (id == R.id.nav_transfer) {
            Intent transfer = new Intent(Home.this, TransferActivity.class);
            transfer.putExtra("User", user);
            startActivity(transfer);
        } else if (id == R.id.nav_statistics) {
            Intent statistics = new Intent(Home.this, StatisticsActivity.class);
            statistics.putExtra("User", user);
            startActivity(statistics);
        } else if (id == R.id.nav_history) {
            Intent history = new Intent(Home.this, HistoryActivity.class);
            history.putExtra("User", user);
            startActivity(history);
        } else if (id == R.id.nav_update) {
            Intent update = new Intent(Home.this, UpdateActivity.class);
            update.putExtra("User", user);
            startActivity(update);
        } else if (id == R.id.nav_logout) {
            readData.eraseData();
            readData.readUsers(getResources().openRawResource(R.raw.users));
            Intent login = new Intent(Home.this, LoginActivity.class);
            startActivity(login);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setUpViewPager(ViewPager viewPager) {
        adapter = new HomeViewPagerAdapter(getSupportFragmentManager());

        int data=user.getRemainingData();
        String talktime=Integer.toString(user.getRemainingTalkTime());
        String sms=Integer.toString(user.getRemainingSMS());

        pageData=new HomePageData();
        adapter.addFragmentPage(pageData, "Data\n"+toGB(data));
        pageTalkTime=new HomePageTalkTime();
        adapter.addFragmentPage(pageTalkTime, "Ομιλία\n"+talktime+"'");
        pageSMS=new HomePageSMS();
        adapter.addFragmentPage(pageSMS, "SMS\n"+sms);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    public class HomeViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments=new ArrayList<>();
        private List<String> pageTitles=new ArrayList<>();

        public HomeViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
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
            return 3;
        }

    }

    public String toGB (int data) {
        if (Integer.toString(data).length()>3) {
            return String.valueOf((float) data/1000)+"GB";
        }
        return String.valueOf(data)+"MB";
    }
}

