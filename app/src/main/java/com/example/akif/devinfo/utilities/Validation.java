package com.example.akif.devinfo.utilities;

import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Pattern;

public class Validation {

    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "###-#######";

    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if (required && !hasText(editText)) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }
        ;

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }


    public static boolean isRequiredField(String strText) {
        return strText != null && !strText.trim().isEmpty();
    }

    public static int getIntFromString(String strText) {

        if (isRequiredField(strText)) {
            try {
                return Integer.parseInt(strText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;

    }

    public static double getDoubleFromString(String strText) {

        if (isRequiredField(strText)) {
            try {
                return Double.parseDouble(strText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;

    }


    public static boolean isEmail(String strEmailAddress) {
        return Pattern.compile(EMAIL_PATTERN).matcher(strEmailAddress).matches();
//        return Patterns.EMAIL_ADDRESS.matcher(strEmailAddress).matches();
    }

    public static boolean isAlphabetic(String strText) {
        return strText.matches("[a-zA-Z ]+");
    }

    public static boolean isMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public static boolean isCardNumber(String cardno) {
        if (isRequiredField(cardno) && (cardno.length() == 16)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCVCcode(String cvc) {
        if (isRequiredField(cvc) && (cvc.length() == 3 || cvc.length() == 4)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isWebUrl(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    public static boolean isPhoneNo(String phoneNo) {
        return Patterns.PHONE.matcher(phoneNo).matches();
    }


    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }
}
