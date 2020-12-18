package da.klnq.code.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class IOUtils {
    
    public static <T> Try<List<T>> readResource(String resource, Function<String, Try<T>> lineParser) {
        final InputStream is = IOUtils.class.getResourceAsStream(resource);
        if (is == null) {
            return Try.failure("Resource '%s' doesn't exist.", resource);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            final List<T> objects = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                final Try<T> parseResult = lineParser.apply(line);
                if (parseResult.isFailure()) {
                    return parseResult.cast();
                }
                objects.add(parseResult.get());
            }
            return Try.of(objects);
        }
        catch (Exception ex) {
            return Try.failure(ex, "Error reading resource: %s", resource);
        }
    }

    public static Try<Integer> parseInteger(String text) {
        try {
            return Try.of(Integer.parseInt(text));
        } catch (NumberFormatException ex) {
            return Try.failure(ex, "Error parsing text '%s' as integer.", text);
        }
    }

    public static Try<Long> parseLong(String text) {
        try {
            return Try.of(Long.parseLong(text));
        } catch (NumberFormatException ex) {
            return Try.failure(ex, "Error parsing text '%s' as integer.", text);
        }
    }

    public static Try<String> parseString(String text) {
        return Try.of(text);
    }

}
