package ordermanagementservicepublisher;

public interface ServicePublish {
	
	public String publishService();

	public String getFoodName(int foodID);

	public float getPrice(int foodID);

	void addOrderToDB(int customerID, float totalAmount, int[][] cart);
    
	public void displayFoodItems(String category) ;

	public void displayMyOrders( int CustomerID);
	
	public void orderItemSet ( int OrderID );

}
