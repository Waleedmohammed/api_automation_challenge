package stepdefinition;

import org.testng.annotations.BeforeTest;
import utilities.RestAssuredUtility;

public class TestInitialize {

    @BeforeTest
    public void TestSetup() {
        RestAssuredUtility.getInstance().resetRestAssured();
    }
}
