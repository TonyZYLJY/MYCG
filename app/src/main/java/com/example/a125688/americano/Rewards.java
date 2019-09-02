package com.example.a125688.americano;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Rewards extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    ProgressBar progress;
    User current_user;
    User user;
    TextView displayMessage;
    TextView showProcess;
    TextView more;
    Button getReward;
    Double rewardedAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        final Context context = this;
        progress = findViewById(R.id.rewardProgress);
        displayMessage = findViewById(R.id.displayRewards);
        getReward = findViewById(R.id.exchangeReward);
        showProcess = findViewById(R.id.showProcess);
        more = findViewById(R.id.more);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // display name on navigation bar
        current_user = getIntent().getParcelableExtra("userInfo");
        View header = navigationView.getHeaderView(0);
        TextView nav = header.findViewById(R.id.nav_name);
        nav.setText(current_user.getName());

        Query queryItem = databaseRef.child("User").orderByChild("userId").equalTo(current_user.getUserId());
        queryItem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    user = data.getValue(User.class);
                    if (user.getRewards() >= 100)
                        user.setRewards(100);
                    progress.setProgress(user.getRewards());
                    showProcess.setText(user.getRewards() + "%");
                    more.setText((progress.getMax() - progress.getProgress()) + "% more");
                    if (progress.getProgress() >= progress.getMax()){
                        displayMessage.setText("You are eligible for free $5!");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        getReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getRewards() >= 100){
                    rewardedAmount = (user.getAccountAmount() + 5.00);
                    final Query update = databaseRef.child("User").orderByChild("userId").equalTo(user.getUserId());
                    update.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                            String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                            String path = "/" + dataSnapshot.getKey() + "/" + key;
                            HashMap<String, Object> result = new HashMap<>();
                            result.put("accountAmount", rewardedAmount);
                            result.put("rewards", 0);
                            databaseRef.child(path).updateChildren(result);
                            Toast.makeText(context, "$5 is Added", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
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
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
            Intent cart = new Intent(Rewards.this, CartPage.class);
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
            Intent menu = new Intent(Rewards.this, Menu.class);
            menu.putExtra("userInfo", current_user);
            startActivity(menu);
        } else if (id == R.id.Order_history) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent orderHistory = new Intent(Rewards.this, OrderHistory.class);
           orderHistory.putExtra("userInfo", current_user);
           startActivity(orderHistory);
        } else if (id == R.id.Rewards) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent rewards = new Intent(Rewards.this, Rewards.class);
           rewards.putExtra("userInfo", current_user);
           startActivity(rewards);
        } else if (id == R.id.Favorites) {
            Intent favorite = new Intent(Rewards.this, favoritePage.class);
            favorite.putExtra("userInfo", current_user);
            startActivity(favorite);
        } else if (id == R.id.Account) {
            Intent intent = new Intent(Rewards.this, AccountPage.class);
            intent.putExtra("userInfo", current_user);
            startActivity(intent);
        } else if (id == R.id.Log_out) {
            Intent logOut = new Intent(Rewards.this, Home.class);
            startActivity(logOut);
        } else if (id == R.id.About_us){
            Intent aboutUs = new Intent(Rewards.this, aboutUsPage.class);
            aboutUs.putExtra("userInfo", current_user);
            startActivity(aboutUs);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
