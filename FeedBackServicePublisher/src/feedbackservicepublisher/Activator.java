package feedbackservicepublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	
	private ServiceRegistration<?> serviceRegistration ;

	static BundleContext getContext() {
		return context;
	}

	//Bundle Start Method
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("** Feedback Publisher Started **");
		FeedbackService feedback = new FeedbackServiceImpl();
		
		//Service Registration with OSGI service registry
		serviceRegistration = bundleContext.registerService(FeedbackService.class.getName(), feedback, null);
	}

	// Bundle Stop Method
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("** Feedback Publisher Stoped **");
		
		//Unregister the Service
		serviceRegistration.unregister();
	}

}
