package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Transaction_history extends AppCompatActivity {
    ListView transaction_listview;
    ArrayList transaction_arrayList;

    CustomTransactionRow adapter;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_home_24);
        Intent intent=getIntent();
        String source=intent.getStringExtra("source");
        Spinner Sort_spinner=findViewById(R.id.sort_spinner);


        TextView heading=findViewById(R.id.transaction_history);
        if(!source.equals("common")){
            heading.setText(source+"'s Transaction History");
        }
        transaction_listview=findViewById(R.id.transactionlist);
        transaction_arrayList = new ArrayList<Transactions>();

        String query="";
        if(source.equals("common")){
            query="SELECT * FROM trans ";
        }
        else{
            query="SELECT * FROM trans WHERE sender='"+source+"' or reciever='"+source+"' ";
        }
        sql_database_call(query+"ORDER BY date DESC");

        Sort_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String order_by="";
                String new_query="";
                if(source.equals("common")){
                    new_query="SELECT * FROM trans ";
                }
                else{
                    new_query="SELECT * FROM trans WHERE sender='"+source+"' or reciever='"+source+"' ";
                }
                if(parent.getSelectedItem().toString().equals("Most Recent")){
                    order_by="ORDER BY date DESC";
                }
                else if(parent.getSelectedItem().toString().equals("Oldest")){
                    order_by=" ORDER BY date ASC";
                }
                else{
                    order_by=" ORDER BY amount DESC";
                }
                //query=query+order_by;
                sql_database_call(new_query+order_by);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        transaction_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Transactions present = (Transactions) transaction_arrayList.get(position);
                TransactionDetailsFragment transactionDetailsFragment= new TransactionDetailsFragment(present);
                transactionDetailsFragment.show(getSupportFragmentManager(), transactionDetailsFragment.getTag());
            }
        });
        ImageView animated_man=findViewById(R.id.animate_man);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.lefttoright);
        animated_man.startAnimation(animation);
    }

    public void sql_database_call(String query){
        transaction_arrayList.clear();
        SQLiteDatabase myDatabase=this.openOrCreateDatabase("user",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS trans (sender VARCHAR, reciever VARCHAR,amount INT(3),status VARCHAR,date VARCHAR,transaction_id VARCHAR,sender_accno VARCHAR,reciever_accno VARCHAR)");
        try (Cursor c = myDatabase.rawQuery(query, null)) {
            int sendernameIndex=c.getColumnIndex("sender");
            int recievernameIndex=c.getColumnIndex("reciever");
            int statusIndex=c.getColumnIndex("status");
            int amountIndex=c.getColumnIndex("amount");
            int dateIndex=c.getColumnIndex("date");
            int transactionIndex=c.getColumnIndex("transaction_id");
            int sender_accnoIndex=c.getColumnIndex("sender_accno");
            int reciever_accnoIndex=c.getColumnIndex("reciever_accno");
            c.moveToFirst();
            while(!c.isAfterLast()){
                transaction_arrayList.add(new Transactions(c.getString(sendernameIndex),c.getString(recievernameIndex),c.getString(statusIndex),c.getInt(amountIndex),c.getString(dateIndex),c.getString(transactionIndex),c.getString(sender_accnoIndex),c.getString(reciever_accnoIndex)));
                adapter = new CustomTransactionRow(this, transaction_arrayList);
                transaction_listview.setAdapter(adapter);
                c.moveToNext();
            }
        }
    }
}