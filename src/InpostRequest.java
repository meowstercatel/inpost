import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class InpostRequest {
    public int statusCode;
    public String body;

    InpostRequest(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }
}
