package com.example.saad.toptaseapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter_receipt extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    Listview_receipt tempValues=null;
    int i=0;
    ArrayList<Integer> priceArray = new ArrayList<>();
    ArrayList<Integer> quantityArray = new ArrayList<>();

    ArrayList<Integer> updatedPriceArray = new ArrayList<>();

    /*************  CustomAdapter Constructor *****************/
    public Adapter_receipt(Activity a, ArrayList d,Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        public TextView Quantity;
        public TextView ItemName;
        public TextView ItemPrice;
        public TextView plus;
        public TextView minus;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder holder;
//        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.activity_adapter_receipt, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.Quantity = (TextView) vi.findViewById(R.id.imageView);
            holder.ItemName=(TextView)vi.findViewById(R.id.txtName);
            holder.ItemPrice=(TextView) vi.findViewById(R.id.txtPrice);

            holder.plus = (TextView) vi.findViewById(R.id.txtPlus);
            holder.minus=(TextView)vi.findViewById(R.id.txtMinus);

        /***** Get each Model object from Arraylist ********/
            tempValues = ( Listview_receipt ) data.get( position );

        Log.e("Data: ",((Listview_receipt) data.get(position)).getItemName().toString());

        final View finalVi = vi;
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer QuantVal=Integer.parseInt(holder.Quantity.getText().toString()) ;
                    Integer PriceVal=priceArray.get(position);

                    holder.Quantity.setText( String.valueOf( QuantVal + 1) );

                    QuantVal++;
                   // holder.ItemPrice.setText( "Rs." + String.valueOf( QuantVal * PriceVal ));

                    updatedPriceArray.set(position, (QuantVal * PriceVal) );


                    updateTotal();
                }
            });

            final View finalVi1 = vi;
            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer QuantVal=Integer.parseInt(holder.Quantity.getText().toString()) ;
                    Integer PriceVal=priceArray.get(position);

                    if(QuantVal!=0) {
                        holder.Quantity.setText(String.valueOf(QuantVal - 1));

                        QuantVal--;
                       // holder.ItemPrice.setText("Rs." + String.valueOf(PriceVal * QuantVal));

                        updatedPriceArray.set(position, (PriceVal * QuantVal));

                        updateTotal();
                    }
                }
            });
            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
//        }
//        else
//            holder=(ViewHolder)vi.getTag();



            /************  Set Model values in Holder elements ***********/
            holder.Quantity.setText(Integer.toString( tempValues.getQuantity()) );
            holder.ItemName.setText( tempValues.getItemName() );
            holder.ItemPrice.append(tempValues.getPrice());

            priceArray.add( Integer.parseInt(holder.ItemPrice.getText().toString().replace("Rs.",""))) ;
            updatedPriceArray.add( Integer.parseInt(holder.ItemPrice.getText().toString().replace("Rs.","")) ) ;


            updateTotal();
            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));

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


            Receipt sct = (Receipt) activity;
            sct.onItemClick(mPosition);
        }
    }

    public Integer calculateTotal()
    {
        Integer sum=0;

        Log.v("Price Array",updatedPriceArray.toString());


        for(int i=0;i<updatedPriceArray.size();i++){
            sum=sum+ (updatedPriceArray.get(i));
        }

      return sum;
    }

    public void updateTotal()
    {
        Receipt sct = (Receipt) activity;
        sct.updateTotal(calculateTotal());

    }
}
