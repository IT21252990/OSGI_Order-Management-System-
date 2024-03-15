package ordermanagementservicepublisher;

import database.OmsDatabase;
import database.OmsDatabaseImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ServicePublishImpl implements ServicePublish{
	
	private Connection connection = null ;
	private OmsDatabase database;
	
	public ServicePublishImpl() {
		super();
		database = (OmsDatabase) new OmsDatabaseImpl() ;
		connection = database.connection(1);
	}

	@Override
	public String publishService() {
		return "Let's Order the food !";
	}

	//Returning the name of a single item 
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

	//Returning the price of a single item 
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

	 //Insert data
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

	//Display food items according to the category
	@Override
	public void displayFoodItems(String category) {
		
		try (
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM  food WHERE category=\""+ category +"\";");
             ResultSet resultSet = statement.executeQuery()) {

			System.out.println("\n------------------------------------------------------");
			System.out.println("                       "+category);
			System.out.println("------------------------------------------------------\n");
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

	//Printing orders of a specific customer
	@Override
	public void displayMyOrders(int CustomerID) {
		
		//SQL query execution
	    try (
	         PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE CustomerID =" + CustomerID + ";");
	         ResultSet resultSet = statement.executeQuery()) {

			System.out.println("\n======================================================");
			System.out.println("                        My Orders                 ");
			System.out.println("======================================================\n");
			System.out.println(" OrderID   Status\t Price \t\tOrder");
			 

	        while (resultSet.next()) {
	            // Process each row of the result set
	        	int orderID = resultSet.getInt("OrderID");
                String status = resultSet.getString("Status");
                float price = resultSet.getFloat("OrderPrice"); 

                // Display or process the data as needed
                System.out.print("\n   " + orderID + " .\t  " + status + "  \tRs. " + price + "  \t"); 
                orderItemSet(orderID); 
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}

    //Printing food names and the quantities of a specific order 
	@Override
	public void orderItemSet(int OrderID) {
		
		//SQL query execution
	    try (
	         PreparedStatement statement = connection.prepareStatement("SELECT Food.FoodName, OrderItems.Quantity\r\n"
														         		+ "FROM OrderItems\r\n"
														         		+ "JOIN Food ON OrderItems.FoodID = Food.FoodID\r\n"
														         		+ "WHERE OrderItems.OrderID =" + OrderID + ";");
	         ResultSet resultSet = statement.executeQuery()) {			 

	        while (resultSet.next()) {
	            // Process each row of the result set
                String name = resultSet.getString("FoodName");
                int quantity = resultSet.getInt("Quantity");

                // Display or process the data as needed
                System.out.print(name + " x " + quantity + " , ");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
	}

}
