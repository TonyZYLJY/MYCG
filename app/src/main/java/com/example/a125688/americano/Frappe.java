package com.example.a125688.americano;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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

public class Frappe extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    User current_user;

    ProductDetail detail;
    ProductDetail detail1;
    ProductDetail detail2;
    ProductDetail detail3;
    ProductDetail detail4;
    ProductDetail detail5;
    ProductDetail detail6;
    ProductDetail detail7;
    ProductDetail detail8;
    ProductDetail detail9;

    Product myProduct;
    Product product1;
    Product product2;
    Product product3;
    Product product4;
    Product product5;
    Product product6;
    Product product7;
    Product product8;
    Product product9;

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
    TextView nineShowQuantity;
    ImageButton nineMinus;
    ImageButton ninePlus;

    TextView priceBox;

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
    Integer quantity9 = 1;

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
    Double ninePrice = 0.0;

    Integer attachedToLineNo;
    String description = ""; // description of the item selected
    Integer lineNo;    //

    //Favorites
    CheckBox loveCaramel;
    CheckBox loveDoubleChocolate;
    CheckBox loveFrozenHotChocolate;
    CheckBox loveJava;
    CheckBox loveOreo;
    CheckBox lovePeanutButter;
    CheckBox loveRaspberry;
    CheckBox loveWhiteChocolate;
    CheckBox lovePeanutButterWhite;

    Cartline newCartLine;
    Button addItemToCart;
    Integer minimumQuantity = 0;
    Integer maximumQuantity = 10;

    int priKey = 3;
    int frappe1 = 38;
    int frappe2 = 31;
    int frappe3 = 37;
    int frappe4 = 39;
    int frappe5 = 36;
    int frappe6 = 32;
    int frappe7 = 34;
    int frappe8 = 33;
    int frappe9 = 35;

    ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9;
    int im1, im2, im3, im4, im5, im6, im7, im8, im9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frappe);

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

        im1 = R.drawable.new_caramel;
        im2 = R.drawable.new_doublechoco;
        im3 = R.drawable.new_frozenhotchoco;
        im4 = R.drawable.new_java;
        im5 = R.drawable.new_oreo;
        im6 = R.drawable.new_peanutbutter;
        im7 = R.drawable.new_rasbpberrywhite;
        im8 = R.drawable.new_whitechoco;
        im9 = R.drawable.peanutbutterwhite;

        img1 = findViewById(R.id.caramel);
        img2 = findViewById(R.id.double_chocolate);
        img3 = findViewById(R.id.frozen_hot_chocolate);
        img4 = findViewById(R.id.java);
        img5 = findViewById(R.id.oreo);
        img6 = findViewById(R.id.peanut_butter);
        img7 = findViewById(R.id.raspberry_white);
        img8 = findViewById(R.id.white_choco);
        img9 = findViewById(R.id.peanut_butter_white);


        ScaleImg(img1, im1);
        ScaleImg(img2, im2);
        ScaleImg(img3, im3);
        ScaleImg(img4, im4);
        ScaleImg(img5, im5);
        ScaleImg(img6, im6);
        ScaleImg(img7, im7);
        ScaleImg(img8, im8);
        ScaleImg(img9, im9);


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
        nineShowQuantity = findViewById(R.id.nineShowQuantity);
        ninePlus = findViewById(R.id.ninePlusButton);
        nineMinus = findViewById(R.id.nineMinusButton);

        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Favorites");
        loveCaramel = findViewById(R.id.favCaramel);
        loveDoubleChocolate = findViewById(R.id.favDoubleChocolate);
        loveFrozenHotChocolate = findViewById(R.id.favFrozenHotChocolate);
        loveJava = findViewById(R.id.favJava);
        loveOreo = findViewById(R.id.favOreo);
        lovePeanutButter = findViewById(R.id.favPeanutButter);
        loveRaspberry = findViewById(R.id.favRaspberryWhite);
        loveWhiteChocolate = findViewById(R.id.favWhiteChocolate);
        lovePeanutButterWhite = findViewById(R.id.favPeanutButterWhite);

        priceBox = findViewById(R.id.frappePrice);

        favoriteProducts(new FirebaseFavoriteCallbackCartLine() {
            @Override
            public void onCallBack(final ArrayList<favorite> favList) {
                //Activate hearts
                for (Integer index = 0; index < favList.size(); index++) {
                    if (favList.get(index).getProductNo() == frappe1) {
                        loveCaramel.setChecked(true);
                    }
                    if (favList.get(index).getProductNo() == frappe2) {
                        loveDoubleChocolate.setChecked(true);
                    }
                    if (favList.get(index).getProductNo() == frappe3) {
                        loveFrozenHotChocolate.setChecked(true);
                    }
                    if (favList.get(index).getProductNo() == frappe4) {
                        loveJava.setChecked(true);
                    }
                    if (favList.get(index).getProductNo() == frappe5) {
                        loveOreo.setChecked(true);
                    }
                    if (favList.get(index).getProductNo() == frappe6) {
                        lovePeanutButter.setChecked(true);
                    }
                    if (favList.get(index).getProductNo() == frappe7) {
                        loveRaspberry.setChecked(true);
                    }
                    if (favList.get(index).getProductNo() == frappe8) {
                        loveWhiteChocolate.setChecked(true);
                    }
                    if (favList.get(index).getProductNo() == frappe9) {
                        lovePeanutButterWhite.setChecked(true);
                    }
                }

                loveCaramel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            favorite favProduct = new favorite();
                            favProduct.setDescription("Caramel Frappe");
                            favProduct.setProductNo(frappe1);
                            favProduct.setUserId(current_user.getUserId());
                            dbRef.push().setValue(favProduct);

                        } else {
                            favorite deletedProduct = new favorite();
                            for (Integer index = 0; index < favList.size(); index++) {
                                if (favList.get(index).getProductNo() == frappe1) {
                                    deletedProduct = favList.get(index);
                                }
                            }
                            dbRef.child(deletedProduct.getKey()).removeValue();
                        }
                    }
                });

                loveDoubleChocolate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            favorite favProduct = new favorite();
                            favProduct.setDescription("Regular DC Frappe");
                            favProduct.setProductNo(frappe2);
                            favProduct.setUserId(current_user.getUserId());
                            dbRef.push().setValue(favProduct);
                        } else {
                            favorite deletedProduct = new favorite();
                            for (Integer index = 0; index < favList.size(); index++) {
                                if (favList.get(index).getProductNo() == frappe2) {
                                    deletedProduct = favList.get(index);
                                }
                            }
                            dbRef.child(deletedProduct.getKey()).removeValue();
                        }

                    }
                });

                loveFrozenHotChocolate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            favorite favProduct = new favorite();
                            favProduct.setDescription("Frozen HC Frappe");
                            favProduct.setProductNo(frappe3);
                            favProduct.setUserId(current_user.getUserId());
                            dbRef.push().setValue(favProduct);
                        } else {
                            favorite deletedProduct = new favorite();
                            for (Integer index = 0; index < favList.size(); index++) {
                                if (favList.get(index).getProductNo() == frappe3) {
                                    deletedProduct = favList.get(index);
                                }
                            }
                            dbRef.child(deletedProduct.getKey()).removeValue();
                        }

                    }
                });
                loveJava.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            favorite favProduct = new favorite();
                            favProduct.setDescription("Java Frappe");
                            favProduct.setProductNo(frappe4);
                            favProduct.setUserId(current_user.getUserId());
                            dbRef.push().setValue(favProduct);
                        } else {
                            favorite deletedProduct = new favorite();
                            for (Integer index = 0; index < favList.size(); index++) {
                                if (favList.get(index).getProductNo() == frappe4) {
                                    deletedProduct = favList.get(index);
                                }
                            }
                            dbRef.child(deletedProduct.getKey()).removeValue();
                        }

                    }
                });

                loveOreo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            favorite favProduct = new favorite();
                            favProduct.setDescription("Oreo Frappe");
                            favProduct.setProductNo(frappe5);
                            favProduct.setUserId(current_user.getUserId());
                            dbRef.push().setValue(favProduct);
                        } else {
                            favorite deletedProduct = new favorite();
                            for (Integer index = 0; index < favList.size(); index++) {
                                if (favList.get(index).getProductNo() == frappe5) {
                                    deletedProduct = favList.get(index);
                                }
                            }
                            dbRef.child(deletedProduct.getKey()).removeValue();
                        }

                    }
                });
                lovePeanutButter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            favorite favProduct = new favorite();
                            favProduct.setDescription("Peanut Butter DC Frappe");
                            favProduct.setProductNo(frappe6);
                            favProduct.setUserId(current_user.getUserId());
                            dbRef.push().setValue(favProduct);
                        } else {
                            favorite deletedProduct = new favorite();
                            for (Integer index = 0; index < favList.size(); index++) {
                                if (favList.get(index).getProductNo() == frappe6) {
                                    deletedProduct = favList.get(index);
                                }
                            }
                            dbRef.child(deletedProduct.getKey()).removeValue();
                        }

                    }
                });

                loveRaspberry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            favorite favProduct = new favorite();
                            favProduct.setDescription("Raspberry WC Frappe");
                            favProduct.setProductNo(frappe7);
                            favProduct.setUserId(current_user.getUserId());
                            dbRef.push().setValue(favProduct);
                        } else {
                            favorite deletedProduct = new favorite();
                            for (Integer index = 0; index < favList.size(); index++) {
                                if (favList.get(index).getProductNo() == frappe7) {
                                    deletedProduct = favList.get(index);
                                }
                            }
                            dbRef.child(deletedProduct.getKey()).removeValue();
                        }

                    }
                });

                loveWhiteChocolate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            favorite favProduct = new favorite();
                            favProduct.setDescription("White Chocolate Frappe");
                            favProduct.setProductNo(frappe8);
                            favProduct.setUserId(current_user.getUserId());
                            dbRef.push().setValue(favProduct);
                        } else {
                            favorite deletedProduct = new favorite();
                            for (Integer index = 0; index < favList.size(); index++) {
                                if (favList.get(index).getProductNo() == frappe8) {
                                    deletedProduct = favList.get(index);
                                }
                            }
                            dbRef.child(deletedProduct.getKey()).removeValue();
                        }

                    }
                });
                lovePeanutButterWhite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            favorite favProduct = new favorite();
                            favProduct.setDescription("Peanut Butter WC Frappe");
                            favProduct.setProductNo(frappe9);
                            favProduct.setUserId(current_user.getUserId());
                            dbRef.push().setValue(favProduct);
                        } else {
                            favorite deletedProduct = new favorite();
                            for (Integer index = 0; index < favList.size(); index++) {
                                if (favList.get(index).getProductNo() == frappe9) {
                                    deletedProduct = favList.get(index);
                                }
                            }
                            dbRef.child(deletedProduct.getKey()).removeValue();
                        }

                    }
                });

            }
        });

        // plus minus button for frappe
        onePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(oneShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity1 = (Integer.parseInt(oneShowQuantity.getText().toString())) + 1;
                    oneShowQuantity.setText(quantity1.toString());
                    readData(frappe1, priKey);
                } else {
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
                    readData(frappe2, priKey);
                    DisplayTotalAomount();
                } else {
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
                    readData(frappe3, priKey);
                    DisplayTotalAomount();
                } else {
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
                    readData(frappe4, priKey);
                    DisplayTotalAomount();
                } else {
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
                    readData(frappe5, priKey);
                    DisplayTotalAomount();
                } else {
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

        sixPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(sixShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity6 = (Integer.parseInt(sixShowQuantity.getText().toString())) + 1;
                    sixShowQuantity.setText(quantity6.toString());
                    readData(frappe6, priKey);
                    DisplayTotalAomount();
                } else {
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
                    DeductPrice(sixPrice);
                    DisplayTotalAomount();
                }
            }
        });

        sevenPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(sevenShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity7 = (Integer.parseInt(sevenShowQuantity.getText().toString())) + 1;
                    sevenShowQuantity.setText(quantity7.toString());
                    readData(frappe7, priKey);
                    DisplayTotalAomount();
                } else {
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
                    DeductPrice(sevenPrice);
                    DisplayTotalAomount();
                }
            }
        });

        eightPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(eightShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity8 = (Integer.parseInt(eightShowQuantity.getText().toString())) + 1;
                    eightShowQuantity.setText(quantity8.toString());
                    readData(frappe8, priKey);
                    DisplayTotalAomount();
                } else {
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
                    DeductPrice(eightPrice);
                    DisplayTotalAomount();
                }
            }
        });
        ninePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(nineShowQuantity.getText().toString()) < maximumQuantity) {
                    quantity9 = (Integer.parseInt(nineShowQuantity.getText().toString())) + 1;
                    nineShowQuantity.setText(quantity9.toString());
                    readData(frappe9, priKey);
                    DisplayTotalAomount();
                } else {
                    Toast.makeText(context, "You can just order " + maximumQuantity, Toast.LENGTH_SHORT).show();
                }
            }
        });

        nineMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(nineShowQuantity.getText().toString()) > minimumQuantity) {
                    quantity9 = (Integer.parseInt(nineShowQuantity.getText().toString())) - 1;
                    nineShowQuantity.setText(quantity9.toString());
                    DeductPrice(ninePrice);
                    DisplayTotalAomount();
                }
            }
        });

        addItemToCart = (Button) findViewById(R.id.addCart);
        readCartLines(new Tea.FirebaseCallbackCartLine() {
            @Override
            public void onCallBack(final Integer newOrderLine) {
                addItemToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((Integer.parseInt(oneShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0, product1.getDescription(),
                                    newOrderLine, onePrice *
                                    (Integer.parseInt(oneShowQuantity.getText().toString())),
                                    product1.getNo(), detail1.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity1);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(twoShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0, product2.getDescription(),
                                    newOrderLine, twoPrice *
                                    (Integer.parseInt(twoShowQuantity.getText().toString())),
                                    product2.getNo(), detail2.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity2);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(threeShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0, product3.getDescription(),
                                    newOrderLine, threePrice *
                                    (Integer.parseInt(threeShowQuantity.getText().toString())),
                                    product3.getNo(), detail3.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity3);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(fourShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0, product4.getDescription(),
                                    newOrderLine, fourPrice *
                                    (Integer.parseInt(fourShowQuantity.getText().toString())),
                                    product4.getNo(), detail4.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity4);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(fiveShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0, product5.getDescription(),
                                    newOrderLine, fivePrice *
                                    (Integer.parseInt(fiveShowQuantity.getText().toString())),
                                    product5.getNo(), detail5.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity5);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(sixShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0, product6.getDescription(),
                                    newOrderLine, sixPrice *
                                    (Integer.parseInt(sixShowQuantity.getText().toString())),
                                    product6.getNo(), detail6.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity6);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(sevenShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0, product7.getDescription(),
                                    newOrderLine, sevenPrice *
                                    (Integer.parseInt(sevenShowQuantity.getText().toString())),
                                    product7.getNo(), detail7.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity7);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(eightShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0, product8.getDescription(),
                                    newOrderLine, eightPrice *
                                    (Integer.parseInt(eightShowQuantity.getText().toString())),
                                    product8.getNo(), detail8.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity8);
                            databaseRef.child("CartProducts");
                            databaseRef.child("CartProducts").push().setValue(newCartLine);
                        }
                        if ((Integer.parseInt(nineShowQuantity.getText().toString())) > 0.0) {
                            newCartLine = new Cartline(attachedToLineNo = 0, product9.getDescription(),
                                    newOrderLine, ninePrice *
                                    (Integer.parseInt(nineShowQuantity.getText().toString())),
                                    product9.getNo(), detail9.getSize().toString() + "oz",
                                    current_user.getUserId(), quantity9);
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
                        sixShowQuantity.setText("0");
                        sevenShowQuantity.setText("0");
                        eightShowQuantity.setText("0");
                        nineShowQuantity.setText("0");

                        totalPrice = 0.0;
                        DisplayTotalAomount();

                    }
                });
            }
        });

    }

    // get the item and item details selected
    public void readData(Integer teaNumber, final int prikey) {
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
                        if (myProduct.getDescription().equals("Caramel")) {
                            onePrice = detail.getPrice();
                            totalPrice += onePrice;
                            DisplayTotalAomount();
                            product1 = myProduct;
                            detail1 = detail;
                        } else if (myProduct.getDescription().equals("Regular DC")) {
                            twoPrice = detail.getPrice();
                            totalPrice += twoPrice;
                            DisplayTotalAomount();
                            product2 = myProduct;
                            detail2 = detail;
                        } else if (myProduct.getDescription().equals("Frozen HC")) {
                            threePrice = detail.getPrice();
                            totalPrice += threePrice;
                            DisplayTotalAomount();
                            product3 = myProduct;
                            detail3 = detail;
                        } else if (myProduct.getDescription().equals("Java")) {
                            fourPrice = detail.getPrice();
                            totalPrice += fourPrice;
                            DisplayTotalAomount();
                            product4 = myProduct;
                            detail4 = detail;
                        } else if (myProduct.getDescription().equals("Oreo")) {
                            fivePrice = detail.getPrice();
                            totalPrice += fivePrice;
                            DisplayTotalAomount();
                            product5 = myProduct;
                            detail5 = detail;
                        } else if (myProduct.getDescription().equals("Peanut Butter DC")) {
                            sixPrice = detail.getPrice();
                            totalPrice += sixPrice;
                            DisplayTotalAomount();
                            product6 = myProduct;
                            detail6 = detail;
                        } else if (myProduct.getDescription().equals("Raspberry WC")) {
                            sevenPrice = detail.getPrice();
                            totalPrice += sevenPrice;
                            DisplayTotalAomount();
                            product7 = myProduct;
                            detail7 = detail;
                        } else if (myProduct.getDescription().equals("White Chocolate")) {
                            eightPrice = detail.getPrice();
                            totalPrice += eightPrice;
                            DisplayTotalAomount();
                            product8 = myProduct;
                            detail8 = detail;
                        } else if (myProduct.getDescription().equals("Peanut Butter WC")) {
                            ninePrice = detail.getPrice();
                            totalPrice += ninePrice;
                            DisplayTotalAomount();
                            product9 = myProduct;
                            detail9 = detail;
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

    private void readCartLines(final Tea.FirebaseCallbackCartLine firebaseCallbackCartLines) {
        // Getting Dairy AddOns from database
        Query queryCartLine = databaseRef.child("CartProducts").orderByChild("userId").equalTo(current_user.getName());
        queryCartLine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer lineNo = 10;
                Integer newLineNo = 10;
                Boolean createNewLine = false;
                for (DataSnapshot cartLine : dataSnapshot.getChildren()) {
                    if (cartLine.child("attachedToLineNo").getValue(Integer.class) == 0) {
                        if (lineNo <= cartLine.child("lineNo").getValue(Integer.class)) {
                            lineNo = cartLine.child("lineNo").getValue(Integer.class);
                            createNewLine = true;
                        }
                    }
                }
                if (createNewLine) {
                    newLineNo = lineNo + 10;
                }
                firebaseCallbackCartLines.onCallBack(newLineNo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public interface FirebaseCallbackCartLine {
        void onCallBack(Integer newOrderLine);
    }

    public void DisplayTotalAomount() {
        double price;
        price = Math.max(0.0, totalPrice);
        priceBox.setText(String.format("$%.2f", price));
    }

    public void DeductPrice(Double singlePrice) {
        totalPrice -= singlePrice;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.carButton) {
            Intent menu = new Intent(Frappe.this, CartPage.class);
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
            Intent menu = new Intent(Frappe.this, com.example.a125688.americano.Menu.class);
            menu.putExtra("userInfo", current_user);
            startActivity(menu);
        } else if (id == R.id.Order_history) {

        } else if (id == R.id.Rewards) {
            Intent rewards = new Intent(Frappe.this, Menu.class);
            rewards.putExtra("userInfo", current_user);
            startActivity(rewards);
        } else if (id == R.id.Favorites) {
            Intent favorite = new Intent(Frappe.this, favoritePage.class);
            favorite.putExtra("userInfo", current_user);
            startActivity(favorite);
        } else if (id == R.id.Account) {
            Intent account = new Intent(Frappe.this, AccountPage.class);
            account.putExtra("userInfo", current_user);
            startActivity(account);
        } else if (id == R.id.Log_out) {
            Intent logOut = new Intent(Frappe.this, Home.class);
            startActivity(logOut);
        } else if (id == R.id.About_us) {
            User current_user = getIntent().getParcelableExtra("userInfo");
            Intent aboutUs = new Intent(Frappe.this, aboutUsPage.class);
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

    public interface FirebaseFavoriteCallbackCartLine {
        void onCallBack(ArrayList<favorite> favList);
    }

    public void ScaleImg(ImageView img, int pic) {
        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), pic, options);

        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth();

        if (imgWidth > screenWidth) {
            int ratio = Math.round((float) imgWidth / (float) screenWidth);
            options.inSampleSize = ratio;
        }

        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(), pic, options);
        img.setImageBitmap(scaledImg);
    }
}