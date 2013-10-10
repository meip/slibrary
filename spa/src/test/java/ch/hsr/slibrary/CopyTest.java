package ch.hsr.slibrary;

import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Copy;
import junit.framework.TestCase;

public class CopyTest extends TestCase {

	public void testBook() {
		Book t = new Book("Design Pattern");
		Copy c1 = new Copy(t);
		assertEquals(Copy.nextInventoryNumber -1, c1.getInventoryNumber());
		Copy c2 = new Copy(t);
		assertEquals(Copy.nextInventoryNumber -1, c2.getInventoryNumber());
		assertEquals(Copy.Condition.NEW, c2.getCondition());
		
		c1.setCondition(Copy.Condition.DAMAGED);
		
		assertEquals(Copy.Condition.DAMAGED, c1.getCondition());
	}


}
