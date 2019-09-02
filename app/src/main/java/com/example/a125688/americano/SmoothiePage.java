package com.example.a125688.americano;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
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

import java.text.DecimalFormat;
import java.util.ArrayList;


public class SmoothiePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    User current_user;
    ProductDetail detail;
    ProductDetail detail1;
    ProductDetail detail2;
    ProductDetail detail3;
    ProductDetail detail4;
    ProductDetail detail5;

    Product myProduct;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Product product5;

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
    TextView priceBox;

    String size;    // size of the item
    Integer quantity; // quantify of the same item
    Integer quantity1 = 1;
    Integer quantity2 = 1;
    Integer quantity3 = 1;
    Integer quantity4 = 1;
    Integer quantity5 = 1;

    Double totalPrice = 0.0;      // total product price
    Double onePrice = 0.0; // price for a type of tea ordered
    Double twoPrice = 0.0;
    Double threePrice = 0.0;
    Double fourPrice = 0.0;
    Double fivePrice = 0.0;

    Integer attachedToLineNo;
    String description = ""; // description of the item selected
    Integer lineNo;    //

    Cartline newCartLine;
    Button addItemToCart;
    Integer minimumQuantity = 0;
    Integer maximumQuantity = 10;

    int priKey1 = 21;
    int priKey2 = 22;
    int priKey3 = 23;
    int priKey4 = 24;
    int priKey5 = 25;
    int dessert1 = 21;
    int dessert2 = 22;
    int dessert3 = 23;
    int dessert4 = 24;
    int dessert5 = 25;

    ImageView img1, img2, img3, img4, img5;
    int im1, im2, im3, im4, im5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smoothie_page);

        Resources res = getResources();

        final Context context = this;

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
       final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Favorites");
       final CheckBox loveBanana     = (CheckBox) findViewById(R.id.favBanana);
       final CheckBox loveMango      = (CheckBox) findViewById(R.id.favMango);
       final CheckBox lovePeach      = (CheckBox) findViewById(R.id.favPeach);
       final CheckBox loveStrawberry = (CheckBox) findViewById(R.id.favStrawberry);
       final CheckBox loveRaspberry  = (CheckBox) findViewById(R.id.favRaspberry);

        im1 = R.drawable.bananasmoothie;
        im2 = R.drawable.mangosmoothie;
        im3 = R.drawable.peachsmoothie;
        im4 = R.drawable.raspberrysmoothie;
        im5 = R.drawable.strawberrysmoothie;

        img1 = findViewById(R.id.banana_smoothie);
        img2 = findViewById(R.id.mango_smoothie);
        img3 = findViewById(R.id.peach_smoothie);
        img4 = findViewById(R.id.raspberry_smoothie);
        img5 = findViewById(R.id.strawberry_smoothie);

        ScaleImg(img1, im1);
        ScaleImg(img2, im2);
        ScaleImg(img3, im3);
        ScaleImg(img4, im4);
        ScaleImg(img5, im5);

        priceBox = findViewById(R.id.frappePrice);

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

        // plus minus button for frappe
        onePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(oneShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity1 = (Integer.parseInt(oneShowQuantity.getText().toString())) + 1;
                    oneShowQuantity.setText(quantity1.toString());
                    readData(dessert1, priKey1);
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
                    DeductPrice(onePrice);
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
                    readData(dessert2, priKey2);
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
                    DeductPrice(twoPrice);
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
                    readData(dessert3, priKey3);
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
                    DeductPrice(threePrice);
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
                    readData(dessert4, priKey4);
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
                    DeductPrice(fourPrice);
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
                    readData(dessert5, priKey5);
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
                    DeductPrice(fivePrice);
                    DisplayTotalAomount();
                }
            }
        });


        addItemToCart = (Button)findViewById(R.id.addCart);
        readCartLines(new Tea.FirebaseCallbackCartLine() {
            @Override
            public void onCallBack(final Integer newOrderLine) {
                addItemToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((Integer.parseInt(oneShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product1.getDescription(),
                                    newOrderLine,             onePrice *
                                    (Integer.parseInt(oneShowQuantity.getText().toString())),
                                    product1.getNo(),      detail1.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity1);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(twoShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product2.getDescription(),
                                    newOrderLine,             twoPrice *
                                    (Integer.parseInt(twoShowQuantity.getText().toString())),
                                    product2.getNo(),      detail2.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity2);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(threeShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product3.getDescription(),
                                    newOrderLine,             threePrice *
                                    (Integer.parseInt(threeShowQuantity.getText().toString())),
                                    product3.getNo(),      detail3.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity3);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(fourShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product4.getDescription(),
                                    newOrderLine,             fourPrice *
                                    (Integer.parseInt(fourShowQuantity.getText().toString())),
                                    product4.getNo(),      detail4.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity4);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(fiveShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0,  product5.getDescription(),
                                    newOrderLine,             fivePrice *
                                    (Integer.parseInt(fiveShowQuantity.getText().toString())),
                                    product5.getNo(),      detail5.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity5);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }


                        if (totalPrice > 0.0)
                            Toast.makeText(context, "Item(s) has been added to Cart", Toast.LENGTH_SHORT).show();

                        if (totalPrice == 0.0)
                            Toast.makeText(context, "Please select an item(s)", Toast.LENGTH_SHORT).show();

                        oneShowQuantity.setText("0");
                        twoShowQuantity.setText("0");
                        threeShowQuantity.setText("0");
                        fourShowQuantity.setText("0");
                        fiveShowQuantity.setText("0");

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
                        if (myProduct.getDescription().equals("Banana Smoothie")){
                            onePrice = detail.getPrice();
                            totalPrice += onePrice;
                            DisplayTotalAomount();
                            product1 = myProduct;
                            detail1 = detail;
                        }
                        else if (myProduct.getDescription().equals("Mango Smoothie")) {
                            twoPrice = detail.getPrice();
                            totalPrice += twoPrice;
                            DisplayTotalAomount();
                            product2 = myProduct;
                            detail2 = detail;
                        }
                        else if (myProduct.getDescription().equals("Peach Smoothie")) {
                            threePrice = detail.getPrice();
                            totalPrice += threePrice;
                            DisplayTotalAomount();
                            product3 = myProduct;
                            detail3 = detail;
                        }
                        else if (myProduct.getDescription().equals("Raspberry Smoothie")) {
                            fourPrice = detail.getPrice();
                            totalPrice += fourPrice;
                            DisplayTotalAomount();
                            product4 = myProduct;
                            detail4 = detail;
                        }
                        else if (myProduct.getDescription().equals("Strawberry Smoothie")) {
                            fivePrice = detail.getPrice();
                            totalPrice += fivePrice;
                            DisplayTotalAomount();
                            product5 = myProduct;
                            detail5 = detail;
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


    private void readCartLines( final Tea.FirebaseCallbackCartLine firebaseCallbackCartLines){
        // Getting Dairy AddOns from database
        Query queryCartLine=databaseRef.child("CartProducts").orderByChild("userId").equalTo(current_user.getName());
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

    public void DeductPrice(Double singlePrice){
        totalPrice -= singlePrice;
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
            Intent menu = new Intent(SmoothiePage.this, CartPage.class);
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
            Intent menu = new Intent(SmoothiePage.this, Menu.class);
            menu.putExtra("userInfo", current_user);
            startActivity(menu);
        } else if (id == R.id.Order_history) {

        } else if (id == R.id.Rewards) {
            Intent rewards = new Intent(SmoothiePage.this, Menu.class);
            rewards.putExtra("userInfo", current_user);
            startActivity(rewards);
        } else if (id == R.id.Favorites) {
            Intent favorite = new Intent(SmoothiePage.this, favoritePage.class);
            favorite.putExtra("userInfo", current_user);
            startActivity(favorite);
        } else if (id == R.id.Account) {
            Intent account = new Intent(SmoothiePage.this, AccountPage.class);
            account.putExtra("userInfo", current_user);
            startActivity(account);
        } else if (id == R.id.Log_out) {
            Intent logOut = new Intent(SmoothiePage.this, Home.class);
            startActivity(logOut);
        } else if (id == R.id.About_us){
            Intent aboutUs = new Intent(SmoothiePage.this, aboutUsPage.class);
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


}
