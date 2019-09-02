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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class Tea extends AppCompatActivity
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
    ProductDetail detail8;

    User current_user;
    Product myProduct;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Product product5;
    Product product6;
    Product product7;
    Product product8;


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
    Double eightPrice = 0.0;

    String size;    // size of the item
    Integer quantity; // quantify of the same item
    Integer quantity1 = 1;
    Integer quantity2 = 1;
    Integer quantity3 = 1;
    Integer quantity4 = 1;
    Integer quantity5 = 1;
    Integer quantity6 = 1;
    Integer quantity7 = 1;
    Integer quantity8 = 1;

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
    Button eight12oz;
    Button eight16oz;
    Button eight20oz;
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
    TextView eightShowQuantity;
    ImageButton eightMinus;
    ImageButton eightPlus;

   ImageView img1, img2, img3, img4, img5, img6, img7, img8;
   int im1, im2, im3, im4, im5, im6, im7, im8;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea);

        final Context context = this;

      im1 = R.mipmap.english_teatime;
      im2 = R.mipmap.cinnamon_apple;
      im3 = R.mipmap.lemon_lift;
      im4 = R.mipmap.raspberry_royale;
      im5 = R.mipmap.orange_jasmine;
      im6 = R.mipmap.green_tea_with_pomegrande;
      im7 = R.mipmap.classic_green_tea;
      im8 = R.mipmap.mint_medley;

      img1 = findViewById(R.id.teaOne);
      img2 = findViewById(R.id.teaTwo);
      img3 = findViewById(R.id.teaThree);
      img4 = findViewById(R.id.teaFour);
      img5 = findViewById(R.id.teaFive);
      img6 = findViewById(R.id.teaSix);
      img7 = findViewById(R.id.teaSeven);
      img8 = findViewById(R.id.teaEight);


      ScaleImg(img1, im1);
      ScaleImg(img2, im2);
      ScaleImg(img3, im3);
      ScaleImg(img4, im4);
      ScaleImg(img5, im5);
      ScaleImg(img6, im6);
      ScaleImg(img7, im7);
      ScaleImg(img8, im8);

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
        eightShowQuantity = findViewById(R.id.eightShowQuantity);
        eightPlus = findViewById(R.id.eightPlusButton);
        eightMinus = findViewById(R.id.eightMinusButton);

       final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Favorites");
       final CheckBox loveEnglishTea = (CheckBox) findViewById(R.id.favEnglishTea);
       final CheckBox loveCinnamonApple = (CheckBox) findViewById(R.id.favCinnnamonTea);
       final CheckBox loveLemonTea    = (CheckBox) findViewById(R.id.favLemonLift);
       final CheckBox loveRaspberryTea= (CheckBox) findViewById(R.id.favRaspberryTea);
       final CheckBox loveJasmineTea  = (CheckBox) findViewById(R.id.favJasmineTea);
       final CheckBox loveGreenPomegradeTea  = (CheckBox) findViewById(R.id.favGreenPomegradeTea);
       final CheckBox loveGreenTea  = (CheckBox) findViewById(R.id.favGreenTea);
       final CheckBox loveMintTea  = (CheckBox) findViewById(R.id.favMintTea);

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
                if(favList.get(index).getProductNo() ==  61 )
                {
                   loveEnglishTea.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 62 )
                {
                   loveCinnamonApple.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 63 )
                {
                   loveLemonTea.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 64 )
                {
                   loveRaspberryTea.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 65 )
                {
                   loveJasmineTea.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 66 )
                {
                   loveGreenPomegradeTea.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 67 )
                {
                   loveGreenTea.setChecked(true);
                }
                if(favList.get(index).getProductNo() == 68 )
                {
                   loveMintTea.setChecked(true);
                }
             }

             loveEnglishTea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "English Teatime Tea");
                      favProduct.setProductNo(61);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 61 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }
                }
             });

             loveCinnamonApple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Cinnamom Apple Tea");
                      favProduct.setProductNo(62);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 62 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });

             loveLemonTea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Lemon Lift Tea");
                      favProduct.setProductNo(63);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 63 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });
             loveRaspberryTea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Raspberry Royale Tea");
                      favProduct.setProductNo(64);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 64 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });

             loveJasmineTea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Orange Jasmine Tea");
                      favProduct.setProductNo(65);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 65 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });

             loveGreenPomegradeTea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Green Tea with Pomegrade");
                      favProduct.setProductNo(66);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 66 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });

             loveGreenTea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Green Tea");
                      favProduct.setProductNo(67);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 67 )
                         {
                            deletedProduct = favList.get(index);
                         }
                      }
                      dbRef.child(deletedProduct.getKey()).removeValue();
                   }

                }
             });

             loveMintTea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                   if(isChecked){
                      favorite favProduct = new favorite();
                      favProduct.setDescription( "Mint Medley Tea");
                      favProduct.setProductNo(68);
                      favProduct.setUserId(current_user.getUserId());
                      dbRef.push().setValue(favProduct);
                   }
                   else
                   {
                      favorite deletedProduct = new favorite();
                      for(Integer index = 0; index < favList.size(); index++)
                      {
                         if(favList.get(index).getProductNo() == 68 )
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

        // plus minus button for tea two
        twoPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(twoShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity2 = (Integer.parseInt(twoShowQuantity.getText().toString())) + 1;
                    twoShowQuantity.setText(quantity2.toString());
                    totalPrice += returnValue(two12oz, two16oz, two20oz);
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
                    totalPrice -= returnValue(two12oz, two16oz, two20oz);
                    DisplayTotalAomount();
                }
            }
        });

        // plus minus for tea three
        threePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(threeShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity3 = (Integer.parseInt(threeShowQuantity.getText().toString())) + 1;
                    threeShowQuantity.setText(quantity3.toString());
                    totalPrice += returnValue(three12oz, three16oz, three20oz);
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
                    totalPrice -= returnValue(three12oz, three16oz, three20oz);
                    DisplayTotalAomount();
                }
            }
        });

        // plus minus for tea four
        fourPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(fourShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity4 = (Integer.parseInt(fourShowQuantity.getText().toString())) + 1;
                    fourShowQuantity.setText(quantity4.toString());
                    totalPrice += returnValue(four12oz, four16oz, four20oz);
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
                    totalPrice -= returnValue(four12oz, four16oz, four20oz);
                    DisplayTotalAomount();
                }
            }
        });

        // plus minus for tea five
        fivePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(fiveShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity5 = (Integer.parseInt(fiveShowQuantity.getText().toString())) + 1;
                    fiveShowQuantity.setText(quantity5.toString());
                    totalPrice += returnValue(five12oz, five16oz, five20oz);
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
                    totalPrice -= returnValue(five12oz, five16oz, five20oz);
                    DisplayTotalAomount();
                }
            }
        });

        // plus minus for tea six
        sixPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(sixShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity6 = (Integer.parseInt(sixShowQuantity.getText().toString())) + 1;
                    sixShowQuantity.setText(quantity6.toString());
                    totalPrice += returnValue(six12oz, six16oz, six20oz);
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
                    totalPrice -= returnValue(six12oz, six16oz, six20oz);
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
                    totalPrice += returnValue(seven12oz, seven16oz, seven20oz);
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
                    totalPrice -= returnValue(seven12oz, seven16oz, seven20oz);
                    DisplayTotalAomount();
                }
            }
        });

        // plus minus for tea eight
        eightPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(eightShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity8 = (Integer.parseInt(eightShowQuantity.getText().toString())) + 1;
                    eightShowQuantity.setText(quantity8.toString());
                    totalPrice += returnValue(eight12oz, eight16oz, eight20oz);
                    DisplayTotalAomount();
                }
                else{
                    Toast.makeText(context, "You can just order " + maximumQuantity, Toast.LENGTH_SHORT).show();
                }
            }
        });

        eightMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(eightShowQuantity.getText().toString()) > minimumQuantity) {
                    quantity8 = (Integer.parseInt(eightShowQuantity.getText().toString())) - 1;
                    eightShowQuantity.setText(quantity8.toString());
                    totalPrice -= returnValue(eight12oz, eight16oz, eight20oz);
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
        eight12oz = findViewById(R.id.eightOz12);
        eight16oz = findViewById(R.id.eightOz16);
        eight20oz = findViewById(R.id.eightOz20);
        priceBox = findViewById(R.id.teaPrice);

        one12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 61;
                int priKey = 61;
                setColor(one12oz, one16oz, one20oz, onePrice, oneShowQuantity, 1);
                if (isColor(one12oz))
                    readData(teaNumber, priKey);
            }
        });
        two12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 62;
                int priKey = 61;
                setColor(two12oz, two16oz, two20oz, twoPrice, twoShowQuantity, 2);
                if (isColor(two12oz))
                    readData(teaNumber, priKey);
            }
        });
        three12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 63;
                int priKey = 61;
                setColor(three12oz, three16oz, three12oz, threePrice, threeShowQuantity, 3);
                if (isColor(three12oz))
                    readData(teaNumber, priKey);
            }
        });
        four12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 64;
                int priKey = 61;
                setColor(four12oz, four16oz, four20oz, fourPrice, fourShowQuantity, 4);
                if (isColor(four12oz))
                    readData(teaNumber, priKey);
            }
        });
        five12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 65;
                int priKey = 61;
                setColor(five12oz, five16oz, five20oz, fivePrice, fiveShowQuantity, 5);
                if (isColor(five12oz))
                    readData(teaNumber, priKey);
            }
        });
        six12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 66;
                int priKey = 61;
                setColor(six12oz, six16oz,six20oz, sixPrice, sixShowQuantity, 6);
                if (isColor(six12oz))
                    readData(teaNumber, priKey);
            }
        });
        seven12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 67;
                int priKey = 61;
                setColor(seven12oz, seven16oz, seven20oz, sevenPrice, sevenShowQuantity, 7);
                if (isColor(seven12oz))
                    readData(teaNumber, priKey);
            }
        });
        eight12oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 68;
                int priKey = 61;
                setColor(eight12oz, eight16oz, eight20oz, eightPrice, eightShowQuantity, 8);
                if (isColor(eight12oz))
                    readData(teaNumber, priKey);
            }
        });

        one16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 61;
                int priKey = 62;
                setColor(one16oz, one12oz, one20oz, onePrice, oneShowQuantity, 1);
                if (isColor(one16oz))
                    readData(teaNumber, priKey);
            }
        });
        two16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 62;
                int priKey = 62;
                setColor(two16oz, two12oz, two20oz, twoPrice, twoShowQuantity, 2);
                if (isColor(two16oz))
                    readData(teaNumber, priKey);
            }
        });
        three16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 63;
                int priKey = 62;
                setColor(three16oz, three12oz, three20oz, threePrice, threeShowQuantity, 3);
                if (isColor(three16oz))
                    readData(teaNumber, priKey);
            }
        });
        four16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 64;
                int priKey = 62;
                setColor(four16oz, four12oz, four20oz, fourPrice, fourShowQuantity, 4);
                if (isColor(four16oz))
                    readData(teaNumber, priKey);
            }
        });
        five16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 65;
                int priKey = 62;
                setColor(five16oz, five12oz, five20oz, fivePrice, fiveShowQuantity, 5);
                if (isColor(five16oz))
                    readData(teaNumber, priKey);
            }
        });
        six16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 66;
                int priKey = 62;
                setColor(six16oz, six12oz, six20oz, sixPrice, sixShowQuantity, 6);
                if (isColor(six16oz))
                    readData(teaNumber, priKey);
            }
        });
        seven16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 67;
                int priKey = 62;
                setColor(seven16oz, seven12oz, seven20oz, sevenPrice, sevenShowQuantity, 7);
                if (isColor(seven16oz))
                    readData(teaNumber, priKey);
            }
        });
        eight16oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 68;
                int priKey = 62;
                setColor(eight16oz, eight12oz, eight20oz, eightPrice, eightShowQuantity, 8);
                if (isColor(eight16oz))
                    readData(teaNumber, priKey);
            }
        });

        one20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 61;
                int priKey = 63;
                setColor(one20oz, one16oz, one12oz, onePrice, oneShowQuantity, 1);
                if (isColor(one20oz))
                    readData(teaNumber, priKey);
            }
        });
        two20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 62;
                int priKey = 63;
                setColor(two20oz, two16oz, two12oz, twoPrice, twoShowQuantity, 2);
                if (isColor(two20oz))
                    readData(teaNumber, priKey);
            }
        });
        three20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 63;
                int priKey = 63;
                setColor(three20oz, three12oz, three16oz, threePrice, threeShowQuantity, 3);
                if (isColor(three20oz))
                    readData(teaNumber, priKey);
            }
        });
        four20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 64;
                int priKey = 63;
                setColor(four20oz, four12oz, four16oz, fourPrice, fourShowQuantity, 4);
                if (isColor(four20oz))
                    readData(teaNumber, priKey);
            }
        });
        five20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 65;
                int priKey = 63;
                setColor(five20oz, five12oz, five16oz, fivePrice, fiveShowQuantity, 5);
                if (isColor(five20oz))
                    readData(teaNumber, priKey);
            }
        });
        six20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 66;
                int priKey = 63;
                setColor(six20oz, six12oz, six16oz, sixPrice, sixShowQuantity, 6);
                if (isColor(six20oz))
                    readData(teaNumber, priKey);
            }
        });
        seven20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 67;
                int priKey = 63;
                setColor(seven20oz, seven12oz, seven16oz, sevenPrice, sevenShowQuantity, 7);
                if (isColor(seven20oz))
                    readData(teaNumber, priKey);

            }
        });
        eight20oz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Integer teaNumber = 68;
                int priKey = 63;
                setColor(eight20oz, eight12oz, eight16oz, eightPrice, eightShowQuantity, 8);
                if (isColor(eight20oz))
                    readData(teaNumber, priKey);
            }
        });

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
                        if (eightPrice > 0.0 && Integer.parseInt(eightShowQuantity.getText().toString()) > minimumQuantity) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product8.getDescription(),
                                    newOrderLine,             eightPrice  *
                                    (Integer.parseInt(eightShowQuantity.getText().toString())),
                                    product8.getNo(),      detail8.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity8);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }

                        if ((onePrice > 0.0 && Integer.parseInt(oneShowQuantity.getText().toString()) > minimumQuantity) ||
                                (twoPrice > 0.0 && Integer.parseInt(twoShowQuantity.getText().toString()) > minimumQuantity) ||
                                (threePrice > 0.0 && Integer.parseInt(threeShowQuantity.getText().toString()) > minimumQuantity) ||
                                (fourPrice > 0.0 && Integer.parseInt(fourShowQuantity.getText().toString()) > minimumQuantity) ||
                                (fivePrice > 0.0 && Integer.parseInt(fiveShowQuantity.getText().toString()) > minimumQuantity) ||
                                (sixPrice > 0.0 && Integer.parseInt(sixShowQuantity.getText().toString()) > minimumQuantity) ||
                                (sevenPrice > 0.0 && Integer.parseInt(sevenShowQuantity.getText().toString()) > minimumQuantity) ||
                                (eightPrice > 0.0 && Integer.parseInt(eightShowQuantity.getText().toString()) > minimumQuantity))
                            Toast.makeText(context, "Item(s) has been added to Cart", Toast.LENGTH_SHORT).show();

                        if (onePrice + twoPrice + threePrice + fourPrice + fivePrice + sixPrice
                                + sevenPrice + eightPrice == 0.0)
                            Toast.makeText(context, "Please select an item(s)", Toast.LENGTH_SHORT).show();
                        else if ((Integer.parseInt(oneShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(twoShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(threeShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(fourShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(fiveShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(sixShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(sevenShowQuantity.getText().toString()) == minimumQuantity) &&
                                (Integer.parseInt(eightShowQuantity.getText().toString()) == minimumQuantity))
                            Toast.makeText(context, "Please select quantity", Toast.LENGTH_SHORT).show();

                        onePrice = 0.0;
                        twoPrice = 0.0;
                        threePrice = 0.0;
                        fourPrice = 0.0;
                        fivePrice = 0.0;
                        sixPrice = 0.0;
                        sevenPrice = 0.0;
                        eightPrice = 0.0;

                        oneShowQuantity.setText("0");
                        twoShowQuantity.setText("0");
                        threeShowQuantity.setText("0");
                        fourShowQuantity.setText("0");
                        fiveShowQuantity.setText("0");
                        sixShowQuantity.setText("0");
                        sevenShowQuantity.setText("0");
                        eightShowQuantity.setText("0");

                        returnColor(one12oz, one16oz, one20oz);
                        returnColor(two12oz, two16oz, two20oz);
                        returnColor(three12oz, three16oz, three20oz);
                        returnColor(four12oz, four16oz, four20oz);
                        returnColor(five12oz, five16oz, five20oz);
                        returnColor(six12oz, six16oz, six20oz);
                        returnColor(seven12oz, seven16oz, seven20oz);
                        returnColor(eight12oz, eight16oz, eight20oz);

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
                        if (myProduct.getDescription().equals("English Teatime")){
                            onePrice = detail.getPrice();
                            individualPrice = (onePrice *
                                    (Integer.parseInt(oneShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product1 = myProduct;
                            detail1 = detail;
                        }
                        else if (myProduct.getDescription().equals("Cinnamon Apple")) {
                            twoPrice = detail.getPrice();
                            individualPrice = (twoPrice *
                                    (Integer.parseInt(twoShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product2 = myProduct;
                            detail2 = detail;
                        }
                        else if (myProduct.getDescription().equals("Lemon Lift")) {
                            threePrice = detail.getPrice();
                            individualPrice = (threePrice *
                                    (Integer.parseInt(threeShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product3 = myProduct;
                            detail3 = detail;
                        }
                        else if (myProduct.getDescription().equals("Raspberry Royale")) {
                            fourPrice = detail.getPrice();
                            individualPrice = (fourPrice *
                                    (Integer.parseInt(fourShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product4 = myProduct;
                            detail4 = detail;
                        }
                        else if (myProduct.getDescription().equals("Orange Jasmine")) {
                            fivePrice = detail.getPrice();
                            individualPrice = (fivePrice *
                                    (Integer.parseInt(fiveShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product5 = myProduct;
                            detail5 = detail;
                        }
                        else if (myProduct.getDescription().equals("Green Tea with Pomegranate")) {
                            sixPrice = detail.getPrice();
                            individualPrice = (sixPrice *
                                    (Integer.parseInt(sixShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product6 = myProduct;
                            detail6 = detail;
                        }
                        else if (myProduct.getDescription().equals("Classic Green Tea")) {
                            sevenPrice = detail.getPrice();
                            individualPrice = (sevenPrice *
                                    (Integer.parseInt(sevenShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product7 = myProduct;
                            detail7 = detail;
                        }
                        else if (myProduct.getDescription().equals("Mint Medley")) {
                            eightPrice = detail.getPrice();
                            individualPrice = (eightPrice *
                                    (Integer.parseInt(eightShowQuantity.getText().toString())));
                            totalPrice += individualPrice;
                            DisplayTotalAomount();
                            product8 = myProduct;
                            detail8 = detail;
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
            else if (teaKind == 8)
                eightPrice = 0.0;
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
            else if (teaKind == 8)
                eightPrice = 0.0;
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
            else if (teaKind == 8)
                eightPrice = 0.0;
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
            Intent menu = new Intent(Tea.this, CartPage.class);
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
            Intent menu = new Intent(Tea.this, com.example.a125688.americano.Menu.class);
            menu.putExtra("userInfo", current_user);
            startActivity(menu);
        } else if (id == R.id.Order_history) {
           User current_user = getIntent().getParcelableExtra("userInfo");
           Intent orderHistory = new Intent(Tea.this, OrderHistory.class);
           orderHistory.putExtra("userInfo", current_user);
           startActivity(orderHistory);
        } else if (id == R.id.Rewards) {
            Intent rewards = new Intent(Tea.this, Rewards.class);
            rewards.putExtra("userInfo", current_user);
            startActivity(rewards);
        } else if (id == R.id.Favorites) {
           Intent favorite = new Intent(Tea.this, favoritePage.class);
           favorite.putExtra("userInfo", current_user);
           startActivity(favorite);
        } else if (id == R.id.Account) {
            Intent account = new Intent(Tea.this, AccountPage.class);
            account.putExtra("userInfo", current_user);
            startActivity(account);
        } else if (id == R.id.Log_out) {
            Intent logOut = new Intent(Tea.this, Home.class);
            startActivity(logOut);
            Tea.this.finish();
        } else if (id == R.id.About_us){
           Intent aboutUs = new Intent(Tea.this, aboutUsPage.class);
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

   @Override
    public void onDestroy(){
       super.onDestroy();
   }

}