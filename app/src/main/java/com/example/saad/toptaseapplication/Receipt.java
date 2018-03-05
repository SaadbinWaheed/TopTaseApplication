package com.example.saad.toptaseapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Receipt extends AppCompatActivity {

    String Passed_Item;
    ListView list;
    Adapter_receipt adapter;
    public  Receipt ReceiptListView = null;
    public ArrayList<Listview_receipt> ReceiptListViewValuesArr = new ArrayList<>();
    ArrayList<String> ItemsArray;
    ArrayList<String> PricesArray;
    TextView totalAmount;
    Button btnOrder;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference OrdersRef;
    FirebaseAuth firebaseAuth;
    TopTasteApplication cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        firebaseAuth= FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        ReceiptListView = this;
        Bundle b= getIntent().getExtras();
        cart= (TopTasteApplication) getApplicationContext();

        firebaseDatabase = FirebaseDatabase.getInstance();
        OrdersRef = FirebaseDatabase.getInstance().getReference();
        OrdersRef=OrdersRef.child("Orders_HomeDelivery");

        totalAmount = (TextView) findViewById(R.id.totalAmount);
        btnOrder = (Button) findViewById(R.id.btnOrder);
        btnOrder.setEnabled(true);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),PlaceOrder.class);
                startActivity(i);

            }
        });
        ItemsArray = b.getStringArrayList("ItemsArray");

        PricesArray =b.getStringArrayList("PricesArray");

//        Bundle b = getIntent().getExtras();
//        Passed_Item = b.getString("Item Name");

        setListData();

        Resources res =getResources();
        list= ( ListView )findViewById( R.id.item_list );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter=new Adapter_receipt( ReceiptListView, ReceiptListViewValuesArr,res );
        list.setAdapter( adapter );


//
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,mobileArray);
//
//        ListView listView = (ListView) findViewById(R.id.item_list);
//        listView.setAdapter(adapter);
    }



    public void setListData()
    {

        for (int i = 0; i < ItemsArray.size(); i++) {

            final Listview_receipt sched = new Listview_receipt();

            /******* Firstly take data in model object ******/
            sched.setItemName(ItemsArray.get(i));
            sched.setQuantity(1);
            sched.setPrice(String.valueOf(PricesArray.get(i)));

            /******** Take Model Object in ArrayList **********/
            ReceiptListViewValuesArr.add( sched );
        }

    }

    public void onItemClick(int mPosition)
    {
        Listview_receipt tempValues = ( Listview_receipt ) ReceiptListViewValuesArr.get(mPosition);


        // SHOW ALERT


//        Toast.makeText(CustomListView,
//                ""+tempValues.getItemName()
//                        +" Image:"+tempValues.getImage()
//            +" Price:"+tempValues.getPrice(),
//        Toast.LENGTH_SHORT)
//                    .show();

    }


    public void updateTotal( int total)
    {
            totalAmount.setText( "Rs. " + String.valueOf(total));

    }

}
