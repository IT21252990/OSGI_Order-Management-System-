package osgi_servicesubscriber;

import com.mtit.service.ServicePublish;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class ServiceActivator implements BundleActivator {

    private ServiceReference<ServicePublish> serviceReference;

    public void start(BundleContext context) throws Exception {
        System.out.println("--- user management subscriber start ---");

        // Retrieve the service reference for the ServicePublish service
        serviceReference = context.getServiceReference(ServicePublish.class);
        if (serviceReference != null) {
            ServicePublish servicePublish = context.getService(serviceReference);

            // Use the published service
            if (servicePublish != null) {
                // Invoke other methods of ServicePublish as needed
            } else {
                System.out.println("Failed to retrieve ServicePublish service.");
            }
        } else {
            System.out.println("ServicePublish service reference is null.");
        }
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("--- user management subscriber stop ---");

        // Release the service reference
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
    }
}
