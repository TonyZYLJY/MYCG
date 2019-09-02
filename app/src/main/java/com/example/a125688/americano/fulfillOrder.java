package com.example.a125688.americano;

import android.app.AlertDialog;
import android.app.DownloadManager;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class fulfillOrder extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
   Button completeOrder;
   DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
   TextView nameOnCup;
   TextView quantity;
   User current_user;
   ImageButton addCup;
   ImageButton subCup;
   Integer quantityCup = 0;
   DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("CartProducts");
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_fulfill_order);
      // define navigation view actions
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
              this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawer.addDrawerListener(toggle);
      toggle.syncState();
      NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);
      final Context context = this;
      AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
      // display name on navigation bar
      current_user = getIntent().getParcelableExtra("userInfo");
      View header = navigationView.getHeaderView(0);
      TextView nav = header.findViewById(R.id.nav_name);
      nav.setText(current_user.getName());

      completeOrder = (Button) findViewById(R.id.completeBt);
      nameOnCup = (TextView) findViewById(R.id.printedName);
      quantity = (TextView) findViewById(R.id.quantityCup);
      addCup = (ImageButton) findViewById(R.id.plusCup);
      subCup = (ImageButton) findViewById(R.id.minusCup);

      addCup.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (quantityCup < 10){
               quantityCup = quantityCup + 1;
               quantity.setText(quantityCup.toString());
            }
         }
      });
      subCup.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if(quantityCup > 0) {
               quantityCup = quantityCup - 1;
               quantity.setText(quantityCup.toString());
            }
         }
      });

   readCartLines(new FirebaseCallbackCartLine() {
      @Override
      public void onCallBack(final Integer newOrderLine) {
         completeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
               if(nameOnCup.getText() == "")
               {
                  dlgAlert.setMessage("Name on cup cannot be empty.");
                  dlgAlert.setTitle("Error Message...");
                  dlgAlert.setPositiveButton("OK", null);
                  dlgAlert.setCancelable(true);
                  dlgAlert.create().show();
               }else {
                  Bundle bundle = new Bundle();
                  bundle.putString("nameOnCup",nameOnCup.getText().toString() );
                  bundle.putInt("quantityCup", quantityCup);
                  User current_user = getIntent().getParcelableExtra("userInfo");
                  Intent checkOutIntent = new Intent(fulfillOrder.this, checkOutPage.class);
                  checkOutIntent.putExtras(bundle);
                  checkOutIntent.putExtra("userInfo", current_user);
                  startActivity(checkOutIntent);
               }
               if(quantityCup > 0)
               {
                  Double price = 0.50 * quantityCup;
                  Cartline extraCups = new Cartline();
                  extraCups.setLineNo(newOrderLine);
                  extraCups.setDescription("Extra Cup");
                  extraCups.setAttachedToLineNo(0);
                  extraCups.setPrice(price);
                  extraCups.setProductNo(9);
                  extraCups.setQuantity(quantityCup);
                  extraCups.setSize("16 oz");
                  extraCups.setUserId(current_user.getUserId());
                  databaseRef.child("CartProducts");
                  databaseRef.child("CartProducts").push().setValue(extraCups);
               }
            }
         });
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
         Intent menu = new Intent(fulfillOrder.this, CartPage.class);
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
         Intent menu = new Intent(fulfillOrder.this, com.example.a125688.americano.Menu.class);
         menu.putExtra("userInfo", current_user);
         startActivity(menu);
      } else if (id == R.id.Order_history) {
         Intent orderHistory = new Intent(fulfillOrder.this, OrderHistory.class);
         orderHistory.putExtra("userInfo", current_user);
         startActivity(orderHistory);
      } else if (id == R.id.Rewards) {

      } else if (id == R.id.Favorites) {
         User current_user = getIntent().getParcelableExtra("userInfo");
         Intent favorite = new Intent(fulfillOrder.this, favoritePage.class);
         favorite.putExtra("userInfo", current_user);
         startActivity(favorite);

      } else if (id == R.id.Account) {
         //User current_user = getIntent().getParcelableExtra("userInfo");
         Intent account = new Intent(fulfillOrder.this, AccountPage.class);
         account.putExtra("userInfo", current_user);
         startActivity(account);
      } else if (id == R.id.Log_out) {
         Intent logOut = new Intent(fulfillOrder.this, Home.class);
         startActivity(logOut);
      } else if (id == R.id.About_us){
         User current_user = getIntent().getParcelableExtra("userInfo");
         Intent aboutUs = new Intent(fulfillOrder.this, aboutUsPage.class);
         aboutUs.putExtra("userInfo", current_user);
         startActivity(aboutUs);
      }

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }

   private void readCartLines( final FirebaseCallbackCartLine firebaseCallbackCartLines){
      // Getting Dairy AddOns from database
      Query queryCartLine= databaseRef.child("CartProducts").orderByChild("userId").equalTo(current_user.getUserId().toString());
      queryCartLine.addValueEventListener(new ValueEventListener(){
         @Override
         public void onDataChange(DataSnapshot dataSnapshot){
            Integer lineNo = 10;
            Integer newLineNo = 10;
            Boolean createNewLine = false;
            for(DataSnapshot cartLine:dataSnapshot.getChildren())
            {
               //if(cartLine.child("attachedToLineNo").getValue(Integer.class) == 0)
               //{
               if(lineNo <= cartLine.child("lineNo").getValue(Integer.class))
               {
                  lineNo = cartLine.child("lineNo").getValue(Integer.class);
                  createNewLine = true;
               }
               //}
            }
            if(createNewLine)
            {
               newLineNo = lineNo + 10;
            }
            firebaseCallbackCartLines.onCallBack(newLineNo);
         }
         @Override
         public void onCancelled(DatabaseError databaseError) {
         }
      });
   }
   public interface FirebaseCallbackCartLine{
      void onCallBack (Integer newOrderLine);
   }
}
