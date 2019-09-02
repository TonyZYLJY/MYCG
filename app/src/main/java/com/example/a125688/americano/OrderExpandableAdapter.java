package com.example.a125688.americano;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tony on 4/13/2018.
 */

public class OrderExpandableAdapter extends BaseExpandableListAdapter {

   private Context _context;
   private ArrayList<OrderHeader> _listDataHeader; // header titles
   private HashMap<Integer, ArrayList<OrderLine>> _listDataChild;
   //DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Order");
   DecimalFormat format = new DecimalFormat("#.##");
   Double unitPrice;
   Integer storeQuantity;
   Double storedTotalAmount;
   //private ArrayList<OrderHeader> currentLine = new ArrayList<Cartline>();
   Double finalAmount;


   public OrderExpandableAdapter(Context context, ArrayList<OrderHeader> listDataHeader,
                                HashMap<Integer, ArrayList<OrderLine>> listChildData) {
      this._context = context;
      this._listDataHeader = listDataHeader;
      this._listDataChild = listChildData;
   }

   @Override
   public Object getChild(int groupPosition, int childPosititon) {

      return this._listDataChild.get(this._listDataHeader.get(groupPosition).getOrderNo()).get(childPosititon);
   }

   @Override
   public long getChildId(int groupPosition, int childPosition) {
      return childPosition;
   }

   @Override
   public View getChildView(int groupPosition, final int childPosition,
                            boolean isLastChild, View convertView, ViewGroup parent) {


      final OrderLine childLine = (OrderLine) getChild(groupPosition, childPosition);

      if (convertView == null) {
         LayoutInflater infalInflater = (LayoutInflater) this._context
                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         convertView = infalInflater.inflate(R.layout.order_lines, null);
      }

      TextView descriptionChild = (TextView) convertView
              .findViewById(R.id.description);
      TextView quantityChild = (TextView) convertView
              .findViewById(R.id.quantity);
      TextView sizeChild = (TextView) convertView
              .findViewById(R.id.size);
      TextView priceChild = (TextView) convertView
              .findViewById(R.id.price);

      descriptionChild.setText(childLine.getDescription());
      quantityChild.setText(childLine.getQuantity().toString());
      sizeChild.setText(childLine.getSize());
      priceChild.setText(String.format("%.2f", childLine.getPrice()));
      return convertView;
   }

   @Override
   public int getChildrenCount(int groupPosition) {
      return this._listDataChild.get(this._listDataHeader.get(groupPosition).getOrderNo())
              .size();
   }

   @Override
   public Object getGroup(int groupPosition) {
      return this._listDataHeader.get(groupPosition);
   }

   @Override
   public int getGroupCount() {
      return this._listDataHeader.size();
   }

   @Override
   public long getGroupId(int groupPosition) {
      return groupPosition;
   }

   @Override
   public View getGroupView(final int groupPosition, boolean isExpanded,
                            View convertView, final ViewGroup parent) {
      final OrderHeader headerTitle = (OrderHeader) getGroup(groupPosition);
      //currentLine = _listDataHeader;

      if (convertView == null) {
         LayoutInflater infalInflater = (LayoutInflater) this._context
                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         convertView = infalInflater.inflate(R.layout.order_header, null);
      }

      TextView description = (TextView) convertView
              .findViewById(R.id.orderDescription);
      TextView orderPrice = (TextView) convertView
              .findViewById(R.id.priceOrder);


      description.setTypeface(null, Typeface.BOLD);
      description.setText("Order No. " + headerTitle.getOrderNo().toString());
      orderPrice.setTypeface(null,Typeface.BOLD);
      orderPrice.setText(String.format("%.2f",headerTitle.getOrderTotalAmount()));

      return convertView;
   }

   @Override
   public boolean hasStableIds() {
      return false;
   }

   @Override
   public boolean isChildSelectable(int groupPosition, int childPosition) {
      return true;
   }

}
