package com.example.saad.toptaseapplication;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Saad on 21/11/2017.
 */

public class TopTasteApplication extends Application {

    private ArrayList<String> Items;
    private ArrayList<Integer> Prices;

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
}