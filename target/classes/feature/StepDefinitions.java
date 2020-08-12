package feature;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class StepDefinitions {

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
