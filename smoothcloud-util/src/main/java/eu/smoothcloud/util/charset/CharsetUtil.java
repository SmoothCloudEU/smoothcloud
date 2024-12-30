package eu.smoothcloud.util.charset;

public class CharsetUtil {
    public static boolean isValidInput(String input) {
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
        for (int i = 0; i < input.length(); i++) {
            if (charset.indexOf(input.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }
}
