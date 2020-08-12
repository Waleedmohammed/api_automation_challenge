package pojo.in;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonRequest {
    String manufacturer;
    @JsonProperty("main-types")
    String mainTypes;
    @JsonProperty("built-dates")
    String builtDates;

    public JsonRequest() {

    }

    public JsonRequest(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public JsonRequest(String manufacturer, String mainTypes) {
        this.manufacturer = manufacturer;
        this.mainTypes = mainTypes;
    }

    public JsonRequest(String manufacturer, String mainTypes, String builtDates) {
        this.manufacturer = manufacturer;
        this.mainTypes = mainTypes;
        this.builtDates = builtDates;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMainTypes() {
        return mainTypes;
    }

    public void setMainTypes(String mainTypes) {
        this.mainTypes = mainTypes;
    }

    public String getBuiltDates() {
        return builtDates;
    }

    public void setBuiltDates(String builtDates) {
        this.builtDates = builtDates;
    }
}
