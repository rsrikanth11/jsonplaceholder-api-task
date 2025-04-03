package com.api.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Utility class for handling localized messages.
 */
public class Messages {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("messages");

    /**
     * Get a message from the properties file and format it with parameters.
     * 
     * @param key The message key.
     * @param params The parameters to format the message.
     * @return The formatted message.
     */
    public static String getMessage(String key, Object... params) {
        return MessageFormat.format(BUNDLE.getString(key), params);
    }
}
