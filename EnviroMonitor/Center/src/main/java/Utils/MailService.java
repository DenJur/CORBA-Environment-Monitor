package Utils;

import Models.CenterToRecipientRecord;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.email.EmailPopulatingBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import java.util.List;

/**
 * Service used to send emails notifications about alarming readings
 */
public class MailService {

    private final Mailer mailer;
    private List<CenterToRecipientRecord> notificationRecords;

    public MailService(List<CenterToRecipientRecord> records) {
        mailer = MailerBuilder
                .withSMTPServer("localhost", 25, "", "")
                .withTransportStrategy(TransportStrategy.SMTP)
                .withSessionTimeout(10 * 1000)
                .buildMailer();
        notificationRecords = records;
    }

    /**
     * Send an email about an alarm in specific region
     * @param region - name of the regional centre where alarm was triggered
     */
    public void sendAlarmEmail(String region) {
        EmailPopulatingBuilder email = EmailBuilder.startingBlank()
                .from("Enviro.co.uk", "alarm@enviro.co.uk")
                .withSubject("Alarm")
                .withHTMLText("<h3><font color=\"red\">Attention!</font></h3>\n" +
                        "<p>Dangerous reading was detected in " + region + " region.</p>")
                .withPlainText("Dangerous reading was detected in \" + region + \" region.")
                .withHeader("X-Priority", 1);
        notificationRecords.stream().filter(x -> x.getCenterName().equals(region)).forEach(x ->
                email.bcc(x.getRecipientName(), x.getRecipientEmail()));
        mailer.sendMail(email.buildEmail());
    }
}
