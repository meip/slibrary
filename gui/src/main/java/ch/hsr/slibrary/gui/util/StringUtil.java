package ch.hsr.slibrary.gui.util;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 21.10.13
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {

    public static String trimToWordsWithMaxLength(String str, int words, int maxLength) {
        int nWords = words;
        StringBuilder builder = new StringBuilder();
        String[] s = str.split(" ");
        for (int i = 0; i < s.length && i < nWords; i++) {
            if(builder.length() + s[i].length() > maxLength) {
                builder.append("...");
                break;
            }

            boolean isLastSubstring = (i == s.length - 1 || i == nWords - 1 );
            builder.append(s[i] + ((isLastSubstring) ? "" : " "));
        }
        return builder.toString();
    }
}
