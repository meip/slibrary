package ch.hsr.slibrary.util;

import ch.hsr.slibrary.gui.util.BookUtil;
import junit.framework.TestCase;

public class BookUtilTest extends TestCase {

    public void testShortBookName() {
        System.out.println(BookUtil.shortBookName("America Bizarro: A Guide to Freaky Festivals, Groovy Gatherings, Kooky Contests, and Other Strange Happenings Across the USA"));
        assertEquals(BookUtil.shortBookName("America Bizarro: A Guide to Freaky Festivals, Groovy Gatherings, Kooky Contests, and Other Strange Happenings Across the USA"), "America Bizarro: A Guide to");
    }
}