import java.util.Scanner;

public class Main {
    static Inpost inpost = new Inpost();
    public static void parseArguments(String[] args) {
        for(int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--token":
                    inpost.setAccessToken("Bearer " + args[i+1]);
                    break;
                case "--package":
                    Parcel parcel = inpost.getParcel(args[i+1]);
                    System.out.println(parcel);
                    System.exit(0);
                    break;
                case "--phone":
                    String phonePrefix = args[i+1].substring(0, 3);
                    String phoneNumber = args[i+1].substring(3);
                    inpost.setPhonePrefix(phonePrefix);
                    inpost.setPhoneNumber(phoneNumber);
                    break;
                default:
                    break;
            }
        }
    }
    public static void main(String[] args) {
        parseArguments(args);
        if(inpost.isAccessTokenSet()) {
            inpost.listParcels();
            return;
        }
        if(inpost.getPhoneNumber() != null) {
            inpost.listParcels();
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