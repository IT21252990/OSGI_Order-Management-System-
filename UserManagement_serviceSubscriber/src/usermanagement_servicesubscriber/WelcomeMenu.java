package usermanagement_servicesubscriber;

import feedbackservicesubscriber.FeedbackSubscriber;
import java.util.List;
import java.util.Scanner;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import restaurantsubscriber.RestaurantSubscriber;
import ordermanagementservicepublisher.ServicePublish;
import ordermangementservicesubscriber.ServiceSubscriber;
import usermanagement_servicepublisher.Admin;
import usermanagement_servicepublisher.Customer;
import usermanagement_servicepublisher.ServicePublishImpl;
import usermanagement_servicepublisher.UserServicePublish;

public class WelcomeMenu {
	
	private UserServicePublish userSrServicePublish;
	
	static BundleContext context;
	
	public WelcomeMenu(UserServicePublish userSrServicePublish , BundleContext context) {
		this.userSrServicePublish = userSrServicePublish;
		this.context = context;
	}

	public static void displayWelcomeMenu() {
        Scanner scanner = new Scanner(System.in);
//      ServicePublish service = new ServicePublishImpl(DatabaseUtil.getConnection());

        int choice;
        do {
        	System.out.println("");
        	System.out.println("");
        	System.out.println("");
        	System.out.println("=============================================");
            System.out.println("========== Welcome to Yum Yum Food ==========");
        	System.out.println("=============================================");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Reset Password");
            System.out.println("4. Exit");
            System.out.println("=============================================");
            System.out.print("Enter your option: ");
            choice = scanner.nextInt();

            switch (choice) {
            case 1:
            	System.out.print("");
                register();
                break;
            case 2:
            	System.out.print("");
                login();
                break;
            case 3:
            	System.out.print("");
                resetPassword();
                break;
            case 4:
            	System.out.println("=============================================");
                System.out.println("============ Exiting Yum Yum Food ===========");
            	System.out.println("=============================================");
                return; // exit the method or loop
            default:
                System.out.println("** Invalid option. Please try again **");
                displayWelcomeMenu();
        }
    } while (choice != 3);
       
    }
	
	public static void register() {
        // Retrieve and display menu logic
        Scanner scanner = new Scanner(System.in);

        System.out.println("");
        System.out.println("--------Register to the Yum Yum food---------");
        System.out.println("---------------------------------------------");
        System.out.print("Enter your name   :");
        String name = scanner.nextLine();
        System.out.print("Enter your email  :");
        String email = scanner.nextLine();
        System.out.print("create a password :");
        String password = scanner.nextLine();
        System.out.print("Enter phone number:");
        String phone = scanner.nextLine();
        System.out.print("Enter your address:");
        String address = scanner.nextLine();
        System.out.println("---------------------------------------------");
        UserServicePublish servicePublish = new ServicePublishImpl();
        servicePublish.registerUser(name, email, password, phone, address);
        
        System.out.println("");
        login();
    }
	
	public static void login() {
	        Scanner scanner = new Scanner(System.in);

	        System.out.println("");
	        System.out.println("");
	        System.out.println("--------- Login to Yum Yum Food ----------");
	        System.out.println("-------------------------------------------");
	        System.out.print("Enter email: ");
	        String email = scanner.nextLine();
	        System.out.print("Enter password: ");
	        String password = scanner.nextLine();

	        UserServicePublish service = new ServicePublishImpl();
	        int customerId = service.login(email, password);

	        if (customerId > 0) {
	            System.out.println("You are logged in as a customer.");
	            displayLoggedInMenu(service, customerId);
	        } else if (customerId < 0) {
	            System.out.println("You are logged in as an admin.");
	            displayAdminMenu();
	        } else {
	            System.out.println("Invalid email or password. Please try again.");
	        }
	}
	
