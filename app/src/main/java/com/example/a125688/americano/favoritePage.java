package com.example.a125688.americano;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class favoritePage extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
   DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
   User current_user;
   ListView favoriteListV;
   favoriteAdapter favAdapt;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_favorite_page);

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

      favoriteListV = findViewById(R.id.favoriteListView);

      favoriteProducts(new FirebaseFavoriteCallbackCartLine() {
         @Override
         public void onCallBack(ArrayList<favorite> favList) {
            favAdapt = new favoriteAdapter(favoritePage.this,favList, current_user);
            favoriteListV.setAdapter(favAdapt);
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

      if (id == R.id.carButton){
         Intent menu = new Intent(favoritePage.this, CartPage.class);
         menu.putExtra("userInfo", current_user);
         startActivity(menu);
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
         //User current_user = getIntent().getParcelableExtra("userInfo");
         Intent menu = new Intent(favoritePage.this, com.example.a125688.americano.Menu.class);
         menu.putExtra("userInfo", current_user);
         startActivity(menu);
      } else if (id == R.id.Order_history) {

      } else if (id == R.id.Rewards) {
         Intent rewards = new Intent(favoritePage.this, com.example.a125688.americano.Menu.class);
         rewards.putExtra("userInfo", current_user);
         startActivity(rewards);
      } else if (id == R.id.Favorites) {

      } else if (id == R.id.Account) {
         //User current_user = getIntent().getParcelableExtra("userInfo");
         Intent account = new Intent(favoritePage.this, AccountPage.class);
         account.putExtra("userInfo", current_user);
         startActivity(account);
      } else if (id == R.id.Log_out) {
         Intent logOut = new Intent(favoritePage.this, Home.class);
         startActivity(logOut);
      } else if (id == R.id.About_us){
         User current_user = getIntent().getParcelableExtra("userInfo");
         Intent aboutUs = new Intent(favoritePage.this, aboutUsPage.class);
         aboutUs.putExtra("userInfo", current_user);
         startActivity(aboutUs);
      }

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }

   private void favoriteProducts(final FirebaseFavoriteCallbackCartLine firebaseCallback) {
      final Context context = this;
      final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
      Query checkCart = databaseRef.child("Favorites").orderByChild("userId").equalTo(current_user.getUserId());
      checkCart.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            ArrayList<favorite> favProducts = new ArrayList<>();
            for (DataSnapshot data : dataSnapshot.getChildren()) {
               favorite product = new favorite();
               product = data.getValue(favorite.class);
               product.setKey(data.getKey());
               favProducts.add(product);
            }
            firebaseCallback.onCallBack(favProducts);
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
      });
   }

   public interface FirebaseFavoriteCallbackCartLine{
      void onCallBack (ArrayList<favorite> favList);
   }
}
