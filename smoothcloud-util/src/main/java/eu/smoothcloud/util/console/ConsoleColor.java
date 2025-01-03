/*
 * Copyright (c) 2024-2025 SmoothCloud team & contributors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.smoothcloud.util.console;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleColor {

    private static final Pattern GRADIENT_PATTERN = Pattern.compile("\\[([A-Fa-f0-9]{6})-([A-Fa-f0-9]{6})](.*?)(?=\\[|&|$)");
    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile("&([0-9A-Fa-fLl])");
    private static final Pattern HEX_PATTERN = Pattern.compile("\\[([A-Fa-f0-9]{6})](.*?)(?=\\[|&|$)");

    private static final String[] ANSI_COLORS = {
            "\033[30m", // &0 - Black
            "\033[34m", // &1 - Dark Blue
            "\033[32m", // &2 - Dark Green
            "\033[36m", // &3 - Dark Aqua
            "\033[31m", // &4 - Dark Red
            "\033[35m", // &5 - Dark Purple
            "\033[33m", // &6 - Gold
            "\033[37m", // &7 - Gray
            "\033[90m", // &8 - Dark Gray
            "\033[94m", // &9 - Blue
            "\033[92m", // &a - Green
            "\033[96m", // &b - Aqua
            "\033[91m", // &c - Red
            "\033[95m", // &d - Light Purple
            "\033[93m", // &e - Yellow
            "\033[97m", // &f - White
            "\033[0m"   // &r - Reset
    };

    public static String apply(String text) {
        text = applyGradients(text);
        text = applyHexColors(text);
        text = applyColorCodes(text);
        return text + "\033[0m";
    }

    private static String applyGradients(String text) {
        Matcher gradientMatcher = GRADIENT_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();

        while (gradientMatcher.find()) {
            String startColor = gradientMatcher.group(1);
            String endColor = gradientMatcher.group(2);
            String gradientText = gradientMatcher.group(3);

            String replacement = applyGradient(startColor, endColor, gradientText);
            gradientMatcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        gradientMatcher.appendTail(result);
        return result.toString();
    }

    private static String applyHexColors(String text) {
        Matcher hexMatcher = HEX_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();

        while (hexMatcher.find()) {
            String hexColor = hexMatcher.group(1);
            String hexText = hexMatcher.group(2);

            String replacement = rgb(hexColor) + hexText;
            hexMatcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        hexMatcher.appendTail(result);
        return result.toString();
    }

    private static String applyColorCodes(String text) {
        Matcher colorCodeMatcher = COLOR_CODE_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();

        while (colorCodeMatcher.find()) {
            char colorCode = Character.toLowerCase(colorCodeMatcher.group(1).charAt(0));
            int index = (colorCode >= '0' && colorCode <= '9') ? colorCode - '0' :
                    (colorCode >= 'a' && colorCode <= 'f') ? colorCode - 'a' + 10 :
                            16;

            String replacement = index >= 0 && index < ANSI_COLORS.length ? ANSI_COLORS[index] : "";
            colorCodeMatcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }

        colorCodeMatcher.appendTail(result);
        return result.toString();
    }

    public static String rgb(String hexColor) {
        int r = Integer.parseInt(hexColor.substring(0, 2), 16);
        int g = Integer.parseInt(hexColor.substring(2, 4), 16);
        int b = Integer.parseInt(hexColor.substring(4, 6), 16);
        return String.format("\033[38;2;%d;%d;%dm", r, g, b);
    }

    public static String gradient(String startColor, String endColor, String text) {
        return applyGradient(startColor, endColor, text);
    }

    private static String applyGradient(String startColor, String endColor, String text) {
        int length = text.length();
        StringBuilder result = new StringBuilder();

        int r1 = Integer.parseInt(startColor.substring(0, 2), 16);
        int g1 = Integer.parseInt(startColor.substring(2, 4), 16);
        int b1 = Integer.parseInt(startColor.substring(4, 6), 16);

        int r2 = Integer.parseInt(endColor.substring(0, 2), 16);
        int g2 = Integer.parseInt(endColor.substring(2, 4), 16);
        int b2 = Integer.parseInt(endColor.substring(4, 6), 16);

        for (int i = 0; i < length; i++) {
            double ratio = (double) i / (length - 1);
            int red = (int) (r1 + ratio * (r2 - r1));
            int green = (int) (g1 + ratio * (g2 - g1));
            int blue = (int) (b1 + ratio * (b2 - b1));

            result.append(String.format("\033[38;2;%d;%d;%dm", red, green, blue))
                    .append(text.charAt(i));
        }

        return result.toString() + "\033[0m";
    }
}
