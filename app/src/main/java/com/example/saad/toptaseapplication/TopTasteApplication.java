package com.example.saad.toptaseapplication;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Saad on 21/11/2017.
 */

public class TopTasteApplication extends Application {

    private ArrayList<String> Items = new ArrayList<>();
    private ArrayList<Integer> Prices=new ArrayList<>();
    private HashMap<String,String> user= new HashMap<>();

    public ArrayList<String> getItems() {
        return Items;
    }

    public void AddItem(String itemName) {
        Items.add(itemName);
    }

    public ArrayList<Integer> getPrices() {
        return Prices;
    }

    public void AddPrice(Integer price) {
        Prices.add(price);
    }

    public void setUser(String name,String number,String address){
        user.put("Name",name);
        user.put("Number",number);
        user.put("Address",address);

    }
    public HashMap<String, String> getUser(){
        return user;
    }

}

