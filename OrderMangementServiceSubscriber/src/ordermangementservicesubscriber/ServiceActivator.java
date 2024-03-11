package ordermangementservicesubscriber;

import com.mtit.service.ServicePublish;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class ServiceActivator implements BundleActivator {

	private ServiceReference serviceReference;

    public void start(BundleContext context) throws Exception {
        System.out.println("Menu Retrieval Subscriber has Started");

        // Retrieve the published service
        serviceReference = context.getServiceReference(ServicePublish.class.getName());
        ServicePublish servicePublish = (ServicePublish) context.getService(serviceReference);

        // Use the published service (e.g., invoke menu-related methods)
        if (servicePublish != null) {
            String menuResponse = servicePublish.publishService();
            System.out.println("Received Menu: " + menuResponse);
        }
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Menu Retrieval Subscriber has Stopped");

        // Unget the published service
        context.ungetService(serviceReference);
    }

}
