package com.example.saad.toptaseapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class mainMenu extends AppCompatActivity {
    TopTasteApplication cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(this));
        cart = ((TopTasteApplication) getApplicationContext());



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){
                // Send intent to SingleViewActivity
                Intent i = new Intent(getApplicationContext(), menuDetailed.class);
                // Pass image index
                i.putExtra("id", position);
                startActivity(i);
            }
        });


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

        if (id == R.id.mybutton) {
            Toast.makeText(getApplicationContext(),cart.getItems().toString(),Toast.LENGTH_LONG).show();
        }


        return super.onOptionsItemSelected(item);
    }
}
