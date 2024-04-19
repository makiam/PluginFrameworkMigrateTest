package aoi.tools;

import artofillusion.Translate;
import artofillusion.api.Model;
import artofillusion.api.Tool;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.Extension;

import javax.swing.*;

@Extension
@NoArgsConstructor
@Slf4j
public class LatheTool implements Tool {

    @Override
    public String getName() {
        // Localized text loaded from resource bundle of Tools plugin
        return Translate.text("Tools:lathe.dialog.name");
    }

    @Override
    public void execute(Model model) {
        //Do something
        String OK = Translate.text("DefaultResource:OK"); // Localized text from other plugin resources
        log.info("The button caption value from resource bundle: {}", OK);
        JOptionPane.showMessageDialog(null, "Lathe tool executed", "Lathe tool", JOptionPane.INFORMATION_MESSAGE);
    }

    public void createLathe() {

    }
}
