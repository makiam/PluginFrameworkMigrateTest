package aoi;

import org.pf4j.Plugin;

public class HelpPlugin extends Plugin /* implements ApplicationLifecycle */ {
    // In original app the Plugin is a root object and also is a ApplicationLifecycle interface
    // To be guaranteed that all help resources are already loaded and available for HelpSystem the HelpSystem initialization moved
    // to onViewCreated and checked it runned only once


    // Here lifecycle code moved to extension class
}
