package utilities;

import org.apache.log4j.Logger;
import stepdefinition.StepDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//**********************************************************************************************************
//Description: PropertyManager class reads global configurations and use them during test execution.
//**********************************************************************************************************
public class PropertyManager {
    static Logger log = Logger.getLogger(StepDefinitions.class.getName());
    private static PropertyManager instance;
    private static final Object lock = new Object();
    private static String home = System.getProperty("user.dir");
    String propertyFilePath = home + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "configuration.properties";
    //    private static String propertyFilePath = System.getProperty("user.dir") +
//            "\\src\\test\\resources\\configuration.properties";
    private static String baseUrl;
    private static String passKey;
    private static String passValue;

    //Create a Singleton instance. We need only one instance of Property Manager.
    public static PropertyManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new PropertyManager();
                instance.loadData();
            }
        }
        return instance;
    }

    //Get all configuration data and assign to related fields.
    private void loadData() {
        //Declare a properties object
        Properties prop = new Properties();

        //Read configuration.properties file
        try {
            prop.load(new FileInputStream(propertyFilePath));
            //prop.load(this.getClass().getClassLoader().getResourceAsStream("configuration.properties"));
        } catch (IOException e) {
            log.error(e.getStackTrace());
            System.out.println("Configuration properties file cannot be found");
        }

        //Get properties from configuration.properties
        baseUrl = prop.getProperty("baseUrl");
        passKey = prop.getProperty("passKey");
        passValue = prop.getProperty("passValue");
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getPassKey() {
        return passKey;
    }

    public String getPassValue() {
        return passValue;
    }
}
