package stepdefinition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pojo.in.JsonRequest;
import pojo.out.JsonResponse;
import utilities.CommonUtils;
import utilities.Constants;
import utilities.PropertyManager;
import utilities.RestAssuredUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StepDefinitions {

    public static ResponseOptions<Response> response;
    JsonResponse jsonResponse;
    List<Map<String, String>> excelData;
    ArrayList<String> randomExcelData;

    Map<String, Object> manufacturerList = new HashMap<>();
    static Logger log = Logger.getLogger(StepDefinitions.class.getName());


    @Given("User perform GET operation for {string} with {string}")
    public void userPerformGETOperationForWith(String url, String manufacturerCode) {
        url = CommonUtils.getEndpoint(url);
        Map<String, String> pathParams = new HashMap<>();
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
    public void userGetsStatusCode(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @Then("User should not get blank wkda object")
    public void userShouldNotGetBlankWkdaObject() {
        if (jsonResponse.getAdditionalProperties().size() == 0) {
            log.error("No Data found for wkda object.");
            Assert.fail("No Data Available in WKDA Object");
        }
    }

    @Given("User perform GET operation on {string} for Schema Validation")
    public void userPerformGETOperationOnForSchemaValidation(String urlLabel) {

        String url = CommonUtils.getEndpoint(urlLabel);
        Map<String, String> pathParams = new HashMap<>();
        try {
            switch (urlLabel) {
                case "manufacturer":
                    response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.MANUFACTURERENDPOINT);
                    break;
                case "main-types":
                    pathParams.put(Constants.MANUFACTURER, Constants.RANDOMMANUFACTURER);
                    response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.MAINTYPEENDPOINT, pathParams);
                    break;
                case "built-dates":
                    pathParams.put(Constants.MANUFACTURER, Constants.RANDOMMANUFACTURER);
                    pathParams.put(Constants.MAINTYPE, Constants.RANDOMMODEL);
                    response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.BUILTDATESENDPOINT, pathParams);
                    break;
            }
            jsonResponse = response.getBody().as(JsonResponse.class);
        } catch (Exception e) {
            log.error(url + " Service call was not successful. " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Then("User validates wrapping json for the service")
    public void userValidatesWrappingJsonForTheService() {
        SoftAssert softAssert = new SoftAssert();
        String responseBody = response.getBody().asString();
        softAssert.assertTrue(responseBody.contains("page"), "Validate JSON key page exist");
        softAssert.assertTrue(responseBody.contains("pageSize"), "Validate JSON key pageSize exist");
        softAssert.assertTrue(responseBody.contains("totalPageCount"), "Validate JSON key totalPageCount exist");
        softAssert.assertTrue(responseBody.contains("wkda"), "Validate JSON key wkda exist");
        softAssert.assertAll();
    }

    @Given("User choose random data from input sheet")
    public void userChooseRandomDataFromInputSheet() {
        excelData = CommonUtils.getInputFromExcel();
        randomExcelData = new ArrayList<>(excelData.get(CommonUtils.getRandomNumber(1, excelData.size() - 1)).values());
    }

    @When("User perform GET operation for {string} using random data from input sheet")
    public void userPerformGETOperationForUsingRandomDataFromInputSheet(String urlLabel) {
        String url = CommonUtils.getEndpoint(urlLabel);
        Map<String, String> pathParams = new HashMap<>();
        switch (urlLabel) {
            case "manufacturer":
                response = RestAssuredUtility.getInstance().GetWithPathParams(url);
                break;
            case "main-types":
                pathParams.put(Constants.MANUFACTURER, randomExcelData.get(1));
                response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.MAINTYPEENDPOINT, pathParams);
                jsonResponse = response.getBody().as(JsonResponse.class);
                Assert.assertTrue(jsonResponse.getAdditionalProperties().containsValue(randomExcelData.get(2)));
                break;
            case "built-dates":
                pathParams.put(Constants.MANUFACTURER, randomExcelData.get(1));
                pathParams.put(Constants.MAINTYPE, randomExcelData.get(2));
                response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.BUILTDATESENDPOINT, pathParams);
                jsonResponse = response.getBody().as(JsonResponse.class);
                Assert.assertTrue(jsonResponse.getAdditionalProperties().containsValue(randomExcelData.get(3)));
                break;
        }
    }

    @Given("User perform GET operation for {string}")
    public void userPerformGETOperationFor(String url) {
        url = CommonUtils.getEndpoint(url);
        try {
            response = RestAssuredUtility.getInstance().GetWithPathParams(url);
            jsonResponse = response.getBody().as(JsonResponse.class);
        } catch (Exception e) {
            log.error(url + " Service call was not successful. " + Arrays.toString(e.getStackTrace()));
        }
    }

    @And("User see unique model types for different manufacturer")
    public void userSeeUniqueModelTypesForDifferentManufacturer() {

        String url = Constants.MAINTYPEENDPOINT;
        jsonResponse = response.getBody().as(JsonResponse.class);
        Assert.assertEquals(Constants.SUCCESSCODE, response.statusCode());
        if (jsonResponse.getAdditionalProperties().size() > 0) {
            manufacturerList = jsonResponse.getAdditionalProperties().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        for (Map.Entry<String, Object> entry : manufacturerList.entrySet()) {
            Map<String, String> pathParams = new HashMap<>();
            pathParams.put(Constants.MANUFACTURER, entry.getKey());
            try {
                response = RestAssuredUtility.getInstance().GetWithPathParams(url, pathParams);
            } catch (Exception e) {
                log.error(url + " Service call was not successful. " + Arrays.toString(e.getStackTrace()));
            }
            Assert.assertEquals(Constants.SUCCESSCODE, response.statusCode());
            jsonResponse = response.getBody().as(JsonResponse.class);
            if (jsonResponse.getAdditionalProperties().size() > 0) {
                ArrayList<Object> responseList = new ArrayList<>(jsonResponse.getAdditionalProperties().values());
                boolean isUnique = CommonUtils.isUniqueValues(responseList);
                Assert.assertTrue(isUnique);
                log.info("Manufacturer car model year list available under wkda object.");
            } else {
                log.error("Manufacturer car model year list not available under wkda object.");
                Assert.fail("No Data Available in WKDA Object");
            }
        }
    }

    @Given("User perform POST operation for {string} with request body")
    public void userPerformPOSTOperationForWithRequestBody(String urlLabel) {
        String url = CommonUtils.getEndpoint(urlLabel);
        JsonRequest jsonRequest = null;
        try {
            switch (urlLabel) {
                case "manufacturer":
                    jsonRequest = new JsonRequest(Constants.RANDOMMANUFACTURER);
                    break;
                case "main-types":
                    jsonRequest = new JsonRequest(Constants.RANDOMMANUFACTURER, Constants.RANDOMMODEL);
                    break;
                case "built-dates":
                    jsonRequest = new JsonRequest(Constants.RANDOMMANUFACTURER, Constants.RANDOMMODEL, Constants.RANDOMYEAR);
                    break;
            }
            response = RestAssuredUtility.getInstance().PostWithBody(url, jsonRequest);

        } catch (Exception e) {
            log.error(url + " Service call was not successful. " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Then("User should not get {int} status code")
    public void userShouldNotGetStatusCode(int statusCode) {
        Assert.assertNotEquals(statusCode, response.statusCode(), "Success code " + response.statusCode() + " is not expected");
    }

    @Given("User perform GET operation for {string} with below parameters")
    public void userPerformGETOperationForWithBelowParameters(String urlLabel, DataTable dt) {
        List<Map<String, String>> newTable = dt.asMaps(String.class, String.class);
        Map<String, String> pathParams = new HashMap<>();
        for (Map<String, String> stringStringMap : newTable) {
            pathParams.put(stringStringMap.get("Key"), stringStringMap.get("Value"));
        }
        switch (urlLabel) {
            case "manufacturer":
                response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.MANUFACTURERENDPOINT, pathParams);
                break;
            case "main-types":
                response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.MAINTYPEENDPOINT, pathParams);
                break;
            case "built-dates":
                response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.BUILTDATESENDPOINT, pathParams);
                break;
        }
    }

    @Given("User perform GET operation for {string} with HTTPS protocol")
    public void userPerformGETOperationForWithHTTPSProtocol(String urlLabel) {
        String url = CommonUtils.getEndpoint(urlLabel);
        String httpsURL = PropertyManager.getInstance().getBaseUrl();
        Map<String, String> pathParams = new HashMap<>();
        try {
            switch (urlLabel) {
                case "manufacturer":
                    response = RestAssuredUtility.getInstance(httpsURL).GetWithPathParams(Constants.MANUFACTURERENDPOINT);
                    break;
                case "main-types":
                    pathParams.put(Constants.MANUFACTURER, Constants.RANDOMMANUFACTURER);
                    response = RestAssuredUtility.getInstance(httpsURL).GetWithPathParams(Constants.MAINTYPEENDPOINT, pathParams);
                    break;
                case "built-dates":
                    pathParams.put(Constants.MANUFACTURER, Constants.RANDOMMODEL);
                    pathParams.put(Constants.MAINTYPE, Constants.RANDOMMODEL);
                    response = RestAssuredUtility.getInstance(httpsURL).GetWithPathParams(Constants.BUILTDATESENDPOINT, pathParams);
                    break;
            }
            jsonResponse = response.getBody().as(JsonResponse.class);
        } catch (Exception e) {
            log.error(url + " Service call was not successful. " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Then("User validates unique values in response")
    public void userValidatesUniqueValuesInResponse() {

        if (jsonResponse.getAdditionalProperties().size() > 0) {
            ArrayList<Object> responseList = new ArrayList<>(jsonResponse.getAdditionalProperties().values());
            boolean isUnique = CommonUtils.isUniqueValues(responseList);
            Assert.assertTrue(isUnique);
            log.info("Manufacturer car model year list available under wkda object.");
        } else {
            log.error("Manufacturer car model year list not available under wkda object.");
            Assert.fail("No Data Available in WKDA Object");
        }
    }

    @Then("User see unique registration year of model types for different manufacturer")
    public void userSeeUniqueRegistrationYearOfModelTypesForDifferentManufacturer() {

        Assert.assertEquals(Constants.SUCCESSCODE, response.statusCode());

        if (jsonResponse.getAdditionalProperties().size() > 0) {
            manufacturerList = jsonResponse.getAdditionalProperties().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            log.info("Manufacturer list available under wkda object.");
        } else {
            log.error("Manufacturer list not available under wkda object.");
            Assert.fail("No Data Available in WKDA Object");

        }

        for (Map.Entry<String, Object> entry : manufacturerList.entrySet()) {
            Map<String, String> pathParams = new HashMap<>();
            pathParams.put(Constants.MANUFACTURER, entry.getKey());
            try {
                response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.MAINTYPEENDPOINT, pathParams);
            } catch (Exception e) {
                log.error(Constants.MANUFACTURER + " Service call was not successful. " + Arrays.toString(e.getStackTrace()));
            }

            Assert.assertEquals(Constants.SUCCESSCODE, response.statusCode());

            jsonResponse = response.getBody().as(JsonResponse.class);
            Map<String, Object> modelList = new HashMap<>();

            if (jsonResponse.getAdditionalProperties().size() > 0) {
                modelList = jsonResponse.getAdditionalProperties().entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                log.info("Manufacturer car model list available under wkda object.");
            } else {
                log.error("Manufacturer car model list not available under wkda object.");
                Assert.fail("No Data Available in WKDA Object");
            }

            for (Map.Entry<String, Object> entryModel : modelList.entrySet()) {
                Map<String, String> pathParamsnew = new HashMap<>();
                pathParamsnew.put(Constants.MANUFACTURER, entry.getKey());
                pathParamsnew.put(Constants.MAINTYPE, entryModel.getKey());
                try {
                    response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.BUILTDATESENDPOINT, pathParamsnew);
                } catch (Exception e) {
                    log.error(Constants.BUILTDATESENDPOINT + " Service call was not successful. " + Arrays.toString(e.getStackTrace()));
                }

                Assert.assertEquals(Constants.SUCCESSCODE, response.statusCode());

                jsonResponse = response.getBody().as(JsonResponse.class);

                if (jsonResponse.getAdditionalProperties().size() > 0) {
                    ArrayList<Object> responseList = new ArrayList<>(jsonResponse.getAdditionalProperties().values());
                    boolean isUnique = CommonUtils.isUniqueValues(responseList);
                    Assert.assertTrue(isUnique);
                    log.info("Manufacturer car model year list available under wkda object.");
                } else {
                    log.error("Manufacturer car model year list not available under wkda object.");
                    Assert.fail("No Data Available in WKDA Object");
                }
            }
        }
    }

    @Then("User validates interdependent data availability for all services")
    public void userValidatesInterdependentDataAvailabilityForAllServices() {

        Assert.assertEquals(Constants.SUCCESSCODE, response.statusCode());

        if (jsonResponse.getAdditionalProperties().size() > 0) {
            manufacturerList = jsonResponse.getAdditionalProperties().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            log.info("Manufacturer list available under wkda object.");
        } else {
            log.error("Manufacturer list not available under wkda object.");
            Assert.fail("No Data Available in WKDA Object");

        }

        for (Map.Entry<String, Object> entry : manufacturerList.entrySet()) {
            Map<String, String> pathParams = new HashMap<>();
            pathParams.put(Constants.MANUFACTURER, entry.getKey());
            try {
                response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.MAINTYPEENDPOINT, pathParams);
            } catch (Exception e) {
                log.error(Constants.MANUFACTURER + " Service call was not successful. " + Arrays.toString(e.getStackTrace()));
            }

            Assert.assertEquals(Constants.SUCCESSCODE, response.statusCode());

            jsonResponse = response.getBody().as(JsonResponse.class);
            Map<String, Object> modelList = new HashMap<>();

            if (jsonResponse.getAdditionalProperties().size() > 0) {
                modelList = jsonResponse.getAdditionalProperties().entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                log.info("Manufacturer car model list available under wkda object.");
            } else {
                log.error("Manufacturer car model list not available under wkda object.");
                Assert.fail("No Data Available in WKDA Object");
            }

            for (Map.Entry<String, Object> entryModel : modelList.entrySet()) {
                Map<String, String> pathParamsnew = new HashMap<>();
                pathParamsnew.put(Constants.MANUFACTURER, entry.getKey());
                pathParamsnew.put(Constants.MAINTYPE, entryModel.getKey());
                try {
                    response = RestAssuredUtility.getInstance().GetWithPathParams(Constants.BUILTDATESENDPOINT, pathParamsnew);
                } catch (Exception e) {
                    log.error(Constants.BUILTDATESENDPOINT + " Service call was not successful. " + Arrays.toString(e.getStackTrace()));
                }

                Assert.assertEquals(Constants.SUCCESSCODE, response.statusCode());

                jsonResponse = response.getBody().as(JsonResponse.class);

                if (jsonResponse.getAdditionalProperties().size() > 0) {
                    log.info("Manufacturer car model year list available under wkda object.");
                } else {
                    log.error("Manufacturer car model year list not available under wkda object.");
                    Assert.fail("No Data Available in WKDA Object");
                }
            }
        }
    }
}
