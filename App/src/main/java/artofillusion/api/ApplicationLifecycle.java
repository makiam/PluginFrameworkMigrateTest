package artofillusion.api;

import artofillusion.ui.LayoutWindow;
import org.pf4j.ExtensionPoint;


public interface ApplicationLifecycle extends ExtensionPoint {
    /*
     * Called when the application is starting.
     */
    void onApplicationStarting();
    /*
     * Called when the LayoutWindow is created and visible
     */
    void onViewCreated(LayoutWindow layout);
}
