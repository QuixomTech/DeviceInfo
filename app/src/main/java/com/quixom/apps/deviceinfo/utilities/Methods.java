package com.quixom.apps.deviceinfo.utilities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.FontRequest;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.quixom.apps.deviceinfo.MainActivity;
import com.quixom.apps.deviceinfo.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by mind on 30/03/17.
 */

public class Methods {

    static MainActivity mActivity;

    public Methods(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * Show Keyboard..
     *
     * @param activity: An activity object.
     */
    public static void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * Hidekeyboard.
     *
     * @param mActivity: Main activity object.
     */
    public static void hideKeyboard(Activity mActivity) {
        if (mActivity != null) {
            mActivity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
            View view = mActivity.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /***
     * To prevent from double clicking the row item and so prevents overlapping fragment.
     * **/
    public static void avoidDoubleClicks(final View view) {
        final long DELAY_IN_MS = 500;
        if (!view.isClickable()) {
            return;
        }
        view.setClickable(false);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setClickable(true);
            }
        }, DELAY_IN_MS);
    }

    /**
     * @param mainActivity use for get applicationContext
     * @param dp           value to convert into px
     * @return converted px from dp value
     */
    public static int dpToPx(MainActivity mainActivity, int dp) {
        DisplayMetrics displayMetrics = mainActivity.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * @param mainActivity use for get applicationContext
     * @param px           value to convert into dp
     * @return converted dp from px value
     */
    public static int pxToDp(MainActivity mainActivity, int px) {
        DisplayMetrics displayMetrics = mainActivity.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Share using intent.
     *
     * @param message: message
     */
    public static void sharing(String message) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        mActivity.startActivity(Intent.createChooser(sendIntent,  "Sharing"));
    }

    /**
     * Convert time into millisecond.
     *
     * @param givenDateString: time which need to convert.
     * @return: time in millisecond.
     */
    public static long convertTomillisecond(String givenDateString) {
        long timeInMilliseconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    /**
     * Change format of date.
     *
     * @param dateTime:   notification date which is converted to local from GMT.
     * @param newFormate: Need to convert format.
     * @return: date with format.
     */
    private static String getDateFormate(String dateTime, String newFormate) {
        DateFormat inputFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat outputFormate = new SimpleDateFormat(newFormate + " 'at' " + "hh:mm aa");
        try {
            Date date = inputFormate.parse(dateTime);
            String outputDateWithFormate = outputFormate.format(date);
            return outputDateWithFormate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Convert GMT to local.
     *
     * @param mDate: notification created date.
     * @return: date which is converted in local.
     */
    public static String getDateFormateGMTToLocal(final String mDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        String str;
        try {
            inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = inputFormat.parse(mDate);
            outputFormat.setTimeZone(TimeZone.getDefault());
            str = outputFormat.format(date);
            System.out.println("str = " + str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDate(long timeStamp) {

        try {
            @SuppressLint("SimpleDateFormat") DateFormat sdf = new SimpleDateFormat(KeyUtil.datePattern);
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }

    /**
     * Set string with spannable.
     *
     * @param first:  string
     * @param second: string
     * @return: string with two different color
     */
    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.O)
    public static SpannableStringBuilder getSpannableString(Context context, String first, String second) {

        Typeface font1 = mActivity.getResources().getFont(R.font.lato_light);
        Typeface font2 = mActivity.getResources().getFont(R.font.lato_regular);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString dkgraySpannable = new SpannableString(first + " ");
        dkgraySpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mActivity, R.color.primary_text)), 0, first.length(), 0);
        dkgraySpannable.setSpan(new CustomTypefaceSpan("", font1), 0, second.length(), 0);
        builder.append(dkgraySpannable);

        SpannableString blackSpannable = new SpannableString(second);
        blackSpannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mActivity, R.color.font_white)), 0, second.length(), 0);
        blackSpannable.setSpan(new CustomTypefaceSpan("", font2), 0, second.length(), 0);
        builder.append(blackSpannable);

        return builder;
    }


    /**
     * Set string with spannable.
     *
     * @return: string with two different color
     */
    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.O)
    public static SpannableStringBuilder getSpannableSensorText(Context context, String text) {


        String[] result = text.split("\n\n");
        String first = result[0];
        String second = result[1];

        Typeface font1 = mActivity.getResources().getFont(R.font.lato_light);
        Typeface font2 = mActivity.getResources().getFont(R.font.lato_regular);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString dkgraySpannable = new SpannableString(first+"\n\n" + " ");
        dkgraySpannable.setSpan(new CustomTypefaceSpan("", font1), 0, second.length(), 0);
        builder.append(dkgraySpannable);

        SpannableString blackSpannable = new SpannableString(second);
        blackSpannable.setSpan(new CustomTypefaceSpan("", font2), 0, second.length(), 0);
        builder.append(blackSpannable);

        return builder;
    }

    /**
     * Set string with spannable.
     *
     * @return: string with two different color
     */
    public static SpannableStringBuilder getSpannablePriceText(Context context, String text) {

        String[] result = text.split(":");
        String first = result[0];
        String second = result[1];
        first = first.concat(":");

//        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/GILROY-SEMIBOLD.OTF");

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString dkgraySpannable = new SpannableString(first + "");
        builder.append(dkgraySpannable);

        SpannableString blackSpannable = new SpannableString(second);
//        blackSpannable.setSpan(new CustomTypefaceSpan("", font), 0, second.length(), 0);
        builder.append(blackSpannable);

        return builder;
    }

