package da.klnq.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    
    public static List<String> readResource(String resource) {
        final InputStream is = IOUtils.class.getResourceAsStream(resource);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            final List<String> lines = new ArrayList<>();
            
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            
            return lines;
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("Error reading resource: " + resource, ex);
        }
    }

}
