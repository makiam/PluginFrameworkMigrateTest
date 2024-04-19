package aoi.tools;

import artofillusion.api.Tool;
import lombok.NoArgsConstructor;
import org.pf4j.Extension;

@Extension
@NoArgsConstructor
public class ToolOne implements Tool {
    @Override
    public String getName() {
        return "Tool One";
    }
}
