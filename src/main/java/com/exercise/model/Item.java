package com.exercise.model;

public class Item {

    private String itemName;
    private double itemCostPrice;
    private double itemSellPrice;
    private int itemQty;

    public Item()
    {

    }
    public Item(String itemName, double itemCostPrice, double itemSellPrice, int itemQty)
    {
        this.itemName = itemName;
        this.itemCostPrice = itemCostPrice;
        this.itemSellPrice = itemSellPrice;
        this.itemQty = itemQty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemCostPrice() {
        return itemCostPrice;
    }

    public void setItemCostPrice(double itemCostPrice) {
        this.itemCostPrice = itemCostPrice;
    }

    public double getItemSellPrice() {
        return itemSellPrice;
    }

    public void setItemSellPrice(double itemSellPrice) {
        this.itemSellPrice = itemSellPrice;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getItemValue() {
        return itemValue;
    }

    public void setItemValue(double itemValue) {
        this.itemValue = itemValue;
    }

    private double itemValue;

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Item)
        {
            Item i = (Item)o;
            if(i.getItemName().equals(itemName))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public  int hashCode()
    {
        int result = 17;
        result = 31 * result + itemName.hashCode();
        return result;


    }
}
