package com.example.a125688.americano;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ImageButton IcedCoffeeButton;
    ImageButton SmoothieButton;
    ImageButton TeaButton;
    ImageButton Frappe;
    ImageButton Desserts;
    ImageButton Chocolate;
    ImageButton Coffee;
    ImageButton Espresso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       System.runFinalization();
       Runtime.getRuntime().gc();
       System.gc();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
        final User current_user = getIntent().getParcelableExtra("userInfo");
        View header = navigationView.getHeaderView(0);
        TextView nav = header.findViewById(R.id.nav_name);
        nav.setText(current_user.getName());

        TeaButton        = findViewById(R.id.HotTeaBt);
        Frappe           = findViewById(R.id.FrappeBt);
        IcedCoffeeButton = (ImageButton)findViewById(R.id.icedCoffeeBt);
        SmoothieButton   = (ImageButton)findViewById(R.id.SmoothiesBt);
        Desserts         = (ImageButton)findViewById(R.id.DessertsBt);
        Chocolate        = (ImageButton) findViewById(R.id.ChocolateBt);
        Coffee           = (ImageButton) findViewById(R.id.CoffeeBt);
        Espresso         = (ImageButton) findViewById(R.id.EspressoBt);

        IcedCoffeeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent icedCoffe = new Intent(Menu.this, IcedCoffeePage.class);
                icedCoffe.putExtra("userInfo", current_user);
                startActivity(icedCoffe);
            }
        });
        SmoothieButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent smoothie = new Intent(Menu.this, SmoothiePage.class);
                smoothie.putExtra("userInfo", current_user);
                startActivity(smoothie);
            }
        });
        TeaButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent tea = new Intent(Menu.this, Tea.class);
                tea.putExtra("userInfo", current_user);
                startActivity(tea);
            }
        });
        Frappe.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent frappe = new Intent(Menu.this, Frappe.class);
                frappe.putExtra("userInfo", current_user);
                startActivity(frappe);
            }
        });
        Desserts.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent dessert = new Intent(Menu.this, Desserts.class);
                dessert.putExtra("userInfo", current_user);
                startActivity(dessert);
            }
        });
        Chocolate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              Intent chocolate = new Intent(Menu.this, Chocolate.class);
              chocolate.putExtra("userInfo", current_user);
              startActivity(chocolate);
           }
        });
        Coffee.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              Intent coffee = new Intent(Menu.this, CoffeePage.class);
              coffee.putExtra("userInfo", current_user);
              startActivity(coffee);
           }
        });
        Espresso.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              Intent espresso = new Intent(Menu.this, Espresso.class);
              espresso.putExtra("userInfo", current_user);
              startActivity(espresso);
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
            Intent cart = new Intent(Menu.this, CartPage.class);
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

        } else if (id == R.id.Order_history) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent orderHistory = new Intent(Menu.this, OrderHistory.class);
           orderHistory.putExtra("userInfo", current_user);
           startActivity(orderHistory);

        } else if (id == R.id.Rewards) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent rewards = new Intent(Menu.this, Rewards.class);
           rewards.putExtra("userInfo", current_user);
           startActivity(rewards);

        } else if (id == R.id.Favorites) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent favorite = new Intent(Menu.this, favoritePage.class);
           favorite.putExtra("userInfo", current_user);
           startActivity(favorite);
        } else if (id == R.id.Account) {
            User current_user = getIntent().getParcelableExtra("userInfo");
            Intent intent = new Intent(Menu.this, AccountPage.class);
            intent.putExtra("userInfo", current_user);
            startActivity(intent);
        } else if (id == R.id.Log_out) {
            Intent logOut = new Intent(Menu.this, Home.class);
            startActivity(logOut);
        } else if (id == R.id.About_us){
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent aboutUs = new Intent(Menu.this, aboutUsPage.class);
           aboutUs.putExtra("userInfo", current_user);
           startActivity(aboutUs);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //   Spinner spinner = (Spinner) findViewById(R.id.spinner);
    // Create an ArrayAdapter using the string array and a default spinner layout
//    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//            R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
//spinner.setAdapter(adapter);

}
