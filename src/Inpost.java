import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Inpost {
    ObjectMapper mapper = new ObjectMapper();
    static HttpClient client = HttpClient.newHttpClient();

    public String phonePrefix;
    public String phoneNumber;
    protected String accessToken;
    protected String refreshToken;

    Inpost(String phonePrefix, String phoneNumber) {
        this.phonePrefix = phonePrefix;
        this.phoneNumber = phoneNumber;
    }
    Inpost(String accessToken) {
        this.accessToken = accessToken;
    }
    Parcel[] getParcels() {
        try {
            InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/v4/parcels/tracked");
            System.out.println(inpostRequest.body);
            TrackedParcels trackedParcels = mapper.readValue(inpostRequest.body, TrackedParcels.class);

            return trackedParcels.parcels;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    boolean sendSmsCode() {
        String data = """
{
  "phoneNumber": {
    "prefix": "%s",
    "value": "%s"
  }
}
                """.formatted(this.phonePrefix, this.phoneNumber);
        InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/v1/account", data);
        return inpostRequest.statusCode == 200;
    }

    boolean verifySmsCode(String code) {
        String data = """
{
  "phoneNumber": {
    "prefix": "%s",
    "value": "%s"
  },
  "smsCode": "%s",
  "devicePlatform": "Android"
}
                """.formatted(this.phonePrefix, this.phoneNumber, code);
        InpostRequest inpostRequest = request("https://api-inmobile-pl.easypack24.net/v1/account/verification", data);
        if(inpostRequest.statusCode == 200) {
            //grab access token
            try {
                SmsVerification smsVerification = mapper.readValue(inpostRequest.body, SmsVerification.class);
                this.accessToken = smsVerification.authToken;
                this.refreshToken = smsVerification.refreshToken;
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
        return inpostRequest.statusCode == 200;
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
//            System.out.println("status code: " + response.statusCode());
//            System.out.println("response body: " + response.body());

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
//            System.out.println("status code: " + response.statusCode());
//            System.out.println("response body: " + response.body());

            return new InpostRequest(response.statusCode(), response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void listParcels() {
        Parcel[] parcels = this.getParcels();
        for(Parcel parcel : parcels) {
            System.out.println(parcel);
        }
    }
}
