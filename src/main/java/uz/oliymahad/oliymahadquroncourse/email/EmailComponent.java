package uz.oliymahad.oliymahadquroncourse.email;

public abstract class EmailComponent {

    private static final String RECEIVER = "User";

    private final static String CONFIRMATION_EMAIL_TEMPLATE = "Dear [[name]],<br>"
            + "Please click the link below to verify your registration:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "Oliy Mahad - Qur'an and Tajvid course.";

    public static String confirmationEmailContentBuilder(final String username, final String link){
        return CONFIRMATION_EMAIL_TEMPLATE.replace("[[name]]", username).replace("[[URL]]", link);
    }

    public static String confirmationEmailContentBuilder(final String link){
        return CONFIRMATION_EMAIL_TEMPLATE.replace("[[name]]", RECEIVER).replace("[[URL]]", link);
    }

}
