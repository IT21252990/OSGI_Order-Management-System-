package com.mtit.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicePublishImpl implements ServicePublish {
    private Connection connection;

    public ServicePublishImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void registerUser(String name, String email, String password, String phone, String address) {
        try {
            String query = "INSERT INTO customers (Name, Email, Password, Phone, Address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, phone);
            statement.setString(5, address);
            statement.executeUpdate();
            System.out.println("Registered successfully.");
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }
    }

    @Override
    public boolean login(String email, String password) {
        try {
            String query = "SELECT CustomerID FROM customers WHERE Email = ? AND Password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void viewAccountDetails(int customerID) {
        try {
            String query = "SELECT * FROM customers WHERE CustomerID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Customer ID: " + resultSet.getInt("CustomerID"));
                System.out.println("Name: " + resultSet.getString("Name"));
                System.out.println("Email: " + resultSet.getString("Email"));
                System.out.println("Phone: " + resultSet.getString("Phone"));
                System.out.println("Address: " + resultSet.getString("Address"));
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing account details: " + e.getMessage());
        }
    }

  
//    public void editAccountDetails(int customerID, String newName, String newEmail, String newPassword, String newPhone, String newAddress) {
//        try {
//            String query = "UPDATE customers SET Name = ?, Email = ?, Password = ?, Phone = ?, Address = ? WHERE CustomerID = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, newName);
//            statement.setString(2, newEmail);
//            statement.setString(3, newPassword);
//            statement.setString(4, newPhone);
//            statement.setString(5, newAddress);
//            statement.setInt(6, customerID);
//            statement.executeUpdate();
//            System.out.println("Account details updated successfully.");
//        } catch (SQLException e) {
//            System.out.println("Error updating account details: " + e.getMessage());
//        }
//    }

//    @Override
//    public void deleteAccount(int customerID) {
//        try {
//            String query = "DELETE FROM customers WHERE CustomerID = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setInt(1, customerID);
//            statement.executeUpdate();
//            System.out.println("Account deleted successfully.");
//        } catch (SQLException e) {
//            System.out.println("Error deleting account: " + e.getMessage());
//        }
//    }

    @Override
    public void deleteCustomer(int customerID) {
        try {
            // Delete the customer record associated with the given CustomerID
            String deleteCustomerQuery = "DELETE FROM customers WHERE CustomerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteCustomerQuery);
            preparedStatement.setInt(1, customerID);
            preparedStatement.executeUpdate();
            System.out.println("\n** Account Deleted Successfully **\n");
        } catch (SQLException e) {
            System.out.println("Error deleting account: " + e.getMessage());
        }
    }

    @Override
    public void logout() {
        System.out.println("Logged out successfully.");
    }


	@Override
	public int getCurrentUserID() {
	    int userID = -1; // Default value indicating no user ID found

	    try {
	        // Implement the logic to retrieve the current user's ID from the database
	        String query = "SELECT CustomerID FROM customers WHERE Email = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
//	        preparedStatement.setString(1, currentUserEmail); // Assuming currentUserEmail is the current user's email
	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            userID = resultSet.getInt("CustomerID");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving current user ID: " + e.getMessage());
	    }

	    return userID;
	}


	public void editCustomerDetails(int customerID, String newName, String newEmail, String newPassword, String newPhone, String newAddress) {
	    try {
	        // Update the customer's information in the database
	        String updateQuery = "UPDATE customers SET Name = ?, Email = ?, Password = ?, Phone = ?, Address = ? WHERE CustomerID = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
	        preparedStatement.setString(1, newName);
	        preparedStatement.setString(2, newEmail);
	        preparedStatement.setString(3, newPassword);
	        preparedStatement.setString(4, newPhone);
	        preparedStatement.setString(5, newAddress);
	        preparedStatement.setInt(6, customerID);
	        preparedStatement.executeUpdate();
	        
	        System.out.println("\n** Account Details Updated Successfully **\n");
	    } catch (SQLException e) {
	        System.out.println("Error updating account details: " + e.getMessage());
	    }
	}

}
