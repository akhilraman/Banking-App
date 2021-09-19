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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomFragment extends BottomSheetDialogFragment {
    String account_holder;
    public BottomFragment(String username){
         account_holder=username;
    }
String amount_value;
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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.add("Select a user");
        SQLiteDatabase myDatabase=getActivity().openOrCreateDatabase("user",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS user (name VARCHAR,accountno VARCHAR ,balance INT(25),IFCcode VARCHAR,phoneno VARCHAR,email VARCHAR)");
        try (Cursor c = myDatabase.rawQuery("SELECT * FROM user", null)) {
            int nameIndex=c.getColumnIndex("name");
            c.moveToFirst();
            while(!c.isAfterLast()){
                //Toast.makeText(this, c.getString(nameIndex), Toast.LENGTH_SHORT).show();

                Log.i("name",c.getString(nameIndex));
                /*arrayList.add(new user(c.getString(nameIndex),c.getInt(ageIndex)));
                adapter = new CustomRow(this, arrayList);
                listview.setAdapter(adapter);*/
                spinnerAdapter.add(c.getString(nameIndex));
                c.moveToNext();
            }
        }
        spinnerAdapter.notifyDataSetChanged();

        EditText amount= (EditText)view.findViewById(R.id.amount);

        //String reciever=spinner.getSelectedItem().toString();
        Button transfer= view.findViewById(R.id.transfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reciever=spinner.getSelectedItem().toString();
                int amount_value=Integer.parseInt(amount.getText().toString());
                Toast.makeText(getContext(), amount_value+" "+reciever+" "+account_holder, Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Transaction Confirmation")
                        .setMessage("Are you sure about this transaction")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"Transaction complete", Toast.LENGTH_SHORT).show();
                                SQLiteDatabase myDatabase=getActivity().openOrCreateDatabase("user",MODE_PRIVATE,null);
                                myDatabase.execSQL("CREATE TABLE IF NOT EXISTS trans (sender VARCHAR, reciever VARCHAR,amount INT(3),status VARCHAR)");
                                myDatabase.execSQL("INSERT INTO trans VALUES ('"+account_holder+"','"+reciever+"','"+amount_value+"','Success')");


                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase myDatabase=getActivity().openOrCreateDatabase("user",MODE_PRIVATE,null);
                                myDatabase.execSQL("CREATE TABLE IF NOT EXISTS trans (sender VARCHAR, reciever VARCHAR,amount INT(3),status VARCHAR)");
                                myDatabase.execSQL("INSERT INTO trans VALUES ('"+account_holder+"','"+reciever+"','"+amount_value+"','Failed')");
                            }
                        })
                        .show();
            }
        });


    }
}