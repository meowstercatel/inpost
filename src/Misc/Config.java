package Misc;

public class Config {
    private String refreshToken;
    private String accessToken;
    private String phonePrefix;
    private String phoneNumber;

    public Config() {}

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }
}
