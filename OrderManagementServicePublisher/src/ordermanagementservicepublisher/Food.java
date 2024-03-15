package ordermanagementservicepublisher;

public class Food {
	
	private int FoodID;
	private String FoodName ;
	private String Description ;
	private String Category ;
	private float Price ;
	
	public Food() {}
	
	public Food(int foodID, String foodName, String description, String category, float price) {
		super();
		FoodID = foodID;
		FoodName = foodName;
		Description = description;
		Category = category;
		Price = price;
	}

	public int getFoodID() {
		return FoodID;
	}

	public String getFoodName() {
		return FoodName;
	}

	public String getDescription() {
		return Description;
	}

	public String getCategory() {
		return Category;
	}

	public float getPrice() {
		return Price;
	}

	public void setFoodID(int foodID) {
		FoodID = foodID;
	}

	public void setFoodName(String foodName) {
		FoodName = foodName;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public void setPrice(float price) {
		Price = price;
	}
	
	public void viewOrder() {
		System.out.println(" " + FoodID  + "\t\t" + FoodName  + "\t"+ Description  + "\t\t" + Category  + "\t\t" + Price );
	}

}
