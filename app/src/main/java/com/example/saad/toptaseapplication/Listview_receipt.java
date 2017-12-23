package com.example.saad.toptaseapplication;

/**
 * Created by Saad on 21/11/2017.
 */

public class Listview_receipt {

    private  String itemName="";
    private  Integer Quantity;
    private  String Price="";

    /*********** Set Methods ******************/

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public void setQuantity(Integer Quantity)
    {
        this.Quantity = Quantity;
    }

    public void setPrice(String Price)
    {
        this.Price = Price;
    }

    /*********** Get Methods ****************/

    public String getItemName()
    {
        return this.itemName;
    }

    public Integer getQuantity()
    {
        return this.Quantity;
    }

    public String getPrice()
    {
        return this.Price;
    }

}
