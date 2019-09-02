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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class checkOutPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    User current_user;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    Integer orderKey;
    Double finalOrderAmount;
    Double orderAmount;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_page);
        context = this;
        final TextView showTotal = (TextView) findViewById(R.id.totalView);
        final Button checkOut = (Button) findViewById(R.id.checkOutButton);

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
       AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
        // display name on navigation bar
        current_user = getIntent().getParcelableExtra("userInfo");
        View header = navigationView.getHeaderView(0);
        TextView nav = header.findViewById(R.id.nav_name);
        nav.setText(current_user.getName());

       Bundle bundle = getIntent().getExtras();

       //Extract the data…
       final String cupName = bundle.getString("nameOnCup");
       Integer cupQuantity = bundle.getInt("quantityCup");

       final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("OrderHeader");
       final DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference("OrderLines");
        final TextView total = (TextView) findViewById(R.id.totalAmount);
        final TextView taxes = (TextView) findViewById(R.id.taxes);
        final TextView totalAmountTaxes = (TextView) findViewById(R.id.totalAmountTax);
        final Button pccCard = (Button) findViewById(R.id.pccCardButton);
        final Button creditCard = (Button) findViewById(R.id.creditCardButton);
        final Double finalAmount = Double.parseDouble(totalAmountTaxes.getText().toString());

        readData(new FirebaseCallback() {
            @Override
            public void onCallBack(final ArrayList<Cartline> currProducts) {
                Double totalAmount = 0.0;
                Double totalTaxes = 0.0;

                removeExtraLines(currProducts);

                for(Integer index = 0; index < currProducts.size(); index++)
                {
                    totalAmount = totalAmount + currProducts.get(index).getPrice();
                }
                totalTaxes = totalAmount * 0.06;

                total.setText(String.format("%.2f", totalAmount));
                taxes.setText(String.format("%.2f", totalTaxes));
                totalAmountTaxes.setText(String.format("%.2f", totalAmount + totalTaxes));

               pccCard.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                     orderAmount = 0.0;
                     for(Integer index = 0; index < currProducts.size(); index++)
                     {
                        orderAmount = orderAmount + currProducts.get(index).getPrice();
                     }
                     finalOrderAmount = orderAmount + (orderAmount * 0.06) - (orderAmount * 0.05);

                     if(current_user.getAccountAmount() >= finalAmount) {
                        final Double remainAmount = current_user.getAccountAmount() - finalOrderAmount;
                        final Integer newRewards = current_user.getRewards() + finalOrderAmount.intValue();

                        final Query getlast = databaseRef.child("OrderHeader").orderByChild("orderNo").limitToLast(1);
                        getlast.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                              for (DataSnapshot child: dataSnapshot.getChildren()) {
                                 if(dataSnapshot.getValue() != null) {
                                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                                    orderKey = child.child("orderNo").getValue(Integer.class) + 1;
                                 }
                             }
                              if(orderKey == null)
                                 orderKey = 1;

                              Date c = Calendar.getInstance().getTime();
                              SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                              String formattedDate = df.format(c);
                              OrderHeader newOrder = new OrderHeader();
                              newOrder.setPrintedName(cupName);
                              newOrder.setUserId(current_user.getUserId());
                              AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                              newOrder.setOrderDate(formattedDate);
                              newOrder.setStatus(1);
                              newOrder.setOrderNo(orderKey);
                              newOrder.setOrderTotalAmount(finalOrderAmount);
                              dbRef.push().setValue(newOrder);
                              Integer lineNo = 0;

                              for(Integer index = 0; index < currProducts.size(); index++)
                              {
                                 OrderLine newLine = new OrderLine();
                                 newLine.setOrdeKey(orderKey);
                                 newLine.setAttachedToLineNo(currProducts.get(index).getAttachedToLineNo());
                                 newLine.setDescription(currProducts.get(index).getDescription());
                                 newLine.setLineNo(currProducts.get(index).getLineNo());
                                 newLine.setPrice(currProducts.get(index).getPrice());
                                 newLine.setQuantity(currProducts.get(index).getQuantity());
                                 newLine.setSize(currProducts.get(index).getSize());
                                 newLine.setProductNo(currProducts.get(index).getProductNo());
                                 newLine.setUserId(currProducts.get(index).getUserId());
                                 dbRef2.push().setValue(newLine);
                                 lineNo = currProducts.get(index).getLineNo();
                              }

                              OrderLine taxesLine = new OrderLine();
                              taxesLine.setUserId(current_user.getUserId());
                              taxesLine.setProductNo(0);
                              taxesLine.setSize(" ");
                              taxesLine.setQuantity(1);
                              taxesLine.setLineNo(lineNo + 1);
                              taxesLine.setDescription("Taxes");
                              taxesLine.setOrdeKey(orderKey);
                              taxesLine.setPrice(orderAmount * 0.06);
                              taxesLine.setAttachedToLineNo(0);
                              dbRef2.push().setValue(taxesLine);

                              OrderLine discountLine = new OrderLine();
                              discountLine.setUserId(current_user.getUserId());
                              discountLine.setProductNo(0);
                              discountLine.setSize(" ");
                              discountLine.setQuantity(1);
                              discountLine.setLineNo(lineNo + 2);
                              discountLine.setDescription("PCC card Discount");
                              discountLine.setOrdeKey(orderKey);
                              discountLine.setPrice(-(orderAmount * 0.05));
                              discountLine.setAttachedToLineNo(0);
                              dbRef2.push().setValue(discountLine);
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                        });
                        Toast.makeText(context, "Payment processed - 5% off", Toast.LENGTH_SHORT).show();

                       final Query update = databaseRef.child("User").orderByChild("userId").equalTo(current_user.getUserId().toString());
                        update.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                              DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                              String key = nodeDataSnapshot.getKey(); // this key is `K1NRz9l5PU_0CFDtgXz`
                              String path = "/" + dataSnapshot.getKey() + "/" + key;
                              HashMap<String, Object> result = new HashMap<>();
                              result.put("accountAmount", remainAmount);
                              result.put("rewards", newRewards);
                              databaseRef.child(path).updateChildren(result);
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                        });
                        removeGroup(currProducts);
                       Intent checkOutIntent = new Intent(checkOutPage.this, com.example.a125688.americano.Menu.class);
                        checkOutIntent.putExtra("userInfo", current_user);
                        startActivity(checkOutIntent);
                     }
                     else
                     {
                        Toast.makeText(context, "Payment failed, unsuficcient founds", Toast.LENGTH_SHORT).show();
                     }
                  }
               });
            }

        });

        creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Bundle bundle = new Bundle();
               bundle.putDouble("totalAmount", finalAmount);
               bundle.putString("cupName", cupName);
               User current_user = getIntent().getParcelableExtra("userInfo");
               Intent checkOutIntent = new Intent(checkOutPage.this, creditCardPaymentPage.class);
               checkOutIntent.putExtras(bundle);
               checkOutIntent.putExtra("userInfo", current_user);
               startActivity(checkOutIntent);
            }
        });
    }

    private void readData(final checkOutPage.FirebaseCallback firebaseCallback){

        ValueEventListener listener;
        User current_user = getIntent().getParcelableExtra("userInfo");
        //Get cart lines for customer
        Query queryProducts = databaseRef.child("CartProducts").orderByChild("userId").equalTo(current_user.getUserId().toString());
         queryProducts.addValueEventListener(new ValueEventListener() {
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
            Intent menu = new Intent(checkOutPage.this, CartPage.class);
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
            Intent menu = new Intent(checkOutPage.this, com.example.a125688.americano.Menu.class);
            menu.putExtra("userInfo", current_user);
            startActivity(menu);
        } else if (id == R.id.Order_history) {
           Intent orderHistory = new Intent(checkOutPage.this, OrderHistory.class);
           orderHistory.putExtra("userInfo", current_user);
           startActivity(orderHistory);

        } else if (id == R.id.Rewards) {

        } else if (id == R.id.Favorites) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent favorite = new Intent(checkOutPage.this, favoritePage.class);
           favorite.putExtra("userInfo", current_user);
           startActivity(favorite);

        } else if (id == R.id.Account) {
            //User current_user = getIntent().getParcelableExtra("userInfo");
            Intent account = new Intent(checkOutPage.this, AccountPage.class);
            account.putExtra("userInfo", current_user);
            startActivity(account);
        } else if (id == R.id.Log_out) {
            Intent logOut = new Intent(checkOutPage.this, Home.class);
            startActivity(logOut);
        } else if (id == R.id.About_us){
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent aboutUs = new Intent(checkOutPage.this, aboutUsPage.class);
           aboutUs.putExtra("userInfo", current_user);
           startActivity(aboutUs);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void removeExtraLines(ArrayList<Cartline> cartProducts){
       DatabaseReference removeExtra = FirebaseDatabase.getInstance().getReference("CartProducts");
       ArrayList<Cartline> headerList = new ArrayList<Cartline>();
       ArrayList<Cartline> detailList = new ArrayList<Cartline>();
       ArrayList<Cartline> deletedLines = new ArrayList<Cartline>();
       Cartline productHeader = new Cartline();

       Boolean hasHeader = false;
       for (int index = 0; index < cartProducts.size(); index++) {
          if (cartProducts.get(index).getAttachedToLineNo().equals(0)) {
             productHeader = cartProducts.get(index);
             headerList.add(productHeader);

          }
       }
       for (int index = 0; index < cartProducts.size(); index++) {
          if (cartProducts.get(index).getAttachedToLineNo() != 0) {
             productHeader = cartProducts.get(index);
             detailList.add(productHeader);
          }
       }

       for(int index2 = 0; index2 < detailList.size(); index2++)
       {
          for(int index3 = 0; index3 < headerList.size(); index3++)
          {
             if(detailList.get(index2).getAttachedToLineNo() == headerList.get(index3).getLineNo()) {
                hasHeader = true;
             }
          }
          if(hasHeader == false)
          {
             deletedLines.add(detailList.get(index2));
          }
          hasHeader = false;
       }

       for(int index = 0; index < deletedLines.size(); index++)
       {
          removeExtra.child(deletedLines.get(index).getKey()).removeValue();
       }
    }

    public void removeGroup(ArrayList<Cartline> cartProducts) {
       DatabaseReference remove = FirebaseDatabase.getInstance().getReference("CartProducts");
      for(int index = 0; index < cartProducts.size(); index++)
      {
         remove.child(cartProducts.get(index).getKey()).removeValue();
      }
    }
}
