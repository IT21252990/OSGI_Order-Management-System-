package com.mtit.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServicePublishImpl implements ServicePublish {

	private Customer currentUser;
	
    @Override
    public String publishService() {
        // This method should return the menu or service provided by the system
        return "Let's order food";
    }

    @Override
    public void registerUser(String name, String email, String password, String phone, String address) {
        Connection connection = DatabaseUtil.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO customers (Name, Email, Password, Phone, Address) VALUES ('" + name + "', '" + email + "', '" + password + "', '" + phone + "', '" + address + "')";
            statement.executeUpdate(sql);
            System.out.println("-------You are Registered successfully-------");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("** Your Registration unsuccessfull **");
        }
    }
    
    
    @Override
    public int login(String email, String password) {
        Connection connection = DatabaseUtil.getConnection();
        try {
            // Check if the provided credentials belong to a customer
            PreparedStatement customerStatement = connection.prepareStatement(
                "SELECT CustomerID FROM customers WHERE Email = ? AND Password = ?"
            );
            customerStatement.setString(1, email);
            customerStatement.setString(2, password);
            ResultSet customerResultSet = customerStatement.executeQuery();
            if (customerResultSet.next()) {
                // Return the customerId if the user is a customer
                return customerResultSet.getInt("CustomerID");
            }

            // Check if the provided credentials belong to an admin
            PreparedStatement adminStatement = connection.prepareStatement(
                "SELECT AdminID FROM Admin WHERE AdminEmail = ? AND AdminPassword = ?"
            );
            adminStatement.setString(1, email);
            adminStatement.setString(2, password);
            ResultSet adminResultSet = adminStatement.executeQuery();
            if (adminResultSet.next()) {
                // Return a negative value to indicate that the user is an admin
                return -1 * adminResultSet.getInt("AdminID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        // Return 0 to indicate login failure
        return 0;
    }



    
    
    
    @Override
    public int getCurrentUserID() {
        int userId = 0; // Default value if user ID is not found
        Connection connection = DatabaseUtil.getConnection();
        try {
            // Query to get the current user ID
            String query = "SELECT UserID FROM users WHERE ..."; // Replace ... with your query conditions
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("UserID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection();
        }
        return userId;
    }
    
   
    
     // Assuming you have a currentUser field

    @Override
    public void viewAccountDetails(int customerId) {
        Customer currentUser = getCustomerDetails(customerId);
        if (currentUser != null) {
            
            // Display the account details of the current user
            System.out.println("|	Username	| 	" + currentUser.getName() );
            System.out.println("|	Email		| 	" + currentUser.getEmail());
            System.out.println("|	Phone		| 	" + currentUser.getPhone());
            System.out.println("|	Address		| 	" + currentUser.getAddress());
            // Add additional fields as needed
        } else {
            System.out.println("User not found.");
        }
    }
    
    public Customer getCustomerDetails(int customerId) {
        Connection connection = DatabaseUtil.getConnection();
        Customer customer = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM customers WHERE CustomerID = ?"
            );
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Create a new Customer object and populate its fields
                customer = new Customer();
                customer.setCustomerId(resultSet.getInt("CustomerID"));
                customer.setName(resultSet.getString("Name"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setPhone(resultSet.getString("Phone"));
                customer.setAddress(resultSet.getString("Address"));
                // Add additional fields as needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving customer details.");
        } 
        return customer;
    }

    
    @Override
    public boolean deleteCurrentUser(int customerId) {
        Connection connection = DatabaseUtil.getConnection();
        try {
            // Retrieve the user's details before deleting for confirmation message
            Customer customerToDelete = getCustomerDetails(customerId);
            if (customerToDelete != null) {
                System.out.println("Are you sure you want to delete your account? (yes/no)");
                Scanner scanner = new Scanner(System.in);
                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("yes")) {
                    PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM customers WHERE CustomerID = ?"
                    );
                    preparedStatement.setInt(1, customerId);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("User deleted successfully.");
                        return true;
                    } else {
                        System.out.println("User not found or unable to delete.");
                    }
                } else {
                    System.out.println("Deletion canceled.");
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting user.");
        } 
        return false;

    }



    
    @Override
    public void updateCurrentUserDetails(int customerId, String newName, String newEmail, String newPhone, String newAddress) {
    	Scanner scanner = new Scanner(System.in);
    	Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE customers SET Name = ?, Email = ?, Phone = ?, Address = ? WHERE CustomerID = ?"
            );
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newEmail);
            preparedStatement.setString(3, newPhone);
            preparedStatement.setString(4, newAddress);
            preparedStatement.setInt(5, customerId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User details updated successfully.");
            } else {
                System.out.println("User not found or unable to update details.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating user details.");
        } 
    }
    
    @Override
    public void resetPassword(String email, String phone, String password) {
        Connection connection = DatabaseUtil.getConnection();
        
        try {
            // Check if the email and phone combination exists in the database
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT CustomerID FROM customers WHERE Email = ? AND Phone = ?"
            );
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                // If the email and phone combination exists, update the password
                int customerId = resultSet.getInt("CustomerID");
                PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE customers SET Password = ? WHERE CustomerID = ?"
                );
                updateStatement.setString(1, password);
                updateStatement.setInt(2, customerId);
                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Password updated successfully.");
                    System.out.println("");
                } else {
                    System.out.println("Unable to update password.");
                }
            } else {
                // If no matching record found for the email and phone combination
                System.out.println("No user found with the provided email and phone combination.");
                System.out.println("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM customers"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("CustomerID"));
                customer.setName(resultSet.getString("Name"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setPhone(resultSet.getString("Phone"));
                customer.setAddress(resultSet.getString("Address"));
                // Add additional fields as needed
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving customers details.");
        } 
        return customers;
    }

    
    @Override
    public List<Admin> getAdmins() {
        List<Admin> admin = new ArrayList<>();
        Connection connection = DatabaseUtil.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM Admin"
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Admin customer = new Admin();
                customer.setAdminId(resultSet.getInt("AdminID"));
                customer.setAdminName(resultSet.getString("AdminName"));
                customer.setAdminEmail(resultSet.getString("AdminEmail"));
                customer.setAdminPhone(resultSet.getString("AdminPhone"));
                customer.setAdminAddress(resultSet.getString("AdminAddress"));
                // Add additional fields as needed
                admin.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving admin details.");
        }
        return admin;
    }
    
//    
//    public int getCurrentUserAdminID() {
//        int userId = 0; // Default value if user ID is not found
//        Connection connection = DatabaseUtil.getConnection();
//        try {
//            // Query to get the current user ID
//            String query = "SELECT AdminID FROM Admin WHERE ..."; // Replace ... with your query conditions
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                userId = resultSet.getInt("UserID");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } 
//        return userId;
//    }
//    
//    //view admin account
//    public void getAdminDetails(int customerId) {
//        Admin currentUser = getAdminDetails(customerId);
//        if (currentUser != null) {
//            
//            // Display the account details of the current user
//            System.out.println("|	Username	| 	" + currentUser.getName() );
//            System.out.println("|	Email		| 	" + currentUser.getEmail());
//            System.out.println("|	Phone		| 	" + currentUser.getPhone());
//            System.out.println("|	Address		| 	" + currentUser.getAddress());
//            // Add additional fields as needed
//        } else {
//            System.out.println("User not found.");
//        }
//    }

}
