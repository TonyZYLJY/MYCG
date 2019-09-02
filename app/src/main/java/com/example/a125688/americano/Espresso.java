package com.example.a125688.americano;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Espresso extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    ProductDetail detail;
    ProductDetail detail1;
    ProductDetail detail2;
    ProductDetail detail3;
    ProductDetail detail4;
    ProductDetail detail5;
    ProductDetail detail6;
    ProductDetail detail7;

    User current_user;
    Product myProduct;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Product product5;
    Product product6;
    Product product7;


    Integer attachedToLineNo;
    String description = ""; // description of the item selected
    Integer lineNo;    //


    Double totalPrice = 0.0;      // total product price
    Double individualPrice = 0.0;
    Double onePrice = 0.0; // price for a type of tea ordered
    Double twoPrice = 0.0;
    Double threePrice = 0.0;
    Double fourPrice = 0.0;
    Double fivePrice = 0.0;
    Double sixPrice = 0.0;
    Double sevenPrice = 0.0;

    String size;    // size of the item
    Integer quantity; // quantify of the same item
    Integer quantity1 = 1;
    Integer quantity2 = 1;
    Integer quantity3 = 1;
    Integer quantity4 = 1;
    Integer quantity5 = 1;
    Integer quantity6 = 1;
    Integer quantity7 = 1;

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
    Button three12oz;
    Button three16oz;
    Button three20oz;
    Button four12oz;
    Button four16oz;
    Button four20oz;
    Button five12oz;
    Button five16oz;
    Button five20oz;
    Button six12oz;
    Button six16oz;
    Button six20oz;
    Button seven12oz;
    Button seven16oz;
    Button seven20oz;
    TextView priceBox;

    TextView oneShowQuantity;
    ImageButton oneMinus;
    ImageButton onePlus;
    TextView twoShowQuantity;
    ImageButton twoMinus;
    ImageButton twoPlus;
    TextView threeShowQuantity;
    ImageButton threeMinus;
    ImageButton threePlus;
    TextView fourShowQuantity;
    ImageButton fourMinus;
    ImageButton fourPlus;
    TextView fiveShowQuantity;
    ImageButton fiveMinus;
    ImageButton fivePlus;
    TextView sixShowQuantity;
    ImageButton sixMinus;
    ImageButton sixPlus;
    TextView sevenShowQuantity;
    ImageButton sevenMinus;
    ImageButton sevenPlus;

   ImageView img1, img2, img3, img4, img5, img6, img7;
   int im1, im2, im3, im4, im5, im6, im7;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso);

        final Context context = this;

      im1 = R.mipmap.mullenix;
      im2 = R.mipmap.mocha;
      im3 = R.mipmap.cappuccino;
      im4 = R.mipmap.latte;
      im5 = R.mipmap.flavoredlatte;
      im6 = R.mipmap.caramelmacchiato;
      im7 = R.mipmap.cafeamericano;

      img1 = findViewById(R.id.teaOne);
      img2 = findViewById(R.id.teaTwo);
      img3 = findViewById(R.id.teaThree);
      img4 = findViewById(R.id.teaFour);
      img5 = findViewById(R.id.teaFive);
      img6 = findViewById(R.id.teaSix);
      img7 = findViewById(R.id.teaSeven);


      ScaleImg(img1, im1);
      ScaleImg(img2, im2);
      ScaleImg(img3, im3);
      ScaleImg(img4, im4);
      ScaleImg(img5, im5);
      ScaleImg(img6, im6);
      ScaleImg(img7, im7);

        // set plus and minus sign actions
        oneShowQuantity = findViewById(R.id.oneShowQuantity);
        onePlus = findViewById(R.id.onePlusButton);
        oneMinus = findViewById(R.id.oneMinusButton);
        twoShowQuantity = findViewById(R.id.twoShowQuantity);
        twoPlus = findViewById(R.id.twoPlusButton);
        twoMinus = findViewById(R.id.twoMinusButton);
        threeShowQuantity = findViewById(R.id.threeShowQuantity);
        threePlus = findViewById(R.id.threePlusButton);
        threeMinus = findViewById(R.id.threeMinusButton);
        fourShowQuantity = findViewById(R.id.fourShowQuantity);
        fourPlus = findViewById(R.id.fourPlusButton);
        fourMinus = findViewById(R.id.fourMinusButton);
        fiveShowQuantity = findViewById(R.id.fiveShowQuantity);
        fivePlus = findViewById(R.id.fivePlusButton);
        fiveMinus = findViewById(R.id.fiveMinusButton);
        sixShowQuantity = findViewById(R.id.sixShowQuantity);
        sixPlus = findViewById(R.id.sixPlusButton);
        sixMinus = findViewById(R.id.sixMinusButton);
        sevenShowQuantity = findViewById(R.id.sevenShowQuantity);
        sevenPlus = findViewById(R.id.sevenPlusButton);
        sevenMinus = findViewById(R.id.sevenMinusButton);

       final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Favorites");
       final CheckBox loveMulleanixMocha     = (CheckBox) findViewById(R.id.favMullenixMocha);
       final CheckBox loveMocha      = (CheckBox) findViewById(R.id.favMocha);
       final CheckBox loveCappuccino      = (CheckBox) findViewById(R.id.favCapuccino);
       final CheckBox loveLate = (CheckBox) findViewById(R.id.favLatte);
       final CheckBox loveFlavoredLatte  = (CheckBox) findViewById(R.id.favFlavoredLatte);
       final CheckBox loveMacchiato = (CheckBox) findViewById(R.id.favCaramelMacchiato);
       final CheckBox loveAmericano  = (CheckBox) findViewById(R.id.favAmericano);

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

       favoriteProducts(new FirebaseFavoriteCallbackCartLine() {
          @Override
          public void onCallBack(final ArrayList<favorite> favList) {
             //Activate hearts
             for(Integer index = 0; index < favList.size(); index++)
             {
                if(favList.get(index).getProductNo() == 71 )
                {
                   loveMulleanixMocha.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 72 )
                {
                   loveMocha.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 73 )
                {
                   loveCappuccino.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 74 )
                {
                   loveLate.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 75 )
                {
                   loveFlavoredLatte.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 76 )
                {
                   loveMacchiato.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 77 )
                {
                   loveAmericano.setChecked(true);
                }
             }

             loveMulleanixMocha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Mullenix Mocha");
                      favProduct.setProductNo(71);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);

                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 71 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }
                }
             });

             loveMocha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Mocha");
                      favProduct.setProductNo(72);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 72 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });

             loveCappuccino.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Cappuccino");
                      favProduct.setProductNo(73);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 73 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });
             loveLate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Latte");
                      favProduct.setProductNo(74);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 74 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });

             loveFlavoredLatte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Flavored Latte");
                      favProduct.setProductNo(75);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 75 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });
             loveMacchiato.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Caramel Macchiato");
                      favProduct.setProductNo(76);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 76 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });
             loveAmericano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Cafe Americano");
                      favProduct.setProductNo(77);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 77 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });

          }
       });

       // plus minus button for tea one
        onePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(oneShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity1 = (Integer.parseInt(oneShowQuantity.getText().toString())) + 1;
                    oneShowQuantity.setText(quantity1.toString());
                    //individualPrice = onePrice *
                    //        (Integer.parseInt(oneShowQuantity.getText().toString()));
                    //totalPrice += individualPrice;
                    totalPrice += returnValue(one12oz, one16oz, one20oz, 1);
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
        threePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(threeShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity3 = (Integer.parseInt(threeShowQuantity.getText().toString())) + 1;
                    threeShowQuantity.setText(quantity3.toString());
                    totalPrice += returnValue(three12oz, three16oz, three20oz, 3);
                    DisplayTotalAomount();
                }
                else{
                    Toast.makeText(context, "You can just order " + maximumQuantity, Toast.LENGTH_SHORT).show();
                }
            }
        });
        threeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(threeShowQuantity.getText().toString()) > minimumQuantity) {
                    quantity3 = (Integer.parseInt(threeShowQuantity.getText().toString())) - 1;
                    threeShowQuantity.setText(quantity3.toString());
                    totalPrice -= returnValue(three12oz, three16oz, three20oz, 3);
                    DisplayTotalAomount();
                }
            }
        });
        fourPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(fourShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity4 = (Integer.parseInt(fourShowQuantity.getText().toString())) + 1;
                    fourShowQuantity.setText(quantity4.toString());
                    totalPrice += returnValue(four12oz, four16oz, four20oz, 4);
                    DisplayTotalAomount();
                }
                else{
                    Toast.makeText(context, "You can just order " + maximumQuantity, Toast.LENGTH_SHORT).show();
                }
            }
        });
        fourMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(fourShowQuantity.getText().toString()) > minimumQuantity) {
                    quantity4 = (Integer.parseInt(fourShowQuantity.getText().toString())) - 1;
                    fourShowQuantity.setText(quantity4.toString());
                    totalPrice -= returnValue(four12oz, four16oz, four20oz, 4);
                    DisplayTotalAomount();
                }
            }
        });
        fivePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(fiveShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity5 = (Integer.parseInt(fiveShowQuantity.getText().toString())) + 1;
                    fiveShowQuantity.setText(quantity5.toString());
                    totalPrice += returnValue(five12oz, five16oz, five20oz, 5);
                    DisplayTotalAomount();
                }
                else{
                    Toast.makeText(context, "You can just order " + maximumQuantity, Toast.LENGTH_SHORT).show();
                }
            }
        });
        fiveMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(fiveShowQuantity.getText().toString()) > minimumQuantity) {
                    quantity5 = (Integer.parseInt(fiveShowQuantity.getText().toString())) - 1;
                    fiveShowQuantity.setText(quantity5.toString());
                    totalPrice -= returnValue(five12oz, five16oz, five20oz, 5);
                    DisplayTotalAomount();
                }
            }
        });
        sixPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(sixShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity6 = (Integer.parseInt(sixShowQuantity.getText().toString())) + 1;
                    sixShowQuantity.setText(quantity6.toString());
                    totalPrice += returnValue(six12oz, six16oz, six20oz, 6);
                    DisplayTotalAomount();
                }
                else{
                    Toast.makeText(context, "You can just order " + maximumQuantity, Toast.LENGTH_SHORT).show();
                }
            }
        });

        sixMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(sixShowQuantity.getText().toString()) > minimumQuantity) {
                    quantity6 = (Integer.parseInt(sixShowQuantity.getText().toString())) - 1;
                    sixShowQuantity.setText(quantity6.toString());
                    totalPrice -= returnValue(six12oz, six16oz, six20oz, 6);
                    DisplayTotalAomount();
                }
            }
        });

        // plus minus for tea seven
        sevenPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(sevenShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity7 = (Integer.parseInt(sevenShowQuantity.getText().toString())) + 1;
                    sevenShowQuantity.setText(quantity7.toString());
                    totalPrice += returnValue(seven12oz, seven16oz, seven20oz, 7);
                    DisplayTotalAomount();
                }
                else{
                    Toast.makeText(context, "You can just order " + maximumQuantity, Toast.LENGTH_SHORT).show();
                }
            }
        });

        sevenMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(sevenShowQuantity.getText().toString()) > minimumQuantity) {
                    quantity7 = (Integer.parseInt(sevenShowQuantity.getText().toString())) - 1;
                    sevenShowQuantity.setText(quantity7.toString());
                    totalPrice -= returnValue(seven12oz, seven16oz, seven20oz, 7);
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
        three12oz = findViewById(R.id.threeOz12);
        three16oz = findViewById(R.id.threeOz16);
        three20oz = findViewById(R.id.threeOz20);
        four12oz  = findViewById(R.id.fourOz12);
        four16oz  = findViewById(R.id.fourOz16);
        four20oz  = findViewById(R.id.fourOz20);
        five12oz  = findViewById(R.id.fiveOz12);
        five16oz  = findViewById(R.id.fiveOz16);
        five20oz  = findViewById(R.id.fiveOz20);
        six12oz   = findViewById(R.id.sixOz12);
        six16oz   = findViewById(R.id.sixOz16);
        six20oz   = findViewById(R.id.sixOz20);
        seven12oz = findViewById(R.id.sevenOz12);
        seven16oz = findViewById(R.id.sevenOz16);
        seven20oz = findViewById(R.id.sevenOz20);
        priceBox = findViewById(R.id.teaPrice);

        one12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 71;
                int priKey = 711;
                setColor(one12oz, one16oz, one20oz, onePrice, oneShowQuantity, 1);
                if (isColor(one12oz))
                    readData(teaNumber, priKey);
            }
        });
        two12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 72;
                int priKey = 721;
                setColor(two12oz, two16oz, two20oz, twoPrice, twoShowQuantity, 2);
                if (isColor(two12oz))
                    readData(teaNumber, priKey);
            }
        });
        three12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 73;
                int priKey = 731;
                setColor(three12oz, three16oz, three12oz, threePrice, threeShowQuantity, 3);
                if (isColor(three12oz))
                    readData(teaNumber, priKey);
            }
        });
        four12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 74;
                int priKey = 741;
                setColor(four12oz, four16oz, four20oz, fourPrice, fourShowQuantity, 4);
                if (isColor(four12oz))
                    readData(teaNumber, priKey);
            }
        });
        five12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 75;
                int priKey = 751;
                setColor(five12oz, five16oz, five20oz, fivePrice, fiveShowQuantity, 5);
                if (isColor(five12oz))
                    readData(teaNumber, priKey);
            }
        });
        six12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 76;
                int priKey = 761;
                setColor(six12oz, six16oz,six20oz, sixPrice, sixShowQuantity, 6);
                if (isColor(six12oz))
                    readData(teaNumber, priKey);
            }
        });
        seven12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 77;
                int priKey = 771;
                setColor(seven12oz, seven16oz, seven20oz, sevenPrice, sevenShowQuantity, 7);
                if (isColor(seven12oz))
                    readData(teaNumber, priKey);
            }
        });

        one16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 71;
                int priKey = 712;
                setColor(one16oz, one12oz, one20oz, onePrice, oneShowQuantity, 1);
                if (isColor(one16oz))
                    readData(teaNumber, priKey);
            }
        });
        two16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 72;
                int priKey = 722;
                setColor(two16oz, two12oz, two20oz, twoPrice, twoShowQuantity, 2);
                if (isColor(two16oz))
                    readData(teaNumber, priKey);
            }
        });
        three16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 73;
                int priKey = 732;
                setColor(three16oz, three12oz, three20oz, threePrice, threeShowQuantity, 3);
                if (isColor(three16oz))
                    readData(teaNumber, priKey);
            }
        });
        four16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 74;
                int priKey = 742;
                setColor(four16oz, four12oz, four20oz, fourPrice, fourShowQuantity, 4);
                if (isColor(four16oz))
                    readData(teaNumber, priKey);
            }
        });
        five16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 75;
                int priKey = 752;
                setColor(five16oz, five12oz, five20oz, fivePrice, fiveShowQuantity, 5);
                if (isColor(five16oz))
                    readData(teaNumber, priKey);
            }
        });
        six16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 76;
                int priKey = 762;
                setColor(six16oz, six12oz, six20oz, sixPrice, sixShowQuantity, 6);
                if (isColor(six16oz))
                    readData(teaNumber, priKey);
            }
        });
        seven16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 77;
                int priKey = 772;
                setColor(seven16oz, seven12oz, seven20oz, sevenPrice, sevenShowQuantity, 7);
                if (isColor(seven16oz))
                    readData(teaNumber, priKey);
            }
        });

        one20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 71;
                int priKey = 713;
                setColor(one20oz, one16oz, one12oz, onePrice, oneShowQuantity, 1);
                if (isColor(one20oz))
                    readData(teaNumber, priKey);
            }
        });
        two20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 72;
                int priKey = 723;
                setColor(two20oz, two16oz, two12oz, twoPrice, twoShowQuantity, 2);
                if (isColor(two20oz))
                    readData(teaNumber, priKey);
            }
        });
        three20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 73;
                int priKey = 733;
                setColor(three20oz, three12oz, three16oz, threePrice, threeShowQuantity, 3);
                if (isColor(three20oz))
                    readData(teaNumber, priKey);
            }
        });
        four20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 74;
                int priKey = 743;
                setColor(four20oz, four12oz, four16oz, fourPrice, fourShowQuantity, 4);
                if (isColor(four20oz))
                    readData(teaNumber, priKey);
            }
        });
        five20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 75;
                int priKey = 753;
                setColor(five20oz, five12oz, five16oz, fivePrice, fiveShowQuantity, 5);
                if (isColor(five20oz))
                    readData(teaNumber, priKey);
            }
        });
        six20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 76;
                int priKey = 763;
                setColor(six20oz, six12oz, six16oz, sixPrice, sixShowQuantity, 6);
                if (isColor(six20oz))
                    readData(teaNumber, priKey);
            }
        });
        seven20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 77;
                int priKey = 773;
                setColor(seven20oz, seven12oz, seven16oz, sevenPrice, sevenShowQuantity, 7);
                if (isColor(seven20oz))
                    readData(teaNumber, priKey);

            }
        });

        addItemToCart = (Button)findViewById(R.id.addCart);
        readCartLines(new Tea.FirebaseCallbackCartLine() {
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
                        if (threePrice > 0.0 && Integer.parseInt(threeShowQuantity.getText().toString()) > minimumQuantity) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product3.getDescription(),
                                    newOrderLine,             threePrice *
                                    (Integer.parseInt(threeShowQuantity.getText().toString())),
                                    product3.getNo(),      detail3.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity3);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if (fourPrice > 0.0 && Integer.parseInt(fourShowQuantity.getText().toString()) > minimumQuantity) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product4.getDescription(),
                                    newOrderLine,             fourPrice *
                                    (Integer.parseInt(fourShowQuantity.getText().toString())),
                                    product4.getNo(),      detail4.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity4);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if (fivePrice > 0.0 && Integer.parseInt(fiveShowQuantity.getText().toString()) > minimumQuantity) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product5.getDescription(),
                                    newOrderLine,             fivePrice *
                                    (Integer.parseInt(fiveShowQuantity.getText().toString())),
                                    product5.getNo(),      detail5.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity5);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if (sixPrice > 0.0 && Integer.parseInt(sixShowQuantity.getText().toString()) > minimumQuantity) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product6.getDescription(),
                                    newOrderLine,             sixPrice *
                                    (Integer.parseInt(sixShowQuantity.getText().toString())),
                                    product6.getNo(),      detail6.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity6);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }if (sevenPrice > 0.0 && Integer.parseInt(sevenShowQuantity.getText().toString()) > minimumQuantity) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product7.getDescription(),
                                    newOrderLine,             sevenPrice *
                                    (Integer.parseInt(sevenShowQuantity.getText().toString())),
                                    product7.getNo(),      detail7.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity7);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }

                        if ((onePrice > 0.0 && Integer.parseInt(oneShowQuantity.getText().toString()) > minimumQuantity) ||
                                (twoPrice > 0.0 && Integer.parseInt(twoShowQuantity.getText().toString()) > minimumQuantity) ||
                                (threePrice > 0.0 && Integer.parseInt(threeShowQuantity.getText().toString()) > minimumQuantity) ||
                                (fourPrice > 0.0 && Integer.parseInt(fourShowQuantity.getText().toString()) > minimumQuantity) ||
                                (fivePrice > 0.0 && Integer.parseInt(fiveShowQuantity.getText().toString()) > minimumQuantity) ||
                                (sixPrice > 0.0 && Integer.parseInt(sixShowQuantity.getText().toString()) > minimumQuantity) ||
                                (sevenPrice > 0.0 && Integer.parseInt(sevenShowQuantity.getText().toString()) > minimumQuantity))
                            Toast.makeText(context, "Item(s) has been added to Cart", Toast.LENGTH_SHORT).show();

                        if (onePrice + twoPrice + threePrice + fourPrice + fivePrice + sixPrice
                                + sevenPrice == 0.0)
                            Toast.makeText(context, "Please select an item(s)", Toast.LENGTH_SHORT).show();
                        else if ((Integer.parseInt(oneShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(twoShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(threeShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(fourShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(fiveShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(sixShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(sevenShowQuantity.getText().toString()) == minimumQuantity))
                            Toast.makeText(context, "Please select quantity", Toast.LENGTH_SHORT).show();

                        onePrice = 0.0;
                        twoPrice = 0.0;
                        threePrice = 0.0;
                        fourPrice = 0.0;
                        fivePrice = 0.0;
                        sixPrice = 0.0;
                        sevenPrice = 0.0;

                        oneShowQuantity.setText("0");
                        twoShowQuantity.setText("0");
                        threeShowQuantity.setText("0");
                        fourShowQuantity.setText("0");
                        fiveShowQuantity.setText("0");
                        sixShowQuantity.setText("0");
                        sevenShowQuantity.setText("0");

                        returnColor(one12oz, one16oz, one20oz);
                        returnColor(two12oz, two16oz, two20oz);
                        returnColor(three12oz, three16oz, three20oz);
                        returnColor(four12oz, four16oz, four20oz);
                        returnColor(five12oz, five16oz, five20oz);
                        returnColor(six12oz, six16oz, six20oz);
                        returnColor(seven12oz, seven16oz, seven20oz);

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
                        if (myProduct.getDescription().equals("Mullenix Mocha")){
                            onePrice = detail.getPrice();
                            individualPrice = (onePrice *
                                    (Integer.parseInt(oneShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product1 = myProduct;
                            detail1 = detail;
                        }
                        else if (myProduct.getDescription().equals("Mocha")) {
                            twoPrice = detail.getPrice();
                            individualPrice = (twoPrice *
                                    (Integer.parseInt(twoShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product2 = myProduct;
                            detail2 = detail;
                        }
                        else if (myProduct.getDescription().equals("Cappuccino")) {
                            threePrice = detail.getPrice();
                            individualPrice = (threePrice *
                                    (Integer.parseInt(threeShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product3 = myProduct;
                            detail3 = detail;
                        }
                        else if (myProduct.getDescription().equals("Latte")) {
                            fourPrice = detail.getPrice();
                            individualPrice = (fourPrice *
                                    (Integer.parseInt(fourShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product4 = myProduct;
                            detail4 = detail;
                        }
                        else if (myProduct.getDescription().equals("Flavored Latte")) {
                            fivePrice = detail.getPrice();
                            individualPrice = (fivePrice *
                                    (Integer.parseInt(fiveShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product5 = myProduct;
                            detail5 = detail;
                        }
                        else if (myProduct.getDescription().equals("Caramel Macchiato")) {
                            sixPrice = detail.getPrice();
                            individualPrice = (sixPrice *
                                    (Integer.parseInt(sixShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product6 = myProduct;
                            detail6 = detail;
                        }
                        else if (myProduct.getDescription().equals("Cafe Americano")) {
                            sevenPrice = detail.getPrice();
                            individualPrice = (sevenPrice *
                                    (Integer.parseInt(sevenShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product7 = myProduct;
                            detail7 = detail;
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
            else if (teaKind == 3)
                threePrice = 0.0;
            else if (teaKind == 4)
                fourPrice = 0.0;
            else if (teaKind == 5)
                fivePrice = 0.0;
            else if (teaKind == 6)
                sixPrice = 0.0;
            else if (teaKind == 7)
                sevenPrice = 0.0;
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
            else if (teaKind == 3)
                threePrice = 0.0;
            else if (teaKind == 4)
                fourPrice = 0.0;
            else if (teaKind == 5)
                fivePrice = 0.0;
            else if (teaKind == 6)
                sixPrice = 0.0;
            else if (teaKind == 7)
                sevenPrice = 0.0;
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
            else if (teaKind == 3)
                threePrice = 0.0;
            else if (teaKind == 4)
                fourPrice = 0.0;
            else if (teaKind == 5)
                fivePrice = 0.0;
            else if (teaKind == 6)
                sixPrice = 0.0;
            else if (teaKind == 7)
                sevenPrice = 0.0;
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

    public double returnValue(Button button12, Button button16, Button button20, int espressoNumber){
        double changes;

        if (((ColorDrawable) button12.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 1)
            changes = 3.45;
        else if(((ColorDrawable) button16.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 1)
            changes = 3.95;
        else if (((ColorDrawable) button20.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 1)
            changes = 4.35;
        else if(((ColorDrawable) button12.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 2)
            changes = 3.25;
        else if(((ColorDrawable) button16.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 2)
            changes = 3.75;
        else if (((ColorDrawable) button20.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 2)
            changes = 4.15;
        else if(((ColorDrawable) button12.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 3)
            changes = 3.15;
        else if(((ColorDrawable) button16.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 3)
            changes = 3.65;
        else if (((ColorDrawable) button20.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 3)
            changes = 4.5;
        else if(((ColorDrawable) button12.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 4)
            changes = 2.8;
        else if(((ColorDrawable) button16.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 4)
            changes = 3.1;
        else if (((ColorDrawable) button20.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 4)
            changes = 3.35;
        else if(((ColorDrawable) button12.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 5)
            changes = 3.45;
        else if(((ColorDrawable) button16.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 5)
            changes = 3.75;
        else if (((ColorDrawable) button20.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 5)
            changes = 4.00;
        else if(((ColorDrawable) button12.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 6)
            changes = 3.45;
        else if(((ColorDrawable) button16.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 6)
            changes = 3.95;
        else if (((ColorDrawable) button20.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 6)
            changes = 4.35;
        else if(((ColorDrawable) button12.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 7)
            changes = 1.90;
        else if(((ColorDrawable) button16.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 7)
            changes = 2.40;
        else if (((ColorDrawable) button20.getBackground()).getColor() == 0xFF93a445 && espressoNumber == 7)
            changes = 2.90;
        else
            changes = 0.0;

        return changes;
    }

    private void readCartLines( final Tea.FirebaseCallbackCartLine firebaseCallbackCartLines){
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
            Intent menu = new Intent(Espresso.this, CartPage.class);
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
            Intent menu = new Intent(Espresso.this, com.example.a125688.americano.Menu.class);
            menu.putExtra("userInfo", current_user);
            startActivity(menu);
        } else if (id == R.id.Order_history) {

        } else if (id == R.id.Rewards) {
            Intent rewards = new Intent(Espresso.this, com.example.a125688.americano.Menu.class);
            rewards.putExtra("userInfo", current_user);
            startActivity(rewards);
        } else if (id == R.id.Favorites) {
           Intent favorite = new Intent(Espresso.this, favoritePage.class);
           favorite.putExtra("userInfo", current_user);
           startActivity(favorite);

        } else if (id == R.id.Account) {
            Intent account = new Intent(Espresso.this, AccountPage.class);
            account.putExtra("userInfo", current_user);
            startActivity(account);
        } else if (id == R.id.Log_out) {
            Intent logOut = new Intent(Espresso.this, Home.class);
            startActivity(logOut);
        } else if (id == R.id.About_us){
           Intent aboutUs = new Intent(Espresso.this, aboutUsPage.class);
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
      void onCallBack(ArrayList<favorite> favList);
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

}
