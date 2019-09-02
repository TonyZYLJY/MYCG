package com.example.a125688.americano;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.design.widget.NavigationView;

import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;

public class CartPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    com.example.a125688.americano.ExpandableListAdapter listAdapter;
   DatabaseReference dbRef;
    ExpandableListView expListView;
    ArrayList<Cartline> listDataHeader = new ArrayList<Cartline>();
    ArrayList<Cartline> childDetails = new ArrayList<Cartline>();
    HashMap<Integer, ArrayList<Cartline>> listDataChild = new HashMap<>();
    ArrayList<Cartline> copyProducts = new ArrayList<Cartline>();
    Integer counter;
    Integer indexHeader = 0;
    Cartline productHeader;
    User current_user;
    ArrayList<String> hashArray = new ArrayList<String>();
    Double finalAmount = 0.0;
    ArrayList<Cartline> currProducts = new ArrayList<Cartline>();
    Query queryProducts;
    ValueEventListener productsListener;
    TextView showTotal;
    Button checkOut;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_cart_page);
       context = this;
       showTotal = (TextView) findViewById(R.id.totalView);
       checkOut = (Button) findViewById(R.id.checkOutButton);

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
       final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
    }

    @Override
    protected void onStart(){
       dbRef = FirebaseDatabase.getInstance().getReference("CartProducts");

       ChildEventListener listener = dbRef.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(DataSnapshot dataSnapshot, String s) {

          }

          @Override
          public void onChildChanged(DataSnapshot dataSnapshot, String s) {

          }

          @Override
          public void onChildRemoved(DataSnapshot dataSnapshot) {
             Cartline xuser = dataSnapshot.getValue(Cartline.class);
             xuser.setKey(dataSnapshot.getKey());
             listDataHeader.remove(xuser);
             listAdapter.notifyDataSetChanged();
          }

          @Override
          public void onChildMoved(DataSnapshot dataSnapshot, String s) {

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
       });

       readData(new FirebaseCallback() {
          @Override
          public void onCallBack(ArrayList<Cartline> currProducts) {
             for (int index = 0; index < currProducts.size(); index++) {
                if (currProducts.get(index).getAttachedToLineNo().equals(0)) {
                   productHeader = currProducts.get(index);
                   listDataHeader.add(productHeader);
                }
             }

             for(int index2 = 0; index2 < listDataHeader.size(); index2++)
             {
                Cartline currentProduct = listDataHeader.get(index2);
                childDetails = new ArrayList<>();
                listDataChild.put(listDataHeader.get(index2).getLineNo(), childDetails);

                for(int index3 = 0; index3 < currProducts.size(); index3++)
                {
                   if(currProducts.get(index3).attachedToLineNo.equals(currentProduct.getLineNo()))
                   {
                      childDetails.add(currProducts.get(index3));
                   }

                }

             }

             expListView =(ExpandableListView) findViewById(R.id.expandableList);

             listAdapter =new com.example.a125688.americano.ExpandableListAdapter(CartPage.this,listDataHeader,listDataChild);
             // setting list adapter
             expListView.setAdapter(listAdapter);


             finalAmount = listAdapter.calculateTotalAmount();
             showTotal.setText(String.format("%.2f", finalAmount));

          }
       });

       checkOut.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             User current_user = getIntent().getParcelableExtra("userInfo");
             Intent checkOutIntent = new Intent(CartPage.this, fulfillOrder.class);
             checkOutIntent.putExtra("userInfo", current_user);
             startActivity(checkOutIntent);
          }
       });
       super.onStart();
    }

    @Override
    protected  void onStop(){
       if(productsListener != null)
          queryProducts.removeEventListener(productsListener);
       super.onStop();
    }

   @Override
   protected  void onDestroy(){
       super.onDestroy();
   }

    private void readData(final FirebaseCallback firebaseCallback){
        User current_user = getIntent().getParcelableExtra("userInfo");
        //Get cart lines for customer
        queryProducts = databaseRef.child("CartProducts").orderByChild("userId").equalTo(current_user.getUserId().toString());
        productsListener = queryProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Cartline> myProducts = new ArrayList<Cartline>();
                Cartline myProduct = new Cartline();

                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    myProduct =data.getValue(Cartline.class);
                    myProduct.setKey(data.getKey());
                    myProducts.add(myProduct);
                }
                firebaseCallback.onCallBack(myProducts);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public interface FirebaseCallback{
        void onCallBack(ArrayList<Cartline> currProducts);
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
            Intent menu = new Intent(CartPage.this, CartPage.class);
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
            Intent menu = new Intent(CartPage.this, com.example.a125688.americano.Menu.class);
            menu.putExtra("userInfo", current_user);
            startActivity(menu);

        } else if (id == R.id.Order_history) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent orderHistory = new Intent(CartPage.this, OrderHistory.class);
           orderHistory.putExtra("userInfo", current_user);
           startActivity(orderHistory);

        } else if (id == R.id.Rewards) {

        } else if (id == R.id.Favorites) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent favorite = new Intent(CartPage.this, favoritePage.class);
           favorite.putExtra("userInfo", current_user);
           startActivity(favorite);

        } else if (id == R.id.Account) {
            //User current_user = getIntent().getParcelableExtra("userInfo");
            Intent account = new Intent(CartPage.this, AccountPage.class);
            account.putExtra("userInfo", current_user);
            startActivity(account);
        } else if (id == R.id.Log_out) {
            Intent logOut = new Intent(CartPage.this, Home.class);
            startActivity(logOut);
        } else if (id == R.id.About_us){
            User current_user = getIntent().getParcelableExtra("userInfo");
            Intent aboutUs = new Intent(CartPage.this, aboutUsPage.class);
            aboutUs.putExtra("userInfo", current_user);
            startActivity(aboutUs);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
