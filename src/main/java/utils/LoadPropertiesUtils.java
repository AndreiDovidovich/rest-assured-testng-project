package utils;

import java.io.InputStream;
import java.util.Properties;

public class LoadPropertiesUtils {
    private static final Properties props = new Properties();

    static {
        try (InputStream is = LoadPropertiesUtils.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is != null) props.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Cannot load config.properties", e);
        }
    }

    public static String get(String key) {
        String sysProp = System.getProperty(key);
        if (sysProp != null) return sysProp;

        String envKey = key.toUpperCase().replace('.', '_');
        String envValue = System.getenv(envKey);
        if (envValue != null) return envValue;

        String value = props.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException(
                    "Property '" + key + "' not found. "
                            + "Set -D" + key + "=... or env " + envKey);
        }
        return value;
    }

    public static int getInt(String key) {
        String value = get(key).trim();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Property '" + key + "' must be integer, but was: '" + value + "'", e);
        }
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static String getBaseUrl() {
        return get("base.url");
    }
}
