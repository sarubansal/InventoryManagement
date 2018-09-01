package com.exercise.service;
import com.exercise.repository.InventoryRepository;


import java.io.IOException;
import java.util.*;

public class InventoryService {
    private static final String create = "CREATE";
    private static final String updateBuy = "UPDATEBUY";
    private static final String updateSell = "UPDATESELL";
    private static final String delete = "DELETE";
    private static final String report = "REPORT";
    private static final String updateSellPrice = "UPDATESELLPRICE";
    private static final String quit = "QUIT";


    public static void main(String args[])
    {
        InventoryRepository inventoryRepository = new InventoryRepository();
        System.out.println("Enter command for the inventory repository or Enter quit to exit: ");

        try{
            Scanner scanner = new Scanner(System.in);

            while(scanner.hasNext())
            {
                String line = scanner.nextLine();
                String[] commands = line.split(" ");
                if(commands[0].equalsIgnoreCase(create))
                {
                    inventoryRepository.create(commands[1], Double.valueOf(commands[2]), Double.valueOf(commands[3]));
                }
                if(commands[0].equalsIgnoreCase(updateBuy) || commands[0].equalsIgnoreCase(updateSell))
                {
                    inventoryRepository.update(commands[0], commands[1], Integer.valueOf(commands[2]));
                }
                if(commands[0].equalsIgnoreCase(updateSellPrice))
                {
                    inventoryRepository.updateSellPrice(commands[1], Double.valueOf(commands[2]));
                }
                if(commands[0].equalsIgnoreCase(delete))
                {
                    inventoryRepository.delete(commands[1]);
                }
                if(commands[0].equalsIgnoreCase(report))
                {
                    System.out.println(String.format("%20s", "INVENTORY REPORT"));
                    System.out.println(String.format("%-15s %-15s %-15s %-15s %-15s", "Item Name", "Bought At", "Sold At", "AvailableQty", "Value"));
                    System.out.println(String.format("%-15s %-15s %-15s %-15s %-15s", "---------", "---------", "-------", "------------",
                            "-----"));
                    inventoryRepository.report();
                }
                if(commands[0].equalsIgnoreCase(quit))
                {
                    break;
                }
            }
        }catch(IOException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
