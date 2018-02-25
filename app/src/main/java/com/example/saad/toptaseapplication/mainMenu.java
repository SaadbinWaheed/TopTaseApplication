package com.example.saad.toptaseapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class mainMenu extends AppCompatActivity {
    TopTasteApplication cart;
    GridView gridview;

    private static String[] osNameList = {
            "French Fries",
            "Burgers",
            "Shawarmas",
            "Shakes",
            "Roll Parathas",
            "Soups",
    };
    private static int[] osImages = {
            R.drawable.fries_new,
            R.drawable.burgers_new,
            R.drawable.shawarmas_new,
            R.drawable.shakes_new,
            R.drawable.rollparathas_new,
            R.drawable.soups_new,
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        gridview = (GridView) findViewById(R.id.customgrid);
        gridview.setAdapter(new Adapter_mainMenu(this, osNameList, osImages));

        cart= (TopTasteApplication) getApplicationContext();

    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();

    }


    private int hot_number = 0;
    private TextView ui_hot = null;

    @Override public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_button, menu);
        final View menu_hotlist = menu.findItem(R.id.menu_hotlist).getActionView();
        ui_hot = (TextView) menu_hotlist.findViewById(R.id.hotlist_hot);

        hot_number=cart.getItems().size();
        updateHotCount(hot_number);
        new MyMenuItemStuffListener(menu_hotlist, "Show hot message") {
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

    // call the updating code on the main thread,
// so we can call this asynchronously
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

    static abstract class MyMenuItemStuffListener implements View.OnClickListener, View.OnLongClickListener {
        private String hint;
        private View view;

        MyMenuItemStuffListener(View view, String hint) {
            this.view = view;
            this.hint = hint;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override abstract public void onClick(View v);

        @Override public boolean onLongClick(View v) {
            final int[] screenPos = new int[2];
            final Rect displayFrame = new Rect();
            view.getLocationOnScreen(screenPos);
            view.getWindowVisibleDisplayFrame(displayFrame);
            final Context context = view.getContext();
            final int width = view.getWidth();
            final int height = view.getHeight();
            final int midy = screenPos[1] + height / 2;
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            Toast cheatSheet = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
            if (midy < displayFrame.height()) {
                cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT,
                        screenWidth - screenPos[0] - width / 2, height);
            } else {
                cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
            }
            cheatSheet.show();
            return true;
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        hot_number=cart.getItems().size();
        updateHotCount(hot_number);
        return true;
    }
}
