package ordermangementservicesubscriber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.mtit.service.ServicePublish;

public class ServiceSubscriber {
	
	private ServicePublish servicePublish;
	
	private int customerID ;
	
	private int[][] cart; // Array to store food ID and quantity
	
	private float totalAmount = 0;
	
	public ServiceSubscriber(ServicePublish servicePublish) {
		this.servicePublish = servicePublish;
		customerID = 1;
		cart = new int[20][3]; // Initialize the cart with a size of 10 items (food ID , quantity and price)
    
	}
	
	public void startMenu() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		Scanner myScanner = new Scanner(System.in);
		
		String userInput = "" ;
		
		// Loop to continuously display the menu and handle user input.
		inputLoop:while(true) {
			
			System.out.println("\n\n\n======================================================");
			System.out.println("         \" Yum-Yum \" -  Food Ordering System        ");
			System.out.println("======================================================\n");
			System.out.println("   		1. Menu");
			System.out.println("		2. My Orders");
			System.out.println("		2. Account");
			System.out.println("		2. Feedbacks");
			System.out.println("		3. Exit");
			System.out.println("\n======================================================\n");
			System.out.print("\tEnter your choice: ");
			
			
			try {
				userInput = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch(userInput) {
			case "1":{
				 displayMenu();
				break;
			}
			case "2":{
				// Implement past orders functionality
				break;
			}
			case "3":{
				break inputLoop;
			}
			default:{
                System.out.println("Invalid option. Please try again.");
				continue inputLoop;
			}
			}
			
		}
		
		
		
	}
	
	public void displayMenu() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		Scanner myScanner = new Scanner(System.in);
		
		String userInput = "" ;
//		int foodID = 0 ;
//		int quantity = 0 ;
		
		// Loop to continuously display the menu and handle user input.
		inputLoop:while(true) {
			
			System.out.println("\n\n======================================================");
			System.out.println("         \" Yum-Yum \" -  Food Ordering System        ");
			System.out.println("======================================================");
			System.out.println("                              Menu                  ");
			System.out.println("======================================================\n");
			System.out.println("   		1. Appertizers");
			System.out.println("		2. Main Meals");
			System.out.println("		3. Desserts");
			System.out.println("		4. Drinks");
			System.out.println("		5. View Cart");
			System.out.println("		6. Exit");
			System.out.println("\n======================================================\n");
			System.out.print("\tEnter your choice: ");			
			
			try {
				userInput = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch(userInput) {
			case "1":{
				servicePublish.displayAppertizers();
				break;
			}
			case "2":{
				servicePublish.displayMainMeals();
				break;
			}
			case "3":{
				servicePublish.displayDesserts();
				break;
			}
			case "4":{
				servicePublish.displayDrinks();
				break;
			}
			case "5":{
				viewCart();
				System.out.println("\n\n");
				displayMenu();
				break;
			}

			case "6":{
				break inputLoop;
			}
			default:{
                System.out.println("\nInvalid option. Please try again.\n");
				continue inputLoop;
			}
			}
			
	            System.out.print("\n\tEnter the ID of the food you want: ");
	            int foodID = myScanner.nextInt();
	     
            	System.out.print("E\n\tnter the Quantity: ");
                int quantity = myScanner.nextInt();
          
            float price = servicePublish.getPrice(foodID);
            addToCart(foodID, quantity, price);
            
            //Recursion function to add more items to the cart
            try {
            	System.out.print("\n\n\tDo you want to get more food ?  (Yes/No)");
				String more = reader.readLine();
				
				if (more.equalsIgnoreCase("no")) {
	                break;
	            } else if (more.equalsIgnoreCase("yes")) {
	            	displayMenu();
	            }
				
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
            
			
		}
		
		//view the cart
		System.out.println("\nSelected Food Items:");
        System.out.println("--------------------");
		viewCart();	
		
		
		//Add to db
		 try {
         	System.out.print("\n\n Please confirm your order :  (Yes/No)");
				String more = reader.readLine();
				
				if (more.equalsIgnoreCase("no")) {
	                System.out.println("Redirecting to the menu .... ");
	                displayMenu();
	            } else if (more.equalsIgnoreCase("yes")) {
	            	addOrderToDB( customerID, totalAmount , cart );
	            	startMenu();
	            }
				
         } catch (IOException | NumberFormatException e) {
             e.printStackTrace();
         }
	}
	
	 private void addToCart(int foodID, int quantity, float price) {
	        for (int i = 0; i < cart.length; i++) {
	            if (cart[i][0] == 0) {
	                cart[i][0] = foodID; // Food ID
	                cart[i][1] = quantity; // Quantity
	                cart[i][2] = (int) price; // Price
	                break;
	            }
	        }
	 }
	
	 public void viewCart() {
		 float tot = 0 ; 
	     System.out.println("Items in your cart:");
        for (int i = 0; i < cart.length; i++) {
            if (cart[i][0] == 0)
                break;
            int foodID = cart[i][0];
            int quantity = cart[i][1];
            float price = servicePublish.getPrice(foodID);
            String foodName = servicePublish.getFoodName(foodID);
            System.out.println("\t" + foodName + " x" + quantity + ": Rs. " + (price * quantity));
            tot += price * quantity;
        }
        totalAmount = tot;
        System.out.println("\n\tTotal Amount: Rs. " + totalAmount);
	 }
	 
	 
	 public void addOrderToDB(int customerID, float totalAmount, int[][] cart) {
		    servicePublish.addOrderToDB(customerID, totalAmount, cart);
		}

}
