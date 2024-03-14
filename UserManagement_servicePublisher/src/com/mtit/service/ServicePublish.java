package com.mtit.service;

import java.util.List;

public interface ServicePublish {
    String publishService();
    void registerUser(String name, String email, String password, String phone, String address);
    int login(String email, String password);
    int getCurrentUserID();
    void viewAccountDetails(int customerId);
    boolean deleteCurrentUser(int customerId);
    void updateCurrentUserDetails(int customerId, String newName, String newEmail, String newPhone, String newAddress);
	void resetPassword(String email, String phone, String password);
	public List<Customer> getAllCustomers();
	public List<Admin> getAdmins();
}
