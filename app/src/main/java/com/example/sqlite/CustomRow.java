package com.example.sqlite;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CustomRow extends ArrayAdapter<user> {
    int likes;
    List<String> likedby;
    public CustomRow (Context context, ArrayList<user> arrayList){
        super(context,0,arrayList);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View  currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.row_item, parent, false);
        }
        TextView username=currentItemView.findViewById(R.id.name);
        TextView accno=currentItemView.findViewById(R.id.accno_rowitem);


        // get the position of the view from the ArrayAdapter
        user currentNumberPosition = getItem(position);


        assert currentNumberPosition != null;

        username.setText(currentNumberPosition.getName());
        accno.setText(currentNumberPosition.getAccountno());
        //age.setText(currentNumberPosition.getAge());

        Button transact_button=currentItemView.findViewById(R.id.transfer_button);
        transact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomFragment bottomFragment= new BottomFragment(currentNumberPosition.getName(),currentNumberPosition.getBalance(),currentNumberPosition.getAccountno());
                bottomFragment.show(((FragmentActivity)getContext()).getSupportFragmentManager(), bottomFragment.getTag());
            }
        });

        return currentItemView;
    }
}

