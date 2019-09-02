package com.example.a125688.americano;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.renderscript.Sampler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderHistory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

   DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
   Query queryProducts;
   ValueEventListener productsListener;
   User current_user;
   Query orderLines;
   ValueEventListener linesListener;
   HashMap<Integer, ArrayList<OrderLine>> listOrderLines = new HashMap<>();
   ExpandableListView expListView;
   OrderExpandableAdapter listAdapter;
   Context context;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_order_history);
      context = this;

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

      current_user = getIntent().getParcelableExtra("userInfo");
      View header = navigationView.getHeaderView(0);
      TextView nav = header.findViewById(R.id.nav_name);
      nav.setText(current_user.getName());
   }

   @Override
   protected  void  onStart(){
      readData(new FirebaseCallback() {
         @Override
         public void onCallBack(final ArrayList<OrderHeader> currOrders) {
            if(currOrders != null)
            {
               orderLines = databaseRef.child("OrderLines").orderByChild("userId").equalTo(current_user.getUserId().toString());
               linesListener = orderLines.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                     ArrayList<OrderLine> myOrderLines = new ArrayList<OrderLine>();
                     OrderLine myOrderLine = new OrderLine();

                     for (DataSnapshot data : dataSnapshot.getChildren())
                     {
                        myOrderLine =data.getValue(OrderLine.class);
                        //myOrderLine.setKey(data.getKey());
                        myOrderLines.add(myOrderLine);
                     }
                     AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);

                     for(int index = 0; index < currOrders.size(); index++)
                     {
                        OrderHeader currentOrder = currOrders.get(index);
                        ArrayList<OrderLine> orderLines = new ArrayList<>();

                        for(int index2 = 0; index2 < myOrderLines.size(); index2++)
                        {
                           if(myOrderLines.get(index2).getOrdeKey() == currentOrder.getOrderNo())
                           {
                              orderLines.add(myOrderLines.get(index2));
                           }

                        }
                        listOrderLines.put(currOrders.get(index).getOrderNo(), orderLines);
                     }

                     expListView =(ExpandableListView) findViewById(R.id.expandableOrderList);

                     listAdapter =new OrderExpandableAdapter(OrderHistory.this,currOrders,listOrderLines);
                     // setting list adapter
                     expListView.setAdapter(listAdapter);
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
               });
            }
         }
      });


      super.onStart();
   }

   @Override
   protected void onStop(){
      if(productsListener != null)
         queryProducts.removeEventListener(productsListener);
      if(linesListener != null)
         orderLines.removeEventListener(linesListener);
      super.onStop();
   }

   @Override
   protected  void onDestroy(){
      super.onDestroy();
   }

   private void readData(final FirebaseCallback firebaseCallback){
      User current_user = getIntent().getParcelableExtra("userInfo");
      //Get cart lines for customer
      queryProducts = databaseRef.child("OrderHeader").orderByChild("userId").equalTo(current_user.getUserId().toString());
      productsListener = queryProducts.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            ArrayList<OrderHeader> myOrders = new ArrayList<OrderHeader>();
            OrderHeader myOrder = new OrderHeader();

            for (DataSnapshot data : dataSnapshot.getChildren())
            {
               myOrder =data.getValue(OrderHeader.class);
               myOrder.setKey(data.getKey());
               myOrders.add(myOrder);
            }
            firebaseCallback.onCallBack(myOrders);
         }
         @Override
         public void onCancelled(DatabaseError databaseError) {
         }
      });
   }

   public interface FirebaseCallback{
      void onCallBack(ArrayList<OrderHeader> currOrders);
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
         Intent menu = new Intent(OrderHistory.this, CartPage.class);
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
         Intent menu = new Intent(OrderHistory.this, com.example.a125688.americano.Menu.class);
         menu.putExtra("userInfo", current_user);
         startActivity(menu);

      } else if (id == R.id.Order_history) {

      } else if (id == R.id.Rewards) {

      } else if (id == R.id.Favorites) {
         User current_user = getIntent().getParcelableExtra("userInfo");
         Intent favorite = new Intent(OrderHistory.this, favoritePage.class);
         favorite.putExtra("userInfo", current_user);
         startActivity(favorite);

      } else if (id == R.id.Account) {
         //User current_user = getIntent().getParcelableExtra("userInfo");
         Intent account = new Intent(OrderHistory.this, AccountPage.class);
         account.putExtra("userInfo", current_user);
         startActivity(account);
      } else if (id == R.id.Log_out) {
         Intent logOut = new Intent(OrderHistory.this, Home.class);
         startActivity(logOut);
      } else if (id == R.id.About_us){
         User current_user = getIntent().getParcelableExtra("userInfo");
         Intent aboutUs = new Intent(OrderHistory.this, aboutUsPage.class);
         aboutUs.putExtra("userInfo", current_user);
         startActivity(aboutUs);
      }

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }
}
