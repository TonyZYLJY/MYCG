package com.example.a125688.americano;
import android.content.ComponentCallbacks2;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
//import net.sourceforge.jtds.jdbc.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity implements ComponentCallbacks2{

    SharedPreferences settings;
    final Context context = this;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    Query query;
    ValueEventListener queryListener;

    //databaseRef.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       System.runFinalization();
       Runtime.getRuntime().gc();
       System.gc();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }
    @Override
    protected  void onStart(){
        log_in();
        register();
        super.onStart();
    }
    @Override
    protected void onStop(){
       if(queryListener != null)
        query.removeEventListener(queryListener);
       query = null;
       queryListener = null;
       databaseRef = null;
       super.onStop();
       System.gc();

    }

    public void log_in() {
        Button login_button = (Button) findViewById(R.id.login_ID);
        final Context context = this;
        login_button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                EditText user = (EditText) findViewById(R.id.user_nameID);
                                                //String user_name = user.getText().toString();
                                                EditText pass = (EditText) findViewById(R.id.passwordID);
                                                //String password = pass.getText().toString();
                                                //current_user.setPassword(pass.getText().toString());
                                                User current_user= new User();
                                                current_user.setUserId(user.getText().toString());
                                                current_user.setPassword(pass.getText().toString());//new User(user.getText().toString(),pass.getText().toString());

                                                if (!(current_user.getPassword().equals("")) && !(current_user.getUserId().equals(""))) {
                                                    validateUser(current_user.getUserId(), current_user.getPassword());
                                                }
                                                else{
                                                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);

                                                    dlgAlert.setMessage("Password or Username cannot be empty");
                                                    dlgAlert.setTitle("Error Message...");
                                                    dlgAlert.setPositiveButton("OK", null);
                                                    dlgAlert.setCancelable(true);
                                                    dlgAlert.create().show();

                                                    dlgAlert.setPositiveButton("Ok",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                }
                                                            });
                                                    user.setText("");
                                                    pass.setText("");
                                                }
                                            }
                                        }
        );
    }

    public void register() {
        Button register_button = (Button) findViewById(R.id.guest_button_id);
        register_button.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   User current_user = new User("Guest","","001",0.0,0);
                                                   Intent menu = new Intent(Home.this, com.example.a125688.americano.Menu.class);
                                                   menu.putExtra("userInfo", current_user);
                                                   startActivity(menu);
                                                   //startActivity(new Intent(Home.this, Menu.class));

                                               }
                                           }
        );
    }
    private void  validateUser(String user_name, final String password) {
        query = databaseRef.child("User").orderByChild("userId").equalTo(user_name);
        // User usernew =query.getClass()
        queryListener = query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Define text boxes and error box
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                EditText user = (EditText) findViewById(R.id.user_nameID);
                EditText pass = (EditText) findViewById(R.id.passwordID);

                // Checking if record is null or not
                if(dataSnapshot.getValue() != null) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        // Saving second into current_user variable
                        User current_user = data.getValue(User.class);
                        // Checking for password
                        if (current_user.getPassword().equals(password)) {
                            //Moving to next page
                            Intent menu = new Intent(Home.this, com.example.a125688.americano.Menu.class);
                            menu.putExtra("userInfo", current_user);
                            startActivity(menu);
                        }
                        else {
                            // Showing error if wrong password
                            dlgAlert.setMessage("Wrong password or username");
                            dlgAlert.setTitle("Error Message...");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();

                            dlgAlert.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                            user.setText("");
                            pass.setText("");
                        }
                    }
                }else{
                    // Show error message if no record found
                    dlgAlert.setMessage("Wrong password or username");
                    dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    user.setText("");
                    pass.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

   public void onTrimMemory(int level) {

      // Determine which lifecycle or system event was raised.
      switch (level) {

         case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:


/*                   Release any UI objects that currently hold memory.

                   The user interface has moved to the background.*/


            break;

         case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
         case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
         case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:


               /*    Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.*/


            break;

         case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
         case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
         case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:


   /*                Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.*/


            break;

         default:

               /*   Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.*/

            break;
      }
   }
}
