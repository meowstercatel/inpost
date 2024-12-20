import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class Main {
    public static void main(String[] args) {
        if(args.length != 0) {
            //assuming that the first one is the access token
            Inpost inpost = new Inpost("Bearer " + args[0]);
            inpost.listParcels();
            return;
        }
        Scanner scanner = new Scanner(System.in);

        System.out.print("enter your phone prefix (eg. +48): ");
        String phonePrefix = scanner.next();

        System.out.print("enter your phone number: ");
        String phoneNumber = scanner.next();

        Inpost inpost = new Inpost(phonePrefix, phoneNumber);
        if(!inpost.sendSmsCode()) {
            System.out.println("something went wrong when sending the sms code");
            return;
        }

        System.out.print("enter verification code: ");
        String verificationCode = scanner.next();

        if(!inpost.verifySmsCode(verificationCode)) {
            System.out.println("something went wrong when verifying the sms code");
            return;
        }

        System.out.println(inpost.refreshToken);
        System.out.println(inpost.accessToken);

        System.out.println("\n\n");
        inpost.listParcels();
    }

}