package com.team15x3.caucse.takecareoftherefrigerator;

import java.util.ArrayList;

public class ShoppingBasket {
    private ArrayList<Things> shoppingList = new ArrayList<Things>();

    public void addShoppingThings(Things thing) { shoppingList.add(thing); }

    public boolean removeThings(Things thing) {
        if (shoppingList.contains(thing)) {
            shoppingList.remove(thing);
        }

        return false;
    }

    public ArrayList<Things> getShoppingList() { return shoppingList; }

}

class Things {
    private String mName;
    private int    mCount;

    public void setName(String name) { this.mName = name; }

    public void setCount(int mCount) { this.mCount = mCount; }

    public int getCount() { return mCount; }

    public String getName() { return mName; }
}

