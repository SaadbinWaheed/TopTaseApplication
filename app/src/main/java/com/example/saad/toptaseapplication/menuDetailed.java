package com.example.saad.toptaseapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class menuDetailed extends AppCompatActivity {

    String Passed_Item;
    ListView list;
    Adapter_menuDetailed adapter;
    public  menuDetailed CustomListView = null;
    public ArrayList<Listview_menuItems> CustomListViewValuesArr = new ArrayList<Listview_menuItems>();
    TopTasteApplication cart;
    private int hot_number = 0;
    private TextView ui_hot = null;

    String imgName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detailed);
        CustomListView = this;

        Bundle b = getIntent().getExtras();
        Passed_Item = b.getString("Item Name");

        switch(Passed_Item){
            case "French Fries": imgName="fries_new";
            break;
            case "Burgers":imgName="sample_2"; break;

            case "Shawarmas": imgName="shawarmas_new";break;
            case "Shakes": imgName="shakes_new";break;
            case "Roll Parathas": imgName="rollparathas_new";break;
            case "Soups": imgName="sample_soup";break;

        }



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
            /******* Firstly take data in model object ******/
            sched.setItemName(Passed_Item+" " +i);

            //int resID = getResources().getIdentifier(imgName , "drawable", getPackageName());
           // Drawable image = getResources().getDrawable(resID);
            sched.setImage(imgName);
            sched.setPrice(i+"00");

            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add( sched );
        }

    }

    public void onItemClick(int mPosition)
    {
        Listview_menuItems tempValues = ( Listview_menuItems ) CustomListViewValuesArr.get(mPosition);


        if(!cart.getItems().contains(tempValues.getItemName())) {
            // SHOW ALERT
            cart.AddItem(tempValues.getItemName());
            cart.AddPrice(Integer.valueOf(tempValues.getPrice()));


            Toast.makeText(CustomListView, tempValues.getItemName() + " added to Cart", Toast.LENGTH_SHORT)
                    .show();
            invalidateOptionsMenu();
        }
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


    @Override public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_button, menu);
        final View menu_hotlist = menu.findItem(R.id.menu_hotlist).getActionView();
        ui_hot = (TextView) menu_hotlist.findViewById(R.id.hotlist_hot);

        hot_number=cart.getItems().size();
        updateHotCount(hot_number);
        new mainMenu.MyMenuItemStuffListener(menu_hotlist, "Show hot message") {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),Receipt.class);
                i.putExtra("ItemsArray",cart.getItems());
                i.putExtra("PricesArray",cart.getPrices());
                startActivity(i);

            }
        };
        return super.onCreateOptionsMenu(menu);
    }

    public void updateHotCount(final int new_hot_number) {
        hot_number = new_hot_number;
        if (ui_hot == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_hot_number == 0)
                    ui_hot.setVisibility(View.INVISIBLE);
                else {
                    ui_hot.setVisibility(View.VISIBLE);
                    ui_hot.setText(Integer.toString(new_hot_number));
                }
            }
        });
    }




}
