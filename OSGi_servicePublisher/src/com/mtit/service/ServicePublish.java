package com.mtit.service;

public interface ServicePublish {
    void registerUser(String name, String email, String password, String phone, String address);
    boolean login(String email, String password);
    void viewAccountDetails(int customerID);
    void deleteCustomer(int customerID);
    int getCurrentUserID();
    void editCustomerDetails(int customerID, String newName, String newEmail, String newPassword, String newPhone, String newAddress);
    void logout();
}