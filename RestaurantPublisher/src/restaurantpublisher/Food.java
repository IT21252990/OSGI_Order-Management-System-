package restaurantpublisher;

public class Food {
	
	private int FoodID ;
	private String FoodName;
	private String Description;
	private String Category;
	private String Price;
	public int getFoodID() {
		return FoodID;
	}
	public void setFoodID(int foodID) {
		FoodID = foodID;
	}
	public String getFoodName() {
		return FoodName;
	}
	public void setFoodName(String foodName) {
		FoodName = foodName;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public void viewFood() {
		System.out.println(" " + FoodID + "\t\t" + FoodName + "\t\t"+ Description + "\t\t" + Category + "\t\t" + Price + "\t");
	}

}
