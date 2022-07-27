package uz.oliymahad.oliymahadquroncourse.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.oliymahad.oliymahadquroncourse.entity.User;
import uz.oliymahad.oliymahadquroncourse.security.confirmatoin_token.ConfirmationTokenProvider;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static uz.oliymahad.oliymahadquroncourse.controller.core.ControllerUtils.AUTH_URI;
import static uz.oliymahad.oliymahadquroncourse.email.EmailSenderConfig.USERNAME;


@Service
public class EmailService {
    private static final String CONFIRMATION_EMAIL = "Verify email";

    private static final String HOST = "http://localhost:";

    private static final String PORT = "8080";

    public static final String REQUEST_PARAM_EMAIL_CONFIRMATION = "/email-confirmation";
    public static final String CONFIRM_LINK =HOST + PORT + AUTH_URI + REQUEST_PARAM_EMAIL_CONFIRMATION +"?token=";

    private static final String REGISTRATION_ID = "&registration-id=";

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender javaMailSender ;

    private final ConfirmationTokenProvider confirmationTokenProvider;

    private static final String FROM_EMAIL = USERNAME;

    public EmailService(JavaMailSender javaMailSender, ConfirmationTokenProvider confirmationTokenProvider) {
        this.javaMailSender = javaMailSender;
        this.confirmationTokenProvider = confirmationTokenProvider;
    }


    @Async
    public void sendMessage(final String toEmail, final String subject, final String message) throws MessagingException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(FROM_EMAIL);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(message);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException exception) {
            logger.error(exception.getMessage());
//            throw new SendFailedException();
        }
    }

    @Async
    public void sendConfirmationMessage(final String toEmail, final User user) {

        final String link = CONFIRM_LINK +
                confirmationTokenProvider.create(user).getToken() +
                REGISTRATION_ID +
                user.getId();

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setSubject(CONFIRMATION_EMAIL);
            helper.setText(
                    user.getUsername() == null ?
                            EmailComponent.confirmationEmailContentBuilder(link) :
                            EmailComponent.confirmationEmailContentBuilder(user.getUsername(), link),
                    true);
            helper.setFrom(FROM_EMAIL);
            helper.setTo(toEmail);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException exception) {
            logger.error(exception.getMessage());
//            throw new RuntimeException();
        }
    }



}
