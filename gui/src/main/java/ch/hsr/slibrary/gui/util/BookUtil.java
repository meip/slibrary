package ch.hsr.slibrary.gui.util;

/**
 * User: p1meier
 * Date: 17.10.13
 */
public class BookUtil {

    public static String shortBookName(String bookName) {
        return StringUtil.trimToWordsWithMaxLength(bookName, 5, 25);
    }
}
