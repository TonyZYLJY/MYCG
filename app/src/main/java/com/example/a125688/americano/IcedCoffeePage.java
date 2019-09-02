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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class IcedCoffeePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   ImageButton LessQuantity;
   ImageButton MoreQuantity;
   TextView quantity;
   String storeTextQuantity;
   Integer storeQuantity;
   DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
   TextView totalAmount;
   Double storedTotalAmount;
   RadioButton icedCoffeeSmallSize;
   RadioButton icedCoffeeLargeSize;
   RadioGroup sizeRadioGroup;
   Integer sizeID;
   String size;
   ArrayList<Boolean> flavorStatus = new ArrayList<Boolean>();
   DecimalFormat format;
   ProductDetail detail;
   Integer counter;
   ArrayList<ProductDetail> pricesOnFunction = new ArrayList<ProductDetail>();
   Spinner sugarSpinner;
   Spinner areaSpinner;
   Button addItemToCart;
   ArrayList<StateVO> listVOs = new ArrayList<>();
   User current_user;
   Cartline newCartLine;
   Integer lineNo;
   Query query;
   ValueEventListener queryListener;
   Query addOnQuery;
   ValueEventListener addOnListener;
   Query query2;
   ValueEventListener query2Listener;
   Query query4;
   ValueEventListener query4Listener;
   Query queryItem;
   ValueEventListener itemListener;
   Query queryItemDetail;
   ValueEventListener itemDetailListener;
   Query queryCartLine;
   ValueEventListener cartLineListener;
   Context context;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_iced_coffee_page);
      LessQuantity        = (ImageButton) findViewById(R.id.minusButton);
      MoreQuantity        = (ImageButton) findViewById(R.id.plusButton);
      quantity            = (TextView) findViewById(R.id.showQuantity);
      storeTextQuantity   = quantity.getText().toString();
      storeQuantity       = Integer.parseInt(storeTextQuantity);
      icedCoffeeSmallSize = (RadioButton) findViewById(R.id.small);
      icedCoffeeLargeSize = (RadioButton) findViewById(R.id.large);
      totalAmount         = (TextView) findViewById(R.id.storeTotal);
      sizeRadioGroup      = (RadioGroup) findViewById(R.id.sizeGroup);
      addItemToCart       = (Button) findViewById(R.id.addToCart);
      storeQuantity       = 1;
      format              = new DecimalFormat("#.##");
      sugarSpinner        = (Spinner) findViewById(R.id.SugarDropDown);
      areaSpinner         = (Spinner) findViewById(R.id.dairyDropDown);
      context = this;
      AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

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

   }

   @Override
   protected  void onStart(){

      readDetails(new FirebaseCallbackDetail() {
         @Override
         public void onCallBack(ArrayList<ProductDetail> pricesList) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
            for(counter =0; counter < pricesList.size(); counter++ ) {
               if(pricesList.get(counter).getSize() == 24)
               {
                  storedTotalAmount = pricesList.get(counter).getPrice();
               }
               // Showing error if wrong password
                    /*dlgAlert.setMessage("Wrong password or username here" + pricesList.get(counter).getPrice().toString());
                    dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();*/
            }
            pricesOnFunction = pricesList;
            totalAmount.setText(String.format("%.2f",storedTotalAmount));

            MoreQuantity.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
                  AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                  storeQuantity = storeQuantity + 1;
                  quantity.setText("" + storeQuantity);
                  sizeID = sizeRadioGroup.getCheckedRadioButtonId();
                  switch(sizeID)
                  {
                     case R.id.small:
                        for(counter =0; counter < pricesOnFunction.size(); counter++ ) {
                           if(pricesOnFunction.get(counter).getSize() == 24)
                           {
                              storedTotalAmount = pricesOnFunction.get(counter).getPrice() * storeQuantity;
                           }
                        }
                        break;
                     case R.id.large:
                        for(counter =0; counter < pricesOnFunction.size(); counter++ ) {
                           if(pricesOnFunction.get(counter).getSize() == 32)
                           {
                              storedTotalAmount = pricesOnFunction.get(counter).getPrice() * storeQuantity;
                           }
                        }
                        break;
                  }
                  totalAmount.setText(String.format("%.2f",storedTotalAmount));
               }
            });
            LessQuantity.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
                  if (storeQuantity > 1) {
                     storeQuantity = storeQuantity - 1;
                     quantity.setText("" + storeQuantity);
                  }
                  sizeID = sizeRadioGroup.getCheckedRadioButtonId();
                  switch(sizeID)
                  {
                     case R.id.small:
                        for(counter =0; counter < pricesOnFunction.size(); counter++ ) {
                           if(pricesOnFunction.get(counter).getSize() == 24)
                           {
                              storedTotalAmount = pricesOnFunction.get(counter).getPrice() * storeQuantity;
                           }
                        }
                        break;
                     case R.id.large:
                        for(counter =0; counter < pricesOnFunction.size(); counter++ ) {
                           if(pricesOnFunction.get(counter).getSize() == 32)
                           {
                              storedTotalAmount = pricesOnFunction.get(counter).getPrice() * storeQuantity;
                           }
                        }
                        break;
                  }
                  totalAmount.setText(String.format("%.2f",storedTotalAmount));

               }
            });

            sizeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(RadioGroup radioGroup, int selectedId) {
                  switch(selectedId)
                  {
                     case R.id.small:
                        for(counter =0; counter < pricesOnFunction.size(); counter++ ) {
                           if(pricesOnFunction.get(counter).getSize() == 24)
                           {
                              storedTotalAmount = pricesOnFunction.get(counter).getPrice() * storeQuantity;
                           }
                        }
                        break;
                     case R.id.large:
                        for(counter =0; counter < pricesOnFunction.size(); counter++ ) {
                           if(pricesOnFunction.get(counter).getSize() == 32)
                           {
                              storedTotalAmount = pricesOnFunction.get(counter).getPrice() * storeQuantity;
                           }
                        }
                        break;
                  }
                  totalAmount.setText(String.format("%.2f",storedTotalAmount));
               }
            });

            // Getting Dairy AddOns from database
            query = databaseRef.child("AddOn").orderByChild("addOnType").equalTo(1);
            queryListener = query.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  // Is better to use a List, because you don't know the size
                  // of the iterator returned by dataSnapshot.getChildren() to
                  // initialize the array
                  final List<String> dairyList = new ArrayList<String>();
                  String myString = "Whole Milk";

                  for (DataSnapshot dairySnapshot: dataSnapshot.getChildren()) {
                     String dairyDescription = dairySnapshot.child("description").getValue(String.class);
                     dairyList.add(dairyDescription);
                  }
                  ArrayAdapter<String> dairyAdapter = new ArrayAdapter<String>(IcedCoffeePage.this, android.R.layout.simple_spinner_item,dairyList);
                  int spinnerPosition = dairyAdapter.getPosition(myString);
                  dairyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  areaSpinner.setAdapter(dairyAdapter);
                  areaSpinner.setSelection(spinnerPosition);

                  areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        String selectedDairy;
                        selectedDairy = parentView.getItemAtPosition(position).toString();
                        addOnQuery = databaseRef.child("AddOn").orderByChild("description").equalTo(selectedDairy);
                        addOnListener = addOnQuery.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                              AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                              Double price = 0.0;
                              for (DataSnapshot sugarSnapshot : dataSnapshot.getChildren()) {
                                 price = sugarSnapshot.child("price").getValue(Double.class);
                              }
                              sizeID = sizeRadioGroup.getCheckedRadioButtonId();
                              switch (sizeID) {
                                 case R.id.small:
                                    for (counter = 0; counter < pricesOnFunction.size(); counter++) {
                                       if (pricesOnFunction.get(counter).getSize() == 24) {
                                          storedTotalAmount = (pricesOnFunction.get(counter).getPrice() + price) * storeQuantity;
                                       }
                                    }
                                    break;
                                 case R.id.large:
                                    for (counter = 0; counter < pricesOnFunction.size(); counter++) {
                                       if (pricesOnFunction.get(counter).getSize() == 32) {
                                          storedTotalAmount = (pricesOnFunction.get(counter).getPrice() + price) * storeQuantity;
                                       }
                                    }
                                    break;
                              }
                              totalAmount.setText(String.format("%.2f", storedTotalAmount));
                           }

                           //}
                           @Override
                           public void onCancelled(DatabaseError databaseError) {
                           }
                        });
                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> parentView) {
                     }
                  });
               }
               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
            });

            // Get flavors from database
            query2 = databaseRef.child("AddOn").orderByChild("addOnType").equalTo(2);
            query2Listener = query2.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  final List<String> flavorList = new ArrayList<String>();
                  Spinner spinner = (Spinner) findViewById(R.id.spinner2);

                  StateVO stateVO2 = new StateVO();
                  stateVO2.setTitle("Select Flavors");
                  stateVO2.setSelected(false);
                  listVOs.add(stateVO2);

                  for (DataSnapshot flavorSnapshot: dataSnapshot.getChildren()) {
                     String flavorDescription = flavorSnapshot.child("description").getValue(String.class);
                     flavorList.add(flavorDescription);
                  }

                  if(flavorStatus.size() == 0) {
                     for (int i = 0; i < flavorList.size() ; i++) {
                        flavorStatus.add(i, false);
                     }
                  }

                  for (int i = 0; i < flavorList.size() ; i++) {
                     StateVO stateVO = new StateVO();
                     stateVO.setTitle(flavorList.get(i));
                     stateVO.setSelected(flavorStatus.get(i));
                     listVOs.add(stateVO);
                  }

                  MyAdapter myAdapter = new MyAdapter(IcedCoffeePage.this, 0,
                          listVOs);
                  spinner.setAdapter(myAdapter);

                  flavorStatus = myAdapter.getModifyList();
               }
               @Override
               public void onCancelled(DatabaseError databaseError) {
               }
            });

            // Getting Sugar from database
            query4 = databaseRef.child("AddOn").orderByChild("addOnType").equalTo(3);
            query4Listener=query4.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  // Is better to use a List, because you don't know the size
                  // of the iterator returned by dataSnapshot.getChildren() to
                  // initialize the array
                  final List<String> sugarList = new ArrayList<String>();

                  for (DataSnapshot sugarSnapshot: dataSnapshot.getChildren()) {
                     String sugarDescription = sugarSnapshot.child("description").getValue(String.class);
                     sugarList.add(sugarDescription);
                  }

                  ArrayAdapter<String> sugarAdapter = new ArrayAdapter<String>(IcedCoffeePage.this, android.R.layout.simple_spinner_dropdown_item,sugarList);
                  sugarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  sugarSpinner.setAdapter(sugarAdapter);

               }
               @Override
               public void onCancelled(DatabaseError databaseError) {
               }
            });

            // Add to Cart
            readCartLines(new FirebaseCallbackCartLine() {
               @Override
               public void onCallBack(final Integer newOrderLine) {
                  addItemToCart.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                        String sugarType = sugarSpinner.getSelectedItem().toString();
                        String dairyType = areaSpinner.getSelectedItem().toString();
                        sizeID= sizeRadioGroup.getCheckedRadioButtonId();
                        Integer active = 0;
                        final Double finalPrice = Double.parseDouble(totalAmount.getText().toString());
                        ArrayList<String> flavorDescriptions = new ArrayList<String>();
                        Boolean createLines = true;
                        lineNo = newOrderLine;
                        switch(sizeID)
                        {
                           case R.id.small:
                              size = "24oz";
                              break;
                           case R.id.large:
                              size = "32oz";
                              break;
                        }

                        for(counter = 0; counter < flavorStatus.size(); counter++)
                        {
                           if(flavorStatus.get(counter).booleanValue())
                           {
                              flavorDescriptions.add(listVOs.get(counter).getTitle().toString());
                           }
                        }

                        if(flavorDescriptions.size() < 1)
                        {
                           dlgAlert.setMessage("Please, select at least one flavor");
                           dlgAlert.setTitle("Error Message...");
                           dlgAlert.setPositiveButton("OK", null);
                           dlgAlert.setCancelable(true);
                           dlgAlert.create().show();
                           createLines = false;

                        }
                        for(int index = 0; index< flavorStatus.size(); index++)
                           flavorStatus.set(index,false);

                        if(createLines) {
                           newCartLine = new Cartline(0, "Iced Coffee", lineNo, finalPrice, 1, size, current_user.getUserId().toString(), storeQuantity);
                           databaseRef.child("CartProducts");
                           databaseRef.child("CartProducts").push().setValue(newCartLine);

                           lineNo = lineNo + 1;

                           //Create Lines for AddOns
                           //Dairy
                           newCartLine = new Cartline(newOrderLine, dairyType, lineNo, 0.00, 1, size, current_user.getUserId().toString(), 0);
                           databaseRef.child("CartProducts");
                           databaseRef.child("CartProducts").push().setValue(newCartLine);

                           lineNo = lineNo + 1;
                           //Sugar
                           newCartLine = new Cartline(newOrderLine, sugarType, lineNo, 0.00, 1, size, current_user.getUserId().toString(), 0);
                           databaseRef.child("CartProducts");
                           databaseRef.child("CartProducts").push().setValue(newCartLine);

                           lineNo = lineNo + 1;
                           //Flavors
                           for (counter = 0; counter < flavorDescriptions.size(); counter++) {
                              newCartLine = new Cartline(newOrderLine, flavorDescriptions.get(counter).toString(), lineNo, 0.00, 1, size, current_user.getUserId().toString(), 0);
                              databaseRef.child("CartProducts");
                              databaseRef.child("CartProducts").push().setValue(newCartLine);
                              lineNo = lineNo + 1;
                           }
                           Toast.makeText(context, storeQuantity + " Iced Coffee added to cart", Toast.LENGTH_SHORT).show();
                                  /*  Intent menu = new Intent(IcedCoffeePage.this, CartPage.class);
                                    startActivity(menu);*/
                        }

                     }
                  });
               }
            });
         }
      });
      super.onStart();
   }

   @Override
   protected  void onStop(){
      if(queryListener != null)
         query.removeEventListener(queryListener);
      if(addOnListener != null)
         addOnQuery.removeEventListener(addOnListener);
      if(query2Listener != null)
         query2.removeEventListener(query2Listener);
      if(query4Listener != null)
         query4.removeEventListener(query4Listener);
      if(itemListener != null)
         queryItem.removeEventListener(itemListener);
      if(itemDetailListener != null)
         queryItemDetail.removeEventListener(itemDetailListener);
      if(cartLineListener != null)
         queryCartLine.removeEventListener(cartLineListener);
      query = null;
      queryListener = null;
      addOnQuery = null;
      addOnListener = null;
      query2 = null;
      query2Listener = null;
      query4 = null;
      query4Listener = null;
      queryItem = null;
      itemListener = null;
      queryItemDetail = null;
      itemDetailListener = null;
      queryCartLine = null;
      cartLineListener = null;
      System.gc();
      super.onStop();
      Runtime.getRuntime().gc();

   }

   @Override
   protected  void onPause(){
      if(queryListener != null)
         query.removeEventListener(queryListener);
      if(addOnListener != null)
         addOnQuery.removeEventListener(addOnListener);
      if(query2Listener != null)
         query2.removeEventListener(query2Listener);
      if(query4Listener != null)
         query4.removeEventListener(query4Listener);
      if(itemListener != null)
         queryItem.removeEventListener(itemListener);
      if(itemDetailListener != null)
         queryItemDetail.removeEventListener(itemDetailListener);
      if(cartLineListener != null)
         queryCartLine.removeEventListener(cartLineListener);
      query = null;
      queryListener = null;
      addOnQuery = null;
      addOnListener = null;
      query2 = null;
      query2Listener = null;
      query4 = null;
      query4Listener = null;
      queryItem = null;
      itemListener = null;
      queryItemDetail = null;
      itemDetailListener = null;
      queryCartLine = null;
      cartLineListener = null;
      System.gc();
      super.onPause();
   }

   @Override
   protected void onDestroy(){
      if(queryListener != null)
         query.removeEventListener(queryListener);
      if(addOnListener != null)
         addOnQuery.removeEventListener(addOnListener);
      if(query2Listener != null)
         query2.removeEventListener(query2Listener);
      if(query4Listener != null)
         query4.removeEventListener(query4Listener);
      if(itemListener != null)
         queryItem.removeEventListener(itemListener);
      if(itemDetailListener != null)
         queryItemDetail.removeEventListener(itemDetailListener);
      if(cartLineListener != null)
         queryCartLine.removeEventListener(cartLineListener);
      query = null;
      queryListener = null;
      addOnQuery = null;
      addOnListener = null;
      query2 = null;
      query2Listener = null;
      query4 = null;
      query4Listener = null;
      queryItem = null;
      itemListener = null;
      queryItemDetail = null;
      itemDetailListener = null;
      queryCartLine = null;
      cartLineListener = null;
      super.onDestroy();
      Runtime.getRuntime().gc();
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
         Intent cart = new Intent(IcedCoffeePage.this, CartPage.class);
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
         Intent menu = new Intent(IcedCoffeePage.this, com.example.a125688.americano.Menu.class);
         menu.putExtra("userInfo", current_user);
         startActivity(menu);
      } else if (id == R.id.Order_history) {

      } else if (id == R.id.Rewards) {
         Intent rewards = new Intent(IcedCoffeePage.this, Menu.class);
         rewards.putExtra("userInfo", current_user);
         startActivity(rewards);
      } else if (id == R.id.Favorites) {
         Intent favorite = new Intent(IcedCoffeePage.this, favoritePage.class);
         favorite.putExtra("userInfo", current_user);
         startActivity(favorite);

      } else if (id == R.id.Account) {
         Intent intent = new Intent(IcedCoffeePage.this, AccountPage.class);
         intent.putExtra("userInfo", current_user);
         startActivity(intent);
      } else if (id == R.id.Log_out) {
         Intent logOut = new Intent(IcedCoffeePage.this, Home.class);
         startActivity(logOut);
      } else if (id == R.id.About_us){
         Intent aboutUs = new Intent(IcedCoffeePage.this, aboutUsPage.class);
         aboutUs.putExtra("userInfo", current_user);
         startActivity(aboutUs);
      }

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }

   private void readData(final FirebaseCallback firebaseCallback){
      //Get current product
      queryItem = databaseRef.child("Product").orderByChild("no").equalTo(1);
      itemListener = queryItem.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            Product myProduct = new Product();
            //AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
            for (DataSnapshot data : dataSnapshot.getChildren())
            {
               myProduct =data.getValue(Product.class);
            }
            firebaseCallback.onCallBack(myProduct);

         }
         @Override
         public void onCancelled(DatabaseError databaseError) {
         }
      });
   }
   private void readDetails(final FirebaseCallbackDetail firebaseCallbackDetails){
      readData(new FirebaseCallback() {
         @Override
         public void onCallBack(Product currProduct) {
            queryItemDetail=databaseRef.child("ProductDetails").orderByChild("productNo").equalTo(currProduct.getNo());
            itemDetailListener = queryItemDetail.addValueEventListener(new ValueEventListener(){
               @Override
               public void onDataChange(DataSnapshot dataSnapshot){
                  ArrayList<ProductDetail> pricesList=new ArrayList<ProductDetail>();
                  for(DataSnapshot data:dataSnapshot.getChildren())
                  {
                     detail=data.getValue(ProductDetail.class);
                     pricesList.add(detail);
                  }
                  firebaseCallbackDetails.onCallBack(pricesList);
               }
               @Override
               public void onCancelled(DatabaseError databaseError){
               }
            });
         }
      });
   }
   private void readCartLines( final FirebaseCallbackCartLine firebaseCallbackCartLines){
      // Getting Dairy AddOns from database
      queryCartLine=databaseRef.child("CartProducts").orderByChild("userId").equalTo(current_user.getUserId().toString());
      cartLineListener =queryCartLine.addValueEventListener(new ValueEventListener(){
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

   public interface FirebaseCallback{
      void onCallBack(Product currProduct);
   }

   public interface FirebaseCallbackDetail{
      void onCallBack(ArrayList<ProductDetail> pricesList);
   }
   public interface FirebaseCallbackCartLine{
      void onCallBack (Integer newOrderLine);
   }
}