	private static void displayLoggedInMenu(UserServicePublish service, int customerId) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        inputLoop:while(true) {
            System.out.println("");
            System.out.println("");
            System.out.println("=============================================");
            System.out.println("--------   ^_^  Yum Yum food  ^_^   ---------");
            System.out.println("=============================================");
            System.out.println("");
            System.out.println("\t1. Menu");
			System.out.println("\t2. My Orders");
			System.out.println("\t3. Feedbacks");
            System.out.println("\t4. View Account Details");
            System.out.println("\t5. Edit Account Details");
            System.out.println("\t6. Delete Account");
            System.out.println("\t7. Logout");
            System.out.println("---------------------------------------------");
            System.out.print("Enter your option    : ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
            	case 1:{
            		ServiceReference<?> orderReference = context.getServiceReference(ServiceSubscriber.class.getName());
            		ServiceSubscriber orderSubscriber = (ServiceSubscriber) context.getService(orderReference);
            		orderSubscriber.displayMenu();
            	}
            	case 2:{
            		ServiceReference<?> orderReference = context.getServiceReference(ServiceSubscriber.class.getName());
            		ServiceSubscriber orderSubscriber = (ServiceSubscriber) context.getService(orderReference);
            		orderSubscriber.callToDisplayMyOrders(customerId);
            	}
            	case 3:{
            		ServiceReference<?> feedbackReference = context.getServiceReference(FeedbackSubscriber.class.getName());
            		FeedbackSubscriber feedbackSubscriber = (FeedbackSubscriber) context.getService(feedbackReference);
            		feedbackSubscriber.startMenu(customerId);
            	}
                case 4:
                    System.out.println("");
                    System.out.println("--------------  My Account  -----------------");
                    System.out.println("---------------------------------------------");
                    service.viewAccountDetails(customerId); // Pass the customer ID
                    break;
                case 5:
                    // Edit account details implementation
                    System.out.println("");
                    System.out.println("------------  Update Account  ---------------");
                    System.out.println("---------------------------------------------");
                    System.out.print("Enter new name       :");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new email      :");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter phone number   :");
                    String newPhone = scanner.nextLine();
                    System.out.print("Enter address        :");
                    String newAddress = scanner.nextLine();
                    service.updateCurrentUserDetails(customerId, newName, newEmail, newPhone, newAddress);
                    break;

                case 6:

                    service.deleteCurrentUser(customerId);
                    System.out.println("**    Your account is not exist **");
                    System.out.println("---------------------------------------------");
                    break inputLoop;
                case 7:
                    System.out.println("** Log out successfully **");
                    System.out.println("---------------------------------------------");
                    break inputLoop;
                default:
                    System.out.println("** Please Enter a Valid Input. **");
                    continue inputLoop;
            }
        }
    }
	
	public static void resetPassword() {
	    Scanner scanner = new Scanner(System.in);
	    
//	    boolean loggedIn = false;
	    
	    System.out.println("");
	    System.out.println("---------       Reset password    -----------");
	    System.out.println("---------------------------------------------");
	    System.out.print("Enter email			:");
	    String email = scanner.nextLine();
	    System.out.print("Enter phone number	:");
	    String phone = scanner.nextLine();
	    System.out.print("Enter a new password	:");
	    String password = scanner.nextLine();

	    UserServicePublish service = new ServicePublishImpl();
	    service.resetPassword(email, phone,password);
	    
	    System.out.println("---------------------------------------------");
	    System.out.println("");
	    displayWelcomeMenu();
	}
	
	//admin
	public static void displayAdminMenu() {
	    Scanner scanner = new Scanner(System.in);
	//  ServicePublish service = new ServicePublishImpl(DatabaseUtil.getConnection());
	    UserServicePublish servicePublish = new ServicePublishImpl();
	   
	    
	    int choice;
	    inputLoop:while(true) {
	    	System.out.println("=============================================");
	        System.out.println("============ CUSTOMER ADMIN PAGE ============");
	    	System.out.println("=============================================");
	    	System.out.println("1. Restaurant Dashboard");
	        System.out.println("2. View Customers Details");
	        System.out.println("3. View Admin Account Details");
	        System.out.println("4. Logout");
	        System.out.println("=============================================");
	        System.out.print("Enter your option: ");
	        choice = scanner.nextInt();

	        switch (choice) {
	        
		        case 1:{
		        	ServiceReference<?> restaurantReference = context.getServiceReference(RestaurantSubscriber.class.getName());
		        	RestaurantSubscriber restaurantSubscriber = (RestaurantSubscriber) context.getService(restaurantReference);
		        	restaurantSubscriber.startMenu();
		        }
		        
		        case 2:
		        	System.out.println("");
		            System.out.println("============ CUSTOMERS DETAILS ============");
		            List<Customer> customers = servicePublish.getAllCustomers();
		            for (Customer customer : customers) {
		                System.out.println("---------------------------------------------");
		                System.out.println("Customer ID: " + customer.getCustomerId());
		                System.out.println("Name: " + customer.getName());
		                System.out.println("Email: " + customer.getEmail());
		                System.out.println("Phone: " + customer.getPhone());
		                System.out.println("Address: " + customer.getAddress());
		            }
		            break;
		        case 3:
		        	System.out.println("");
		            System.out.println("============== ADMIN DETAILS ==============");
		            List<Admin> admin = servicePublish.getAdmins();
		            for (Admin admin1 : admin) {
		                System.out.println("---------------------------------------------");
		                System.out.println("Admin ID: " + admin1.getAdminId());
		                System.out.println("Name: " + admin1.getAdminName());
		                System.out.println("Email: " + admin1.getAdminEmail());
		                System.out.println("Phone: " + admin1.getAdminPhone());
		                System.out.println("Address: " + admin1.getAdminAddress());
		            }
		            break;
		        case 4:
		        	System.out.println("** Log out successfully **");
		            System.out.println("---------------------------------------------");
		            break inputLoop;
		        default:
		            System.out.println("** Invalid option. Please try again **");
		            continue inputLoop;
	        }
	} 
	   
	}
	
}
