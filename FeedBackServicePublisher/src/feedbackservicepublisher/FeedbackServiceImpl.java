package feedbackservicepublisher;

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

import org.osgi.framework.BundleContext;


public class FeedbackServiceImpl implements FeedbackService{
	
    // Instance variables for database connection and statement handling.
	private Connection connection = null ;
	private Statement statement = null ;
	private OmsDatabase database ;
	private ResultSet resultSet ;
	private ResultSet resultSet2 ;
	private BundleContext context = Activator.getContext();
	
    // Constructor initializing database connection.
	public FeedbackServiceImpl() {
		super();
		database = (OmsDatabase) new OmsDatabaseImpl();
		connection = database.connection();
	}
	
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
	Scanner scanner = new Scanner(System.in);

	// Method to allow a customer to provide feedback.
	@Override
	public void giveFeedback(int CustomerID) {
		
		// Create new Feedback object to store feedback information.
		Feedback newFeedback = new Feedback();
		
		System.out.print("Enter Food Name : ");
		
		String foodName;
		int foodID = 0 ;
		
		try {
			
			foodName = reader.readLine();
			
			// Retrieve FoodID based on the provided FoodName.
			String sql = "SELECT FoodID FROM food WHERE FoodName LIKE '" + foodName + "'" ;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.next()) { 
		        foodID = resultSet.getInt("FoodID");
		        newFeedback.setFoodID(foodID);
		        
		    } else {
		        System.out.println("\n** Food Code Not Found! **\n");
		    }
			
			// If FoodID is found, proceed with collecting feedback details.
			if(foodID != 0 ) {
				System.out.print("Enter Feedback Title :");
				String title = reader.readLine().trim();
				
				newFeedback.setTitle(title);
				
				System.out.print("Enter Description : ");
				String description = reader.readLine().trim();
				
				newFeedback.setDescription(description);
				
				// Prompt user for star rating.
				while(true) {
					System.out.print("Do you need the add a Start Rating for your reviewed Food (Yes/No)");
					String answer = reader.readLine();
					
					if(answer.equalsIgnoreCase("yes")) {
						
						System.out.print("Enter number between 1 and 5 :");
						int rating = scanner.nextInt();
						
						if(rating>5 || rating < 1) {
							System.out.println("Please Enter valid rating number !!");
							continue;
						}else {
							newFeedback.setStarRating(rating);
							break;
						}
						
					}else if(answer.equalsIgnoreCase("no")) {
						
						break;
						
					}else {
						System.out.println("Enter your answer to Continue the process");
					}
				}
				
				// Insert the feedback into the database.
				String addFeedback = "INSERT INTO feedback "
						+ "VALUES('" + 0 + "', '" + CustomerID + "', '" + newFeedback.getFoodID() + "', '" + newFeedback.getStarRating() + "', '" + newFeedback.getTitle() + "', '" + newFeedback.getDescription() + "')";
				
				try {
					statement = connection.createStatement();
					statement.executeUpdate(addFeedback);
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
				}
				
				System.out.println("\n** Feedback Added Successfully **\n");
			} else {
			 	System.out.println("\n** FoodID Not Found! **\n");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	// Method to view all feedback provided by a specific customer.
	@Override
	public void viewMyFeedback(int CustomerID) {
		
		 // ArrayList to store all feedbacks retrieved from the database.
		ArrayList<Feedback> feedbackList = new ArrayList<Feedback>(); 
		
		try {
			statement = connection.createStatement();
			
			// Retrieve all feedbacks associated with the given CustomerID.
			String allFeedbacks = "SELECT * FROM feedback WHERE CustomerID ='" + CustomerID + "'";
			
			resultSet = statement.executeQuery(allFeedbacks);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			// Iterate through the result set and construct Feedback objects.
			while(resultSet.next()) {
				
				Feedback newFeedback = new Feedback();
				
				int FoodID = 0;
				
				newFeedback.setFeedbackID(resultSet.getInt("FeedbackID"));
				newFeedback.setCustomerID(resultSet.getInt("CustomerID"));
				newFeedback.setFoodID(resultSet.getInt("FoodID"));	
				FoodID = resultSet.getInt("FoodID");
				newFeedback.setStarRating(resultSet.getInt("StarRating"));
				newFeedback.setTitle(resultSet.getString("Title"));
				newFeedback.setDescription(resultSet.getString("Description"));
				
				// Retrieve the FoodName associated with the FoodID.
				String sql = "SELECT FoodName FROM food WHERE FoodID = '" + FoodID  + "'" ;
				statement = connection.createStatement();
				resultSet2 = statement.executeQuery(sql);
				
				if (resultSet2.next()) { 
			        String foodName = resultSet2.getString("FoodName");
			        newFeedback.setFoodName(foodName);
			        
			    } else {
			        System.out.println("\n** FoodID Not Found! **\n");
			    }
				
				// Add the constructed Feedback object to the feedbackList.
				feedbackList.add(newFeedback);
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Print all feedbacks retrieved from the database.
		
		System.out.println("==================================================================================================================================================================");
		System.out.println("                                                                     Your Feedbacks               ");
		System.out.println("==================================================================================================================================================================");
		System.out.println("Feedback Code\tFood Code\tFood Name\tStar Rating\tTitle\t\t\t\tDescription\t");
		System.out.println("==================================================================================================================================================================");
		for(Feedback feedback : feedbackList) {
			if(feedbackList.isEmpty()) {
				System.out.println("You did not provide any feedback yet.");
			} else {
				feedback.viewFeedback();
			}
		}
		System.out.println("==================================================================================================================================================================");
		
	}

	// Method to delete feedback by provided feedback ID
	@Override
	public void deleteFeedback(int CustomerID) {
		
		//display all feedbacks in the database to choose the one want to delete
		viewMyFeedback(CustomerID);
		
		System.out.print("Enter the Feedback ID you want to Delete :");
		
		String feedbackID;
		
		try {
			
			feedbackID = reader.readLine();
			
			// Ask for confirmation
	        System.out.print("Are you sure you want to delete this feedback? (yes/no): ");
	        String confirmation = reader.readLine().toLowerCase();
	        
	        if(confirmation.equals("yes")) {
	        	
	        	// delete feedback associate with given feedback ID
				String deleteFeedback = "DELETE FROM feedback WHERE FeedbackID = '" + feedbackID + "'" ;
				
				try {
					
					statement = connection.createStatement();
					statement.executeUpdate(deleteFeedback);
					System.out.println("\n** Feedback Deleted Successfully **\n");
					
				} catch (SQLException e) {
					System.out.println("Error with Deleting Feedback");
					e.printStackTrace();
				}
	        	
	        }else {
	        	System.out.println("\n** Feedback Deletion Canceled **\n");
	        }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	//Method to show feedback and necessary details associated with given Food Name 
	@Override
	public void reviewFeedbacks(int CustomerID) {
		
		// ArrayList to store all feedbacks retrieved from the database by given FoodID
		ArrayList<Feedback> feedbackList = new ArrayList<Feedback>(); 
		
		String foodName = null;
		int foodID = 0 ;
		
		System.out.print("Enter the Food Name you want to review :");
		try {
			foodName = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//get FoodID associate with given FoodName from the database
		String getfoodID = "SELECT FoodID FROM food WHERE FoodName = '" + foodName  + "'" ;
		
		try {
			statement = connection.createStatement();
		    resultSet =	statement.executeQuery(getfoodID);
		    
		    if (resultSet.next()) { 
			    foodID = resultSet.getInt("FoodID");
			    
			} else {
			    System.out.println("\n** FoodID Not Found! **\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//get all feedbacks from the database having with given FoodID
		String sql = "SELECT * FROM feedback WHERE FoodID ='" + foodID + "'";
		
		try {
			statement = connection.createStatement();
			resultSet2 = statement.executeQuery(sql) ;
	
			// Iterate through the result set and construct Feedback objects.
			while(resultSet2.next()) {
				
				int customerID = 0 ;
				
				Feedback newFeedback = new Feedback();
								
				newFeedback.setFeedbackID(resultSet2.getInt("FeedbackID"));
				newFeedback.setCustomerID(resultSet2.getInt("CustomerID"));
				customerID = resultSet2.getInt("CustomerID") ;
				newFeedback.setFoodID(resultSet2.getInt("FoodID"));	
				newFeedback.setStarRating(resultSet2.getInt("StarRating"));
				newFeedback.setTitle(resultSet2.getString("Title"));
				newFeedback.setDescription(resultSet2.getString("Description"));
				
				// Retrieve the Customer name associated with the CustomerID from the Customers table
				String getCustomerName = "SELECT Name FROM customers WHERE CustomerID = '" + customerID  + "'" ;
				statement = connection.createStatement();
				resultSet = statement.executeQuery(getCustomerName);
				
				if (resultSet.next()) { 
			        String customerName = resultSet.getString("Name");
			        newFeedback.setCustomerName(customerName);
			        
			    } else {
			        System.out.println("\n** Customer Not Found! **\n");
			    }
				
				// Add the constructed Feedback object to the feedbackList.
				feedbackList.add(newFeedback);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		// Print all feedbacks retrieved from the database for given food name
		
		System.out.println("==================================================================================================================================================================");
		System.out.println("                                            Feedbacks for " + foodName + "            ");
		System.out.println("==================================================================================================================================================================");
		System.out.println("Feedback Code\tCustomer name\tStar Rating\tTitle\t\t\t\tDescription\t");
		System.out.println("==================================================================================================================================================================");
		for(Feedback feedback : feedbackList) {
			if(feedbackList.isEmpty()) {
				System.out.println("Did not have any feedback yet for this food");
			} else {
				feedback.viewFeedbackByFoodName();
			}
		}
		System.out.println("==================================================================================================================================================================");
	
		
	}

	// method to allow users to update their Feedbacks
	@Override
	public void updateFeedback(int CustomerID) {
		
		int feedbackID = 0;
		
		// Display Customers Feedbacks for identify the Feedback id he/she want to update
		viewMyFeedback(CustomerID);
		
		System.out.print("Enter the Feedback ID you want to Update :");
		feedbackID = scanner.nextInt();
		
		// Display options get choice to what he/she want to update in the database
		System.out.println("What do you want to update?");
		System.out.println("====================================================");
        System.out.println("1. Title");
        System.out.println("2. Description");
        System.out.println("3. Star Rating");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
        int toUpdate = scanner.nextInt();
        
        // handle cases in different update scenarios
        switch (toUpdate) {
        	case 1: 
				try {
					System.out.print("Enter the Updated Feedback Title : ");
					String title = reader.readLine();
					
					// Ask for confirmation before update
			        System.out.print("Are you sure you want to Update this feedback? (yes/no): ");
			        String confirmation = reader.readLine().toLowerCase();
			        
			        if(confirmation.equals("yes")) {
			        	
			        	statement = connection.createStatement();
						String sql = "UPDATE feedback SET Title = '" + title + "' WHERE FeedbackID = '" + feedbackID + "'";
						statement.executeUpdate(sql);
			        	System.out.println("\n** Your Feedback Updated Successfully **\n");
			        	
			        }else {
			        	System.out.println("\n** Feedback Updation Canceled **\n");
			        }
					
					
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
				break;
        	case 2: 
				try {
					System.out.print("Enter the Updated Feedback Description : ");
					String description = reader.readLine();
					
					// Ask for confirmation before update
			        System.out.print("Are you sure you want to Update this feedback? (yes/no): ");
			        String confirmation = reader.readLine().toLowerCase();
			        
			        if(confirmation.equals("yes")) {
			        	
			        	statement = connection.createStatement();
						String sql = "UPDATE feedback SET Description = '" + description + "' WHERE FeedbackID = '" + feedbackID + "'";
						statement.executeUpdate(sql);
			        	System.out.println("\n** Your Feedback Updated Successfully **\n");
			        	
			        }else {
			        	System.out.println("\n** Feedback Updation Canceled **\n");
			        }
					
					
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
				break;
        	case 3: 
				try {
					System.out.print("Enter the Updated Feedback Star Rating : ");
					int starRating =  scanner.nextInt();
					
					// Ask for confirmation before update
			        System.out.print("Are you sure you want to Update this feedback? (yes/no): ");
			        String confirmation = reader.readLine().toLowerCase();
			        
			        if(confirmation.equals("yes")) {
			        	
			        	statement = connection.createStatement();
						String sql = "UPDATE feedback SET StarRating = '" + starRating + "' WHERE FeedbackID = '" + feedbackID + "'";
						statement.executeUpdate(sql);
			        	System.out.println("\n** Your Feedback Updated Successfully **\n");
			        	
			        }else {
			        	System.out.println("\n** Feedback Updation Canceled **\n");
			        }
					
					
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
				break;
        	case 4: 
				break;
			default:
				System.out.println("Invalid Choice !");
				break;
        }
        
	}

}