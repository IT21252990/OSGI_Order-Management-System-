package ordermanagementservicepublisher;

public class Order {
	
	private int OrderID;
	private int CustomerID ;
	private String Status ;
	private float OrderPrice ;
	
	public Order() {}
	
	public Order(int orderID, int customerID, String status, float orderPrice) {
		super();
		OrderID = orderID;
		CustomerID = customerID;
		Status = status;
		OrderPrice = orderPrice;
	}
	
	public int getOrderID() {
		return OrderID;
	}
	public int getCustomerID() {
		return CustomerID;
	}
	public String getStatus() {
		return Status;
	}
	public float getOrderPrice() {
		return OrderPrice;
	}
	public void setOrderID(int orderID) {
		OrderID = orderID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public void setOrderPrice(float orderPrice) {
		OrderPrice = orderPrice;
	}
	
	public void viewOrder() {
		System.out.println(" " + OrderID + "\t\t" + Status + "\t"+ OrderPrice );
	}

}
