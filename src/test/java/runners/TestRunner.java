package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions(features = {"src/main/resources/feature/"},
        glue = {"stepdefinition"},
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-pretty",
                "de.monochromata.cucumber.report.PrettyReports:target/cucumber",
                "json:target/report.json",
                "testng:target/testng-cucumber-reports/cuketestng.xml"})
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

