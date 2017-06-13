package com.yuzhi.fine.Lg;

/**
 * Created by xieye on 2017/3/8.
 */

public final class Lg {

    /**
     * A more natural android logging facility.
     *
     * WARNING: CHECK OUT COMMON PITFALLS BELOW
     *
     * Unlike {@link android.util.Log}, Log provides sensible defaults.
     * Debug and Verbose logging is enabled for applications that
     * have "android:debuggable=true" in their AndroidManifest.xml.
     * For apps built using SDK Tools r8 or later, this means any debug
     * build.  Release builds built with r8 or later will have verbose
     * and debug log messages turned off.
     *
     * The default tag is automatically set to your app's packagename,
     * and the current context (eg. activity, service, application, etc)
     * is appended as well.  You can add an additional parameter to the
     * tag using {@link #Log(String)}.
     *
     * Log-levels can be programatically overridden for specific instances
     * using {@link #Log(String, boolean, boolean)}.
     *
     * Log messages may optionally use {@link String#format(String, Object...)}
     * formatting, which will not be evaluated unless the log statement is output.
     * Additional parameters to the logging statement are treated as varrgs parameters
     * to {@link String#format(String, Object...)}
     *
     * Also, the current file and line is automatically appended to the tag
     * (this is only done if debug is enabled for performance reasons).
     *
     * COMMON PITFALLS:
     * * Make sure you put the exception FIRST in the call.  A common
     *   mistake is to place it last as is the android.util.Log convention,
     *   but then it will get treated as varargs parameter.
     * * vararg parameters are not appended to the log message!  You must
     *   insert them into the log message using %s or another similar
     *   format parameter
     *
     * Usage Examples:
     *
     * Ln.v("hello there");
     * Ln.d("%s %s", "hello", "there");
     * Ln.e( exception, "Error during some operation");
     * Ln.w( exception, "Error during %s operation", "some other");
     *
     *
     */


    /**
     * lgImpl is initially set to LgImpl() with sensible defaults, then replaced
     * by whatever binding you choose during guice static injection pass.
     */
    @SuppressWarnings(value = "MS_SHOULD_BE_FINAL")
    protected static LgInterface lgImpl = new LgImpl();
    /**
     * Drawing toolbox
     */
    public static final char TOP_LEFT_CORNER = '╔';
    public static final char BOTTOM_LEFT_CORNER = '╚';
    public static final char MIDDLE_CORNER = '╟';
    public static final char HORIZONTAL_DOUBLE_LINE = '║';
    public static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    public static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    public static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    public static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    public static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;


    private Lg() {
    }


    public static int v(Throwable t) {
        return lgImpl.v(t);
    }

    public static int v(Object s1, Object... args) {
        return lgImpl.v(s1, args);
    }

    public static int v(Throwable throwable, Object s1, Object... args) {
        return lgImpl.v(throwable, s1, args);
    }

    public static int d(Throwable t) {
        return lgImpl.d(t);
    }

    public static int d(Object s1, Object... args) {
        return lgImpl.d(s1, args);
    }

    public static int d(Throwable throwable, Object s1, Object... args) {
        return lgImpl.d(throwable, s1, args);
    }

    public static int i(Throwable t) {
        return lgImpl.i(t);
    }

    public static int i(Object s1, Object... args) {
        return lgImpl.i(s1, args);
    }

    public static int i(Throwable throwable, Object s1, Object... args) {
        return lgImpl.i(throwable, s1, args);
    }

    public static int w(Throwable t) {
        return lgImpl.w(t);
    }

    public static int w(Object s1, Object... args) {
        return lgImpl.w(s1, args);
    }

    public static int w(Throwable throwable, Object s1, Object... args) {
        return lgImpl.w(throwable, s1, args);
    }

    public static int e(Throwable t) {
        return lgImpl.e(t);
    }

    public static int e(Object s1, Object... args) {
        return lgImpl.e(s1, args);
    }

    public static int e(Throwable throwable, Object s1, Object... args) {
        return lgImpl.e(throwable, s1, args);
    }

    public static boolean isDebugEnabled() {
        return lgImpl.isDebugEnabled();
    }

    public static boolean isVerboseEnabled() {
        return lgImpl.isVerboseEnabled();
    }

    public static int getLoggingLevel() {
        return lgImpl.getLoggingLevel();
    }

    public static void setLoggingLevel(int level) {
        lgImpl.setLoggingLevel(level);
    }

    public static String logLevelToString(int loglevel) {
        return lgImpl.logLevelToString(loglevel);
    }
}
