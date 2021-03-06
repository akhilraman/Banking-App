package com.example.sqlite;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomFragment extends BottomSheetDialogFragment {
    String account_holder;
    int balance;
    String reciever_accno;
    String sender_accno;
    int reciever_balance;
    ArrayList spinnerList=new ArrayList();
    public BottomFragment(String username,int balance,String sender_accno){
         account_holder=username;
         this.balance=balance;
         this.sender_accno=sender_accno;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BottomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomFragment newInstance(String param1, String param2) {
        BottomFragment fragment = new BottomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        //ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        ArrayAdapter<user> spinnerAdapter = new ArrayAdapter<user>(getContext(), android.R.layout.simple_spinner_item, spinnerList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerList.add(new user("Select a user","xyx",000,"yyy","12345","xyz"));
        SQLiteDatabase myDatabase=getActivity().openOrCreateDatabase("user",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS user (name VARCHAR,accountno VARCHAR ,balance INT(25),IFCcode VARCHAR,phoneno VARCHAR,email VARCHAR)");
        try (Cursor c = myDatabase.rawQuery("SELECT * FROM user WHERE name != '" + account_holder + "'", null)) {
            int nameIndex=c.getColumnIndex("name");
            int accnoIndex=c.getColumnIndex("accountno");
            int balanceIndex=c.getColumnIndex("balance");
            int IFCcodeIndex=c.getColumnIndex("IFCcode");
            int phonenoIndex=c.getColumnIndex("phoneno");
            int emailIndex=c.getColumnIndex("email");
            c.moveToFirst();
            while(!c.isAfterLast()){

                spinnerList.add(new user(c.getString(nameIndex),c.getString(accnoIndex),c.getInt(balanceIndex),c.getString(IFCcodeIndex),c.getString(phonenoIndex),c.getString(emailIndex)));

                //spinnerAdapter.add(c.getString(nameIndex));
                c.moveToNext();
            }
        }
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user u = (user) parent.getSelectedItem();
                reciever_accno=u.getAccountno();
                reciever_balance=u.getBalance();
                displayUserData(u);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        EditText amount= (EditText)view.findViewById(R.id.amount);

        //String reciever=spinner.getSelectedItem().toString();
        Button transfer= view.findViewById(R.id.transfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reciever=spinner.getSelectedItem().toString();
                int amount_value=Integer.parseInt(amount.getText().toString());
                if(amount_value>balance){
                    amount.requestFocus();
                    amount.setError("Check your Balance");
                }
                else if(reciever.equals("Select a user")){
                    amount.requestFocus();
                    amount.setError("Select a user");
                }

                else {
                    int remaining_balance = balance - amount_value;
                    int new_rec_balance=reciever_balance+amount_value;
                    Random random = new Random();
                    String transaction_id = "SPR" + random.nextInt(500) + account_holder.charAt(0) + reciever.charAt(0);
                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Transaction Confirmation")
                            .setMessage("Are you sure about this transaction")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase myDatabase = getActivity().openOrCreateDatabase("user", MODE_PRIVATE, null);

                                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS trans (sender VARCHAR, reciever VARCHAR,amount INT(3),status VARCHAR,date VARCHAR,transaction_id VARCHAR,sender_accno VARCHAR , reciever_accno VARCHAR)");
                                    myDatabase.execSQL("INSERT INTO trans VALUES ('" + account_holder + "','" + reciever + "','" + amount_value + "','Success',datetime(),'" + transaction_id + "','" + sender_accno + "','" + reciever_accno + "')");

                                    myDatabase.execSQL("UPDATE user SET balance="+ remaining_balance + " WHERE name='" + account_holder + "'");
                                    myDatabase.execSQL("UPDATE user SET balance=" + new_rec_balance + " WHERE name='" + reciever + "'");

                                    Toast.makeText(getContext(), "Transfer Success!", Toast.LENGTH_SHORT).show();


                                }
                            })
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase myDatabase = getActivity().openOrCreateDatabase("user", MODE_PRIVATE, null);

                                    myDatabase.execSQL("CREATE TABLE IF NOT EXISTS trans (sender VARCHAR, reciever VARCHAR,amount INT(3),status VARCHAR,date VARCHAR,transaction_id VARCHAR,sender_accno VARCHAR , reciever_accno VARCHAR)");
                                    myDatabase.execSQL("INSERT INTO trans VALUES ('" + account_holder + "','" + reciever + "','" + amount_value + "','Failed',datetime(),'" + transaction_id + "','" + sender_accno + "','" + reciever_accno + "')");
                                    Toast.makeText(getContext(), "Transfer Failed", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }}

        });


    }

    /*public void getSelectedUser(View v) {
        user user = (user) spinner.getSelectedItem();
        displayUserData(user);
    }*/
    private void displayUserData(user u) {
        String name = u.getName();
        String userData = "Name: " + name;
    }

}