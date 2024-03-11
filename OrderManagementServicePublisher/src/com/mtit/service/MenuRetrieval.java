// publisher/src/com/mtit/service/MenuRetrieval.java
package com.mtit.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuRetrieval {

    public static void retrieveAndDisplayMenu() {
        // Retrieve and display menu logic
        Scanner scanner = new Scanner(System.in);
        int option;

        
        System.out.println("\nYum-Yum - Food Ordering System");
        System.out.println("-------------------------------");
        System.out.println("1. Menu");
        System.out.println("2. Past orders");
        System.out.println("3. Exit\n");

        System.out.print("Enter your option: ");
        option = scanner.nextInt();

        switch (option) {
            case 1:
                displayMenu();
                break;
            case 2:
                // Implement past orders functionality
                break;
            case 3:
                System.out.println("Exiting Food Ordering System. Goodbye!");
                return; // exit the method or loop
            default:
                System.out.println("Invalid option. Please try again.");
        }
        
    }
    
    public static void displayMenu() {
        // Retrieve and display menu logic
        Scanner scanner = new Scanner(System.in);
        int option;

        
        System.out.println("\nYum-Yum - Food Ordering System");
        System.out.println("-------------------------------");
        System.out.println("1. Appertizers");
        System.out.println("2. Main Meals");
        System.out.println("3. Desserts");
        System.out.println("4. drinks");

        System.out.print("Enter your option: ");
        option = scanner.nextInt();

        switch (option) {
            case 1:
                displayAppertizers();
                break;
            case 2:
            	displayMainMeals();
                break;
            case 3:
            	displayDesserts();
                return; 
            case 4:
            	displaydrinks();
                return; 
            default:
                System.out.println("Invalid option. Please try again.");
        }
        
    }

    private static void displayAppertizers() {
    	
        // Example SQL query execution
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("select*from food where category=\"Appetizer\";");
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\nAppertizers");
            System.out.println("----------");

            while (resultSet.next()) {
                // Process each row of the result set
                String itemName = resultSet.getString("FoodName");
                String description = resultSet.getString("Description");

                // Display or process the data as needed
                System.out.println(itemName + " - " + description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
private static void displayMainMeals() {
    	
        // Example SQL query execution
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("select*from food where category=\"Mains\";");
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\nMain Meals");
            System.out.println("----------");

            while (resultSet.next()) {
                // Process each row of the result set
                String itemName = resultSet.getString("FoodName");
                String description = resultSet.getString("Description");

                // Display or process the data as needed
                System.out.println(itemName + " - " + description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

private static void displayDesserts() {
	
    // Example SQL query execution
    try (Connection connection = DatabaseUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement("select*from food where category=\"Desserts\";");
         ResultSet resultSet = statement.executeQuery()) {

        System.out.println("\nDessters");
        System.out.println("----------");

        while (resultSet.next()) {
            // Process each row of the result set
            String itemName = resultSet.getString("FoodName");
            String description = resultSet.getString("Description");

            // Display or process the data as needed
            System.out.println(itemName + " - " + description);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void displaydrinks() {
	
    // Example SQL query execution
    try (Connection connection = DatabaseUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement("select*from food where category=\"Drinks\";");
         ResultSet resultSet = statement.executeQuery()) {

        System.out.println("\nDrinks");
        System.out.println("----------");

        while (resultSet.next()) {
            // Process each row of the result set
            String itemName = resultSet.getString("FoodName");
            String description = resultSet.getString("Description");

            // Display or process the data as needed
            System.out.println(itemName + " - " + description);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
