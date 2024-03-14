package feedbackservicepublisher;

public interface FeedbackService {
	
	public void giveFeedback(int CustomerID);
	public void viewMyFeedback(int CustomerID);
	public void deleteFeedback(int CustomerID);
	public void reviewFeedbacks(int CustomerID);
	public void updateFeedback(int CustomerID);

}
