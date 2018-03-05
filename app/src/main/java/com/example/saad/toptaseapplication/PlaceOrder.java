package com.example.saad.toptaseapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.saad.toptaseapplication.Adapter_receipt.arrItemsName;
import static com.example.saad.toptaseapplication.Adapter_receipt.arrItemsQuantity;

public class PlaceOrder extends AppCompatActivity {

    private RadioGroup rgRadio;
    private RadioButton rbDelivery, rbCollection;
    private EditText etDeliverAddress;
    private Button btnDone;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference OrdersRef;
    FirebaseAuth firebaseAuth;
    TopTasteApplication cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        firebaseAuth= FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        OrdersRef = FirebaseDatabase.getInstance().getReference();
        OrdersRef=OrdersRef.child("Orders_HomeDelivery");

        cart= (TopTasteApplication) getApplicationContext();
//        ItemsArray = b.getStringArrayList("ItemsArray");

        rgRadio = (RadioGroup)findViewById(R.id.radioCondition);
        rbDelivery = (RadioButton)findViewById(R.id.radioDelivery);
        rbCollection = (RadioButton)findViewById(R.id.radioCollection);
        etDeliverAddress = (EditText)findViewById(R.id.etDeliverAddress);
        btnDone = (Button)findViewById(R.id.btnDone);

        rgRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if((RadioButton)findViewById(checkedId)==rbDelivery){
                        etDeliverAddress.setText("");
                        etDeliverAddress.setVisibility(View.VISIBLE);
                    }
                    else{
                        etDeliverAddress.setVisibility(View.GONE);
                    }
               }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser!=null) {
                    Date currentTime = Calendar.getInstance().getTime();


                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ItemsOrdered", CreateStringforItems());
                    if(rbCollection.isChecked())
                        map.put("Location", "Sample Location");
                    else
                        map.put("Location", etDeliverAddress.getText().toString());
                    map.put("Name", "John Doe");
                    map.put("Phone Number", "03001234567");
                    map.put("Time", currentTime.toString());
                    String key = OrdersRef.push().getKey();

                    OrdersRef.child(key).setValue(map);

                    cart.getItems().clear();
                    cart.getPrices().clear();

                    Toast.makeText(getApplicationContext(),"Order Placed",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PlaceOrder.this,NavigationDrawer.class);
                    startActivity(i);
                    btnDone.setEnabled(false);
                }
                else {
                    Bundle b= new Bundle();
                    b.putString("From","PlaceOrder");
                    Intent i = new Intent(PlaceOrder.this, Login.class);
                    i.putExtras(b);
                    startActivity(i);
                }
            }
        });
    }
    private String CreateStringforItems()
    {
        String StringforItems="";

        for ( int c=0;c<arrItemsName.length;c++)
        {
            StringforItems=StringforItems+arrItemsQuantity[c] + "x " +
                    arrItemsName[c]+", ";
        }
        return StringforItems;
    }
}
