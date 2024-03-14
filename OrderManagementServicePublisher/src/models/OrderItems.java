package models;

public class OrderItems {
	
	private int OrderID; 
	private int FoodID;
	private int Quantity; 
	
	public OrderItems() {}

	public OrderItems(int orderID, int foodID, int quantity) {
		super();
		OrderID = orderID;
		FoodID = foodID;
		Quantity = quantity;
	}

	public int getOrderID() {
		return OrderID;
	}

	public int getFoodID() {
		return FoodID;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setOrderID(int orderID) {
		OrderID = orderID;
	}

	public void setFoodID(int foodID) {
		FoodID = foodID;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	
	public void viewOrder() {
		System.out.println(" " + OrderID + "\t\t" + FoodID + "\t"+ Quantity);
	}
}
