package generic;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.support.PageFactory;
import utill.Common;
import webPages.AmazonHomePage;

import java.io.IOException;
import java.util.Properties;

public  class StarterClass extends Common {
    public static AmazonHomePage amazonHomePage;
    private final String PropertiesFilePath ="config.properties";

    private static Properties prop;
    {
        try {
            prop = loadProperties(PropertiesFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String testingEnvironment= System.getProperty("Testing_Environment");
    private Boolean useCloudEnv= Boolean.parseBoolean(System.getProperty("Use_Cloud_Env")) ;
    private String cloudEnvName= System.getProperty("Cloud_Env_Name");
    private String os= System.getProperty("Os_Name");
    private String os_version = System.getProperty("Os_Version");
    private String browserName = System.getProperty("Browser_Name");
    private String browserVersion = System.getProperty("Browser_Version");
    private long implicitlyWaitTime=Long.parseLong(System.getProperty("Implicitly_Wait_Time").trim());

    //private  String secretFilePath =System.getProperty("Secret_File_Path");
    private  String secretFilePath ="src/test/resources/secret.properties";
    private final String secret_FilePath ="\""+secretFilePath+"\"";
    private static Properties secretProp;
    {
        try {
            secretProp = loadProperties(secretFilePath);
            //secretProp = loadProperties(secret_FilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String url = secretProp.getProperty(testingEnvironment);
    public static void Init() {
        amazonHomePage = PageFactory.initElements(driver,AmazonHomePage.class);
    }

    @Before
    public void setUp_Init() throws IOException {
        System.out.println(testingEnvironment);
        System.out.println(useCloudEnv);
        System.out.println(cloudEnvName);
        System.out.println(os);
        System.out.println(os_version);
        System.out.println(browserName);
        System.out.println(browserVersion);
        System.out.println(implicitlyWaitTime);

        setUp( useCloudEnv,  cloudEnvName,
                os,  os_version,  browserName,
                browserVersion, url,implicitlyWaitTime);
        Init();
    }
    @After
    public void tearDown(Scenario scenario) throws IOException {
        if(scenario.isFailed()){
            try{
            System.out.println(scenario.getName()+" is Failed");
            screenShot(scenario);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            try{
            System.out.println(scenario.getName()+" is Passed");
            }catch (Exception E){
                E.printStackTrace();
            }
        }
        driver.quit();

    }
}