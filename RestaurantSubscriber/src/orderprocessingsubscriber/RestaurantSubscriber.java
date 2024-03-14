package orderprocessingsubscriber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import orderprocessingpublisher.RestaurantPublish;

public class RestaurantSubscriber {

	private RestaurantPublish restaurantPublish;
	
	public RestaurantSubscriber(RestaurantPublish restaurantPublish) {
		this.restaurantPublish = restaurantPublish;
	}
	
	
	public void startMenu() {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			String userInput = "" ;
			
			inputLoop:while(true) {
				
				displayMenu();
				
				try {
					userInput = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				switch(userInput) {
				case "1":{
					restaurantPublish.viewFoods();
					break;
				}
				case "2":{
					restaurantPublish.addFood();
					break;
				}
				case "3":{
					restaurantPublish.deleteFood();
					break;
				}
				case "4":{
					restaurantPublish.viewOrders();
					break;
				}
				case "5":{
					restaurantPublish.acceptOrder();
					break;
				}
				case "6":{
					restaurantPublish.rejectOrder();
					break;
				}
				case "7":{
					restaurantPublish.markAsComplete();
					break;
				}
				case "8":{
					break inputLoop;
				}
				default:{
					System.out.println("Enter 1 to view orders or enter 2 to exit\n");
					continue inputLoop;
				}
				}
				
			}
			
		}
		
		public void displayMenu() {
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("======================================================");
			System.out.println("         \" Yum-Yum \" -  Restaurant System        ");
			System.out.println("======================================================");
			System.out.println("                       Dashboard                    ");
			System.out.println("======================================================");
			System.out.println("   		1. View All Food Items");
			System.out.println("		2. Add Food Item");
			System.out.println("		3. Remove Food Item");
			System.out.println("   		4. View All Orders");
			System.out.println("		5. Accept Order");
			System.out.println("		6. Reject Order");
			System.out.println("		7. Complete Order");
			System.out.println("		8. Exit");
			System.out.println("======================================================");
			System.out.print("Enter your choice: ");
		}
		
}
