package restaurantpublisher;

import database.OmsDatabase;
import database.OmsDatabaseImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantPublishImpl implements RestaurantPublish{
	
	private Connection connection = null ;
	private Statement statement = null ;
	private OmsDatabase database ;
	private ResultSet resultSet ;
	
	public RestaurantPublishImpl() {
		super();
		database = (OmsDatabase) new OmsDatabaseImpl();
		connection = database.connection(1);
	}
	
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
	Scanner scanner = new Scanner(System.in);

	@Override
	public void publishService() {
		System.out.println("Publisher Running");
	}

	@Override
	public void viewOrders() {
		
		ArrayList<Order> orderList = new ArrayList<Order>(); 
		
		try {
			statement = connection.createStatement();
			
			String allOrders = "SELECT * FROM orders";
			
			resultSet = statement.executeQuery(allOrders);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			while(resultSet.next()) {
				
				Order newOrder = new Order();
				
				newOrder.setOrderID(resultSet.getInt("OrderID"));
				newOrder.setCustomerID(resultSet.getInt("CustomerID"));
				newOrder.setStatus(resultSet.getString("Status"));
				newOrder.setOrderPrice(resultSet.getString("OrderPrice"));
				
				orderList.add(newOrder);
				
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("==================================================================================================================================================================");
		System.out.println("                                                                     Orders               ");
		System.out.println("==================================================================================================================================================================");
		System.out.println("Order ID\tCustomer ID\tStatus\t\tOrder Price\t");
		System.out.println("==================================================================================================================================================================");
		
		
		for(Order order : orderList) {
			if(orderList.isEmpty()) {
				System.out.println("No orders to show.");
			} else {
				order.viewOrder();
			}
		}
		System.out.println("==================================================================================================================================================================");
	}

	@Override
	public void acceptOrder() {
		
		System.out.print("Enter Order ID : ");
		
		try {
			
			String orderId = reader.readLine();
			
			// Retrieve FoodID based on the provided FoodName.
			String sql = "SELECT * FROM orders WHERE OrderID = '" + orderId + "'" ;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.next()) { 
				String accept = "UPDATE orders SET status = 'Ongoing' WHERE OrderID = '" + orderId + "'" ;
				statement = connection.createStatement();
				statement.executeUpdate(accept);
				System.out.println("\n** Order Accepted **\n");
				
		        
		    } else {
		        System.out.println("\n** Order Not Found! **\n");
		    }
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void rejectOrder() {
		
		System.out.print("Enter Order ID : ");
		
		try {
			
			String orderId = reader.readLine();
			
			// Retrieve FoodID based on the provided FoodName.
			String sql = "SELECT * FROM orders WHERE OrderID = '" + orderId + "'" ;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.next()) { 
				String reject = "UPDATE orders SET status = 'Rejected' WHERE OrderID = '" + orderId + "'" ;
				statement = connection.createStatement();
				statement.executeUpdate(reject);
				System.out.println("\n** Order Rejected **\n");
				
		        
		    } else {
		        System.out.println("\n** Order Not Found! **\n");
		    }
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void markAsComplete() {
		
		System.out.print("Enter Order ID : ");
		
		try {
			
			String orderId = reader.readLine();
			
			// Retrieve FoodID based on the provided FoodName.
			String sql = "SELECT * FROM orders WHERE OrderID = '" + orderId + "'" ;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.next()) { 
				String complete = "UPDATE orders SET status = 'Completed' WHERE OrderID = '" + orderId + "'" ;
				statement = connection.createStatement();
				statement.executeUpdate(complete);
				System.out.println("\n** Order Completed **\n");
				
		        
		    } else {
		        System.out.println("\n** Order Not Found! **\n");
		    }
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void addFood() {
		
		Food newFood = new Food();
		
		int foodID = 0 ;
		
		try {
			System.out.print("Enter Food Name : ");
			String foodName = reader.readLine();
			
			String sql = "SELECT * FROM food WHERE FoodName LIKE '" + foodName + "'" ;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (!resultSet.next()) { 
				
				newFood.setFoodID(foodID);
				newFood.setFoodName(foodName);
				
		        System.out.print("Enter Description :");
				String description = reader.readLine().trim();
				newFood.setDescription(description);
				
				System.out.print("Enter Category : ");
				String category = reader.readLine().trim();
				newFood.setCategory(category);
				
				System.out.print("Enter Price : ");
				String price = reader.readLine().trim();
				newFood.setPrice(price);
				
		        
		    } else {
		        System.out.println("\n** Food Item Already Exists! **\n");
		    }
			
				String addFood = "INSERT INTO food "
						+ "VALUES('" + newFood.getFoodID() + "', '" + newFood.getFoodName() + "', '" + newFood.getDescription() + "', '" + newFood.getCategory() + "', '" + newFood.getPrice() + "')";
				
				try {
					statement = connection.createStatement();
					statement.executeUpdate(addFood);
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
				}
				
				System.out.println("\n** Food Item Added Successfully **\n");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void viewFoods() {

		ArrayList<Food> foodList = new ArrayList<Food>(); 
		
		try {
			statement = connection.createStatement();
			
			String allFood = "SELECT * FROM food";
			
			resultSet = statement.executeQuery(allFood);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			while(resultSet.next()) {
				
				Food newFood = new Food();
				
				newFood.setFoodID(resultSet.getInt("FoodID"));
				newFood.setFoodName(resultSet.getString("FoodName"));
				newFood.setDescription(resultSet.getString("Description"));
				newFood.setCategory(resultSet.getString("Category"));
				newFood.setPrice(resultSet.getString("Price"));
				
				foodList.add(newFood);
				
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("==================================================================================================================================================================");
		System.out.println("                                                                     Orders               ");
		System.out.println("==================================================================================================================================================================");
		System.out.println("Food ID\tFood Name\tDescription\t\t\tCategory\tPrice\t");
		System.out.println("==================================================================================================================================================================");
		
		
		for(Food food : foodList) {
			if(foodList.isEmpty()) {
				System.out.println("No orders to show.");
			} else {
				food.viewFood();
			}
		}
		System.out.println("==================================================================================================================================================================");
		
	}

	@Override
	public void deleteFood() {
		
		System.out.print("Enter the Food ID of the Food Item you want to delete :");
		
		String foodID;
		
		try {
			
			foodID = reader.readLine();
			
	        System.out.print("Are you sure you want to delete this Food Item? (yes/no): ");
	        String confirmation = reader.readLine().toLowerCase();
	        
	        if(confirmation.equals("yes")) {
	        	
				String deleteFood = "DELETE FROM food WHERE FoodID = '" + foodID + "'" ;
				
				try {
					
					statement = connection.createStatement();
					statement.executeUpdate(deleteFood);
					System.out.println("\n** Food Item Deleted Successfully **\n");
					
				} catch (SQLException e) {
					System.out.println("Could Not Delete Food Item");
					e.printStackTrace();
				}
	        	
	        }else {
	        	System.out.println("\n** Food Item Deletion Canceled **\n");
	        }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
