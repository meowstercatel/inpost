import Authentication.Reauthentication;
import Authentication.SmsVerification;
import Misc.Config;
import Misc.InCoins;
import Misc.ParcelInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Inpost {
    ObjectMapper mapper = new ObjectMapper();
    static HttpClient client = HttpClient.newHttpClient();

    void saveConfig() {
        Config config = new Config();
        config.setPhonePrefix(phonePrefix);
        config.setPhoneNumber(phoneNumber);
        config.setAccessToken(accessToken);
        config.setRefreshToken(refreshToken);

        try {
            mapper.writeValue(new File("config.json"), config);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config to config.json", e);
        }
    }

    boolean loadConfig() {
        try {
            Config config = mapper.readValue(new File("config.json"), Config.class);
            this.phonePrefix = config.getPhonePrefix();
            this.phoneNumber = config.getPhoneNumber();
            this.accessToken = config.getAccessToken();
            this.refreshToken = config.getRefreshToken();
            return true;
        } catch (IOException e) {
            return false;
//            throw new RuntimeException("Failed to load configuration from config.json", e);
        }
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    boolean isAccessTokenSet() {
        return this.accessToken != null;
    }

    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    private String phonePrefix;
    private String phoneNumber;

    public String getAccessToken() {
        return accessToken;
    }

    private String accessToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    private String refreshToken;

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    Inpost() {}

    Inpost(String phonePrefix, String phoneNumber) {
        this.phonePrefix = phonePrefix;
        this.phoneNumber = phoneNumber;
    }

    void reauthenticate(String refreshToken) {
        try {
            String data = "{\"refreshToken\": \"%s\",\"phoneOS\": \"Android\"}".formatted(refreshToken);

            InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/v1/authenticate", data);
            Reauthentication reauthentication = mapper.readValue(inpostRequest.body, Reauthentication.class);

            System.out.printf("access token: %s\n", reauthentication.getAuthToken());
            this.setAccessToken(reauthentication.getAuthToken());
            this.setRefreshToken(refreshToken);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    ForeignParcel getParcel(String shipmentNumber) {
        try {
            InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/v4/parcels/tracked");
            TrackedParcels trackedParcels = mapper.readValue(inpostRequest.body, TrackedParcels.class);

            //there's probably a built-in function for this
            for(ForeignParcel foreignParcel : trackedParcels.foreignParcels) {
                if(foreignParcel.getShipmentNumber().equals(shipmentNumber)) {
                    return foreignParcel;
                }
            }
            return new ForeignParcel();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    ForeignParcel[] getParcels() {
        try {
            InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/v4/parcels/tracked");
            System.out.println(inpostRequest.body);
            TrackedParcels trackedParcels = mapper.readValue(inpostRequest.body, TrackedParcels.class);

            return trackedParcels.foreignParcels;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    boolean sendSmsCode() {
        String data = "{\"phoneNumber\": {\"prefix\": \"%s\",\"value\": \"%s\"}}".formatted(this.phonePrefix, this.phoneNumber);

        InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/v1/account", data);
        return inpostRequest.statusCode == 200;
    }

    boolean verifySmsCode(String code) {
        String data = "{\"phoneNumber\": {\"prefix\": \"%s\",\"value\": \"%s\"},\"smsCode\": \"%s\",\"devicePlatform\": \"Android\"}".formatted(this.phonePrefix, this.phoneNumber, code);
        try {
            InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/v1/account/verification", data);
            SmsVerification smsVerification = mapper.readValue(inpostRequest.body, SmsVerification.class);
            this.setRefreshToken(smsVerification.refreshToken);
            this.setAccessToken(smsVerification.authToken);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    private void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getInCoins() {
        try {
            InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/loyalty/v1/wallet/balance");
            InCoins inCoins = mapper.readValue(inpostRequest.body, InCoins.class);
            return inCoins.getCurrentBalance();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ParcelInfo getParcelInfo() {
        try {
            InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/v2/prices/parcels?sourceCountryCode=PL");
            return mapper.readValue(inpostRequest.body, ParcelInfo.class);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ParcelInfo getParcelInfo(String countryCode) {
        try {
            InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/v2/prices/parcels?sourceCountryCode="+countryCode);
            return mapper.readValue(inpostRequest.body, ParcelInfo.class);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static InpostRequest request(String endpoint, String data) {
        System.out.println(data);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(endpoint))
                    .header("Content-Type", "application/json")
                    .header("User-Agent", "InPost-Mobile/3.32.0(33200002) (Android 15; Pixel 7 Pro; google cheetah; en)")
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new InpostRequest(response.statusCode(), response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public InpostRequest request(String endpoint) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(endpoint))
                    .header("Content-Type", "application/json")
                    .header("User-Agent", "InPost-Mobile/3.32.0(33200002) (Android 15; Pixel 7 Pro; google cheetah; en)")
                    .header("Authorization", this.accessToken)
                    .GET()
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return new InpostRequest(response.statusCode(), response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void listParcels() {
        ForeignParcel[] foreignParcels = this.getParcels();
        for(ForeignParcel foreignParcel : foreignParcels) {
            if(foreignParcel.getReceiver().getPhoneNumber().getValue().equals(this.phoneNumber)) {
                // ta definicja nie jest solidna, ale wiadomo o co chodzi
                // --> polimorfizm - traktowanie klas jako innych klas
                // a więc działa to też w 2 stronę i castowanie to polimorfizm
                PersonalParcel personalParcel = (PersonalParcel) foreignParcel;
                System.out.println(personalParcel);
            } else {
                System.out.println(foreignParcel);
            }
        }
    }
}