    /**
     * Set string with spannable.
     *
     * @return: string with two different color
     */
    public static SpannableStringBuilder getSpannablePriceNewText(Context context, String text) {

        String[] result = text.split(":");
        String first = result[0];
        String second = result[1];
        first = first.concat(":");

//        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString dkgraySpannable = new SpannableString(first + "");
        builder.append(dkgraySpannable);

        SpannableString blackSpannable = new SpannableString(second);
//        blackSpannable.setSpan(new CustomTypefaceSpan("", font), 0, second.length(), 0);
        builder.append(blackSpannable);

        return builder;
    }

    /**
     * Set string with spannable.
     */
    public static SpannableStringBuilder getSpannablePriceTotalText(Context context, String text, String fontPath, boolean setFontBigger) {

        String[] result = text.split("=");
        String first = result[0];
        String second = result[1];
        first = first.concat("=");

//        Typeface font = Typeface.createFromAsset(context.getAssets(), fontPath);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString dkgraySpannable = new SpannableString(first + "");
        builder.append(dkgraySpannable);

        SpannableString blackSpannable = new SpannableString(second);
        if (setFontBigger) {
            blackSpannable.setSpan(new RelativeSizeSpan(1.3f), 0, second.length(), 0); // set size
        }
//        blackSpannable.setSpan(new CustomTypefaceSpan("", font), 0, second.length(), 0); // font weight
        builder.append(blackSpannable);

        return builder;
    }

    /*** Meaning of the constants
     Dv: Absolute humidity in grams/meter3
     m: Mass constant
     Tn: Temperature constant
     Ta: Temperature constant
     Rh: Actual relative humidity in percent (%) from phone’s sensor
     Tc: Current temperature in degrees C from phone’ sensor
     A: Pressure constant in hP
     K: Temperature constant for converting to kelvin
     */
    public static float calculateAbsoluteHumidity(float temperature, float relativeHumidity) {
        float Dv = 0;
        float m = 17.62f;
        float Tn = 243.12f;
        float Ta = 216.7f;
        float Rh = relativeHumidity;
        float Tc = temperature;
        float A = 6.112f;
        float K = 273.15f;

        Dv = (float) (Ta * (Rh / 100) * A * Math.exp(m * Tc / (Tn + Tc)) / (K + Tc));

        return Dv;
    }

    /*** Meaning of the constants
     Td: Dew point temperature in degrees Celsius
     m: Mass constant
     Tn: Temperature constant
     Rh: Actual relative humidity in percent (%) from phone’s sensor
     Tc: Current temperature in degrees C from phone’ sensor
     */
    public static float calculateDewPoint(float temperature, float relativeHumidity) {
        float Td = 0;
        float m = 17.62f;
        float Tn = 243.12f;
        float Rh = relativeHumidity;
        float Tc = temperature;

        Td = (float) (Tn * ((Math.log(Rh / 100) + m * Tc / (Tn + Tc)) / (m - (Math.log(Rh / 100) + m * Tc / (Tn + Tc)))));

        return Td;
    }

    public static boolean isNetworkConnected(MainActivity mActivity) {
        ConnectivityManager cm = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static String isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return mActivity.getResources().getString(R.string.wifi);
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return mActivity.getResources().getString(R.string.network);
            }
        } else {
            return mActivity.getResources().getString(R.string.unavailable);
        }
        return "";
    }

    /**
     * Returns MAC address of the given interface name.
     *
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) return "";
                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) buf.append(String.format("%02X:", aMac));
                if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static int calculatePercentage(double value, double total) {
        double usage = (int) ((value * 100.0f) / total);
        return (int) usage;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, Drawable draw) {
        Drawable drawable = draw;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static HashSet<String> getExternalMounts() {
        final HashSet<String> out = new HashSet<String>();
        String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
        String s = "";
        try {
            final Process process = new ProcessBuilder().command("mount")
                    .redirectErrorStream(true).start();
            process.waitFor();
            final InputStream is = process.getInputStream();
            final byte[] buffer = new byte[1024];
            while (is.read(buffer) != -1) {
                s = s + new String(buffer);
            }
            is.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        // parse output
        final String[] lines = s.split("\n");
        for (String line : lines) {
            if (!line.toLowerCase(Locale.US).contains("asec")) {
                if (line.matches(reg)) {
                    String[] parts = line.split(" ");
                    for (String part : parts) {
                        if (part.startsWith("/"))
                            if (!part.toLowerCase(Locale.US).contains("vold"))
                                out.add(part);
                    }
                }
            }
        }
        return out;
    }

    /**
     * Get max cpu rate.
     *
     * This works by examining the list of CPU frequencies in the pseudo file
     * "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state" and how much time has been spent
     * in each. It finds the highest non-zero time and assumes that is the maximum frequency (note
     * that sometimes frequencies higher than that which was designed can be reported.) So it is not
     * impossible that this method will return an incorrect CPU frequency.
     *
     * Also note that (obviously) this will not reflect different CPU cores with different
     * maximum speeds.
     *
     * @return cpu frequency in MHz
     */
    public static int getMaxCPUFreqMHz() {

        int maxFreq = -1;
        try {

            RandomAccessFile reader = new RandomAccessFile( "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state", "r" );

            boolean done = false;
            while ( ! done ) {
                String line = reader.readLine();
                if ( null == line ) {
                    done = true;
                    break;
                }
                String[] splits = line.split( "\\s+" );
                assert ( splits.length == 2 );

                int timeInState = Integer.parseInt( splits[1] );
                if ( timeInState > 0 ) {
                    int freq = Integer.parseInt( splits[0] ) / 1000;
                    System.out.println("maxFreq = " + freq);
                    if ( freq > maxFreq ) {
                        maxFreq = freq;
                    }
                }
            }

        } catch ( IOException ex ) {
            ex.printStackTrace();
        }

        return maxFreq;
    }
}
