package rb.project.activefox.utilserivces.message;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageUtil {
    public static String getMessage(String message, Language language, MessageSource messageSource) {
        return messageSource.getMessage(message,new Object[0],new Locale(language.name()));
    }
    public static String getMessage(String message, Language language, MessageSource messageSource,Object... args) {
        return messageSource.getMessage(message,args,new Locale(language.name()));
    }
}
