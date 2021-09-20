package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class Account_Details extends AppCompatActivity {
    String username;
    String accno;
    String phoneno;
    int balance;
    String IFCcode;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_home_24);
        Intent intent = getIntent();
        user present = (user)intent.getSerializableExtra("uservalue");

        TextView uname=findViewById(R.id.accname);
        TextView accno_view=findViewById(R.id.accno);
        TextView phoneno_view=findViewById(R.id.contact_number);
        TextView IFCcode_view=findViewById(R.id.IFC);
        TextView balance_view=findViewById(R.id.balance);
        username=present.getName().toString();
        accno=present.getAccountno();
        phoneno=present.getPhoneno();
        balance=present.getBalance();
        IFCcode=present.getIFCcode();
        balance_view.setText(balance+" ");
        IFCcode_view.setText(IFCcode);
        accno_view.setText(accno);
        phoneno_view.setText(phoneno);
        uname.setText(present.getName());
    }

    public void transact(View view){
        /*transaction_popup cp=new transaction_popup();
        cp.show(getSupportFragmentManager(), "this");*/
        BottomFragment bottomFragment= new BottomFragment(username,balance,accno);
        bottomFragment.show(getSupportFragmentManager(), bottomFragment.getTag());
    }

    public void view_history(View view){
        Intent i = new Intent(getApplicationContext(),Transaction_history.class);
        i.putExtra("source",username);
        startActivity(i);
    }
}