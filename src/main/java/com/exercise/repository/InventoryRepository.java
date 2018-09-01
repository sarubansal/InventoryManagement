package com.exercise.repository;

import com.exercise.model.Item;

import java.util.*;

public class InventoryRepository {

    private Set<Item> items = new HashSet<Item>();
    private double profit;
    Comparator<Item> c1 = (i1,i2)-> i1.getItemName().compareTo(i2.getItemName());


    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public void create(String itemName, double itemCostPrice, double itemSellPrice) throws Exception
    {
        if(itemCostPrice <itemSellPrice) {
            Item item = new Item(itemName, Math.round(itemCostPrice * 100.00) / 100.00, Math.round(itemSellPrice * 100.00) / 100.00, 0);
            items.add(item);
        }
        else
        {
            throw new Exception ("Invalid CostPrice or SellPrice: " + itemName);
        }
    }
    public void update(String command, String itemName, int itemQty) throws Exception
    {
        Item updatedItem = null;
        double updatedProfit = 0;
        boolean itemFound = false;
        for(Item item: items )
        {
            if(item.getItemName().equals(itemName))
            {
                if(command.equals("updateBuy"))
                {
                    updatedItem = new Item(itemName, item.getItemCostPrice(), item.getItemSellPrice(),
                            item.getItemQty() + itemQty);
                }
                else if(command.equals("updateSell"))
                {
                    if((item.getItemQty() - itemQty) >= 0) {
                        updatedItem = new Item(itemName, item.getItemCostPrice(), item.getItemSellPrice(),
                                item.getItemQty() - itemQty);
                        profit += itemQty * (item.getItemSellPrice() - item.getItemCostPrice());
                    }
                    else
                    {
                        throw new Exception ("Insufficient Quantity: " + itemName);
                    }
                }
                itemFound = true;
                items.remove(item);
                break;
            }
        }

        if(itemFound == false)
        {
            throw new Exception("Item not found: " + itemName);
        }
        else
        {
            items.add(updatedItem);

        }
    }
    public void updateSellPrice(String itemName, double itemSellPrice) throws Exception
    {
        Item updatedItem = null;
        boolean itemFound = false;
        for(Item item: items )
        {
            if(item.getItemName().equals(itemName))
            {
                if(item.getItemCostPrice() < itemSellPrice) {
                    itemFound = true;

                    updatedItem = new Item(itemName, item.getItemCostPrice(), itemSellPrice,
                            item.getItemQty());
                    items.remove(item);
                    break;
                }
                else {
                    throw new Exception ("Invalid SellPrice: " + itemName);
                }

            }
        }

        if(itemFound == false)
        {
            throw new Exception("Item not found: " + itemName);
        }
        else
        {

            items.add(updatedItem);

        }
    }
    public void delete(String itemName) throws Exception
    {
        boolean itemFound = false;
        for(Item item: items )
        {
            if(item.getItemName().equals(itemName))
            {
                itemFound = true;
                profit = profit-(item.getItemQty() * item.getItemCostPrice());
                items.remove(item);
                break;
            }
        }

        if(itemFound == false)
        {
            throw new Exception("Item not found: " + itemName);
        }

    }
    public void report()
    {
        double totalValue = 0;
        TreeSet<Item> sortedSet = new TreeSet(c1);
        sortedSet.addAll(items);
        for (Item item: sortedSet)
        {
            double value = item.getItemCostPrice()*item.getItemQty();
            System.out.println(String.format("%-15s %-15.2f %-15.2f %-15d %-15.2f", item.getItemName(), item.getItemCostPrice(),
                    item.getItemSellPrice(), item.getItemQty(), value));
            totalValue += value;

        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(String.format("%-65s", "Total Value") + String.format("%.2f",totalValue));
        System.out.println(String.format("%-65s", "Profit since previous report") + String.format("%.2f", profit));
        profit= 0;
    }
}
