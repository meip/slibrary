package ch.hsr.slibrary;


import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Shelf;
import junit.framework.TestCase;

public class BookTest extends TestCase {

	public void testBookCreation() {
		Book b = new Book("The Definitive ANTLR Reference");
		b.setName("The Definitive ANTLR Reference");
		b.setAuthor("Terence Parr");
		b.setPublisher("The Pragmatic Programmers");
		b.setShelf(Shelf.A1);
		
		assertEquals("The Definitive ANTLR Reference", b.getName());
		b.setName("NewName");
		assertEquals("NewName", b.getName());
		assertEquals("Terence Parr", b.getAuthor());
		assertEquals("The Pragmatic Programmers", b.getPublisher());
		
	}
}
