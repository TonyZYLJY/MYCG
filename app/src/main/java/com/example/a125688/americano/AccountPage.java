package com.example.a125688.americano;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AccountPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    User current_user;
    User userDeposit;
    TextView depositAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        current_user = getIntent().getParcelableExtra("userInfo");
        depositAvailable = findViewById(R.id.PaymentMethod);

        Query queryItem = databaseRef.child("User").orderByChild("userId").equalTo(current_user.getUserId());
        queryItem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    userDeposit = data.getValue(User.class);
                    depositAvailable.append(String.format(" $%.2f", userDeposit.getAccountAmount()));
                }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });





        TextView displayName = (TextView) findViewById(R.id.displayName);

        displayName.setText(current_user.getName());

        // change the password
        TextView change_password = (TextView) findViewById(R.id.ChangePassword);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User current_user = getIntent().getParcelableExtra("userInfo");
                Intent change_pw = new Intent(AccountPage.this, Change_password.class);
                change_pw.putExtra("userInfo", current_user);
                startActivity(change_pw);
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.carButton) {
            User current_user = getIntent().getParcelableExtra("userInfo");
            Intent cart = new Intent(AccountPage.this, CartPage.class);
            cart.putExtra("userInfo", current_user);
            startActivity(cart);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Menu) {
            Intent menu = new Intent(AccountPage.this, com.example.a125688.americano.Menu.class);
            menu.putExtra("userInfo", current_user);
            startActivity(menu);
        } else if (id == R.id.Order_history) {

        } else if (id == R.id.Rewards) {
/*            Intent rewards = new Intent(AccountPage.this, Rewards.class);
            rewards.putExtra("userInfo", current_user);
            startActivity(rewards);*/
        } else if (id == R.id.Favorites) {
           Intent favorite = new Intent(AccountPage.this, favoritePage.class);
           favorite.putExtra("userInfo", current_user);
           startActivity(favorite);

        } else if (id == R.id.Account) {
            Intent account = new Intent(AccountPage.this, AccountPage.class);
            account.putExtra("userInfo", current_user);
            startActivity(account);
        } else if (id == R.id.Log_out) {
            Intent logOut = new Intent(AccountPage.this, Home.class);
            startActivity(logOut);
        } else if (id == R.id.About_us){
           Intent aboutUs = new Intent(AccountPage.this, aboutUsPage.class);
           aboutUs.putExtra("userInfo", current_user);
           startActivity(aboutUs);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
