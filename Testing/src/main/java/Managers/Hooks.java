package Managers;


import com.aventstack.extentreports.ExtentReports;
import com.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.net.NetworkUtils;
import org.openqa.selenium.remote.CapabilityType;
import cucumber.api.java.After;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class Hooks {
    public static WebDriver driver;
    public final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    public final String INTERNETEXPLORER_DRIVER_PROPERTY = "webdriver.ie.driver";
    public static Properties prop = new Properties();
    private static Logger Log = Logger.getLogger(Hooks.class.getName());
    public static String sFileName = "da_portal";
    public static String failedOutputPath = "src\\test\\Failed_Har_Screenshot\\";
    ExtentReports extent;


    public Properties loadconfigurations() throws IOException {
        File file = new File(
                System.getProperty("src\\main\\resources\\configs\\Configuration.properties"));
        FileInputStream fis = new FileInputStream(file);
        prop.load(fis);
        return prop;
    }

    @Before
    public static void initializeMethod(Scenario scenario) {
        Log.info("***********  Test Started-" + scenario.getName() + "  ****************");
    }

    public WebDriver init(String url) throws Throwable {
        driver = createDriver();
        getUrl(prop.getProperty("portal_QAT_SG"));
        return driver;
    }


    public void getUrl(String url) {
        if (url != null) {
            driver.get(url);
            driver.manage().window().maximize();
        } else {
            throw new RuntimeException("url is not specified in Configuration.properties file");
        }
    }


    private WebDriver createDriver() throws Throwable {
        FileInputStream fn = new FileInputStream("src\\main\\resources\\configs\\Configuration.properties");
        prop.load(fn);
        if (prop.getProperty("browser").equals("chrome")) {
            System.setProperty(CHROME_DRIVER_PROPERTY, prop.getProperty("chromedriverPath"));
            Proxy seleniumProxy = null;
            String ipAddress = new NetworkUtils().getIp4NonLoopbackAddressOfThisMachine().getHostAddress();
            Proxy proxy = new Proxy();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--test-type", "--disable-popup-blocking");
            options.addArguments("--incognito");
            options.setAcceptInsecureCerts(true);
            options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
            options.setCapability("proxy", proxy);
            options.setCapability("proxy", seleniumProxy);
            options.setCapability(CapabilityType.PROXY, seleniumProxy);
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } else if (prop.getProperty("browser").equals("firefox")) {
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } else if (prop.getProperty("browser").equals("iexplorer")) {
            System.setProperty(INTERNETEXPLORER_DRIVER_PROPERTY, prop.getProperty("iedriverPath"));
            driver = new InternetExplorerDriver();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } else {
            driver = new ChromeDriver();
        }
        driver.manage().deleteAllCookies();
        return driver;
    }

    public static void tearDown() throws Throwable {
        driver.close();
        driver.quit();
    }


    @After
    /**
     * Embed a screenshot in test report if scenario is marked as failed
     */
    public void embedScreenshot(Scenario scenario) throws Throwable {

        if (scenario.isFailed()) {
            String imgfileExt = "screenshot.png";
            String randomdatetime = datetimegen().toString();
            String FileName = failedOutputPath + randomdatetime + imgfileExt;
            try {
                Log.info("************" + scenario.getName() + "End**************");
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(FileName));

            } catch (WebDriverException e) {
                System.out.println(e.getMessage());
                Reporter.addStepLog("Test ended abruptly");
            }
        }
        tearDown();
    }


    public static String datetimegen() throws Throwable {
        String datetime = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        LocalDateTime now = LocalDateTime.now();
        datetime = dtf.format(now);
        return datetime;
    }


}




