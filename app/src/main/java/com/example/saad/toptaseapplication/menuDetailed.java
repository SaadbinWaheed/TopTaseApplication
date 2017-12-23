package com.example.saad.toptaseapplication;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class menuDetailed extends AppCompatActivity {

    String Passed_Item;
    ListView list;
    Adapter_menuDetailed adapter;
    public  menuDetailed CustomListView = null;
    public ArrayList<Listview_menuItems> CustomListViewValuesArr = new ArrayList<Listview_menuItems>();
    TopTasteApplication cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detailed);
        CustomListView = this;

        Bundle b = getIntent().getExtras();
        Passed_Item = b.getString("Item Name");

        cart= (TopTasteApplication) getApplicationContext();

        setListData();

        Resources res =getResources();
        list= ( ListView )findViewById( R.id.item_list );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter=new Adapter_menuDetailed( CustomListView, CustomListViewValuesArr,res );
        list.setAdapter( adapter );


//
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,mobileArray);
//
//        ListView listView = (ListView) findViewById(R.id.item_list);
//        listView.setAdapter(adapter);
    }



    public void setListData()
    {

        for (int i = 0; i < 11; i++) {

            final Listview_menuItems sched = new Listview_menuItems();
            String imgName=Passed_Item.toLowerCase().replaceAll(" ","");

            /******* Firstly take data in model object ******/
            sched.setItemName(Passed_Item+" " +i);
           // sched.setImage(imgName);
            sched.setPrice(i+"00");

            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add( sched );
        }

    }

    public void onItemClick(int mPosition)
    {
        Listview_menuItems tempValues = ( Listview_menuItems ) CustomListViewValuesArr.get(mPosition);


        // SHOW ALERT
        cart.AddItem(tempValues.getItemName());
        cart.AddPrice(Integer.valueOf(tempValues.getPrice()));


//        Toast.makeText(CustomListView,
//                ""+tempValues.getItemName()
//                        +" Image:"+tempValues.getImage()
//            +" Price:"+tempValues.getPrice(),
//        Toast.LENGTH_SHORT)
//                    .show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_hotlist) {
            Toast.makeText(getApplicationContext(),cart.getItems().toString(),Toast.LENGTH_LONG).show();
        }


        return super.onOptionsItemSelected(item);
    }





}
