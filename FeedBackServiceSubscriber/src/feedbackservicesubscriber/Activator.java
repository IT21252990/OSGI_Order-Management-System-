package feedbackservicesubscriber;

import feedbackservicepublisher.FeedbackService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	
	private ServiceReference<?> feedbackReference;
	ServiceRegistration<?> registration;

	static BundleContext getContext() {
		return context;
	}

	//Bundle Start Method
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("** Feedback Subscriber Started **");
		
		// Retrieve the service reference for the FeedbackService
		feedbackReference = context.getServiceReference(FeedbackService.class.getName());
		FeedbackService feedbackManager = (FeedbackService) context.getService(feedbackReference);
		
		// Create Subscriber
		FeedbackSubscriber currentSubscriber = new FeedbackSubscriber(feedbackManager);
		
		// Register the FeedbackSubscriber service
		registration = context.registerService(FeedbackSubscriber.class.getName(), currentSubscriber, null);
		
		//start Subscriber Activity
//		currentSubscriber.startMenu();
	}

	//Bundle Stop Method
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("** Feedback Subscriber Stoped **");
	}

}
