package com.example.saad.toptaseapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Adapter_menuDetailed extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    Listview_menuItems tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public Adapter_menuDetailed(Activity a, ArrayList d,Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public Adapter_menuDetailed() {

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView text;
        public TextView text1;
        public TextView textWide;
        public ImageView image;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.activity_adapter_menu_detailed, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.txtName);
            holder.text1=(TextView)vi.findViewById(R.id.txtPrice);
            holder.image=(ImageView)vi.findViewById(R.id.imageView);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.text.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( Listview_menuItems ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.text.setText( tempValues.getItemName());
            holder.text1.setText( "Price: Rs." + tempValues.getPrice() );
//            holder.image.setImageResource(
//                    res.getIdentifier(
//                            tempValues.getImage()
//                            ,null,null));
//            holder.image.setImageResource( vi.getContext().getResources().getIdentifier(tempValues.getImage(), null, vi.getContext().getPackageName()));

            Log.e("ImageName",tempValues.getImage());
            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            menuDetailed sct = (menuDetailed) activity;

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            sct.onItemClick(mPosition);
        }
    }
}
