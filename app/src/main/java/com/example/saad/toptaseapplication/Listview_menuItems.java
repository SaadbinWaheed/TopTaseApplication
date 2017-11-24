package com.example.saad.toptaseapplication;

/**
 * Created by Saad on 21/11/2017.
 */

public class Listview_menuItems {

        private  String itemName="";
        private  String Image="";
        private  String Price="";

        /*********** Set Methods ******************/

        public void setItemName(String itemName)
        {
            this.itemName = itemName;
        }

        public void setImage(String Image)
        {
            this.Image = Image;
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

        public String getImage()
        {
            return this.Image;
        }

        public String getPrice()
        {
            return this.Price;
        }

}
