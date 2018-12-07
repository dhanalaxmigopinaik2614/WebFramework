package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static Managers.Hooks.driver;

/**
 * Created by dgopinaik on 12/5/2018.
 */
public class LandingPage {
    public LandingPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#navbar > div > div.navbar-collapse.collapse > nav > div.block-user-menu > div > ul > li.nav-login > a")
    WebElement login_lnk;


    public void clickLogin() throws Throwable {
        if (login_lnk.isDisplayed()) {
            login_lnk.click();
        }
    }


}
