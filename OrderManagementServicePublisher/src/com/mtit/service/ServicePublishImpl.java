package com.mtit.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServicePublishImpl implements ServicePublish {
	
	private Connection connection =null ;
	
	
	public ServicePublishImpl() {
		DatabaseUtil database = (DatabaseUtil) new DatabaseUtil();
		connection = database.getConnection();
	}

	@Override
	public String publishService() {
		
		return "Let's Order the food !";
	}

	@Override
	public void displayAppertizers() {
		
		//SQL query execution
        try (
             PreparedStatement statement = connection.prepareStatement("select*from food where category=\"Appetizer\";");
             ResultSet resultSet = statement.executeQuery()) {

			System.out.println("\n======================================================");
			System.out.println("                       Appertizers               ");
			System.out.println("======================================================\n");
			System.out.println(" FoodID   Name\t\tPrice\t\tDescription\n");

            while (resultSet.next()) {
                // Process each row of the result set
            	int itemID = resultSet.getInt("FoodID");
                String itemName = resultSet.getString("FoodName");
                String description = resultSet.getString("Description");
                float price = resultSet.getFloat("Price");

                // Display or process the data as needed
                System.out.println(itemID + " . " + itemName + "\t\t : " + price + "\t\t - " + description);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	public void displayMainMeals() {
		
		try (
	             PreparedStatement statement = connection.prepareStatement("select*from food where category=\"Mains\";");
	             ResultSet resultSet = statement.executeQuery()) {

			System.out.println("\n======================================================");
			System.out.println("                         Main Meals                 ");
			System.out.println("======================================================\n");
			System.out.println(" FoodID   Name\t\tPrice\t\tDescription\n");

	            while (resultSet.next()) {
	                // Process each row of the result set
	            	int itemID = resultSet.getInt("FoodID");
	                String itemName = resultSet.getString("FoodName");
	                String description = resultSet.getString("Description");
	                float price = resultSet.getFloat("Price");

	                // Display or process the data as needed
	                System.out.println(itemID + " . " + itemName + "\t\t : " + price + "\t\t - " + description);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
	}

	@Override
	public void displayDesserts() {
		
		//SQL query execution
	    try (
	         PreparedStatement statement = connection.prepareStatement("select*from food where category=\"Desserts\";");
	         ResultSet resultSet = statement.executeQuery()) {

			System.out.println("\n======================================================");
			System.out.println("                           Desserts                    ");
			System.out.println("======================================================\n");
			System.out.println(" FoodID   Name\t\tPrice\t\tDescription\n");

	        while (resultSet.next()) {
	            // Process each row of the result set
	        	int itemID = resultSet.getInt("FoodID");
                String itemName = resultSet.getString("FoodName");
                String description = resultSet.getString("Description");
                float price = resultSet.getFloat("Price");

                // Display or process the data as needed
                System.out.println(itemID + " . \t" + itemName + "\t\t : " + price + "\t\t - " + description);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
		
	}

	@Override
	public void displayDrinks() {

	    //SQL query execution
	    try (
	         PreparedStatement statement = connection.prepareStatement("select*from food where category=\"Drinks\";");
	         ResultSet resultSet = statement.executeQuery()) {

			System.out.println("\n======================================================");
			System.out.println("                        Drinks                 ");
			System.out.println("======================================================\n");
			System.out.println(" FoodID   Name\t\tPrice\t\tDescription\n");

	        while (resultSet.next()) {
	            // Process each row of the result set
	        	int itemID = resultSet.getInt("FoodID");
                String itemName = resultSet.getString("FoodName");
                String description = resultSet.getString("Description");
                float price = resultSet.getFloat("Price");

                // Display or process the data as needed
                System.out.println(itemID + " . " + itemName + "\t\t : " + price + "\t\t - " + description);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}
	
   
	 
	 @Override
	 public float getPrice(int foodID) {
	     float price = 0;
	     try (
	         PreparedStatement statement = connection.prepareStatement("select Price from food where FoodID = ?")) {
	         statement.setInt(1, foodID); // Set the parameter for FoodID
	         ResultSet resultSet = statement.executeQuery();
	         if (resultSet.next()) {
	             price = resultSet.getFloat("Price");
	         }
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
	     return price;
	 }

	 
	 @Override
	 public String getFoodName(int foodID) {
	     String foodName = "";
	     try (
	         PreparedStatement statement = connection.prepareStatement("select FoodName from food where FoodID = ?")) {
	         statement.setInt(1, foodID); // Set the parameter for FoodID
	         ResultSet resultSet = statement.executeQuery();
	         if (resultSet.next()) {
	             foodName = resultSet.getString("FoodName");
	         }
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
	     return foodName;
	 }

	 @Override
	 public void addOrderToDB(int customerID, float totalAmount, int[][] cart) {
	     try (
	         PreparedStatement orderStatement = connection.prepareStatement("INSERT INTO Orders (CustomerID, Status, OrderPrice) VALUES (?, ?, ?)",
	             Statement.RETURN_GENERATED_KEYS);
	         PreparedStatement orderItemStatement = connection.prepareStatement("INSERT INTO OrderItems (OrderID, FoodID, Quantity) VALUES (?, ?, ?)")
	     ) {
	         // Insert order details into Orders table
	         orderStatement.setInt(1, customerID);
	         orderStatement.setString(2, "Pending"); // Assuming status is initially set to "Pending"
	         orderStatement.setFloat(3, totalAmount);
	         orderStatement.executeUpdate();

	         ResultSet generatedKeys = orderStatement.getGeneratedKeys();
	         int orderID = -1;
	         if (generatedKeys.next()) {
	             orderID = generatedKeys.getInt(1); // Retrieve the generated OrderID
	         } else {
	             throw new SQLException("Failed to insert order, no ID obtained.");
	         }

	         // Insert order items into OrderItems table
	         for (int i = 0; i < cart.length; i++) {
	             if (cart[i][0] == 0) {
	                 break;
	             }
	             int foodID = cart[i][0];
	             int quantity = cart[i][1];
	             orderItemStatement.setInt(1, orderID);
	             orderItemStatement.setInt(2, foodID);
	             orderItemStatement.setInt(3, quantity);
	             orderItemStatement.executeUpdate();
	         }
	         System.out.println("Order added successfully with OrderID: " + orderID);
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
	 }

    public List<Integer> displayFoodItems(String category) {
        List<Integer> foodIDs = new ArrayList<>();
        try (
             PreparedStatement statement = connection.prepareStatement("SELECT FoodID, FoodName FROM Food WHERE Category = ?")) {
            statement.setString(1, category);
            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("\n" + category);
                System.out.println("----------");
                while (resultSet.next()) {
                    int itemID = resultSet.getInt("FoodID");
                    String itemName = resultSet.getString("FoodName");
                    foodIDs.add(itemID);
                    System.out.println(itemID + ". " + itemName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodIDs;
    }
	

}
