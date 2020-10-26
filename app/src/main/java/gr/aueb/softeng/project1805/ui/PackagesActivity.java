package gr.aueb.softeng.project1805.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import gr.aueb.softeng.project1805.R;
import gr.aueb.softeng.project1805.domain.ServiceType;
import gr.aueb.softeng.project1805.domain.User;
import gr.aueb.softeng.project1805.domain.Package;
import gr.aueb.softeng.project1805.memorydao.ReadData;
import gr.aueb.softeng.project1805.service.PackagePurchase;

import static java.security.AccessController.getContext;

public class PackagesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    User user;
    AlertDialog.Builder dialogBuilder,successBuilder,cardBuilder,purchasedBuilder;
    AlertDialog alert;
    ReadData readData = new ReadData();
    ArrayList<AlertDialog> alerts=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);
        user=(User) getIntent().getSerializableExtra("User");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_packages);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.drawer_username);
        navUsername.setText(user.getUsername());

        headerView = navigationView.getHeaderView(0);
        TextView navPhone = headerView.findViewById(R.id.drawer_phone);
        navPhone.setText(user.getPhoneNum());

        LinearLayout data_packages=this.findViewById(R.id.data_packages_layout);
        LinearLayout talktime_packages=this.findViewById(R.id.talktime_packages_layout);
        LinearLayout sms_packages=this.findViewById(R.id.sms_packages_layout);

        int i=-1;
        for (Package p : readData.getPackageDAO().findAll()) {
            i++;
            final int index=i;
            final Package p2=p;
            final View pack = getLayoutInflater().inflate(R.layout.package_view, null);

            final TextView txtName=pack.findViewById(R.id.name);
            txtName.setText(p.getName());
            final TextView txtPrice=pack.findViewById(R.id.price);
            String price=String.format("%,.2f", p.getPrice())+"€";
            txtPrice.setText(price);
            final TextView txtDuration=pack.findViewById(R.id.duration);
            String duration=String.valueOf(p.getDuration())+" μέρες";
            txtDuration.setText(duration);
            final TextView txtQuantity=pack.findViewById(R.id.quantity);
            String quantity=String.valueOf(p.getQuantity());

            if (p.getServiceType()== ServiceType.Data) {
                quantity+=" MB";
                txtQuantity.setText(quantity);
                data_packages.addView(pack);
            }
            else if (p.getServiceType()== ServiceType.TalkTime) {
                quantity+=" Λεπτά";
                txtQuantity.setText(quantity);
                talktime_packages.addView(pack);
            }
            else {
                quantity+=" SMS";
                txtQuantity.setText(quantity);
                sms_packages.addView(pack);
            }
            final View viewInflated = LayoutInflater.from(PackagesActivity.this).inflate(R.layout.card_input, (ViewGroup) getWindow().getDecorView(), false);
            final EditText input = (EditText) viewInflated.findViewById(R.id.input);
            dialogBuilder= new AlertDialog.Builder(PackagesActivity.this,R.style.AlertDialogTheme);
            dialogBuilder.setView(viewInflated);

            dialogBuilder.setTitle(txtName.getText().toString()+"  "+ txtPrice.getText().toString());
            dialogBuilder.setMessage("Εισάγετε τον αριθμό της κάρτας σας:");

            dialogBuilder.setCancelable(true);

            dialogBuilder.setPositiveButton(
                    "Αγορά", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String card=input.getText().toString();
                            PackagePurchase purchase=new PackagePurchase();
                            if (purchase.simpleCheckPayment(card,p2.getPrice())) {
                                if (purchase.purchasePackage(readData.getUserDAO().find(user.getUid()), p2, card)==null) {
                                    purchasedBuilder= new AlertDialog.Builder(PackagesActivity.this,R.style.AlertDialogTheme);
                                    purchasedBuilder.setMessage("Το πακέτο είναι ήδη ενεργοποιημένο!");
                                    purchasedBuilder.setPositiveButton(
                                            "Ok",null);


                                    alert = purchasedBuilder.create();

                                    alert.show();

                                }
                                else {
                                    successBuilder= new AlertDialog.Builder(PackagesActivity.this,R.style.AlertDialogTheme);
                                    successBuilder.setMessage("Η αγορά πραγματοποιήθηκε.");
                                    successBuilder.setPositiveButton(
                                            "Ok",null);


                                    alert =successBuilder.create();
                                    alert.show();
                                }
                            }
                            else {
                                if (readData.getAccountDAO().find(card) == null) {
                                    cardBuilder = new AlertDialog.Builder(PackagesActivity.this, R.style.AlertDialogTheme);
                                    cardBuilder.setMessage("Ο αριθμός κάρτας δεν υπάρχει.");
                                    cardBuilder.setPositiveButton(
                                            "Ok", null);


                                    alert = cardBuilder.create();
                                    alert.show();
                                }
                                else {
                                    cardBuilder = new AlertDialog.Builder(PackagesActivity.this, R.style.AlertDialogTheme);
                                    cardBuilder.setMessage("To υπόλοιπο της κάρτας σας δεν επαρκεί για την αγορα.");
                                    cardBuilder.setPositiveButton(
                                            "Ok", null);


                                    alert = cardBuilder.create();
                                    alert.show();
                                }
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
            alerts.add(alert);
            pack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alerts.get(index).show();
                }
            });
        }
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
        navigationView.setCheckedItem(R.id.nav_packages);
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
            return true;
        }
        else if (id == R.id.action_transfer) {
            Intent transfer = new Intent(PackagesActivity.this, TransferActivity.class);
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
            Intent home = new Intent(PackagesActivity.this, Home.class);
            home.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(home);
        } else if (id == R.id.nav_packages) {

        } else if (id == R.id.nav_transfer) {
            Intent transfer = new Intent(PackagesActivity.this, TransferActivity.class);
            transfer.putExtra("User", user);
            startActivity(transfer);
        } else if (id == R.id.nav_statistics) {
            Intent statistics = new Intent(PackagesActivity.this, StatisticsActivity.class);
            statistics.putExtra("User", user);
            startActivity(statistics);
        } else if (id == R.id.nav_history) {
            Intent history = new Intent(PackagesActivity.this, HistoryActivity.class);
            history.putExtra("User", user);
            startActivity(history);
        } else if (id == R.id.nav_update) {
            Intent update = new Intent(PackagesActivity.this, UpdateActivity.class);
            update.putExtra("User", user);
            startActivity(update);
        } else if (id == R.id.nav_logout) {
            readData.eraseData();
            readData.readUsers(getResources().openRawResource(R.raw.users));
            Intent login = new Intent(PackagesActivity.this, LoginActivity.class);
            startActivity(login);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
