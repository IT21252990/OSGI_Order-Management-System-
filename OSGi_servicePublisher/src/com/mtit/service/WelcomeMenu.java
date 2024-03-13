package com.mtit.service;

import java.util.Scanner;

public class WelcomeMenu {
    public static void displayWelcomeMenu() {
        Scanner scanner = new Scanner(System.in);
//        ServicePublish service = new ServicePublishImpl(DatabaseUtil.getConnection());

        int choice;
    
            System.out.println("=== Welcome to Yum Yum Food ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your option: ");

            choice = scanner.nextInt();
            choice = scanner.nextInt(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("register");
                    break;
                case 2:
                    System.out.println("login");
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
       
    }

    private static void registerUser(ServicePublish service, Scanner scanner) {
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter phone number:");
        String phone = scanner.nextLine();
        System.out.println("Enter address:");
        String address = scanner.nextLine();

        service.registerUser(name, email, password, phone, address);
        System.out.println("Registered successfully.");
    }

    private static void login(ServicePublish service, Scanner scanner) {
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (service.login(email, password)) {
            System.out.println("Logged in successfully.");
            displayLoggedInMenu(service, scanner);
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private static void displayLoggedInMenu(ServicePublish service, Scanner scanner) {
        int choice;
        do {
            System.out.println("=== Logged In Menu ===");
            System.out.println("1. View Account Details");
            System.out.println("2. Edit Account Details");
            System.out.println("3. Delete Account");
            System.out.println("4. Logout");
            System.out.print("Enter your option: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    int customerID = service.getCurrentUserID();
//                    service.viewCustomerDetails(customerID);
                    break;
                case 2:
                    editAccountDetails(service, scanner);
                    break;
                case 3:
                    int deleteCustomerID = service.getCurrentUserID();
                    service.deleteCustomer(deleteCustomerID);
                    break;
                case 4:
                    service.logout();
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        } while (choice != 4);
    }

    private static void editAccountDetails(ServicePublish service, Scanner scanner) {
        int customerID = service.getCurrentUserID();
        System.out.println("Enter new name:");
        String newName = scanner.nextLine();
        System.out.println("Enter new email:");
        String newEmail = scanner.nextLine();
        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();
        System.out.println("Enter new phone number:");
        String newPhone = scanner.nextLine();
        System.out.println("Enter new address:");
        String newAddress = scanner.nextLine();

        service.editCustomerDetails(customerID, newName, newEmail, newPassword, newPhone, newAddress);
        System.out.println("Account details updated successfully.");
    }
}
