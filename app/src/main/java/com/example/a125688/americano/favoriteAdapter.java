package com.example.a125688.americano;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by SaraChiraDiaz on 2/23/2018.
 */

public class favoriteAdapter extends ArrayAdapter<favorite> {
   private Context mContext;
   private ArrayList<favorite> listFavorites;
   private User currUser;
   DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Favorites");
   public favoriteAdapter(Context context, ArrayList<favorite> favorites, User current_user) {
      super(context, R.layout.favorite_item, favorites);
      this.mContext  = context;
      this.listFavorites = favorites;
      this.currUser = current_user;
   }

   @Override
   public int getCount() {
      return listFavorites.size();
   }

   @Override
   public View getView(final int position, View convertView, ViewGroup parent) {
      final ViewHolder holder;

      if (convertView == null) {
         LayoutInflater layoutInflator = LayoutInflater.from(mContext);
         convertView = layoutInflator.inflate(R.layout.favorite_item, null);
         holder = new ViewHolder();
         holder.mTextView = (TextView) convertView
                 .findViewById(R.id.itemDescription);
         holder.maddCart = (Button) convertView
                 .findViewById(R.id.cartButton);
         holder.mdelete = (Button) convertView.findViewById(R.id.deleteButton);
         convertView.setTag(holder);
      } else {
         holder = (ViewHolder) convertView.getTag();
      }

      holder.mTextView.setText(listFavorites.get(position).getDescription());

      holder.mdelete.setTag(position);
      holder.mdelete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            dbRef.child(listFavorites.get(position).getKey()).removeValue();
         }
      });

      holder.maddCart.setTag(position);
      holder.maddCart.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if((listFavorites.get(position).getProductNo() > 20) || (listFavorites.get(position).getProductNo() < 30)){
               Intent smoothie = new Intent(mContext, SmoothiePage.class);
               smoothie.putExtra("userInfo",currUser);
               mContext.startActivity(smoothie);
            }
         }
      });

      // To check weather checked event fire from getview() or user input
      return convertView;
   }

   private class ViewHolder {
      private TextView mTextView;
      private Button maddCart;
      private Button mdelete;
   }

}
