package dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import enums.DriverType;
import enums.EnvironmentType;

public class ConfigFileReader {

    private final Properties properties;
    private final String propertyFilePath = "configs//Configuration.properties";

    public ConfigFileReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getDriverPath() {
        String driverPath = properties.getProperty("driverPath");
        if (driverPath != null)
            return driverPath;
        else
            throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
    }

    public String getIEDriverPath() {
        String driverPath = properties.getProperty("driverPathIE");
        if (driverPath != null)
            return driverPath;
        else
            throw new RuntimeException("driverPath for IE not specified in the Configuration.properties file.");
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if (implicitlyWait != null)
            return Long.parseLong(implicitlyWait);
        else
            throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file.");
    }

    public String getApplicationUrl() {
        String url = properties.getProperty("url");
        if (url != null)
            return url;
        else
            throw new RuntimeException("url not specified in the Configuration.properties file.");
    }

    public String getReviewerUser() {
        String reviewerUserName = properties.getProperty("ReviewerUserName");
        if (reviewerUserName != null)
            return reviewerUserName;
        else
            throw new RuntimeException("Reviewer Name not specified in the Configuration.properties file.");
    }

    public String getApproverUser() {
        String approverUserName = properties.getProperty("ApproverUserName");
        if (approverUserName != null)
            return approverUserName;
        else
            throw new RuntimeException("Approver Name not specified in the Configuration.properties file.");
    }

    public DriverType getBrowser() {
        String browserName = properties.getProperty("browser");
        if (browserName == null || browserName.equals("chrome"))
            return DriverType.CHROME;
        else if (browserName.equalsIgnoreCase("firefox"))
            return DriverType.FIREFOX;
        else if (browserName.equals("iexplorer"))
            return DriverType.INTERNETEXPLORER;
        else if (browserName.equals("RM"))
            return DriverType.REMOTEDESKTOP;
        else
            throw new RuntimeException(
                    "Browser Name Key value in Configuration.properties is not matched : " + browserName);
    }

    public EnvironmentType getEnvironment() {
        String environmentName = properties.getProperty("environment");
        if (environmentName == null || environmentName.equalsIgnoreCase("local"))
            return EnvironmentType.LOCAL;
        else if (environmentName.equals("remote"))
            return EnvironmentType.REMOTE;
        else
            throw new RuntimeException(
                    "Environment Type Key value in Configuration.properties is not matched : " + environmentName);
    }

    public Boolean getBrowserWindowSize() {
        String windowSize = properties.getProperty("windowMaximize");
        if (windowSize != null)
            return Boolean.valueOf(windowSize);
        return true;
    }

    public String getAccessKey() {
        String testDataResourcePath = properties.getProperty("access_key");
        if (testDataResourcePath != null)
            return testDataResourcePath;
        else
            throw new RuntimeException(
                    "Test Data Resource Path not specified in the Configuration.properties file for the Key:testDataResourcePath");
    }

    public String getUsername() {
        String url = properties.getProperty("username");
        if (url != null)
            return url;
        else
            throw new RuntimeException("Site Admin Url not specified in the Configuration.properties file.");
    }

    public String getReportConfigPath() {
        String reportConfigPath = properties.getProperty("reportConfigPath");
        if (reportConfigPath != null)
            return reportConfigPath;
        else
            throw new RuntimeException(
                    "Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
    }
}
