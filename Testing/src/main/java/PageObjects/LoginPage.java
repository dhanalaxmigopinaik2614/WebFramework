package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

/**
 * Created by dgopinaik on 12/5/2018.
 */
public class LoginPage {
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#j_username")
    WebElement username_txt;

    @FindBy(css = "#j_password")
    WebElement password_txt;

    @FindBy(css = "#loginForm > button")
    WebElement signIn_btn;


    public void enterUserCredentials(Map<String, String> data) throws Throwable {
        username_txt.sendKeys(data.get("username"));
        password_txt.sendKeys(data.get("password"));

    }

    public void clickSignIn() throws Throwable{
        if(signIn_btn.isEnabled()){
            signIn_btn.click();
        }
    }

}
