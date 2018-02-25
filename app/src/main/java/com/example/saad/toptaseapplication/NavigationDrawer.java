package com.example.saad.toptaseapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TopTasteApplication cart;
    GridView gridview;
    MenuItem navLogout;
    TextView navUsername;
    FirebaseAuth mAuth;
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
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_navigation_drawer);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridview = (GridView) findViewById(R.id.customgrid);
        gridview.setAdapter(new Adapter_mainMenu(NavigationDrawer.this, osNameList, osImages));

        cart= (TopTasteApplication) getApplicationContext();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        navUsername = (TextView) headerView.findViewById(R.id.navUsername);


        navLogout = navigationView.getMenu().getItem(3);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id== R.id.nav_book)
        {
            Intent i = new Intent(NavigationDrawer.this,Table_Booking.class);
            startActivity(i);
        }
        else if (id == R.id.nav_about) {
            // Handle the camera action
        } else if (id == R.id.nav_logout) {

            if(navLogout.getTitle().equals("Log Out")) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                LoginManager.getInstance().logOut();
                navLogout.setTitle("Log In");
            }
            else
            {
                Bundle b= new Bundle();
                b.putString("From","NavigationDrawer");
                Intent i = new Intent(NavigationDrawer.this,Login.class);
                i.putExtras(b);
                startActivity(i);
               finish();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null) {
            upDateNav(currentUser);
            getUserDetails();
        }
        else
        {
            navLogout.setTitle("Log In");
        }

    }

    private void upDateNav(FirebaseUser currentUser) {
        String Username=currentUser.getDisplayName();
        navUsername.setText(Username);
        navLogout.setTitle("Log Out");
    }

    public void getUserDetails() {
        FirebaseDatabase firebaseDatabase;
        DatabaseReference OrdersRef;
        firebaseDatabase = FirebaseDatabase.getInstance();
        OrdersRef = firebaseDatabase.getInstance().getReference();
        OrdersRef=OrdersRef.child("Customers_HomeDelivery");

        OrdersRef.orderByChild("Email").equalTo(mAuth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(getApplicationContext(),dataSnapshot.child("Name").getValue().toString(),Toast.LENGTH_SHORT).show();
               // cart.setUser();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
