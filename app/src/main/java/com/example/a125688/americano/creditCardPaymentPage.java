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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class creditCardPaymentPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   User current_user;
   DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
   final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("OrderHeader");
   final DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference("OrderLines");
   final DatabaseReference dbRef3 = FirebaseDatabase.getInstance().getReference("Transaction");
   EditText creditCardNumber;
   EditText securityCodeNumber;
   EditText expirationMonth;
   EditText expirationYear;
   Button validate;
   Boolean creditCardValid;
   Integer orderKey;
   String cupName;
   Transaction newTransaction;
   Double totalOrderAmount;
   Double orderAmount;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_credit_card_payment_page);
      final Context context = this;
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

      // display name on navigation bar
      current_user = getIntent().getParcelableExtra("userInfo");
      View header = navigationView.getHeaderView(0);
      TextView nav = header.findViewById(R.id.nav_name);
      nav.setText(current_user.getName());

      Bundle bundle = getIntent().getExtras();

      //Extract the data…
      final Double finalAmount = bundle.getDouble("totalAmount");
      cupName = bundle.getString("cupName");
      //totalOrderAmount = bundle.getDouble("orderAmount");

      creditCardNumber = (EditText) findViewById(R.id.cardNumber);
      securityCodeNumber = (EditText) findViewById(R.id.secCode);
      expirationMonth = (EditText) findViewById(R.id.exMonth);
      expirationYear = (EditText) findViewById(R.id.exYear);
      validate = (Button) findViewById(R.id.validateBt);

readData(new FirebaseCallback() {
   @Override
   public void onCallBack(final ArrayList<Cartline> currProducts) {

      validate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            creditCardValid = validateCreditCard(creditCardNumber.getText().toString());
            if(creditCardValid)
            {
               //final Integer newRewards = current_user.getRewards() + finalAmount.intValue();

               Toast.makeText(context, "Payment processed successfully", Toast.LENGTH_SHORT).show();

               orderAmount = 0.0;
               for(Integer index = 0; index < currProducts.size(); index++)
               {
                  orderAmount = orderAmount + currProducts.get(index).getPrice();
               }
               totalOrderAmount = orderAmount + (orderAmount * 0.06);

               final Query getlast = databaseRef.child("OrderHeader").orderByChild("orderNo").limitToLast(1);
               getlast.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {
                     Integer lastLine = 0;
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
                     newOrder.setOrderDate(formattedDate);
                     newOrder.setStatus(1);
                     newOrder.setOrderNo(orderKey);
                     newOrder.setOrderTotalAmount(totalOrderAmount);
                     dbRef.push().setValue(newOrder);

                     newTransaction = new Transaction();
                     newTransaction.setAmount(finalAmount);
                     newTransaction.setDate(formattedDate);
                     newTransaction.setOrdeNo(orderKey);
                     newTransaction.setUserId(current_user.getUserId());
                     dbRef3.push().setValue(newTransaction);

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
                        lastLine = currProducts.get(index).getLineNo();
                     }

                     OrderLine taxesLine = new OrderLine();
                     taxesLine.setUserId(current_user.getUserId());
                     taxesLine.setProductNo(0);
                     taxesLine.setSize(" ");
                     taxesLine.setQuantity(1);
                     taxesLine.setLineNo(lastLine + 1);
                     taxesLine.setDescription("Taxes");
                     taxesLine.setOrdeKey(orderKey);
                     taxesLine.setPrice(orderAmount * 0.06);
                     taxesLine.setAttachedToLineNo(0);
                     dbRef2.push().setValue(taxesLine);
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
               });


               final Double remainAmount = current_user.getAccountAmount() - totalOrderAmount;
               final Integer newRewards = current_user.getRewards() + totalOrderAmount.intValue();
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
               User current_user = getIntent().getParcelableExtra("userInfo");
               Intent favorite = new Intent(creditCardPaymentPage.this, com.example.a125688.americano.Menu.class);
               favorite.putExtra("userInfo", current_user);
               startActivity(favorite);
            }
            else{
               Toast.makeText(context, "Payment processed failed", Toast.LENGTH_SHORT).show();
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
         Intent menu = new Intent(creditCardPaymentPage.this, CartPage.class);
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
         Intent menu = new Intent(creditCardPaymentPage.this, Menu.class);
         menu.putExtra("userInfo", current_user);
         startActivity(menu);
      } else if (id == R.id.Order_history) {

      } else if (id == R.id.Rewards) {

      } else if (id == R.id.Favorites) {

      } else if (id == R.id.Account) {
         //User current_user = getIntent().getParcelableExtra("userInfo");
         Intent account = new Intent(creditCardPaymentPage.this, AccountPage.class);
         account.putExtra("userInfo", current_user);
         startActivity(account);
      } else if (id == R.id.Log_out) {
         Intent logOut = new Intent(creditCardPaymentPage.this, Home.class);
         startActivity(logOut);
      } else if (id == R.id.About_us){
         User current_user = getIntent().getParcelableExtra("userInfo");
         Intent aboutUs = new Intent(creditCardPaymentPage.this, aboutUsPage.class);
         aboutUs.putExtra("userInfo", current_user);
         startActivity(aboutUs);
      }

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }

   public boolean validateCreditCard(String ccnum)
   {
      final Context context = this;
      Boolean isValid = false;
      if(ccnum.length() == 16)
         isValid = true;
/*      if(ccnum.length()==16){
         char[] c = ccnum.toCharArray();
         int[] cint = new int[16];
         for(int i=0;i<16;i++){
            if(i%2==1){
               cint[i] = Integer.parseInt(String.valueOf(c[i]))*2;
               if(cint[i] >9)
                  cint[i]=1+cint[i]%10;
            }
            else
               cint[i] = Integer.parseInt(String.valueOf(c[i]));
         }
         int sum=0;
         for(int i=0;i<16;i++){
            sum+=cint[i];
         }
         if(sum%10==0) {
            Toast.makeText(context, " Credit Card is Valid, transaction approved", Toast.LENGTH_SHORT).show();
            isValid = true;
         }  //result.setText("Card is Valid");
         else {
            Toast.makeText(context, " Credit Card is Invalid, transaction denied", Toast.LENGTH_SHORT).show();
         }//result.setText("Card is Invalid");
      }else
         Toast.makeText(context," Credit Card is Invalid, transaction denied", Toast.LENGTH_SHORT).show();
      // result.setText("Card is Invalid");*/
      return isValid;
   }

   private void readData(final FirebaseCallback firebaseCallback){

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

   public void removeGroup(ArrayList<Cartline> cartProducts) {
      DatabaseReference remove = FirebaseDatabase.getInstance().getReference("CartProducts");
      for(int index = 0; index < cartProducts.size(); index++)
      {
         remove.child(cartProducts.get(index).getKey()).removeValue();
      }
   }
}
