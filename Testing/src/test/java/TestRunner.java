import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.junit.runner.RunWith;
import org.testng.annotations.AfterClass;

import java.io.File;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
        features = {"src/test/resources/features"},
        glue = {"step_definitions", "managers"},
        tags = {"@Login"})


public class TestRunner extends AbstractTestNGCucumberTests {
    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File("src\\main\\resources\\configs\\extent-config.xml"));
    }

}
