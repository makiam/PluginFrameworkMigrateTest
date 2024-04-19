package aoi;

import artofillusion.AppPluginManager;
import artofillusion.PluginRegistry;
import artofillusion.api.ApplicationLifecycle;
import artofillusion.ui.LayoutWindow;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.Extension;

import javax.swing.*;

@Slf4j
@Extension
@NoArgsConstructor
public class HelpExtension implements ApplicationLifecycle {

    @Override
    public void onApplicationStarting() {
        log.info("HelpPlugin is starting");
        //Now load the default helpset from plugin bundled resource
        AppPluginManager.PluginResource rh = PluginRegistry.getResource("help", "AOIHelp");
        //Do JavaHelp registration with helpset
        //HelpSet hs = new HelpSet(null, HelpSet.findHelpSet(rh.getClassLoader(), rh.getName()));

        //Load all other resources with same type
        for(AppPluginManager.PluginResource resource: artofillusion.PluginRegistry.getResources("help")) {
            if(resource.getId().equals("AOIHelp")) continue;
            //Do JavaHelp registration with 3rd party helpsets
//            try {
//                HelpSet add = new HelpSet(cl, HelpSet.findHelpSet(resource.getClassLoader(), resource.getName()));
//                mapIdToHelpSet.put(resource.getId(), add);
//                hb.getHelpSet().add(add);
//            } catch (HelpSetException ex) {
//                log.log(Level.SEVERE, null, ex);
//            }
        }
    }

    @Override
    public void onViewCreated(LayoutWindow layout) {

        log.info("LayoutWindow is visible");
        // Do Some work. Ex. register menus, buttons, etc.
        layout.getJMenuBar().add(new JMenu("Help"));
    }
}
