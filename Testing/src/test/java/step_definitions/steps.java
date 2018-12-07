package step_definitions;

import Managers.Hooks;
import com.cucumber.listener.Reporter;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class steps {
    static WebDriver driver;
    String policyNumberVal = "";
    String dbPremium = "";
    static String url = "";


    @Given("^the user hits \"(.*)\" URL$")
    public void the_user_hits_DA_URL(String url) throws Throwable {
        Hooks hooks = new Hooks();
        if (driver == null) {
            driver = hooks.init(url);
        } else {
            driver.navigate().refresh();
            driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

            if (driver.findElement(By.cssSelector("#sessionExpired > div > div > div > div.modal-header")).isDisplayed()) {
                driver.findElement(By.cssSelector("#sessionExpired > div > div > div > div.modal-header > button")).click();
            }
            if (driver.findElement(By.cssSelector("#daExceptionModal > div > div > div > div.modal-header")).isDisplayed()) {
                driver.findElement(By.cssSelector("#daExceptionModal > div > div > div > div.modal-header > button")).click();
                driver.navigate().to(url);
            }



        }

    }

}
