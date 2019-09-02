package com.example.a125688.americano;
import android.app.AlertDialog;
import android.content.Context;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by SaraChiraDiaz on 2/23/2018.
 */

public class MyAdapter extends ArrayAdapter<StateVO> {
    private Context mContext;
    private ArrayList<StateVO> listState;
    private MyAdapter myAdapter;
    private boolean isFromView = false;
    private Integer counter = 0;
    private ArrayList<Boolean> checked = new ArrayList<Boolean>();


    public MyAdapter(Context context, int resource, List<StateVO> objects) {
        super(context, resource, objects);
        this.mContext  = context;
        this.listState = (ArrayList<StateVO>) objects;
        this.myAdapter = this;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return listState.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (checked.size() == 0)
        {
            for (int i = 0; i < this.getCount(); i++) {
                checked.add(i, false);
            }
        }

        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView,
                              ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(mContext);
            convertView = layoutInflator.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.mTextView = (TextView) convertView
                    .findViewById(R.id.text);
            holder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int getPosition = (Integer) buttonView.getTag();

                if (isChecked && counter >= 5) {
                    holder.mCheckBox.setChecked(false);
                    listState.get(getPosition).setSelected(false);
                } else {
                    if (isChecked) {
                        counter++;
                        listState.get(getPosition).setSelected(true);
                        holder.mCheckBox.setChecked(true);
                        checked.set(getPosition,true);
                    } else {
                        counter--;
                        listState.get(getPosition).setSelected(false);
                        holder.mCheckBox.setChecked(false);
                        checked.set(getPosition, false);
                    }
                }
            }
        });

        holder.mTextView.setText(listState.get(position).getTitle());

        // To check weather checked event fire from getview() or user input
        isFromView = true;
        holder.mCheckBox.setChecked(checked.get(position));
        isFromView = false;

        if ((position == 0)) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }

        return convertView;
    }
    private class ViewHolder {
        private TextView mTextView;
        private CheckBox mCheckBox;
    }

    public ArrayList<Boolean> getModifyList() {
        return checked;
    }
}