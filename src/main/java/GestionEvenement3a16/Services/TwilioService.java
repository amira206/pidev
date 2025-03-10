package GestionEvenement3a16.Services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioService {

    //private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    // private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    // These are your Twilio Account SID and Auth Token
    private static final String ACCOUNT_SID = "AC52149aef69c9bd900726b24259acad9b";
    private static final String AUTH_TOKEN = "69998e285c3877f52cab6a5aa8e76ca2";
    public void sendSms(String to, String from, String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber(to),  // Replace with your phone number
                new PhoneNumber(from),  // Replace with your Twilio number
                body)
            .create();

        System.out.println(message.getSid());
    }
}