package com.mobile.place.common;

import java.text.MessageFormat;

public class LogFormat {
    public static final String LOGGER_OK_PATTERN = "op={}, status=OK, desc={}";
    public static final String LOGGER_KO_PATTERN = "op={}, status=KO, desc={}";
    public static final String LOGGER_KO_EXCEPTION_PATTERN = "op={0}, status=KO, desc={1}, exception={2}";
    public static final String MDC_SESSIONID = "sessionId";

    public static String error(final String logOpInfo, final String message, final Exception exception) {
        return MessageFormat.format(LOGGER_KO_EXCEPTION_PATTERN, logOpInfo, message, (null == exception.getMessage() ? exception.getMessage() : exception.getLocalizedMessage()));
    }
}
