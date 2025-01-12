package Authentication;

public class Reauthentication {
    private String authToken;
    private String reauthenticationRequired;
    private String pushIdStatus;


    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getReauthenticationRequired() {
        return reauthenticationRequired;
    }

    public void setReauthenticationRequired(String reauthenticationRequired) {
        this.reauthenticationRequired = reauthenticationRequired;
    }

    public String getPushIdStatus() {
        return pushIdStatus;
    }

    public void setPushIdStatus(String pushIdStatus) {
        this.pushIdStatus = pushIdStatus;
    }
}