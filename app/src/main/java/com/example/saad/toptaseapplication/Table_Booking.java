package com.example.saad.toptaseapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Table_Booking extends AppCompatActivity {

    Button btn_BookTable;
    EditText txt_numberOfPeople,txt_estimatedArrivalTime;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference OrdersRef;
    Boolean family=false;
    TopTasteApplication globalVar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table__booking);

        globalVar= (TopTasteApplication) getApplicationContext();

        btn_BookTable= findViewById(R.id.btn_Book);
        txt_numberOfPeople=findViewById(R.id.txt_numberOfPeople);
        txt_estimatedArrivalTime = findViewById(R.id.txt_estimatedArrivalTime);

        firebaseDatabase = FirebaseDatabase.getInstance();
        OrdersRef = FirebaseDatabase.getInstance().getReference();
        OrdersRef=OrdersRef.child("Table_Bookings");

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton:
                        family=false;
                        break;
                    case R.id.radioButton2:
                        family=true;
                        break;
                }
            }
        });

        btn_BookTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String numberOfPeople=txt_numberOfPeople.getText().toString();
                String estimateArrivalTime=txt_estimatedArrivalTime.getText().toString();

                Date currentTime = Calendar.getInstance().getTime();

                if(TextUtils.isEmpty(numberOfPeople))
                {
                    Toast.makeText(Table_Booking.this, "Enter Number of People!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String key=OrdersRef.push().getKey();


                HashMap<String, String> map = new HashMap<String, String>();
                map.put("Family",family.toString());
                map.put("EstimatedArrivalTime",numberOfPeople);
                map.put("NumberOfPeople",numberOfPeople);
                map.put("TimeofBooking", currentTime.toString());
                map.put("Name", globalVar.getUser().get("Name"));
                map.put("Number", globalVar.getUser().get("Number"));

                OrdersRef.child(key).setValue(map);




            }
        });
    }
}
