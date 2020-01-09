package automation.testing.framework.config;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvironmentHandler {
    private final Properties environmentProps = new Properties();

    public EnvironmentHandler() {
        initEnvironment();
    }

    public String valueOf(String propertyKey) {
        String fromEnv = System.getProperty(propertyKey);
        return StringUtils.isBlank(fromEnv) ? environmentProps.getProperty(propertyKey) : fromEnv;
    }


    private void initEnvironment() {
        String envt = System.getProperty("environment");
        envt = StringUtils.isBlank(envt) ? "dev" : envt.trim();
        loadEnvironmentProperties(envt);
        environmentProps.put("environment", envt);
    }

    private void loadEnvironmentProperties(String envt) {
        try (final InputStream stream = new FileInputStream("src/test/resources/env/" + envt + "/environment.properties")) {
            environmentProps.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
