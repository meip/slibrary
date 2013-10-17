package ch.hsr.slibrary.gui.util;

/**
 * User: p1meier
 * Date: 17.10.13
 */
public class BookUtil {

    public static String shortBookName(String bookName) {
        int nWords = 5;
        StringBuilder builder = new StringBuilder();
        String[] s = bookName.split(" ");
        for (int i = 0; i < s.length && i < nWords; i++) {
            boolean isLastSubstring = (i == s.length - 1 || i == nWords - 1);
            builder.append(s[i] + ((isLastSubstring) ? "" : " "));
        }
        return builder.toString();
    }
}
