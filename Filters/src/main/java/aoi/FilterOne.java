package aoi;

import artofillusion.api.Filter;
import lombok.NoArgsConstructor;
import org.pf4j.Extension;

@Extension
@NoArgsConstructor
public class FilterOne implements Filter {
    @Override
    public String getName() {
        return "Filter One";
    }
}
