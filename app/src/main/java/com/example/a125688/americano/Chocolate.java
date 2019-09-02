package com.example.a125688.americano;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.widget.Toast;

public class Chocolate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
   DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
   ProductDetail detail;
   ProductDetail detail1;
   ProductDetail detail2;

   User current_user;
   Product myProduct;
   Product product1;
   Product product2;

   Integer attachedToLineNo;
   String description = ""; // description of the item selected
   Integer lineNo;    //


   Double totalPrice = 0.0;      // total product price
   Double individualPrice = 0.0;
   Double onePrice = 0.0; // price for a type of tea ordered
   Double twoPrice = 0.0;

   String size;    // size of the item
   Integer quantity; // quantify of the same item
   Integer quantity1 = 1;
   Integer quantity2 = 1;

   Cartline newCartLine;
   Button addItemToCart;
   Integer minimumQuantity = 0;
   Integer maximumQuantity = 10;

   Button one12oz;
   Button one16oz;
   Button one20oz;
   Button two12oz;
   Button two16oz;
   Button two20oz;
   TextView priceBox;

   TextView oneShowQuantity;
   ImageButton oneMinus;
   ImageButton onePlus;
   TextView twoShowQuantity;
   ImageButton twoMinus;
   ImageButton twoPlus;

   ImageView img1, img2;
   int im1, im2;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_hotchocolate);

      final Context context = this;

      im1 = R.mipmap.regularhotchoco;
      im2 = R.mipmap.whitehotchoco;

      img1 = findViewById(R.id.teaOne);
      img2 = findViewById(R.id.teaTwo);


      ScaleImg(img1, im1);
      ScaleImg(img2, im2);

      // set plus and minus sign actions
      oneShowQuantity = findViewById(R.id.oneShowQuantity);
      onePlus = findViewById(R.id.onePlusButton);
      oneMinus = findViewById(R.id.oneMinusButton);
      twoShowQuantity = findViewById(R.id.twoShowQuantity);
      twoPlus = findViewById(R.id.twoPlusButton);
      twoMinus = findViewById(R.id.twoMinusButton);

      // plus minus button for tea one
      onePlus.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (Integer.parseInt(oneShowQuantity.getText().toString()) < maximumQuantity) {
               quantity1 = (Integer.parseInt(oneShowQuantity.getText().toString())) + 1;
               oneShowQuantity.setText(quantity1.toString());
               totalPrice += returnValue(one12oz, one16oz, one20oz,1);
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
               totalPrice -= returnValue(one12oz, one16oz, one20oz, 1);
               DisplayTotalAomount();
            }
         }
      });

      // plus minus button for tea two
      twoPlus.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (Integer.parseInt(twoShowQuantity.getText().toString()) < maximumQuantity) {
               quantity2 = (Integer.parseInt(twoShowQuantity.getText().toString())) + 1;
               twoShowQuantity.setText(quantity2.toString());
               totalPrice += returnValue(two12oz, two16oz, two20oz, 2);
               DisplayTotalAomount();
            }
            else{
               Toast.makeText(context, "You can just order " + maximumQuantity, Toast.LENGTH_SHORT).show();
            }
         }
      });


      twoMinus.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (Integer.parseInt(twoShowQuantity.getText().toString()) > minimumQuantity) {
               quantity2 = (Integer.parseInt(twoShowQuantity.getText().toString())) - 1;
               twoShowQuantity.setText(quantity2.toString());
               totalPrice -= returnValue(two12oz, two16oz, two20oz, 2);
               DisplayTotalAomount();
            }
         }
      });



      // set price
      one12oz   = findViewById(R.id.oneOz12);
      one16oz   = findViewById(R.id.oneOz16);
      one20oz   = findViewById(R.id.oneOz20);
      two12oz   = findViewById(R.id.twoOz12);
      two16oz   = findViewById(R.id.twoOz16);
      two20oz   = findViewById(R.id.twoOz20);
      priceBox = findViewById(R.id.teaPrice);

      one12oz.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            Integer teaNumber = 51;
            int priKey = 511;
            setColor(one12oz, one16oz, one20oz, onePrice, oneShowQuantity, 1);
            if (isColor(one12oz))
               readData(teaNumber, priKey);
         }
      });
      two12oz.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            Integer teaNumber = 52;
            int priKey = 521;
            setColor(two12oz, two16oz, two20oz, twoPrice, twoShowQuantity, 2);
            if (isColor(two12oz))
               readData(teaNumber, priKey);
         }
      });
      one16oz.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            Integer teaNumber = 51;
            int priKey = 512;
            setColor(one16oz, one12oz, one20oz, onePrice, oneShowQuantity, 1);
            if (isColor(one16oz))
               readData(teaNumber, priKey);
         }
      });
      two16oz.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            Integer teaNumber = 52;
            int priKey = 522;
            setColor(two16oz, two12oz, two20oz, twoPrice, twoShowQuantity, 2);
            if (isColor(two16oz))
               readData(teaNumber, priKey);
         }
      });
      one20oz.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            Integer teaNumber = 51;
            int priKey = 513;
            setColor(one20oz, one16oz, one12oz, onePrice, oneShowQuantity, 1);
            if (isColor(one20oz))
               readData(teaNumber, priKey);
         }
      });
      two20oz.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
            Integer teaNumber = 52;
            int priKey = 523;
            setColor(two20oz, two16oz, two12oz, twoPrice, twoShowQuantity, 2);
            if (isColor(two20oz))
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

      addItemToCart = (Button)findViewById(R.id.addCart);
      readCartLines(new FirebaseCallbackCartLine() {
         @Override
         public void onCallBack(final Integer newOrderLine) {
            addItemToCart.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  if (onePrice > 0.0 && Integer.parseInt(oneShowQuantity.getText().toString()) > minimumQuantity) {
                     newCartLine = new Cartline(attachedToLineNo = 0,  product1.getDescription(),
                             newOrderLine,             onePrice *
                             (Integer.parseInt(oneShowQuantity.getText().toString())),
                             product1.getNo(),      detail1.getSize().toString() + "oz",
                             current_user.getUserId(), quantity1);
                     databaseRef.child("CartProducts");
                     databaseRef.child("CartProducts").push().setValue(newCartLine);
                  }
                  if (twoPrice > 0.0 && Integer.parseInt(twoShowQuantity.getText().toString()) > minimumQuantity) {
                     newCartLine = new Cartline(attachedToLineNo = 0,  product2.getDescription(),
                             newOrderLine,             twoPrice *
                             (Integer.parseInt(twoShowQuantity.getText().toString())),
                             product2.getNo(),      detail2.getSize().toString() + "oz",
                             current_user.getUserId(), quantity2);
                     databaseRef.child("CartProducts");
                     databaseRef.child("CartProducts").push().setValue(newCartLine);
                  }

                  if ((onePrice > 0.0 && Integer.parseInt(oneShowQuantity.getText().toString()) > minimumQuantity) ||
                          (twoPrice > 0.0 && Integer.parseInt(twoShowQuantity.getText().toString()) > minimumQuantity))
                     Toast.makeText(context, "Item(s) has been added to Cart", Toast.LENGTH_SHORT).show();

                  if (onePrice + twoPrice == 0.0)
                     Toast.makeText(context, "Please select an item(s)", Toast.LENGTH_SHORT).show();
                  else if ((Integer.parseInt(oneShowQuantity.getText().toString()) == minimumQuantity) &&
                          (Integer.parseInt(twoShowQuantity.getText().toString()) == minimumQuantity))
                     Toast.makeText(context, "Please select quantity", Toast.LENGTH_SHORT).show();

                  onePrice = 0.0;
                  twoPrice = 0.0;

                  oneShowQuantity.setText("0");
                  twoShowQuantity.setText("0");

                  returnColor(one12oz, one16oz, one20oz);
                  returnColor(two12oz, two16oz, two20oz);

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
                  if (myProduct.getDescription().equals("Regular H.C.")){
                     onePrice = detail.getPrice();
                     individualPrice = (onePrice *
                             (Integer.parseInt(oneShowQuantity.getText().toString())));
                     totalPrice += individualPrice;
                     DisplayTotalAomount();
                     product1 = myProduct;
                     detail1 = detail;
                  }
                  else if (myProduct.getDescription().equals("White H.C.")) {
                     twoPrice = detail.getPrice();
                     individualPrice = (twoPrice *
                             (Integer.parseInt(twoShowQuantity.getText().toString())));
                     totalPrice += individualPrice;
                     DisplayTotalAomount();
                     product2 = myProduct;
                     detail2 = detail;
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
         else if (teaKind == 2)
            twoPrice = 0.0;
      }
      else if (isColor(button2)) {
         returnColor(button1, button2, button3);
         button1.setBackgroundColor(0xFF93a445);
         button1.setTextColor(Color.WHITE);
         totalPrice -= price *
                 (Integer.parseInt(quantity.getText().toString()));
         if (teaKind == 1)
            onePrice = 0.0;
         else if (teaKind == 2)
            twoPrice = 0.0;
      }
      else if (isColor(button3)) {
         returnColor(button1, button2, button3);
         button1.setBackgroundColor(0xFF93a445);
         button1.setTextColor(Color.WHITE);
         totalPrice -= price *
                 (Integer.parseInt(quantity.getText().toString()));
         if (teaKind == 1)
            onePrice = 0.0;
         else if (teaKind == 2)
            twoPrice = 0.0;
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

   public double returnValue(Button button12, Button button16, Button button20, int chocolateNumber){
      double changes;

      if (((ColorDrawable) button12.getBackground()).getColor() == 0xFF93a445 && chocolateNumber == 1)
         changes = 2.75;
      else if(((ColorDrawable) button16.getBackground()).getColor() == 0xFF93a445 && chocolateNumber == 1)
         changes = 3.25;
      else if (((ColorDrawable) button20.getBackground()).getColor() == 0xFF93a445 && chocolateNumber == 1)
         changes = 3.65;
      else if(((ColorDrawable) button12.getBackground()).getColor() == 0xFF93a445 && chocolateNumber == 2)
         changes = 2.75;
      else if(((ColorDrawable) button16.getBackground()).getColor() == 0xFF93a445 && chocolateNumber == 2)
         changes = 3.25;
      else if (((ColorDrawable) button20.getBackground()).getColor() == 0xFF93a445 && chocolateNumber == 2)
         changes = 3.65;
      else
         changes = 0.0;

      return changes;
   }

   private void readCartLines( final FirebaseCallbackCartLine firebaseCallbackCartLines){
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

      if (id == R.id.carButton){
         Intent menu = new Intent(Chocolate.this, CartPage.class);
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
         Intent menu = new Intent(Chocolate.this, com.example.a125688.americano.Menu.class);
         menu.putExtra("userInfo", current_user);
         startActivity(menu);
      } else if (id == R.id.Order_history) {

      } else if (id == R.id.Rewards) {
         Intent rewards = new Intent(Chocolate.this, com.example.a125688.americano.Menu.class);
         rewards.putExtra("userInfo", current_user);
         startActivity(rewards);
      } else if (id == R.id.Favorites) {
         Intent favorite = new Intent(Chocolate.this, favoritePage.class);
         favorite.putExtra("userInfo", current_user);
         startActivity(favorite);
      } else if (id == R.id.Account) {
         Intent account = new Intent(Chocolate.this, AccountPage.class);
         account.putExtra("userInfo", current_user);
         startActivity(account);
      } else if (id == R.id.Log_out) {
         Intent logOut = new Intent(Chocolate.this, Home.class);
         startActivity(logOut);
      } else if (id == R.id.About_us){
         Intent aboutUs = new Intent(Chocolate.this, aboutUsPage.class);
         aboutUs.putExtra("userInfo", current_user);
         startActivity(aboutUs);
      }

      DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }

}