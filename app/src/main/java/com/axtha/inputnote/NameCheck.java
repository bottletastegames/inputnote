package com.axtha.inputnote;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by user on 2017-07-24.
 */

public class NameCheck {
    public enum Result {
        OK(0), REQUIRED(1), INVALID(2);
        private final int value;

        Result(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static Result Do(String str) {
        if (str == null || str.isEmpty()) {
            return Result.REQUIRED;
        }

        boolean result = true;
        String encoded = new String();
        try {
            encoded = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            result = false;
        }

        if (result == false) {
            return Result.INVALID;
        }

        encoded = encoded.replaceAll("\\+", "%20");
        char c;
        for (int i = 0; i < encoded.length(); ++i) {
            c = encoded.charAt(i);

            if (c == '%' || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                continue;
            }

            return Result.INVALID;
        }

        return Result.OK;
    }
}
