package com.example.a125688.americano;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class
Change_password extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // get a Firebase reference
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    // store new password
    String password, password_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

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
        User current_user = getIntent().getParcelableExtra("userInfo");
        View header = navigationView.getHeaderView(0);
        TextView nav = header.findViewById(R.id.nav_name);
        nav.setText(current_user.getName());

        Button confirm = (Button) findViewById(R.id.confirm);

        // change the password
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText new_password = (EditText) findViewById(R.id.enter_password);
                EditText enter_password = (EditText) findViewById(R.id.reenter_password);
                final TextView isEmpty        = (TextView) findViewById(R.id.isEmpty);
                final TextView notSame         = (TextView) findViewById(R.id.notSame);

                password = new_password.getText().toString();
                password_again = enter_password.getText().toString();

                if (password.isEmpty()|| password_again.isEmpty()){
                    isEmpty.setText("Can't be empty");
                    notSame.setText("");
                }
                // check if user knows his password
                else if (password.equals(password_again)) {
                    User current_user = (User) getIntent().getParcelableExtra("userInfo");

                    // get key value for current user's password and update the password
                    databaseRef.child("User").orderByChild("name").equalTo(current_user.getName())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String key = null;
                                    for (DataSnapshot childSnapshot: dataSnapshot.getChildren())
                                        key = childSnapshot.getKey();
                                    databaseRef.child("User").child(key).child("password").setValue(password);
                                    isEmpty.setText("");
                                    notSame.setText("Saved Successfully");
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });

                }
                else{
                    notSame.setText("Password is not the same");
                    isEmpty.setText("");
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
            Intent cart = new Intent(com.example.a125688.americano.Change_password.this, CartPage.class);
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
            User current_user = getIntent().getParcelableExtra("userInfo");
            Intent menu = new Intent(Change_password.this, com.example.a125688.americano.Menu.class);
            menu.putExtra("userInfo", current_user);
            startActivity(menu);
        } else if (id == R.id.Order_history) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent orderHistory = new Intent(Change_password.this, OrderHistory.class);
           orderHistory.putExtra("userInfo", current_user);
           startActivity(orderHistory);
        } else if (id == R.id.Rewards) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent rewards = new Intent(Change_password.this, Rewards.class);
           rewards.putExtra("userInfo", current_user);
           startActivity(rewards);
        } else if (id == R.id.Favorites) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent favorite = new Intent(Change_password.this, favoritePage.class);
           favorite.putExtra("userInfo", current_user);
           startActivity(favorite);

        } else if (id == R.id.Account) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent account = new Intent(Change_password.this, AccountPage.class);
           account.putExtra("userInfo", current_user);
           startActivity(account);

        } else if (id == R.id.Log_out) {
            Intent logOut = new Intent(Change_password.this, Home.class);
            startActivity(logOut);
        } else if (id == R.id.About_us){
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent aboutUs = new Intent(Change_password.this, aboutUsPage.class);
           aboutUs.putExtra("userInfo", current_user);
           startActivity(aboutUs);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}