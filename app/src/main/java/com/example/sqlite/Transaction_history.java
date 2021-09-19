package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Transaction_history extends AppCompatActivity {
    ListView transaction_listview;
    ArrayList transaction_arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        transaction_listview=findViewById(R.id.transactionlist);
        transaction_arrayList = new ArrayList<Transactions>();
        CustomTransactionRow adapter;
        SQLiteDatabase myDatabase=this.openOrCreateDatabase("user",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS trans (sender VARCHAR, reciever VARCHAR,amount INT(3),status VARCHAR)");
        try (Cursor c = myDatabase.rawQuery("SELECT * FROM trans", null)) {
            int sendernameIndex=c.getColumnIndex("sender");
            int recievernameIndex=c.getColumnIndex("reciever");
            int statusIndex=c.getColumnIndex("status");
            c.moveToFirst();
            while(!c.isAfterLast()){
                //Toast.makeText(this, c.getString(nameIndex), Toast.LENGTH_SHORT).show();

                transaction_arrayList.add(new Transactions(c.getString(sendernameIndex),c.getString(recievernameIndex),c.getString(statusIndex)));
                adapter = new CustomTransactionRow(this, transaction_arrayList);
                transaction_listview.setAdapter(adapter);
                c.moveToNext();
            }
        }
        transaction_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Transactions present = (Transactions) transaction_arrayList.get(position);
                //Toast.makeText(getApplicationContext(), present.getName(), Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(getApplicationContext(),Account_Details.class);
                i.putExtra("TransactionDetails",present);
                startActivity(i);*/
                TransactionDetailsFragment transactionDetailsFragment= new TransactionDetailsFragment(present);
                transactionDetailsFragment.show(getSupportFragmentManager(), transactionDetailsFragment.getTag());
            }
        });
    }
}