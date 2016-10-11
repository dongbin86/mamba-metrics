package mamba.timeline.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sanbing on 10/10/16.
 */
public class Configuration {

    public final Log LOG = LogFactory.getLog(this.getClass());
    private final Properties properties;

    public Configuration(String configFile) {
        properties = new Properties();

        //Get property file stream from classpath
        InputStream inputStream = Configuration.class.getResourceAsStream(configFile);

        if (inputStream == null) {
            throw new IllegalArgumentException(configFile + " not found in classpath");
        }

        // load the properties
        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (FileNotFoundException fnf) {
            LOG.info("No configuration file " + configFile + " found in classpath.", fnf);
        } catch (IOException ie) {
            throw new IllegalArgumentException("Can't read configuration file " +
                    configFile, ie);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

}
