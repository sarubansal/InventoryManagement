package com.exercise.test;

import com.exercise.model.Item;
import com.exercise.repository.InventoryRepository;
import com.exercise.service.InventoryService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;


public class InventoryTest {
    InventoryRepository inventoryRepository = new InventoryRepository();
    @Test
    public void testCreate() {
        Item book01 = new Item("Book01", 10.50, 13.79, 0);
        try {
            inventoryRepository.create("Book01", 10.50, 13.79);
            inventoryRepository.create("Food01", 1.47, 3.98);
            inventoryRepository.create("Med01", 30.63, 34.29);
            inventoryRepository.create("Tab01", 57.00, 84.98);
            inventoryRepository.create("Book01", 10.50, 13.79);
            inventoryRepository.create("Book01", 10, 13);
            Set<Item> items = inventoryRepository.getItems();
            Assert.assertTrue(items.contains(book01)); //item is created
            Assert.assertTrue(items.size() == 4); //duplicate items are not created
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(false);
        }
        try {
            inventoryRepository.create("Book01", 10.50, 9.50);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(true); //invalid cost price or sell price
        }
    }
    @Test
    public void testUpdateBuy()
    {
        try {
            inventoryRepository.create("Tab01" ,57.00, 84.98);
            inventoryRepository.create("Food01", 1.47, 3.98);
            inventoryRepository.create("Med01", 30.63, 34.29);
            inventoryRepository.update("updateBuy", "Book01", 100);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(true); //item not present
        }
        try {
            inventoryRepository.update("updateBuy", "Tab01", 100);
            inventoryRepository.update("updateBuy", "Tab01", 2);
            Set<Item> items = inventoryRepository.getItems();
            items.forEach(item->{
                if (item.getItemName().equals("Tab01")){
                    Assert.assertTrue(item.getItemQty() == 102);
                }
            });

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(false); //item should be present
        }
    }
    @Test
    public void testUpdateSell()
    {
        try {
            inventoryRepository.create("Tab01" ,57.00, 84.98);
            inventoryRepository.create("Food01", 1.47, 3.98);
            inventoryRepository.create("Med01", 30.63, 34.29);
            inventoryRepository.update("updateSell", "Book01", 100);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(true); //item not present
        }
        try {
            inventoryRepository.update("updateSell", "Tab01", 100);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(true); //quantity not enough
        }
        try {
            inventoryRepository.update("updateBuy", "Tab01", 100);
            inventoryRepository.update("updateSell", "Tab01", 101);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(true); //quantity again not enough
        }
        try {
            inventoryRepository.update("updateSell", "Tab01", 99);

           Set<Item> items = inventoryRepository.getItems();
            items.forEach(item->{
                if (item.getItemName().equals("Tab01")){
                    Assert.assertTrue(item.getItemQty() == 1); //Quantity is updated
                    Assert.assertTrue(inventoryRepository.getProfit() == (99*(84.98-57.00))); //Profit is calculated
                }
            });

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(false); //item should be present
        }
    }

    @Test
    public void testUpdateSellPrice()
    {
        try {
            inventoryRepository.create("Tab01" ,57.00, 84.98);
            inventoryRepository.create("Food01", 1.47, 3.98);
            inventoryRepository.create("Med01", 30.63, 34.29);
            inventoryRepository.updateSellPrice("Book01", 90.00);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(true); //item not present
        }
        try {

            inventoryRepository.updateSellPrice("Tab01", 50.00);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(true); //invalid sell price
        }
        try {
            inventoryRepository.update("updateBuy", "Tab01", 100);
            inventoryRepository.update("updateSell", "Tab01", 2);
            Assert.assertTrue(inventoryRepository.getProfit() == 2*(84.98-57.00));

            inventoryRepository.updateSellPrice( "Tab01", 100);

            Set<Item> items = inventoryRepository.getItems();
            items.forEach(item->{
                if(item.getItemName().equals("Tab01")) {
                    Assert.assertTrue(item.getItemSellPrice() == 100);
                }//Sell price is updated
            });


            inventoryRepository.update("updateSell", "Tab01", 2);
            //Profit is calculated based on updated sell price
            Assert.assertTrue(inventoryRepository.getProfit() == (2*(84.98-57.00)) + (2*(100-57.00)));


        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(false); //item not present
        }
    }

    @Test
    public void testDelete()
    {
        try {
            inventoryRepository.create("Tab01" ,57.00, 84.98);
            inventoryRepository.create("Food01", 1.47, 3.98);
            inventoryRepository.create("Med01", 30.63, 34.29);
            inventoryRepository.delete("Book01");
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(true); //item not present
        }
        try {
            inventoryRepository.update("updateBuy", "Tab01", 100);
            inventoryRepository.delete("Tab01");
            Set<Item> items = inventoryRepository.getItems();
            items.forEach(item->{
                    Assert.assertFalse(item.getItemName().equals("Tab01")); //item is not preset
                });
            Assert.assertTrue(inventoryRepository.getProfit() == (0-(57.00*100))); //check profit

            inventoryRepository.update("updateBuy", "Food01", 100);
            inventoryRepository.delete("Food01");
            Assert.assertTrue(inventoryRepository.getProfit() == ((0-(57.00*100)) - (1.47*100)));

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(false); //item not present
        }
    }
    @Test
    public void testReport()
    {
        try {
            inventoryRepository.create("Tab01" ,57.00, 84.98);
            inventoryRepository.create("Food01", 1.47, 3.98);
            inventoryRepository.create("Med01", 30.63, 34.29);
            inventoryRepository.update("updateBuy", "Food01", 100);
            inventoryRepository.update("updateBuy", "Med01", 100);
            inventoryRepository.update("updateBuy", "Tab01", 100);
            inventoryRepository.update("updateSell", "Tab01", 2);
            inventoryRepository.updateSellPrice( "Tab01", 100);
            inventoryRepository.update("updateSell", "Tab01", 2);
            inventoryRepository.delete("Tab01");
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            Assert.assertTrue(false);
        }
        inventoryRepository.report();
        Assert.assertTrue(inventoryRepository.getProfit() == 0); //profit is reset

    }
}
