package utilities;

public class CommonUtils {

    public static String getEndpoint(String param) {
        String endPointUrl = "";
        switch (param.toLowerCase()) {
            case "manufacturer":
                endPointUrl = Constants.MANUFACTURERENDPOINT;
                break;
            case "main-types":
                endPointUrl = Constants.MAINTYPEENDPOINT;
                break;
            case "built-dates":
                endPointUrl = Constants.BUILTDATESENDPOINT;
                break;
        }
        return endPointUrl;
    }
}
