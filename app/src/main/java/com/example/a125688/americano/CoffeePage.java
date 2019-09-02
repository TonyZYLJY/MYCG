package com.example.a125688.americano;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CoffeePage extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

   DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
   Spinner areaSpinner;
   ProductDetail detail;
   ProductDetail detail1;

   User current_user;
   Product myProduct;
   Product product1;

   Integer attachedToLineNo;
   String description = ""; // description of the item selected
   Integer lineNo;    //


   Double totalPrice = 0.0;      // total product price
   Double individualPrice = 0.0;
   Double onePrice = 0.0; // price for a type of tea ordered

   String size;    // size of the item
   Integer quantity; // quantify of the same item
   Integer quantity1 = 1;

   Cartline newCartLine;
   Button addItemToCart;
   Integer minimumQuantity = 1;
   Integer maximumQuantity = 10;

   Button one12oz;
   Button one16oz;
   Button one20oz;
   TextView priceBox;

   TextView oneShowQuantity;
   ImageButton oneMinus;
   ImageButton onePlus;

   ImageView img1;
   int im1;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_coffee_page);
      final Context context = this;

      im1 = R.mipmap.hotcoffee;

      img1 = findViewById(R.id.coffeeOne);


      ScaleImg(img1, im1);

      // set plus and minus sign actions
      oneShowQuantity = findViewById(R.id.oneShowQuantity);
      onePlus = findViewById(R.id.onePlusButton);
      oneMinus = findViewById(R.id.oneMinusButton);

      // plus minus button for tea one
      onePlus.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (Integer.parseInt(oneShowQuantity.getText().toString()) < maximumQuantity) {
               quantity1 = (Integer.parseInt(oneShowQuantity.getText().toString())) + 1;
               oneShowQuantity.setText(quantity1.toString());
               totalPrice += returnValue(one12oz, one16oz, one20oz);
               DisplayTotalAomount();
            }
            else{
               Toast.makeText(context, "You can just order " + maximumQuantity, Toast.LENGTH_SHORT).show();
            }
         }
      });

      oneMinus.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (Integer.parseInt(oneShowQuantity.getText().toString()) > minimumQuantity) {
               quantity1 = (Integer.parseInt(oneShowQuantity.getText().toString())) - 1;
               oneShowQuantity.setText(quantity1.toString());
               totalPrice -= returnValue(one12oz, one16oz, one20oz);
               DisplayTotalAomount();
            }
         }
      });



      // set price
      one12oz   = findViewById(R.id.oneOz12);
      one16oz   = findViewById(R.id.oneOz16);
      one20oz   = findViewById(R.id.oneOz20);
      priceBox = findViewById(R.id.coffeePrice);

      one12oz.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            Integer teaNumber = 4;
            int priKey = 41;
            setColor(one12oz, one16oz, one20oz, onePrice, oneShowQuantity, 1);
            if (isColor(one12oz))
               readData(teaNumber, priKey);
         }
      });
      one16oz.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            Integer teaNumber = 4;
            int priKey = 42;
            setColor(one16oz, one12oz, one20oz, onePrice, oneShowQuantity, 1);
            if (isColor(one16oz))
               readData(teaNumber, priKey);
         }
      });
      one20oz.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            Integer teaNumber = 4;
            int priKey = 43;
            setColor(one20oz, one16oz, one12oz, onePrice, oneShowQuantity, 1);
            if (isColor(one20oz))
               readData(teaNumber, priKey);
         }
      });


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

      areaSpinner         = (Spinner) findViewById(R.id.flavorDropDown);

      // Getting Dairy AddOns from database
      Query query = databaseRef.child("AddOn").orderByChild("addOnType").equalTo(4);
      query.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {

            final List<String> flavorList = new ArrayList<String>();
            String myString = "Kenya";
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

            for (DataSnapshot flavorSnapshot: dataSnapshot.getChildren()) {
               String flavorDescription = flavorSnapshot.child("description").getValue(String.class);
               flavorList.add(flavorDescription);
            }

            ArrayAdapter<String> flavorAdapter = new ArrayAdapter<String>(CoffeePage.this, android.R.layout.simple_spinner_item,flavorList);
            int spinnerPosition = flavorAdapter.getPosition(myString);
            flavorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            areaSpinner.setAdapter(flavorAdapter);
            areaSpinner.setSelection(spinnerPosition);

         }
         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
      });

      addItemToCart = (Button)findViewById(R.id.addCart);
      readCartLines(new Chocolate.FirebaseCallbackCartLine() {
         @Override
         public void onCallBack(final Integer newOrderLine) {
            addItemToCart.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  if (onePrice > 0.0) {
                     newCartLine = new Cartline(attachedToLineNo = 0,  product1.getDescription(),
                             newOrderLine,             onePrice *
                             (Integer.parseInt(oneShowQuantity.getText().toString())),
                             product1.getNo(),      detail1.getSize().toString() + "oz",
                             current_user.getUserId(), quantity1);
                     databaseRef.child("CartProducts");
                     databaseRef.child("CartProducts").push().setValue(newCartLine);
                     returnColor(one12oz, one16oz, one20oz);
                  }

                  if (onePrice > 0.0)
                     Toast.makeText(context, "Item(s) has been added to Cart", Toast.LENGTH_SHORT).show();

                  if (onePrice == 0.0)
                     Toast.makeText(context, "Please select an item(s)", Toast.LENGTH_SHORT).show();

                  onePrice = 0.0;

                  totalPrice = 0.0;
                  DisplayTotalAomount();

               }
            });
         }
      });

   }

   // get the item and item details selected
   public void readData(Integer teaNumber, final int prikey){
      //Get current product
      Query queryItem = databaseRef.child("Product").orderByChild("no").equalTo(teaNumber);
      queryItem.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot data : dataSnapshot.getChildren())
               myProduct = data.getValue(Product.class);

            final Query details = databaseRef.child("ProductDetails").orderByChild("productNo").equalTo(prikey);
            details.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  for (DataSnapshot data : dataSnapshot.getChildren())
                     detail = data.getValue(ProductDetail.class);
                  if (myProduct.getDescription().equals("Coffee")){
                     onePrice = detail.getPrice();
                     individualPrice = (onePrice *
                             (Integer.parseInt(oneShowQuantity.getText().toString())));
                     totalPrice += individualPrice;
                     DisplayTotalAomount();
                     product1 = myProduct;
                     detail1 = detail;
                  }
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
            });
         }
         @Override
         public void onCancelled(DatabaseError databaseError) {
         }
      });
   }


   // set all Textview background to Common Ground's color and Textview text to white
   public void returnColor(Button button12, Button button16, Button button20)
   {
      button12.setBackgroundColor(Color.WHITE);
      button12.setTextColor(0xFF93a445);

      button16.setBackgroundColor(Color.WHITE);
      button16.setTextColor(0xFF93a445);

      button20.setBackgroundColor(Color.WHITE);
      button20.setTextColor(0xFF93a445);

   }

   // set background color of button when click
   public void setColor(Button button1, Button button2, Button button3, Double price, TextView quantity, int teaKind){
      if (isColor(button1)) {
         returnColor(button1, button2, button3);
         totalPrice -= price *
                 (Integer.parseInt(quantity.getText().toString()));
         if (teaKind == 1)
            onePrice = 0.0;
      }
      else if (isColor(button2)) {
         returnColor(button1, button2, button3);
         button1.setBackgroundColor(0xFF93a445);
         button1.setTextColor(Color.WHITE);
         totalPrice -= price *
                 (Integer.parseInt(quantity.getText().toString()));
         if (teaKind == 1)
            onePrice = 0.0;
      }
      else if (isColor(button3)) {
         returnColor(button1, button2, button3);
         button1.setBackgroundColor(0xFF93a445);
         button1.setTextColor(Color.WHITE);
         totalPrice -= price *
                 (Integer.parseInt(quantity.getText().toString()));
         if (teaKind == 1)
            onePrice = 0.0;
      }
      else{

         button1.setBackgroundColor(0xFF93a445);
         button1.setTextColor(Color.WHITE);
      }


      DisplayTotalAomount();

   }

   public boolean isColor(Button button){
      return ((ColorDrawable) button.getBackground()).getColor() == 0xFF93a445;
   }

   public double returnValue(Button button12, Button button16, Button button20){
      double changes;

      if (((ColorDrawable) button12.getBackground()).getColor() == 0xFF93a445)
         changes = 1.50;
      else if(((ColorDrawable) button16.getBackground()).getColor() == 0xFF93a445)
         changes = 1.70;
      else if (((ColorDrawable) button20.getBackground()).getColor() == 0xFF93a445)
         changes = 1.90;
      else
         changes = 0.0;

      return changes;
   }

   private void readCartLines( final Chocolate.FirebaseCallbackCartLine firebaseCallbackCartLines){
      // Getting Dairy AddOns from database
      Query queryCartLine=databaseRef.child("CartProducts").orderByChild("userId").equalTo(current_user.getName().toString());
      queryCartLine.addValueEventListener(new ValueEventListener(){
         @Override
         public void onDataChange(DataSnapshot dataSnapshot){
            Integer lineNo = 10;
            Integer newLineNo = 10;
            Boolean createNewLine = false;
            for(DataSnapshot cartLine:dataSnapshot.getChildren())
            {
               if(cartLine.child("attachedToLineNo").getValue(Integer.class) == 0)
               {
                  if(lineNo <= cartLine.child("lineNo").getValue(Integer.class))
                  {
                     lineNo = cartLine.child("lineNo").getValue(Integer.class);
                     createNewLine = true;
                  }
               }
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
      void onCallBack(Integer newOrderLine);
   }

   public void DisplayTotalAomount(){
      double price;
      price = Math.max(0.0, totalPrice);
      priceBox.setText(String.format("$%.2f", price));
   }

   public void ScaleImg(ImageView img, int pic){
      Display screen = getWindowManager().getDefaultDisplay();
      BitmapFactory.Options options = new BitmapFactory.Options();

      options.inJustDecodeBounds = true;
      BitmapFactory.decodeResource(getResources(), pic, options);

      int imgWidth = options.outWidth;
      int screenWidth = screen.getWidth();

      if (imgWidth > screenWidth){
         int ratio = Math.round((float)imgWidth / (float) screenWidth);
         options.inSampleSize = ratio;
      }

      options.inJustDecodeBounds = false;
      Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
      img.setImageBitmap(scaledImg);
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
         Intent menu = new Intent(CoffeePage.this, CartPage.class);
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
         Intent menu = new Intent(CoffeePage.this, Menu.class);
         menu.putExtra("userInfo", current_user);
         startActivity(menu);
      } else if (id == R.id.Order_history) {

      } else if (id == R.id.Rewards) {
         Intent rewards = new Intent(CoffeePage.this, Menu.class);
         rewards.putExtra("userInfo", current_user);
         startActivity(rewards);
      } else if (id == R.id.Favorites) {
         Intent favorite = new Intent(CoffeePage.this, favoritePage.class);
         favorite.putExtra("userInfo", current_user);
         startActivity(favorite);
      } else if (id == R.id.Account) {
         Intent account = new Intent(CoffeePage.this, AccountPage.class);
         account.putExtra("userInfo", current_user);
         startActivity(account);
      } else if (id == R.id.Log_out) {
         Intent logOut = new Intent(CoffeePage.this, Home.class);
         startActivity(logOut);
      } else if (id == R.id.About_us){
         Intent aboutUs = new Intent(CoffeePage.this, aboutUsPage.class);
         aboutUs.putExtra("userInfo", current_user);
         startActivity(aboutUs);
      }

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }
}
