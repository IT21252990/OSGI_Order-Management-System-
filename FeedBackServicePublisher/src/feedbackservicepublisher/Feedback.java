package feedbackservicepublisher;

public class Feedback {
	
	private int FeedbackID ;
	private int CustomerID;
	private String CustomerName;
	private int FoodID;
	private String FoodName;
	private int StarRating;
	private String Title;
	private String Description;
	
	public int getFeedbackID() {
		return FeedbackID;
	}
	
	public void setFeedbackID(int FeedbackID) {
		this.FeedbackID = FeedbackID;
	}
	
	public int getCustomerID() {
		return CustomerID;
	}
	
	public void setCustomerID(int CustomerID) {
		this.CustomerID = CustomerID;
	}
	
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String CustomerName) {
		this.CustomerName = CustomerName;
	}
	
	public int getFoodID() {
		return FoodID;
	}
	public void setFoodID(int FoodID) {
		this.FoodID = FoodID;
	}
	
	public String getFoodName() {
		return FoodName;
	}
	public void setFoodName(String FoodName) {
		this.FoodName = FoodName;
	}
	
	public int getStarRating() {
		return StarRating;
	}
	
	public void setStarRating(int starRating) {
		StarRating = starRating;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String Title) {
		this.Title = Title;
	}
	public String getDescription() {
		return Description;
	}
	
	public void setDescription(String Description) {
		this.Description = Description;
	}
	
	public void viewFeedback() {
		System.out.println(" FB " + FeedbackID + "\t\tFN " + FoodID + "\t\t"+ FoodName + "\t\t" + StarRating + "\t\t" + Title + "\t\t" + Description + "\t");
	}
	
	public void viewFeedbackByFoodName() {
		System.out.println(" FB" + FeedbackID + "\t\t"+ CustomerName + "\t\t" + StarRating + "\t\t" + Title + "\t\t" + Description + "\t");
	}

}
