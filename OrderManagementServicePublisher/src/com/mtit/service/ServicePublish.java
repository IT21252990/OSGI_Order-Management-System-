package com.mtit.service;

import java.sql.SQLException;
import java.util.List;

public interface ServicePublish {

	public String publishService();
	
	void displayAppertizers();
    void displayMainMeals();
    void displayDesserts();
    void displayDrinks();

	public String getFoodName(int foodID);

	public float getPrice(int foodID);

	void addOrderToDB(int customerID, float totalAmount, int[][] cart);
    
	public List<Integer> displayFoodItems(String category) ;

	
}
