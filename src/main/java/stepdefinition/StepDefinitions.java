package stepdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.apache.log4j.Logger;
import pojo.out.JsonResponse;
import utilities.CommonUtils;
import utilities.Constants;
import utilities.RestAssuredUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepDefinitions {

    public static ResponseOptions<Response> response;
    JsonResponse jsonResponse;
    List<Map<String, String>> excelData;
    ArrayList<String> randomExcelData;

    static Logger log = Logger.getLogger(StepDefinitions.class.getName());


    @Given("User perform GET operation for {string} with {string}")
    public void userPerformGETOperationForWith(String url, String manufacturerCode) {
        url = CommonUtils.getEndpoint(url);
        Map<String, String> pathParams = new HashMap<String, String>();
        if (manufacturerCode.contains(",")) {
            String[] manufacturerCodes = manufacturerCode.split(",");
            pathParams.put(Constants.MANUFACTURER, manufacturerCodes[0]);
            pathParams.put(Constants.MAINTYPE, manufacturerCodes[1]);
        } else {
            pathParams.put(Constants.MANUFACTURER, manufacturerCode);
        }
        try {
            response = RestAssuredUtility.getInstance().GetWithPathParams(url, pathParams);
            jsonResponse = response.getBody().as(JsonResponse.class);
        } catch (Exception e) {
            log.error(url + " Service call was not successful. " + Arrays.toString(e.getStackTrace()));
        }
    }

    @When("User gets {int} status code")
    public void userGetsStatusCode(int arg0) {

    }

    @Then("User should not get blank wkda object")
    public void userShouldNotGetBlankWkdaObject() {

    }
}
