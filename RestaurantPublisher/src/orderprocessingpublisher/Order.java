package orderprocessingpublisher;

public class Order {

	private int OrderID ;
	private int CustomerID;
	private String Status;
	private String OrderPrice;
	
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		this.OrderID = orderID;
	}
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		this.CustomerID = customerID;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		this.Status = status;
	}
	public String getOrderPrice() {
		return OrderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.OrderPrice = orderPrice;
	}
	public void viewOrder() {
		System.out.println(" " + OrderID + "\t\t" + CustomerID + "\t\t"+ Status + "\t\t" + OrderPrice + "\t");
	}
	
	
	
}
