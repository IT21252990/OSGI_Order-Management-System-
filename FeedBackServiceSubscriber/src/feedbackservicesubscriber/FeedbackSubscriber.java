package feedbackservicesubscriber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import feedbackservicepublisher.FeedbackService;

public class FeedbackSubscriber {
	
	private FeedbackService feedbackService;
	private int CustomerID ;
	
	public FeedbackSubscriber(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
		CustomerID = 1;
	}
	
	public void startMenu() {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String userInput = "" ;
		
		// Loop to continuously display the menu and handle user input.
		inputLoop:while(true) {
			
			displayMenu();
			
			try {
				userInput = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch(userInput) {
			case "1":{
				feedbackService.giveFeedback(CustomerID);
				break;
			}
			case "2":{
				feedbackService.viewMyFeedback(CustomerID);
				break;
			}
			case "3":{
				feedbackService.deleteFeedback(CustomerID);
				break;
			}
			case "4":{
				break inputLoop;
			}
			default:{
				System.out.println("Enter 1 for give new feedback or enter 2 for exit\n");
				continue inputLoop;
			}
			}
			
		}
		
	}
	
	public void displayMenu() {
		System.out.println("======================================================");
		System.out.println("         \" Yum-Yum \" -  Food Ordering System        ");
		System.out.println("======================================================");
		System.out.println("                  Feedback Dashboard                  ");
		System.out.println("======================================================");
		System.out.println("   		1. Send Feedback");
		System.out.println("		2. View My Feedbacks");
		System.out.println("		3. Delete Feedback");
		System.out.println("		4. Exit");
		System.out.println("======================================================");
		System.out.print("Enter your choice: ");
	}
	
	

}
