package com.yuzhi.fine.Lg;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.util.Log;


/**
 * Created by xieye on 2017/3/8.
 */

public class LgImpl implements LgInterface {

    private static final int TRACE_METHOD_COUNT = 2;
    private static final int MIN_TRACK_OFFSET = 3;
    private static final int MIN_STACK_TRACE_SIZE = 131071; //128 KB - 1
    protected static int minimumLogLevel = Log.VERBOSE;
    protected static String packageName = "";
    protected static String tag = "";

    public LgImpl() {
        // do nothing, used by Lg before injection is set up
    }

    public static void init(Application context) {
        try {
            packageName = context.getPackageName();
            final int flags = context.getPackageManager().getApplicationInfo(packageName, 0).flags;
            minimumLogLevel = (flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0 ? Log.VERBOSE : Log.ASSERT;
            //tag = packageName.toLowerCase(Locale.US);

            Lg.d("Configuring Logging, minimum log level is %s", Lg.logLevelToString(minimumLogLevel));

        } catch (Exception e) {
            try {
                Lg.e(packageName, "Error configuring logger", e);
            } catch (RuntimeException f) { // NOPMD - Legal empty catch block
                // HACK ignore Stub! errors in mock objects during testing
            }
        }
    }

    @Override
    public int v(Throwable t) {
        return getLoggingLevel() <= Log.VERBOSE ? println(Log.VERBOSE, Log.getStackTraceString(t)) : 0;
    }

    @Override
    public int v(Object s1, Object... args) {
        if (getLoggingLevel() > Log.VERBOSE)
            return 0;

        final String s = Strings.toString(s1);
        final String message = formatArgs(s, args);
        return println(Log.VERBOSE, message);
    }

    @Override
    public int v(Throwable throwable, Object s1, Object[] args) {
        if (getLoggingLevel() > Log.VERBOSE)
            return 0;

        final String s = Strings.toString(s1);
        final String message = formatArgs(s, args) + '\n' + Log.getStackTraceString(throwable);
        return println(Log.VERBOSE, message);
    }

    @Override
    public int d(Throwable t) {
        return getLoggingLevel() <= Log.DEBUG ? println(Log.DEBUG, Log.getStackTraceString(t)) : 0;
    }

    @Override
    public int d(Object s1, Object... args) {
        if (getLoggingLevel() > Log.DEBUG)
            return 0;

        final String s = Strings.toString(s1);
        final String message = formatArgs(s, args);
        return println(Log.DEBUG, message);
    }

    @Override
    public int d(Throwable throwable, Object s1, Object... args) {
        if (getLoggingLevel() > Log.DEBUG)
            return 0;

        final String s = Strings.toString(s1);
        final String message = formatArgs(s, args) + '\n' + Log.getStackTraceString(throwable);
        return println(Log.DEBUG, message);
    }

    @Override
    public int i(Throwable t) {
        return getLoggingLevel() <= Log.INFO ? println(Log.INFO, Log.getStackTraceString(t)) : 0;
    }

    @Override
    public int i(Throwable throwable, Object s1, Object... args) {
        if (getLoggingLevel() > Log.INFO)
            return 0;

        final String s = Strings.toString(s1);
        final String message = formatArgs(s, args) + '\n' + Log.getStackTraceString(throwable);
        return println(Log.INFO, message);
    }

    @Override
    public int i(Object s1, Object... args) {
        if (getLoggingLevel() > Log.INFO)
            return 0;

        final String s = Strings.toString(s1);
        final String message = formatArgs(s, args);
        return println(Log.INFO, message);
    }

    @Override
    public int w(Throwable t) {
        return getLoggingLevel() <= Log.WARN ? println(Log.WARN, Log.getStackTraceString(t)) : 0;
    }

    @Override
    public int w(Throwable throwable, Object s1, Object... args) {
        if (getLoggingLevel() > Log.WARN)
            return 0;

        final String s = Strings.toString(s1);
        final String message = formatArgs(s, args) + '\n' + Log.getStackTraceString(throwable);
        return println(Log.WARN, message);
    }

    @Override
    public int w(Object s1, Object... args) {
        if (getLoggingLevel() > Log.WARN)
            return 0;

        final String s = Strings.toString(s1);
        final String message = formatArgs(s, args);
        return println(Log.WARN, message);
    }

    @Override
    public int e(Throwable t) {
        return getLoggingLevel() <= Log.ERROR ? println(Log.ERROR, Log.getStackTraceString(t)) : 0;
    }

    @Override
    public int e(Throwable throwable, Object s1, Object... args) {
        if (getLoggingLevel() > Log.ERROR)
            return 0;

        final String s = Strings.toString(s1);
        final String message = formatArgs(s, args) + '\n' + Log.getStackTraceString(throwable);
        return println(Log.ERROR, message);
    }

    @Override
    public int e(Object s1, Object... args) {
        if (getLoggingLevel() > Log.ERROR)
            return 0;

        final String s = Strings.toString(s1);
        final String message = formatArgs(s, args);
        return println(Log.ERROR, message);
    }

    @Override
    public boolean isDebugEnabled() {
        return getLoggingLevel() <= Log.DEBUG;
    }

    @Override
    public boolean isVerboseEnabled() {
        return getLoggingLevel() <= Log.VERBOSE;
    }

    @Override
    public String logLevelToString(int loglevel) {
        switch (loglevel) {
            case Log.VERBOSE:
                return "VERBOSE";
            case Log.DEBUG:
                return "DEBUG";
            case Log.INFO:
                return "INFO";
            case Log.WARN:
                return "WARN";
            case Log.ERROR:
                return "ERROR";
            case Log.ASSERT:
                return "ASSERT";

            default:
                return "UNKNOWN";
        }
    }

    @Override
    public String getTraceElement() {
        int methodCount = TRACE_METHOD_COUNT;

        StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(traces);

        StringBuilder builder = new StringBuilder();
        int stackIndex = methodCount + stackOffset;
        if (stackIndex < traces.length) {
            builder.append("(")
                    .append(traces[stackIndex].getFileName())
                    .append(":")
                    .append(traces[stackIndex].getLineNumber())
                    .append(")");
        }

        return builder.toString();
    }

    private int getStackOffset(StackTraceElement[] traces) {
        for (int i = MIN_TRACK_OFFSET; i < traces.length; i++) {
            StackTraceElement e = traces[i];
            String name = e.getClassName();
            if (!name.equals(LgImpl.class.getName())) {
                return --i;
            }
        }
        return -1;
    }


    @Override
    public int getLoggingLevel() {
        return minimumLogLevel;
    }

    @Override
    public void setLoggingLevel(int level) {
        minimumLogLevel = level;
    }

    public int println(int priority, String msg) {
        return Log.println(priority, getTag(), processMessage(msg));
    }

    protected String processMessage(String msg) {
        if (getLoggingLevel() <= Log.DEBUG)
            msg = msg + "";
        return msg;
    }

    protected String getTag() {
        if (getLoggingLevel() <= Log.DEBUG) {
            return tag + getTraceElement();
        }

        return tag;
    }

    //protected for testing.
    protected String formatArgs(final String s, Object... args) {
        //this is a bit tricky : if args is null, it is passed to formatting
        //(and yes this can still break depending on conversion of the formatter, see String.format)
        //else if there is no args, we return the message as-is, otherwise we pass args to formatting normally.
        if (args != null && args.length == 0) {
            return s;
        } else {
            return String.format(s, args);
        }
    }


}
