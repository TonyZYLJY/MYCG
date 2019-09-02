package com.example.a125688.americano;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import android.widget.BaseExpandableListAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by SaraChiraDiaz on 2/23/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter{

    private Context _context;
    private ArrayList<Cartline> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Integer, ArrayList<Cartline>> _listDataChild;
   DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("CartProducts");
    DecimalFormat format = new DecimalFormat("#.##");
    Double unitPrice;
    Integer storeQuantity;
    Double storedTotalAmount;
    private ArrayList<Cartline> currentLine = new ArrayList<Cartline>();
    Double finalAmount;


    public ExpandableListAdapter(Context context, ArrayList<Cartline> listDataHeader,
                                 HashMap<Integer, ArrayList<Cartline>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getLineNo()).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        final Cartline childText = (Cartline) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.cart_lines, null);
        }

        TextView descriptionChild = (TextView) convertView
                .findViewById(R.id.lineDescription);

        descriptionChild.setText(childText.getDescription());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).getLineNo())
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
        final Cartline headerTitle = (Cartline) getGroup(groupPosition);
        currentLine = _listDataHeader;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.cart_item, null);
        }

        TextView description = (TextView) convertView
                .findViewById(R.id.itemDescription);
        final TextView quantity = (TextView) convertView
                .findViewById(R.id.quantityVH);
        TextView size = (TextView) convertView
                .findViewById(R.id.sizeVH);
        final TextView price = (TextView) convertView.findViewById(R.id.priceVH);
        Button plusSign = (Button) convertView.findViewById(R.id.plusSignButton);
        Button minusSign = (Button) convertView.findViewById(R.id.minusSignButton);

        description.setTypeface(null, Typeface.BOLD);
        description.setText(headerTitle.getDescription().toString());
        quantity.setTypeface(null, Typeface.BOLD);
        quantity.setText("" + headerTitle.getQuantity().toString());
        size.setTypeface(null, Typeface.BOLD);
        size.setText(headerTitle.getSize().toString());
        price.setTypeface(null,Typeface.BOLD);
        price.setText(String.format("%.2f",headerTitle.getPrice()));

        plusSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cartline product = currentLine.get(groupPosition);
                unitPrice = product.getPrice()/(double)product.getQuantity();
                storeQuantity = product.getQuantity();
                storeQuantity = storeQuantity + 1;
                storedTotalAmount = unitPrice * storeQuantity;
                if(storeQuantity <= 10) {
                    product.setPrice(storedTotalAmount);
                    product.setQuantity(storeQuantity);
                    quantity.setText("" + storeQuantity);
                    price.setText(String.format("%.2f", storedTotalAmount));
                   String Key  = _listDataHeader.get(groupPosition).getKey();
                   Map<String, Object> childUpdates = new HashMap<>();
                   childUpdates.put("price" , storedTotalAmount);
                   childUpdates.put("quantity", storeQuantity);
                   dbRef.child(Key).updateChildren(childUpdates);
                    _listDataHeader.clear();
                    notifyDataSetInvalidated();
                   notifyDataSetChanged();

                }
            }
        });
        minusSign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cartline lessProduct = currentLine.get(groupPosition);
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(_context);
                unitPrice = lessProduct.getPrice()/(double)lessProduct.getQuantity();
                storeQuantity = lessProduct.getQuantity();
                storeQuantity = storeQuantity - 1;
                storedTotalAmount = unitPrice * storeQuantity;
                currentLine.set(groupPosition, lessProduct);
                if(storeQuantity > 0 ) {
                    // get key value for current user's password and update the password
                    lessProduct.setQuantity(storeQuantity);
                    lessProduct.setPrice(storedTotalAmount);
                    quantity.setText("" + storeQuantity);
                    price.setText(String.format("%.2f", storedTotalAmount));
                   String Key  = _listDataHeader.get(groupPosition).getKey();
                   Map<String, Object> childUpdates = new HashMap<>();
                   childUpdates.put("price" , storedTotalAmount);
                   childUpdates.put("quantity", storeQuantity);
                   dbRef.child(Key).updateChildren(childUpdates);
                   _listDataHeader.clear();
                   notifyDataSetInvalidated();
                   notifyDataSetChanged();
                }
                if(storeQuantity == 0) {
                   if (!_listDataHeader.isEmpty()) {
                      String Key;
                      Key  = _listDataHeader.get(groupPosition).getKey();
                      dbRef.child(Key).removeValue();
                      _listDataHeader.clear();
                      notifyDataSetChanged();
                   }
                }
            }
        });


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

    public Double calculateTotalAmount()
    {
        Double totalAmount = 0.0;
        for(Integer index = 0; index < _listDataHeader.size(); index++)
        {
            totalAmount = totalAmount + _listDataHeader.get(index).getPrice();
        }
        return totalAmount;
    }

}
