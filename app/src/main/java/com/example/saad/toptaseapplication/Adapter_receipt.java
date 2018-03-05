package com.example.saad.toptaseapplication;

import android.app.Activity;
        import android.content.Context;
        import android.content.res.Resources;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import java.util.ArrayList;


public class Adapter_receipt extends BaseAdapter {

    private Activity activity;
    private ArrayList data;
    public Resources res;
    private static LayoutInflater inflater=null;

    private Listview_receipt tempValues=null;
    public static String[] arrItemsQuantity;
    private String[] arrItemsPrice;
    public static String[] arrItemsName;

    public Adapter_receipt(Activity a, ArrayList d,Resources resLocal) {

        /********** Take passed values **********/
        activity = a;
        data=d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        arrItemsQuantity = new String[data.size()];
        arrItemsPrice = new String[data.size()];
        arrItemsName = new String[data.size()];

        for(int iCount=0;iCount<data.size();iCount++){
            Listview_receipt listview_receipt = (Listview_receipt) data.get(iCount);
            arrItemsQuantity[iCount]=String.valueOf(listview_receipt.getQuantity());
            arrItemsPrice[iCount]=listview_receipt.getPrice();
            arrItemsName[iCount] = listview_receipt.getItemName();
        }
        updateTotal();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(data != null && data.size() != 0){
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;
        final ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_adapter_receipt, null);

            holder.Quantity = (TextView) convertView.findViewById(R.id.imageView);
            holder.ItemName=(TextView) convertView.findViewById(R.id.txtName);
            holder.ItemPrice=(TextView) convertView.findViewById(R.id.txtPrice);
            holder.plus = (TextView) convertView.findViewById(R.id.txtPlus);
            holder.minus=(TextView) convertView.findViewById(R.id.txtMinus);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ref = position;

        holder.Quantity.setText(arrItemsQuantity[position]);
        holder.ItemName.setText(arrItemsName[position]);
        holder.ItemPrice.setText(arrItemsPrice[position]);
        holder.Quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                arrItemsQuantity[holder.ref] = arg0.toString();
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = Integer.parseInt(arrItemsQuantity[position])+1;
                holder.Quantity.setText(String.valueOf(newQuantity));

                updateTotal();
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newQuantity = Integer.parseInt(arrItemsQuantity[position])-1;
                if (newQuantity>=0) {
                    holder.Quantity.setText(String.valueOf(newQuantity));
                    updateTotal();
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public TextView Quantity;
        public TextView ItemName;
        public TextView ItemPrice;
        public TextView plus;
        public TextView minus;
        int ref;
    }

    public int calculateTotal(){
        int total=0;
        for (int a=0;a<data.size();a++) {
            total += Integer.parseInt(arrItemsPrice[a])*Integer.parseInt(arrItemsQuantity[a]);
        }
        return total;
    }
    public void updateTotal()
    {
        Receipt sct = (Receipt) activity;
        sct.updateTotal(calculateTotal());

    }

}

