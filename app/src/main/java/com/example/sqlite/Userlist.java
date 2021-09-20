package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Userlist extends AppCompatActivity {

    ListView listview;
    ArrayList arrayList;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_home_24);
        setContentView(R.layout.activity_main);
        listview=findViewById(R.id.listview);
        arrayList = new ArrayList<user>();
        CustomRow adapter;

        SQLiteDatabase myDatabase=this.openOrCreateDatabase("user",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS user (name VARCHAR,accountno VARCHAR ,balance INT(25),IFCcode VARCHAR,phoneno VARCHAR,email VARCHAR)");
        //myDatabase.execSQL("CREATE TABLE IF NOT EXISTS trans (sender VARCHAR, reciever VARCHAR,amount INT(3),status VARCHAR)");
        //myDatabase.execSQL("INSERT INTO user VALUES ('Akhil ','SFB200AR',20000,'5676','8939158281','akhilraman@gmail.com')");
        /*myDatabase.execSQL("INSERT INTO user VALUES ('S Raman ','SFB300SR',19000,'5634','9490470808','sraman@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Shreya ','SFB200SHR',34000,'5611','8939158345','shreyaraman@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Revanth ','SFB234RR',56000,'5233','95250233554 ','revanth@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Prajapath ','SFB200KP',20000,'5111','95250233555','prajakomm@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Rithik ','SFB200RIR',25000,'6454','8939158282','rithik@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Danish ','SFB250DS',90000,'2345','8939158299','Danish@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Pritham ','SFB450PP',20000,'7890','9839158281','pritham@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Jagan ','SFB200JR',50000,'5555','8939158333','jagan@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Priya ','SFB400PR',23000,'5999','9389158281','priya@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Ravi ','SFB600RD',90000,'5988','9932158281','ravi123@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Mohan ','SFB340MR',20000,'5992','8939158289','Mohan@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Bhanu ','SFB100BP',40000,'2343','9939158281','bhanu123@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Kalyan ','SFB200KK',20000,'1676','7939158281','kalyan@gmail.com')");
        myDatabase.execSQL("INSERT INTO user VALUES ('Anu ','SFB200AA',50000,'9676','7739158281','anu@gmail.com')");*/
        //myDatabase.execSQL("DELETE FROM user WHERE name='Akhil '");
        //myDatabase.execSQL("INSERT INTO user VALUES ('Akhil ','SFB200AR',20000,'5676','8939158281','akhilraman@gmail.com')");
        try (Cursor c = myDatabase.rawQuery("SELECT * FROM user", null)) {
            int nameIndex=c.getColumnIndex("name");
            int accnoIndex=c.getColumnIndex("accountno");
            int balanceIndex=c.getColumnIndex("balance");
            int IFCcodeIndex=c.getColumnIndex("IFCcode");
            int phonenoIndex=c.getColumnIndex("phoneno");
            int emailIndex=c.getColumnIndex("email");
            c.moveToFirst();
            while(!c.isAfterLast()){
                //Toast.makeText(this, c.getString(nameIndex), Toast.LENGTH_SHORT).show();

                arrayList.add(new user(c.getString(nameIndex),c.getString(accnoIndex),c.getInt(balanceIndex),c.getString(IFCcodeIndex),c.getString(phonenoIndex),c.getString(emailIndex)));
                adapter = new CustomRow(this, arrayList);
                listview.setAdapter(adapter);
                c.moveToNext();
            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                user present = (user)arrayList.get(position);
                Intent i = new Intent(getApplicationContext(),Account_Details.class);
                i.putExtra("uservalue",present);
                startActivity(i);
            }
        });

    }
}