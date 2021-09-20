package com.example.sqlite;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomTransactionRow extends ArrayAdapter<Transactions> {
    int likes;
    List<String> likedby;
    public CustomTransactionRow(Context context, ArrayList<Transactions> arrayList){
        super(context,0,arrayList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View  currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_row, parent, false);
        }
        TextView sender_name=currentItemView.findViewById(R.id.Sender_name);
        TextView sender_accno=currentItemView.findViewById(R.id.Sender_accno);
        TextView reciever_name=currentItemView.findViewById(R.id.Reciever_name);
        TextView reciever_accno=currentItemView.findViewById(R.id.Reciever_accno);
        TextView status=currentItemView.findViewById(R.id.success_fail);
        TextView amount=currentItemView.findViewById(R.id.amount_view);
        // get the position of the view from the ArrayAdapter
        Transactions currentNumberPosition = getItem(position);


        assert currentNumberPosition != null;

        //username.setText(currentNumberPosition.getName());
        sender_name.setText(currentNumberPosition.getSender_name());
        reciever_name.setText(currentNumberPosition.getReceiver_name());
        sender_accno.setText(currentNumberPosition.getSender_accno());
        reciever_accno.setText(currentNumberPosition.getReceiver_accno());
        status.setText(currentNumberPosition.getStatus());
        String amount_value=String.valueOf(currentNumberPosition.getAmount());
        amount.setText(amount_value);

        //age.setText(currentNumberPosition.getAge());

        return currentItemView;
    }
}
