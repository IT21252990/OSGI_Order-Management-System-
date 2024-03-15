package ordermangementservicesubscriber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import ordermanagementservicepublisher.ServicePublish;

public class ServiceSubscriber {
		
	private ServicePublish orderPublish;
	
	private int customerID ;
	
	private int[][] cart; // Array to store food ID and quantity
	
	private float totalAmount = 0;
	
	public ServiceSubscriber(ServicePublish orderPublish) {
		this.orderPublish = orderPublish;
		customerID = 1;
		cart = new int[20][3]; // Initialize the cart with a size of 10 items (food ID , quantity and price)
	}
	
	public void startMenu() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		Scanner myScanner = new Scanner(System.in);
		
		String userInput = "" ;
		
		System.out.println("\n\n\n======================================================");
		System.out.println("         \" Yum-Yum \" -  Food Ordering System        ");
		System.out.println("======================================================\n");
		
		// Loop to continuously display the menu and handle user input.
		inputLoop:while(true) {
			
			System.out.println("\n------------------------------------------------------\n");		
			System.out.println("   		1. Menu");
			System.out.println("		2. My Orders");
			System.out.println("		3. Exit");
			System.out.println("\n------------------------------------------------------\n");
			System.out.print("\t~ Enter your choice: ");
			
			
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
//				servicePublish.displayMyOrders(customerID);
				callToDisplayMyOrders(customerID);
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
	
	public void callToDisplayMyOrders(int customerID) {
		orderPublish.displayMyOrders(customerID);
	}
	
	public void displayMenu() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		Scanner myScanner = new Scanner(System.in);
		
		String userInput = "" ;
		
		// Loop to continuously display the menu and handle user input.
		inputLoop:while(true) {
			
			System.out.println("\n------------------------------------------------------");
			System.out.println("                          Menu                  ");
			System.out.println("------------------------------------------------------\n");
			System.out.println("   		1. Appertizers");
			System.out.println("		2. Main Meals");
			System.out.println("		3. Desserts");
			System.out.println("		4. Drinks");
			System.out.println("		5. View Cart");
			System.out.println("		6. Exit");
			System.out.println("\n------------------------------------------------------\n");
			System.out.print("\t~ Enter your choice: ");			
			
			try {
				userInput = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch(userInput) {
			case "1":{
				orderPublish.displayFoodItems("Appetizer");
				break;
			}
			case "2":{
				orderPublish.displayFoodItems("Mains");
				break;
			}
			case "3":{
				orderPublish.displayFoodItems("Desserts");
				break;
			}
			case "4":{
				orderPublish.displayFoodItems("Drinks");
				break;
			}
			case "5":{
				System.out.println("\n\n");
				startMenu();
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
			
	            System.out.print("\n\t~ Enter the foodID to add to the cart : ");
	            int foodID = myScanner.nextInt();
	     
            	System.out.print("\n\t~ Enter the Quantity: ");
                int quantity = myScanner.nextInt();
          
            float price = orderPublish.getPrice(foodID);
            addToCart(foodID, quantity, price);
            
            //Recursion function to add more items to the cart
            try {
            	System.out.print("\n\n\t~ Do you want to get more food ?  (Yes/No)");
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
		System.out.println("\n---------------------------");
		System.out.println(" Selected Food Items:");
        System.out.println("---------------------------");
		viewCart();	
		
		
		//Add to db
		 try {
         	System.out.print("\n\n\t ~ Please confirm your order :  (Yes/No)");
				String more = reader.readLine();
				
				if (more.equalsIgnoreCase("no")) {
	                System.out.println(" Redirecting to the menu .... ");
	                viewCart();
	                startMenu();
	            } else if (more.equalsIgnoreCase("yes")) {
	            	addOrderToDB( customerID, totalAmount , cart );
	            	emptyCart(); 
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
	     System.out.println("\n Items in your cart:");
        for (int i = 0; i < cart.length; i++) {
            if (cart[i][0] == 0)
                break;
            int foodID = cart[i][0];
            int quantity = cart[i][1];
            float price = orderPublish.getPrice(foodID);
            String foodName = orderPublish.getFoodName(foodID);
            System.out.println("\t" + foodName + " x" + quantity + ": Rs. " + (price * quantity));
            tot += price * quantity;
        }
        totalAmount = tot;
        System.out.println("\n\tTotal Amount: Rs. " + totalAmount);
	 }
	 
	 public void emptyCart() {
		    for (int i = 0; i < cart.length; i++) {
		        for (int j = 0; j < cart[i].length; j++) {
		            cart[i][j] = 0; 
		        }
		    }
		}
	 
	 
	 public void addOrderToDB(int customerID, float totalAmount, int[][] cart) {
		 if (totalAmount == 0 ) {
			 System.out.println("Please add food items to the cart before confirming the order ! \n");
		 }
		 else {
			 orderPublish.addOrderToDB(customerID, totalAmount, cart);
		 }
		   
		}

}
